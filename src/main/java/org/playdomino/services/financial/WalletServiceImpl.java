package org.playdomino.services.financial;

import lombok.RequiredArgsConstructor;
import org.playdomino.components.messages.MessagesComponent;
import org.playdomino.exceptions.financial.WalletException;
import org.playdomino.exceptions.financial.WalletExceptionConstants;
import org.playdomino.models.auth.User;
import org.playdomino.models.financial.Wallet;
import org.playdomino.repositories.financial.WalletRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    private final MessagesComponent messagesComponent;

    @Transactional(readOnly = true)
    public Wallet getUserWallet(User user) {
        return walletRepository.findWalletByUser(user).orElseThrow(() -> new WalletException(WalletExceptionConstants.WALLET_DOES_NOT_EXISTS, messagesComponent.getMessage(WalletExceptionConstants.WALLET_DOES_NOT_EXISTS)));
    }

    @Transactional(readOnly = true)
    public Wallet getCurrentUserWallet() {
        final User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return getUserWallet(user);
    }

}
