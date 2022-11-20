package dev.rollczi.liteindex.space;

import java.util.function.Function;

class SpaceVectorProviderImpl<SPACE, VECTOR> implements SpaceVectorProvider<SPACE, VECTOR> {

    private final Function<SPACE, VECTOR> minProvider;
    private final Function<SPACE, VECTOR> maxProvider;

    SpaceVectorProviderImpl(Function<SPACE, VECTOR> minProvider, Function<SPACE, VECTOR> maxProvider) {
        this.minProvider = minProvider;
        this.maxProvider = maxProvider;
    }

    @Override
    public VECTOR getMinVector(SPACE space) {
        return minProvider.apply(space);
    }

    @Override
    public VECTOR getMaxVector(SPACE space) {
        return maxProvider.apply(space);
    }

}
