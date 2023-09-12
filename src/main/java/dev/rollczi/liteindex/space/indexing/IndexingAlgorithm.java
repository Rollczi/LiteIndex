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

    static <VECTOR> IndexingAlgorithm<VECTOR> calculateOptimal(double expectedBorderSize, double expectedBoxSize, int decimalPrecisionScale) {
        return IndexingAlgorithmChunkImpl.optimal(expectedBorderSize, expectedBoxSize, decimalPrecisionScale);
    }

    static <VECTOR> IndexingAlgorithm<VECTOR> calculateOptimal(double expectedBorderSize, double expectedBoxSize) {
        return IndexingAlgorithmChunkImpl.optimal(expectedBorderSize, expectedBoxSize, 0);
    }

}
