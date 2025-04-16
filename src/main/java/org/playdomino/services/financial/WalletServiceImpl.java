package org.playdomino.services.financial;

import lombok.RequiredArgsConstructor;
import org.playdomino.components.messages.MessagesComponent;
import org.playdomino.exceptions.financial.WalletException;
import org.playdomino.exceptions.financial.WalletExceptionConstants;
import org.playdomino.models.auth.User;
import org.playdomino.models.financial.Wallet;
import org.playdomino.models.financial.WalletTransaction;
import org.playdomino.models.financial.WalletTransactionType;
import org.playdomino.models.financial.dto.WalletAmount;
import org.playdomino.repositories.financial.WalletRepository;
import org.playdomino.repositories.financial.WalletTransactionRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    private final MessagesComponent messagesComponent;
    private final WalletTransactionRepository walletTransactionRepository;

    @Override
    @Transactional(readOnly = true)
    public Wallet getUserWallet(User user) {
        return walletRepository.findWalletByUser(user).orElseThrow(() -> new WalletException(WalletExceptionConstants.WALLET_DOES_NOT_EXISTS, messagesComponent.getMessage(WalletExceptionConstants.WALLET_DOES_NOT_EXISTS)));
    }

    @Override
    @Transactional(readOnly = true)
    public Wallet getCurrentUserWallet() {
        final User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return getUserWallet(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deposit(final WalletAmount walletAmount) {
        final Wallet wallet = walletAmount.getWallet();
        wallet.setAvailableCents(wallet.getAvailableCents() + walletAmount.getAmountCents());
        walletRepository.saveAndFlush(wallet);
        logTransaction(walletAmount.walletTransaction(WalletTransactionType.DEPOSIT));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void withdraw(final WalletAmount walletAmount) {
        final Wallet wallet = walletAmount.getWallet();

        if (wallet.cannotPerformTransaction(walletAmount.getAmountCents())) {
            throw new IllegalArgumentException("Saldo insuficiente para saque.");
        }

        wallet.setAvailableCents(wallet.getAvailableCents() - walletAmount.getAmountCents());
        wallet.setPendingWithdrawCents(wallet.getPendingWithdrawCents() + walletAmount.getAmountCents());
        walletRepository.save(wallet);
        logTransaction(walletAmount.walletTransaction(WalletTransactionType.WITHDRAW));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void confirmWithdraw(final WalletAmount walletAmount) {
        final Wallet wallet = walletAmount.getWallet();
        wallet.setPendingWithdrawCents(wallet.getPendingWithdrawCents() - walletAmount.getAmountCents());
        walletRepository.save(wallet);
        logTransaction(walletAmount.walletTransaction(WalletTransactionType.CONFIRM_WITHDRAW));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelWithdraw(final WalletAmount walletAmount) {
        final Wallet wallet = walletAmount.getWallet();
        wallet.setPendingWithdrawCents(wallet.getPendingWithdrawCents() - walletAmount.getAmountCents());
        wallet.setAvailableCents(wallet.getAvailableCents() + walletAmount.getAmountCents());
        walletRepository.save(wallet);
        logTransaction(walletAmount.walletTransaction(WalletTransactionType.CANCEL_WITHDRAW));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void lockForGame(final WalletAmount walletAmount) {
        final Wallet wallet = walletAmount.getWallet();

        if (wallet.cannotPerformTransaction(walletAmount.getAmountCents())) {
            throw new IllegalArgumentException("Saldo insuficiente para entrar na partida.");
        }

        wallet.setAvailableCents(wallet.getAvailableCents() - walletAmount.getAmountCents());
        wallet.setLockedCents(wallet.getLockedCents() + walletAmount.getAmountCents());
        walletRepository.save(wallet);
        logTransaction(walletAmount.walletTransaction(WalletTransactionType.GAME_ENTRY));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void unlockFromGame(final WalletAmount walletAmount) {
        final Wallet wallet = walletAmount.getWallet();
        wallet.setLockedCents(wallet.getLockedCents() - walletAmount.getAmountCents());
        wallet.setAvailableCents(wallet.getAvailableCents() + walletAmount.getAmountCents());
        walletRepository.save(wallet);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void prize(final WalletAmount walletAmount) {
        final Wallet wallet = walletAmount.getWallet();
        wallet.setLockedCents(wallet.getLockedCents() - walletAmount.getAmountCents());
        walletRepository.save(wallet);
        logTransaction(walletAmount.walletTransaction(WalletTransactionType.GAME_PRIZE));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void chargeFee(final WalletAmount walletAmount) {
        final Wallet wallet = walletAmount.getWallet();
        wallet.setAvailableCents(wallet.getAvailableCents() - walletAmount.getAmountCents());
        walletRepository.save(wallet);
        logTransaction(walletAmount.walletTransaction(WalletTransactionType.FEE));
    }

    @Transactional(rollbackFor = Exception.class)
    public void logTransaction(WalletTransaction transaction) {
        walletTransactionRepository.saveAndFlush(transaction);
    }
}
