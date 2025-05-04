package org.playdomino.configuration.properties;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@NoArgsConstructor
@Configuration
@ConfigurationProperties("org.playdomino.websocket")
public class WebSocketProperties {
    private String relayHost;
    private Integer relayPort;
    private String user;
    private String password;
}
