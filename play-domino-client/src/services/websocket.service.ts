import {Client, IMessage} from '@stomp/stompjs';
import {getValidTokenFromStorage} from "../utils/tokenUtils.ts";

class WebSocketService {
    private client: Client;
    private isActive: boolean = false;
    private _isConnected: boolean = false;
    private _isReconnecting: boolean = false;

    constructor() {
        const wsUrl: string = `${import.meta.env.VITE_WS_URL}/ws`;
        this.client = new Client({
            brokerURL: wsUrl,
            beforeConnect: (client): void => {
                const token = getValidTokenFromStorage('authToken');
                client.connectHeaders = {
                    Authorization: `Bearer ${token}`,
                };
            },
            reconnectDelay: 5000,
            heartbeatIncoming: 10000, // ✅ Heartbeat otimizado
            heartbeatOutgoing: 10000,
        });

        this.client.onConnect = () => {
            this.isActive = true;
            this._isConnected = true;
            this._isReconnecting = false;
        };

        this.client.onDisconnect = () => {
            this.isActive = false;
            this._isConnected = false;
        };

        this.client.onStompError = (frame) => {
            console.error('[WebSocket] 🛑 Erro STOMP:', frame.headers['message'], frame.body);
        };

        this.client.onWebSocketClose = () => {
            this._isReconnecting = true;
        };

        this.client.onUnhandledFrame = (frame) => {
            console.error('[Websocket] <UNK> Erro STOMP:', frame.headers['message'], frame.body);
        }

        this.client.onUnhandledReceipt = (frame) => {
            console.error('[WebSocket] <UNK> Erro STOMP:', frame.headers['message']);
        }

        this.client.onUnhandledMessage = (frame) => {
            console.error('[WebSocket] <UNK> Erro STOMP:', frame.headers['message']);
        }
    }

    connect() {
        if (!this.isActive) {
            this.client.activate();
        }
    }

    disconnect() {
        if (this.isActive) {
            void this.client.deactivate();
        }
    }

    subscribe(topic: string, callback: (message: IMessage) => void) {
        if (!this.isActive) {
            console.warn('[WebSocket] Tentativa de subscrever sem conexão');
        }
        return this.client.subscribe(topic, callback);
    }

    send(destination: string, body: unknown) {
        if (this.isActive) {
            this.client.publish({destination, body: JSON.stringify(body)});
        } else {
            console.warn('[WebSocket] Tentativa de enviar sem conexão');
        }
    }

    get isConnected() {
        return this._isConnected;
    }

    get isReconnecting() {
        return this._isReconnecting;
    }
}

const webSocketService = new WebSocketService();

window.addEventListener('beforeunload', () => {
    webSocketService.disconnect();
});

export default webSocketService;
