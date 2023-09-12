package dev.rollczi.liteindex.space.range;

import java.util.function.Function;

public interface SpaceRangeProvider<SPACE, VECTOR> {

    VECTOR getMinRange(SPACE space);

    VECTOR getMaxRange(SPACE space);

    static <S, V> SpaceRangeProvider<S, V> create(Function<S, V> minRange, Function<S, V> maxRange) {
        return new SpaceRangeProviderImpl<>(minRange, maxRange);
    }

}
