package org.playdomino.exceptions.financial;

public final class WalletExceptionConstants {
    private WalletExceptionConstants() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }

    public static final String WALLET_DOES_NOT_EXISTS = "wallet.does-not-exists";
    public static final String WALLET_INSUFFICIENT_BALANCE = "wallet.insufficient-balance";
    public static final String PENDING_DEPOSIT_EXISTS = "wallet.deposit.exists-pending";
    public static final String DEPOSIT_MINIMUM_VALUE = "wallet.deposit.minimum-value";
    public static final String DEPOSIT_MAXIMUM_VALUE = "wallet.deposit.maximum-value";
    public static final String INCORRECT_PENDING_DEPOSIT_AMOUNT = "wallet.deposit.incorrect-pending-amount";

    public static final String PENDING_WITHDRAW_EXISTS = "wallet.withdraw.exists-pending";
    public static final String WITHDRAW_MINIMUM_VALUE = "wallet.withdraw.minimum-value";
    public static final String WITHDRAW_MAXIMUM_VALUE = "wallet.withdraw.maximum-value";
    public static final String INCORRECT_PENDING_WITHDRAW_AMOUNT = "wallet.withdraw.incorrect-pending-amount";
}
