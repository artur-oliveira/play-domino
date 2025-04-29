package org.playdomino.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.playdomino.GenericIntegrationTest;
import org.playdomino.models.auth.Country;
import org.playdomino.models.auth.UserVerification;
import org.playdomino.models.auth.dto.UserCreate;
import org.playdomino.models.auth.dto.UserToken;
import org.playdomino.repositories.auth.UserVerificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthControllerTest extends GenericIntegrationTest {

    @Autowired
    private UserVerificationRepository userVerificationRepository;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;

    @Test
    @DisplayName("Test AuthController: Should register a new user, verify, and get a new token")
    void testRegisterNewUser() throws Exception {
        byte[] userCreateBody = mapper.writeValueAsBytes(UserCreate
                .builder()
                .username("test")
                .password("Test123456@")
                .country(Country.BRAZIL)
                .email("test@test.com")
                .federalDocument("99999999999")
                .firstname("Test")
                .lastname("Testing")
                .build());

        mvc.perform(MockMvcRequestBuilders
                        .post("/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userCreateBody))
                .andDo(print())
                .andExpect(status().isNoContent());

        mvc.perform(MockMvcRequestBuilders
                        .post("/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userCreateBody))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("A user with provided info already exist."));

        String token = userVerificationRepository.findUserVerificationByUserUsername("test").map(UserVerification::getToken).orElse(null);

        Assertions.assertNotNull(token);

        mvc.perform(MockMvcRequestBuilders
                        .post("/v1/auth/verify/" + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());

        mvc.perform(MockMvcRequestBuilders
                        .post("/v1/auth/token")
                        .content(mapper.writeValueAsBytes(UserToken.builder()
                                .email("test@test.com")
                                .password("Test123456@")
                                .build()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").isNotEmpty())
                .andExpect(jsonPath("$.refreshToken").isNotEmpty());

    }
}