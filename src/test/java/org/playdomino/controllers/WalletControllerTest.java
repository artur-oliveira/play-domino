package org.playdomino.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.playdomino.GenericIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

class WalletControllerTest extends GenericIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Test WalletController: should wallet be created when user details info")
    void testShouldBeCreatedWallet() throws Exception {
        mockMvc.perform(get("/v1/wallet")
                        .accept(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, generateJwt())
                ).andDo(print())
                .andExpect(jsonPath("$.length()").value(4))
                .andExpect(jsonPath("$.availableCents").value(0))
                .andExpect(jsonPath("$.lockedCents").value(0))
                .andExpect(jsonPath("$.pendingWithdrawCents").value(0))
                .andExpect(jsonPath("$.pendingDepositCents").value(0));
    }

}