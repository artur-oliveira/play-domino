package org.playdomino.models.ws;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.Arrays;

public enum NotificationTopic {
    FINANCIAL {
        @Override
        public String prefix() {
            return "/topic/finance.";
        }
    },
    GAME {
        @Override
        public String prefix() {
            return "/topic/game.";
        }
    },
    PUBLIC {
        @Override
        public String prefix() {
            return "/topic/public";
        }
    };

    public abstract String prefix();

    public @Nonnull String getTopicFor(@Nonnull Long id) {
        return prefix() + id;
    }

    public @Nullable Long getIdForTopic(@Nonnull String topicDestination) {
        try {
            return Long.parseLong(topicDestination.replace(prefix(), ""));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static @Nullable NotificationTopic valueOfTopic(@Nonnull String topicDestination) {
        return Arrays.stream(values()).filter(it -> topicDestination.startsWith(it.prefix())).findFirst().orElse(null);
    }
}
