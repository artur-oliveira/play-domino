import {createContext} from 'react';
import {IMessage} from "@stomp/stompjs";

export type WebSocketSend = {
    type: never;
    data: unknown;
}

export type SupportedMessageType = (
    'transaction.deposit.deposit' |
    'transaction.deposit.withdraw'
);

export type WebSocketNotification = {
    messageId: string | number;
    messageType: SupportedMessageType;
    message: string;
    data: unknown;
}

export type WebSocketContextType = {
    messages: Record<string, WebSocketNotification[]>;
    sendMessage: (destination: string, payload: WebSocketSend) => void;
    subscribeToTopic: (topic: string, parser?: (msg: IMessage) => WebSocketNotification) => void;
    unsubscribeFromTopic: (topic: string) => void;
    isConnected: boolean;
    isReconnecting: boolean;
};

export const WebSocketContext = createContext<WebSocketContextType | undefined>(undefined);


