package dev.rollczi.liteindex.space;

import dev.rollczi.liteindex.axis.AxesSet;
import dev.rollczi.liteindex.axis.Axis;
import dev.rollczi.liteindex.axis.AxisCoordinateProvider;
import dev.rollczi.liteindex.shared.Validation;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class SpaceIndexBuilder<SPACE, VECTOR> {

    private SpaceVectorProvider<SPACE, VECTOR> spaceVectorProvider;
    private IndexSize indexSize = IndexSize.of(4);
    private boolean concurrent = false;

    private final List<Axis<VECTOR>> axes = new ArrayList<>();

    public SpaceIndexBuilder<SPACE, VECTOR> space(SpaceVectorProvider<SPACE, VECTOR> provider) {
        this.spaceVectorProvider = provider;
        return this;
    }

    public SpaceIndexBuilder<SPACE, VECTOR> space(Function<SPACE, VECTOR> minProvider, Function<SPACE, VECTOR> maxProvider) {
        this.spaceVectorProvider = SpaceVectorProvider.create(minProvider, maxProvider);
        return this;
    }

    public SpaceIndexBuilder<SPACE, VECTOR> indexSize(IndexSize indexSize) {
        this.indexSize = indexSize;
        return this;
    }

    public SpaceIndexBuilder<SPACE, VECTOR> concurrent(boolean concurrent) {
        this.concurrent = concurrent;
        return this;
    }

    public SpaceIndexBuilder<SPACE, VECTOR> axisX(AxisCoordinateProvider<VECTOR> provider) {
        this.axes.add(Axis.of("x", provider));
        return this;
    }

    public SpaceIndexBuilder<SPACE, VECTOR> axisY(AxisCoordinateProvider<VECTOR> provider) {
        this.axes.add(Axis.of("y", provider));
        return this;
    }

    public SpaceIndexBuilder<SPACE, VECTOR> axisZ(AxisCoordinateProvider<VECTOR> provider) {
        this.axes.add(Axis.of("z", provider));
        return this;
    }

    public SpaceIndexBuilder<SPACE, VECTOR> axisW(AxisCoordinateProvider<VECTOR> provider) {
        this.axes.add(Axis.of("w", provider));
        return this;
    }

    public SpaceIndexBuilder<SPACE, VECTOR> axis(String name, AxisCoordinateProvider<VECTOR> provider) {
        this.axes.add(Axis.of(name, provider));
        return this;
    }

    public SpaceIndex<SPACE, VECTOR> build() {
        Validation.isNotNull(spaceVectorProvider, "space vector provider can not be null");
        Validation.isNotNull(indexSize, "chunkIndex can not be null");
        Validation.isNotEmpty(axes, "axes can not be empty");

        UniversalSpaceIndex<SPACE, VECTOR> spaceIndex = new UniversalSpaceIndex<>(spaceVectorProvider, AxesSet.create(axes), indexSize);

        if (concurrent) {
            return new UniversalConcurrentSpaceIndex<>(spaceIndex);
        }

        return spaceIndex;
    }

}
