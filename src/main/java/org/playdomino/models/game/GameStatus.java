package org.playdomino.models.game;

import java.util.List;

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

    public static List<GameStatus> unfinisheds() {
        return List.of(WAITING_FOR_PLAYERS, IN_PROGRESS);
    }

    public abstract boolean ongoing();
}

