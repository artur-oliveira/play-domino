package org.playdomino.components.websocket;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.playdomino.services.ws.WebSocketTopicValidator;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;


@Component
@Order(1)
@RequiredArgsConstructor
public class WebSocketSubscribeInterceptor implements ChannelInterceptor {

    private final List<WebSocketTopicValidator> topicValidatorList;

    @Override
    public Message<?> preSend(@Nonnull Message<?> message, @Nonnull MessageChannel channel) {
        final StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (Objects.nonNull(accessor) && StompCommand.SUBSCRIBE.equals(accessor.getCommand()) && Objects.nonNull(accessor.getDestination())) {
            topicValidatorList.forEach(it -> it.validate(accessor));
        }

        return message;
    }
}
