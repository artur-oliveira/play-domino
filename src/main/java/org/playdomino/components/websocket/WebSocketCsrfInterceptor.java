package org.playdomino.components.websocket;

import org.springframework.core.annotation.Order;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@Component("csrfChannelInterceptor")
@Order(3)
public class WebSocketCsrfInterceptor implements ChannelInterceptor {

}
