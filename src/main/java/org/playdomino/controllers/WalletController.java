package org.playdomino.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.playdomino.models.financial.WalletTransaction;
import org.playdomino.models.financial.dto.WalletAmount;
import org.playdomino.models.financial.dto.WalletDTO;
import org.playdomino.models.financial.dto.WalletDepositAmount;
import org.playdomino.models.financial.dto.WalletTransactionDTO;
import org.playdomino.models.generic.ListReponse;
import org.playdomino.services.financial.WalletService;
import org.playdomino.services.financial.WalletTransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/v1/wallet")
@RequiredArgsConstructor
public class WalletController {
    private final WalletService walletService;
    private final WalletTransactionService walletTransactionService;

    @GetMapping
    public WalletDTO getWallet() {
        return WalletDTO.of(walletService.getCurrentUserWallet());
    }

    @GetMapping("/transactions")
    public ListReponse<WalletTransactionDTO> getWalletTransactions(
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> size
    ) {
        return new ListReponse<>(
                walletTransactionService.findAllTransactionsByWallet(
                        walletService.getCurrentUserWallet(),
                        page.orElse(0),
                        size.orElse(10)
                ).stream().map(WalletTransactionDTO::of).toList()
        );
    }

    @PostMapping("/deposit")
    public ResponseEntity<Void> deposit(
            @Valid @RequestBody WalletAmount walletAmount
    ) {
        walletService.initiateDeposit(
                WalletAmount.builder()
                        .wallet(walletService.getCurrentUserWallet())
                        .amountCents(walletAmount.getAmountCents())
                        .build()
        );
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/deposit/confirm")
    public ResponseEntity<Void> confirmDeposit(
            @Valid @RequestBody WalletAmount walletAmount
    ) {
        walletService.confirmDeposit(
                WalletAmount.builder()
                        .wallet(walletService.getCurrentUserWallet())
                        .amountCents(walletAmount.getAmountCents())
                        .build()
        );
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/withdraw")
    public ResponseEntity<Void> withdraw(
            @Valid @RequestBody WalletAmount walletAmount
    ) {
        walletService.initiateWithdraw(
                WalletAmount.builder()
                        .wallet(walletService.getCurrentUserWallet())
                        .amountCents(walletAmount.getAmountCents())
                        .build()
        );
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/withdraw/confirm")
    public ResponseEntity<Void> confirmWithdraw(
            @Valid @RequestBody WalletDepositAmount walletAmount
    ) {
        walletService.confirmWithdraw(
                WalletAmount.builder()
                        .wallet(walletService.getCurrentUserWallet())
                        .amountCents(walletAmount.getAmountCents())
                        .build()
        );
        return ResponseEntity.noContent().build();
    }
}
