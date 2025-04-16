package org.playdomino.models.game;

public enum GameStatus {
    WAITING_FOR_PLAYERS {
        @Override
        public boolean ongoing() {
            return true;
        }
    },
    IN_PROGRESS {
        @Override
        public boolean ongoing() {
            return true;
        }
    },
    FINISHED {
        @Override
        public boolean ongoing() {
            return false;
        }
    },
    CANCELLED {
        @Override
        public boolean ongoing() {
            return false;
        }
    };

    public abstract boolean ongoing();
}

