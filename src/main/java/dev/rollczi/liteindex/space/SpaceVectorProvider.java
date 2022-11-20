package dev.rollczi.liteindex.space;

import java.util.function.Function;

public interface SpaceVectorProvider<SPACE, VECTOR> {

    VECTOR getMinVector(SPACE space);

    VECTOR getMaxVector(SPACE space);

    static <S, V> SpaceVectorProvider<S, V> create(Function<S, V> minProvider, Function<S, V> maxProvider) {
        return new SpaceVectorProviderImpl<>(minProvider, maxProvider);
    }

}
