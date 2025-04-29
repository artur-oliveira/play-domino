package org.playdomino.models.financial;

public enum WalletTransactionType {
    DEPOSIT {
        @Override
        public boolean incoming() {
            return true;
        }

        @Override
        public String propertyName() {
            return "transaction.deposit";
        }
    },
    WITHDRAW {
        @Override
        public boolean incoming() {
            return false;
        }

        @Override
        public String propertyName() {
            return "transaction.withdraw";
        }
    },
    CANCEL_WITHDRAW {
        @Override
        public boolean incoming() {
            return true;
        }

        @Override
        public String propertyName() {
            return "transaction.cancel-withdraw";
        }
    },
    GAME_ENTRY {
        @Override
        public boolean incoming() {
            return false;
        }

        @Override
        public String propertyName() {
            return "transaction.game-entry";
        }
    },
    GAME_PRIZE {
        @Override
        public boolean incoming() {
            return true;
        }

        @Override
        public String propertyName() {
            return "transaction.game-prize";
        }
    },
    FEE {
        @Override
        public boolean incoming() {
            return false;
        }

        @Override
        public String propertyName() {
            return "transaction.fee";
        }
    },
    ADJUSTMENT {
        @Override
        public boolean incoming() {
            return true;
        }

        @Override
        public String propertyName() {
            return "transaction.adjustment";
        }
    };

    public abstract boolean incoming();

    public abstract String propertyName();
}

