package org.playdomino.configuration;

import lombok.RequiredArgsConstructor;
import org.playdomino.configuration.properties.WebSocketProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

import java.util.List;

@Order(Ordered.HIGHEST_PRECEDENCE + 200)
@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    public static final int MAX_WORKERS_COUNT = Math.max(2, Runtime.getRuntime().availableProcessors());
    public static final int TASK_QUEUE_SIZE = 10_000;

    private static final String[] APP_PREFIXES = new String[]{"/app", "/exchange"};
    public static final String[] BROKER_PREFIXES = new String[]{"/queue", "/topic", "/exchange"};
    private final WebSocketProperties webSocketProperties;
    private final List<ChannelInterceptor> channelInterceptorList;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").setAllowedOrigins("*");
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setPreservePublishOrder(true)
                .setApplicationDestinationPrefixes(APP_PREFIXES)
                .enableStompBrokerRelay(BROKER_PREFIXES)
                .setRelayHost(webSocketProperties.getRelayHost())
                .setRelayPort(webSocketProperties.getRelayPort())
                .setSystemLogin(webSocketProperties.getUser())
                .setSystemPasscode(webSocketProperties.getPassword())
                .setClientLogin(webSocketProperties.getUser())
                .setClientPasscode(webSocketProperties.getPassword())
                .setUserDestinationBroadcast("/topic/unresolved-user")
                .setUserRegistryBroadcast("/topic/user-registry");


        registry.configureBrokerChannel()
                .taskExecutor().corePoolSize(1).maxPoolSize(MAX_WORKERS_COUNT).queueCapacity(TASK_QUEUE_SIZE);
    }


    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration
                .taskExecutor()
                .corePoolSize(1)
                .maxPoolSize(MAX_WORKERS_COUNT)
                .queueCapacity(TASK_QUEUE_SIZE);
        registration.interceptors(channelInterceptorList.toArray(new ChannelInterceptor[]{}));

    }

    @Override
    public void configureClientOutboundChannel(ChannelRegistration registration) {
        registration.taskExecutor().corePoolSize(1).maxPoolSize(MAX_WORKERS_COUNT).queueCapacity(TASK_QUEUE_SIZE);
    }


    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registry) {
        registry.setMessageSizeLimit(4 * 8192);
        registry.setTimeToFirstMessage(30000);
    }
}
