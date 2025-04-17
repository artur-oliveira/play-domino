package org.playdomino;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.playdomino.repositories.auth.UserRepository;
import org.playdomino.services.auth.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        classes = DominoApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties"
)
public class GenericIntegrationTest {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserRepository userRepository;

    static boolean isInitialized = false;

    @BeforeAll
    static void beforeAll(
            @Autowired TestConfigurationData configurationData
    ) {
        if (!isInitialized) {
            configurationData.initializeDatabase();
            isInitialized = true;
        }
    }

    protected String generateJwt() {
        return jwtService.generateAccessToken(new UsernamePasswordAuthenticationToken(
                userRepository.findUserByUsername("testuser").orElseThrow(),
                null
        ));
    }

    protected String generateJwtAdmin() {
        return jwtService.generateAccessToken(new UsernamePasswordAuthenticationToken(
                userRepository.findUserByUsername("testadmin").orElseThrow(),
                null
        ));
    }
}
