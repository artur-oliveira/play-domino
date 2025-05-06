package org.playdomino.services.financial;

import lombok.RequiredArgsConstructor;
import org.playdomino.components.auth.UserUtils;
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
import org.playdomino.services.financial.process.confirmdeposit.AfterConfirmDepositService;
import org.playdomino.services.financial.process.confirmdeposit.BeforeConfirmDepositService;
import org.playdomino.services.financial.process.confirmwithdraw.AfterConfirmWithdrawService;
import org.playdomino.services.financial.process.confirmwithdraw.BeforeConfirmWithdrawService;
import org.playdomino.services.financial.process.deposit.BeforeDepositService;
import org.playdomino.services.financial.process.lock.BeforeLockService;
import org.playdomino.services.financial.process.unlock.BeforeUnlockService;
import org.playdomino.services.financial.process.withdraw.BeforeWithdrawService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    private final MessagesComponent messagesComponent;
    private final WalletTransactionRepository walletTransactionRepository;
    private final List<BeforeDepositService> beforeDepositServiceList;
    private final List<BeforeConfirmDepositService> beforeConfirmDepositServices;
    private final List<AfterConfirmDepositService> afterConfirmDepositServices;
    private final List<BeforeWithdrawService> beforeWithdrawServices;
    private final List<BeforeConfirmWithdrawService> beforeConfirmWithdrawServices;
    private final List<AfterConfirmWithdrawService> afterConfirmWithdrawServices;
    private final List<BeforeLockService> beforeLockServices;
    private final List<BeforeUnlockService> beforeUnlockServices;

    @Override
    @Transactional(readOnly = true)
    public Wallet getUserWallet(User user) {
        return walletRepository.findWalletByUser(user).orElseThrow(() -> new WalletException(WalletExceptionConstants.WALLET_DOES_NOT_EXISTS, messagesComponent.getMessage(WalletExceptionConstants.WALLET_DOES_NOT_EXISTS)));
    }

    @Override
    @Transactional(readOnly = true)
    public Wallet getCurrentUserWallet() {
        return getUserWallet(UserUtils.currentUser());
    }

    @Override
    @Transactional(readOnly = true)
    public boolean cannotPerformTransaction(User user, Long amountCents) {
        return getUserWallet(user).cannotPerformTransaction(amountCents);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void initiateDeposit(final WalletAmount walletAmount) {
        beforeDepositServiceList.forEach(it -> it.process(walletAmount));
        final Wallet wallet = walletAmount.getWallet();
        wallet.setPendingDepositCents(wallet.getPendingDepositCents() + walletAmount.getAmountCents());
        walletRepository.saveAndFlush(wallet);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void confirmDeposit(final WalletAmount walletAmount) {
        final Wallet wallet = walletAmount.getWallet();
        beforeConfirmDepositServices.forEach(it -> it.process(walletAmount));
        wallet.setPendingDepositCents(0L);
        wallet.setAvailableCents(wallet.getAvailableCents() + walletAmount.getAmountCents());
        walletRepository.saveAndFlush(wallet);
        final WalletTransaction deposit = logTransaction(walletTransaction(WalletTransactionType.DEPOSIT, walletAmount));

        afterConfirmDepositServices.forEach(it -> it.process(deposit));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void initiateWithdraw(WalletAmount walletAmount) {
        beforeWithdrawServices.forEach(it -> it.process(walletAmount));
        final Wallet wallet = walletAmount.getWallet();
        wallet.setAvailableCents(wallet.getAvailableCents() - walletAmount.getAmountCents());
        wallet.setPendingWithdrawCents(wallet.getPendingWithdrawCents() + walletAmount.getAmountCents());
        walletRepository.saveAndFlush(wallet);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void confirmWithdraw(final WalletAmount walletAmount) {
        final Wallet wallet = walletAmount.getWallet();
        beforeConfirmWithdrawServices.forEach(it -> it.process(walletAmount));

        final Long amountCents = walletAmount.getAmountCents();
        final Long feeAmount = BigDecimal
                .valueOf(amountCents)
                .setScale(2, RoundingMode.UNNECESSARY)
                .multiply(BigDecimal.valueOf(wallet.getFeePercent()))
                .divide(BigDecimal.valueOf(100L).setScale(2, RoundingMode.UNNECESSARY), RoundingMode.HALF_EVEN)
                .setScale(0, RoundingMode.HALF_EVEN)
                .longValue();

        final Long netAmount = amountCents - feeAmount;

        wallet.setPendingWithdrawCents(0L);
        walletRepository.saveAndFlush(wallet);

        logTransaction(walletTransaction(WalletTransactionType.FEE, WalletAmount.builder().wallet(wallet).amountCents(feeAmount).build()));
        final WalletTransaction withdraw = logTransaction(walletTransaction(WalletTransactionType.WITHDRAW, WalletAmount.builder().wallet(wallet).amountCents(netAmount).build()));

        afterConfirmWithdrawServices.forEach(it -> it.process(withdraw));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelWithdraw(final WalletAmount walletAmount) {
        final Wallet wallet = walletAmount.getWallet();
        wallet.setPendingWithdrawCents(wallet.getPendingWithdrawCents() - walletAmount.getAmountCents());
        wallet.setAvailableCents(wallet.getAvailableCents() + walletAmount.getAmountCents());
        walletRepository.save(wallet);
        logTransaction(walletTransaction(WalletTransactionType.CANCEL_WITHDRAW, walletAmount));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void lockForGame(final WalletAmount walletAmount) {
        final Wallet wallet = walletAmount.getWallet();

        beforeLockServices.forEach(it -> it.process(walletAmount));

        wallet.setAvailableCents(wallet.getAvailableCents() - walletAmount.getAmountCents());
        wallet.setLockedCents(wallet.getLockedCents() + walletAmount.getAmountCents());
        walletRepository.save(wallet);

        logTransaction(walletTransaction(WalletTransactionType.GAME_ENTRY, walletAmount));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void unlockFromGame(final WalletAmount walletAmount) {
        final Wallet wallet = walletAmount.getWallet();

        beforeUnlockServices.forEach(it -> it.process(walletAmount));

        wallet.setLockedCents(wallet.getLockedCents() - walletAmount.getAmountCents());
        wallet.setAvailableCents(wallet.getAvailableCents() + walletAmount.getAmountCents());
        walletRepository.save(wallet);

        logTransaction(walletTransaction(WalletTransactionType.GAME_CANCEL, walletAmount));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void prize(final WalletAmount walletAmount) {
        final Wallet wallet = walletAmount.getWallet();
        wallet.setLockedCents(wallet.getLockedCents() - walletAmount.getAmountCents());
        walletRepository.save(wallet);
        logTransaction(walletTransaction(WalletTransactionType.GAME_PRIZE, walletAmount));
    }

    private WalletTransaction walletTransaction(WalletTransactionType type, WalletAmount walletAmount) {
        return walletAmount.walletTransaction(type, messagesComponent.getMessage(type.propertyName()));
    }

    @Transactional(rollbackFor = Exception.class)
    public WalletTransaction logTransaction(WalletTransaction transaction) {
        return walletTransactionRepository.saveAndFlush(transaction);
    }
}
