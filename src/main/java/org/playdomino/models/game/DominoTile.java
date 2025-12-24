package org.playdomino.models.game;

import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.security.SecureRandom;
import java.util.*;
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
    private final boolean sameValue;
    private final int sum;

    DominoTile(int left, int right) {
        this(left, right, left == right, left + right);
    }

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    public static List<DominoTile> randomOrder(int playersQuantity) {
        List<DominoTile> excludeTileList = new ArrayList<>();
        if (playersQuantity == 3) {
            excludeTileList.add(ZERO_ZERO);
        }
        List<DominoTile> list = Arrays.stream(values()).filter(it -> !excludeTileList.contains(it)).collect(Collectors.toList());
        for (int i = list.size() - 1; i > 0; i--) {
            int j = SECURE_RANDOM.nextInt(i + 1);
            Collections.swap(list, i, j);
        }
        return list;
    }

    public boolean accepts(@NotNull DominoTile tile) {
        return Objects.equals(tile.getLeft(), getLeft()) || Objects.equals(tile.getLeft(), getRight()) || Objects.equals(tile.getRight(), getLeft()) || Objects.equals(tile.getRight(), getRight());
    }

    public boolean accepts(@NotNull int tileNumber) {
        return Objects.equals(tileNumber, getLeft()) || Objects.equals(tileNumber, getRight());
    }

    public int otherFace(int tileNumber) {
        if (Objects.equals(tileNumber, getLeft())) {
            return getRight();
        } else if (Objects.equals(tileNumber, getRight())) {
            return getLeft();
        }
        throw new IllegalArgumentException("Invalid tile number: " + tileNumber);
    }
}

