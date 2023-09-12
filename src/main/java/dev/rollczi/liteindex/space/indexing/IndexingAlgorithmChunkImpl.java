package dev.rollczi.liteindex.space.indexing;

import dev.rollczi.liteindex.axis.Axis;

class IndexingAlgorithmChunkImpl<VECTOR> implements IndexingAlgorithm<VECTOR> {

    private final int powerOfTwo;
    private final int decimalPrecisionScale;

    private IndexingAlgorithmChunkImpl(int powerOfTwo, int decimalPrecisionScale) {
        this.powerOfTwo = powerOfTwo;
        this.decimalPrecisionScale = decimalPrecisionScale;
    }

    @Override
    public int toIndex(Axis<VECTOR> axis, VECTOR vector) {
        double coordinate = axis.getAxisCoordinate(vector);
        int scaledCoordinate = (int) scale(coordinate, decimalPrecisionScale);

        return scaledCoordinate >> powerOfTwo;
    }

    @Override
    public double toCoordinate(int index) {
        int scaledCoordinate = index << powerOfTwo;
        return unScale(scaledCoordinate, decimalPrecisionScale);
    }

    static <VECTOR> IndexingAlgorithm<VECTOR> of(int power) {
        return new IndexingAlgorithmChunkImpl<>(power, 0);
    }

    static <VECTOR> IndexingAlgorithm<VECTOR> of(int power, int decimalPrecisionScale) {
        return new IndexingAlgorithmChunkImpl<>(power, decimalPrecisionScale);
    }

    private static final int MEDIAN_RATIO = 100;

    static <VECTOR> IndexingAlgorithm<VECTOR> optimal(double expectedBorderSize, double expectedBoxSize, int decimalPrecisionScale) {
        if (expectedBorderSize < expectedBoxSize) {
            throw new IllegalArgumentException("Expected border size cannot be less than expected box size");
        }

        decimalPrecisionScale = Math.max(decimalPrecisionScale, decimalPlaces(expectedBoxSize));
        decimalPrecisionScale = Math.max(decimalPrecisionScale, decimalPlaces(expectedBorderSize));

        int scaledBorderSize = (int) scale(expectedBorderSize, decimalPrecisionScale);
        int scaledBoxSize = (int) scale(expectedBoxSize, decimalPrecisionScale);
        int scaledRatio = scaledBorderSize / scaledBoxSize;
        int median = scaledRatio / MEDIAN_RATIO;
        int medianAndBoxAverage = (median + scaledBoxSize) / 2;

        for (int power = 0; power < Integer.MAX_VALUE; power++) {
            int realChunkSize = (int) Math.pow(2, power);

            if (realChunkSize > scaledBorderSize) {
                return new IndexingAlgorithmChunkImpl<>(power - 1, decimalPrecisionScale);
            }

            if (realChunkSize > medianAndBoxAverage) {
                return new IndexingAlgorithmChunkImpl<>(power, decimalPrecisionScale);
            }
        }

        throw new IllegalStateException("Cannot find optimal chunk size");
    }

    private static int decimalPlaces(double value) {
        if (value == 0) {
            return 0;
        }

        int decimalPlaces = 0;
        while (value % 1 != 0) {
            value *= 10;
            decimalPlaces++;
        }

        return decimalPlaces;
    }

    private static double scale(double value, int decimalPrecisionScale) {
        return value * Math.pow(10, decimalPrecisionScale);
    }

    private static double unScale(double value, int decimalPrecisionScale) {
        return value / Math.pow(10, decimalPrecisionScale);
    }

}
