package org.playdomino.controllers;

import lombok.RequiredArgsConstructor;
import org.playdomino.models.financial.Wallet;
import org.playdomino.services.financial.WalletService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/wallet")
@RequiredArgsConstructor
public class WalletController {
    private final WalletService walletService;

    @GetMapping
    public Wallet getWallet() {
        return walletService.getCurrentUserWallet();
    }

}
