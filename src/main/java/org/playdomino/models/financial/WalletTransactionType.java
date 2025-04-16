package org.playdomino.models.financial;

public enum WalletTransactionType {
    DEPOSIT {
        @Override
        public boolean incoming() {
            return true;
        }
    },
    WITHDRAW {
        @Override
        public boolean incoming() {
            return false;
        }
    },
    CONFIRM_WITHDRAW {
        @Override
        public boolean incoming() {
            return false;
        }
    },
    CANCEL_WITHDRAW {
        @Override
        public boolean incoming() {
            return true;
        }
    },
    GAME_ENTRY {
        @Override
        public boolean incoming() {
            return false;
        }
    },
    GAME_PRIZE {
        @Override
        public boolean incoming() {
            return false;
        }
    },
    FEE {
        @Override
        public boolean incoming() {
            return false;
        }
    },
    ADJUSTMENT {
        @Override
        public boolean incoming() {
            return true;
        }
    };

    public abstract boolean incoming();
}

