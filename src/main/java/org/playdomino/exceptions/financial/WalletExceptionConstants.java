package org.playdomino.exceptions.financial;

public final class WalletExceptionConstants {
    private WalletExceptionConstants() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }

    public static final String WALLET_DOES_NOT_EXISTS = "wallet.does-not-exists";
    public static final String WALLET_INSUFFICIENT_BALANCE = "wallet.insufficient-balance";
}
