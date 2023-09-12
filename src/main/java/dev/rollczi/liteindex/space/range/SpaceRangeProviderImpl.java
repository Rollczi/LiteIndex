package dev.rollczi.liteindex.space.range;

import java.util.function.Function;

class SpaceRangeProviderImpl<SPACE, VECTOR> implements SpaceRangeProvider<SPACE, VECTOR> {

    private final Function<SPACE, VECTOR> minRange;
    private final Function<SPACE, VECTOR> maxRange;

    SpaceRangeProviderImpl(Function<SPACE, VECTOR> minRange, Function<SPACE, VECTOR> maxRange) {
        this.minRange = minRange;
        this.maxRange = maxRange;
    }

    @Override
    public VECTOR getMinRange(SPACE space) {
        return minRange.apply(space);
    }

    @Override
    public VECTOR getMaxRange(SPACE space) {
        return maxRange.apply(space);
    }

}
