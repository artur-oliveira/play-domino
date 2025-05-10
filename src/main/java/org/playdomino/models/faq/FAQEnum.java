package org.playdomino.models.faq;

public enum FAQEnum {

    BET_NEEDED {
        @Override
        public String getQuestion() {
            return "faq.question.bet-needed";
        }

        @Override
        public String getAnswer() {
            return "faq.answer.bet-needed";
        }
    },
    ANY_FEES {
        @Override
        public String getQuestion() {
            return "faq.question.any-fees";
        }

        @Override
        public String getAnswer() {
            return "faq.answer.any-fees";
        }
    },
    PLAY_WITH_FRIENDS {
        @Override
        public String getQuestion() {
            return "faq.question.play-with-friends";
        }

        @Override
        public String getAnswer() {
            return "faq.answer.play-with-friends";
        }
    },
    SHUFFLING_WORKS {
        @Override
        public String getQuestion() {
            return "faq.question.shuffling-works";
        }

        @Override
        public String getAnswer() {
            return "faq.answer.shuffling-works";
        }
    };

    public abstract String getQuestion();

    public abstract String getAnswer();
}
