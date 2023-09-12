package dev.rollczi.liteindex.space;

import dev.rollczi.liteindex.axis.AxesSet;
import dev.rollczi.liteindex.axis.Axis;
import dev.rollczi.liteindex.axis.AxisCoordinateProvider;
import dev.rollczi.liteindex.shared.Validation;
import dev.rollczi.liteindex.space.indexing.IndexingAlgorithm;
import dev.rollczi.liteindex.space.indexing.IndexingAlgorithmSet;
import dev.rollczi.liteindex.space.range.SpaceRangeProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class SpaceIndexBuilder<SPACE, VECTOR> {

    private final List<Axis<VECTOR>> axes = new ArrayList<>();
    private final IndexingAlgorithmSet.Builder<VECTOR> indexingAlgorithmBuilder = IndexingAlgorithmSet.<VECTOR>builder()
            .defaultAlgorithm(IndexingAlgorithm.chunk(8));

    private SpaceRangeProvider<SPACE, VECTOR> spaceRangeProvider;
    private boolean concurrent = false;

    public SpaceIndexBuilder<SPACE, VECTOR> space(SpaceRangeProvider<SPACE, VECTOR> provider) {
        this.spaceRangeProvider = provider;
        return this;
    }

    public SpaceIndexBuilder<SPACE, VECTOR> space(Function<SPACE, VECTOR> minProvider, Function<SPACE, VECTOR> maxProvider) {
        this.spaceRangeProvider = SpaceRangeProvider.create(minProvider, maxProvider);
        return this;
    }

    public SpaceIndexBuilder<SPACE, VECTOR> indexing(IndexingAlgorithm<VECTOR> indexing) {
        Validation.isNotNull(indexing, "indexing can not be null");

        this.indexingAlgorithmBuilder.defaultAlgorithm(indexing);
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

    public SpaceIndexBuilder<SPACE, VECTOR> axisX(AxisCoordinateProvider<VECTOR> provider, IndexingAlgorithm<VECTOR> indexing) {
        Axis<VECTOR> axis = Axis.of("x", provider);

        this.axes.add(axis);
        this.indexingAlgorithmBuilder.algorithm(axis, indexing);
        return this;
    }

    public SpaceIndexBuilder<SPACE, VECTOR> axisY(AxisCoordinateProvider<VECTOR> provider) {
        this.axes.add(Axis.of("y", provider));
        return this;
    }

    public SpaceIndexBuilder<SPACE, VECTOR> axisY(AxisCoordinateProvider<VECTOR> provider, IndexingAlgorithm<VECTOR> indexing) {
        Axis<VECTOR> axis = Axis.of("y", provider);

        this.axes.add(axis);
        this.indexingAlgorithmBuilder.algorithm(axis, indexing);
        return this;
    }

    public SpaceIndexBuilder<SPACE, VECTOR> axisZ(AxisCoordinateProvider<VECTOR> provider) {
        this.axes.add(Axis.of("z", provider));
        return this;
    }

    public SpaceIndexBuilder<SPACE, VECTOR> axisZ(AxisCoordinateProvider<VECTOR> provider, IndexingAlgorithm<VECTOR> indexing) {
        Axis<VECTOR> axis = Axis.of("z", provider);

        this.axes.add(axis);
        this.indexingAlgorithmBuilder.algorithm(axis, indexing);
        return this;
    }

    public SpaceIndexBuilder<SPACE, VECTOR> axisW(AxisCoordinateProvider<VECTOR> provider) {
        this.axes.add(Axis.of("w", provider));
        return this;
    }

    public SpaceIndexBuilder<SPACE, VECTOR> axisW(AxisCoordinateProvider<VECTOR> provider, IndexingAlgorithm<VECTOR> indexing) {
        Axis<VECTOR> axis = Axis.of("w", provider);

        this.axes.add(axis);
        this.indexingAlgorithmBuilder.algorithm(axis, indexing);
        return this;
    }

    public SpaceIndexBuilder<SPACE, VECTOR> axis(String name, AxisCoordinateProvider<VECTOR> provider) {
        this.axes.add(Axis.of(name, provider));
        return this;
    }

    public SpaceIndexBuilder<SPACE, VECTOR> axis(String name, AxisCoordinateProvider<VECTOR> provider, IndexingAlgorithm<VECTOR> indexing) {
        Axis<VECTOR> axis = Axis.of(name, provider);

        this.axes.add(axis);
        this.indexingAlgorithmBuilder.algorithm(axis, indexing);
        return this;
    }

    public SpaceIndex<SPACE, VECTOR> build() {
        Validation.isNotNull(spaceRangeProvider, "space vector provider can not be null");
        Validation.isNotEmpty(axes, "axes can not be empty");

        UniversalSpaceIndex<SPACE, VECTOR> spaceIndex = new UniversalSpaceIndex<>(spaceRangeProvider, AxesSet.create(axes), indexingAlgorithmBuilder.build());

        if (concurrent) {
            return new UniversalConcurrentSpaceIndex<>(spaceIndex);
        }

        return spaceIndex;
    }

}
