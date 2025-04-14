package org.playdomino.configuration.properties;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@NoArgsConstructor
@Configuration
@ConfigurationProperties("org.playdomino.auth")
public class AuthProperties {
    private String secretAccess;
    private String secretRefresh;
}
