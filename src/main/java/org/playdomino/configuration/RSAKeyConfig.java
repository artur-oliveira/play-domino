package org.playdomino.configuration;

import lombok.RequiredArgsConstructor;
import org.playdomino.configuration.properties.RSAKeyProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Configuration
@RequiredArgsConstructor
public class RSAKeyConfig {
    private final RSAKeyProperties properties;

    @Bean
    public RSAPrivateKey rsaPrivateKey() throws Exception {
        byte[] decoded = Base64.getDecoder().decode(properties.getPrivateKey());
        String pem = new String(decoded, StandardCharsets.UTF_8);

        String key = pem
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s+", "");

        byte[] keyBytes = Base64.getDecoder().decode(key);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        return (RSAPrivateKey) keyFactory.generatePrivate(new PKCS8EncodedKeySpec(keyBytes));
    }

    @Bean
    public RSAPublicKey rsaPublicKey() throws Exception {
        byte[] decoded = Base64.getDecoder().decode(properties.getPublicKey());
        String pem = new String(decoded, StandardCharsets.UTF_8);

        String key = pem
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s+", "");

        byte[] keyBytes = Base64.getDecoder().decode(key);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        return (RSAPublicKey) keyFactory.generatePublic(new X509EncodedKeySpec(keyBytes));
    }
}
