package org.playdomino.services.auth.verification;

import lombok.RequiredArgsConstructor;
import org.playdomino.models.auth.User;
import org.playdomino.models.financial.Wallet;
import org.playdomino.repositories.financial.WalletRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateWalletServiceImpl implements AfterVerificationService {
    private final WalletRepository walletRepository;

    @Override
    public void afterVerification(User user) {
        walletRepository.saveAndFlush(Wallet
                .builder()
                .user(user)
                .build());
    }
}
