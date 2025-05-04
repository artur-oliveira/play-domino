import {ReactNode, useEffect, useRef, useState} from "react";
import {IMessage, StompSubscription} from '@stomp/stompjs';
import webSocketService from "../../services/websocket.service.ts";
import {WebSocketContext, WebSocketNotification, WebSocketSend} from "./WebSocketContext.tsx";

export const WebSocketProvider = ({children}: { children: ReactNode }) => {
    const [messages, setMessages] = useState<Record<string, WebSocketNotification[]>>({});
    const [isConnected, setIsConnected] = useState(webSocketService.isConnected);
    const [isReconnecting, setIsReconnecting] = useState(webSocketService.isReconnecting);
    const subscriptionsRef = useRef<Record<string, StompSubscription>>({});

    useEffect(() => {
        webSocketService.connect();

        const interval = setInterval(() => {
            setIsConnected(webSocketService.isConnected);
            setIsReconnecting(webSocketService.isReconnecting);
        }, 1000);

        return () => {
            // ✅ Cancela todas as subscrições ao desmontar
            Object.values(subscriptionsRef.current).forEach((sub) => {
                try {
                    sub.unsubscribe();
                } catch (e) {
                    console.error(e);
                }
            });
            subscriptionsRef.current = {};
            clearInterval(interval);
            webSocketService.disconnect();
        };
    }, []);

    const subscribeToTopic = (topic: string, parser?: (msg: IMessage) => WebSocketNotification) => {
        if (!webSocketService.isConnected) {
            return;
        }

        // ✅ Se já está subscrito, não subscreve de novo
        if (subscriptionsRef.current[topic]) {
            return;
        }

        subscriptionsRef.current[topic] = webSocketService.subscribe(topic, (message: IMessage) => {
            let parsed;
            try {
                parsed = parser ? parser(message) : JSON.parse(message.body);
            } catch (err) {
                console.error('[WebSocket] Erro ao parsear mensagem:', err);
                return;
            }

            setMessages((prev) => ({
                ...prev,
                [topic]: [...(prev[topic] || []), parsed],
            }));
        });
    };

    const unsubscribeFromTopic = (topic: string) => {
        const subscription = subscriptionsRef.current[topic];
        if (subscription) {
            subscription.unsubscribe();
            delete subscriptionsRef.current[topic];
        }
    };

    const sendMessage = (destination: string, payload: WebSocketSend) => {
        webSocketService.send(destination, payload);
    };

    return (
        <WebSocketContext.Provider
            value={{
                messages,
                sendMessage,
                subscribeToTopic,
                unsubscribeFromTopic,
                isConnected,
                isReconnecting
            }}
        >
            {children}
        </WebSocketContext.Provider>
    );
};
