package org.playdomino.services.financial;

import org.playdomino.models.auth.User;
import org.playdomino.models.financial.Wallet;

public interface WalletService {

    Wallet getUserWallet(User user);

    Wallet getCurrentUserWallet();

}
