package dev.rollczi.liteindex.space.indexing;

import dev.rollczi.liteindex.axis.Axis;

public interface IndexingAlgorithm<VECTOR> {

    int toIndex(Axis<VECTOR> axis, VECTOR vector);

    double toCoordinate(int index);

    static <VECTOR> IndexingAlgorithm<VECTOR> chunk(int powerOfTwo) {
        return IndexingAlgorithmChunkImpl.of(powerOfTwo);
    }

    static <VECTOR> IndexingAlgorithm<VECTOR> chunk(int powerOfTwo, int decimalPrecisionScale) {
        return IndexingAlgorithmChunkImpl.of(powerOfTwo, decimalPrecisionScale);
    }

    static <VECTOR> IndexingAlgorithm<VECTOR> optimalChunk(double expectedBorderSize, double expectedBoxSize) {
        return IndexingAlgorithmChunkImpl.optimal(expectedBorderSize, expectedBoxSize);
    }

}
