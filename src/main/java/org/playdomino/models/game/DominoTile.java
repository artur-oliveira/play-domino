package org.playdomino.models.game;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Arrays;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum DominoTile {
    ZERO_ZERO(0, 0),
    ZERO_ONE(0, 1),
    ZERO_TWO(0, 2),
    ZERO_THREE(0, 3),
    ZERO_FOUR(0, 4),
    ZERO_FIVE(0, 5),
    ZERO_SIX(0, 6),
    ONE_ONE(1, 1),
    ONE_TWO(1, 2),
    ONE_THREE(1, 3),
    ONE_FOUR(1, 4),
    ONE_FIVE(1, 5),
    ONE_SIX(1, 6),
    TWO_TWO(2, 2),
    TWO_THREE(2, 3),
    TWO_FOUR(2, 4),
    TWO_FIVE(2, 5),
    TWO_SIX(2, 6),
    THREE_THREE(3, 3),
    THREE_FOUR(3, 4),
    THREE_FIVE(3, 5),
    THREE_SIX(3, 6),
    FOUR_FOUR(4, 4),
    FOUR_FIVE(4, 5),
    FOUR_SIX(4, 6),
    FIVE_FIVE(5, 5),
    FIVE_SIX(5, 6),
    SIX_SIX(6, 6);
    @EqualsAndHashCode.Include
    private final int left;
    @EqualsAndHashCode.Include
    private final int right;

    @Override
    public String toString() {
        return String.format("[%d|%d]", left, right);
    }
}

