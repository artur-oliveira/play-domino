package org.playdomino.configuration.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties("org.playdomino.auth")
public class RSAKeyProperties {
    private String privateKey;
    private String publicKey;
}
