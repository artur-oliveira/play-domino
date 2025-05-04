import {useContext, useEffect} from "react";
import {WebSocketContext, WebSocketContextType, WebSocketNotification} from "./WebSocketContext.tsx";
import {IMessage} from "@stomp/stompjs";

export const useWebSocketContext = (): WebSocketContextType => {
    const context = useContext(WebSocketContext);
    if (!context) {
        throw new Error('useWebSocketContext deve ser usado dentro do WebSocketProvider');
    }
    return context;
};

export const useWebSocket = (topic: string, parser?: (msg: IMessage) => WebSocketNotification) => {
    const {subscribeToTopic, unsubscribeFromTopic, messages} = useWebSocketContext();

    useEffect(() => {
        subscribeToTopic(topic, parser);

        return () => {
            unsubscribeFromTopic(topic);
        }
    }, [topic, parser, subscribeToTopic, unsubscribeFromTopic]);

    return messages[topic] || [];
}
