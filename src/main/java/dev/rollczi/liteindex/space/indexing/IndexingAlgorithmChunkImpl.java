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
        int scaledCoordinate = (int) IndexingAlgorithmChunkImpl.scale(coordinate, decimalPrecisionScale);

        return scaledCoordinate >> powerOfTwo;
    }

    @Override
    public double toCoordinate(int index) {
        int scaledCoordinate = index << powerOfTwo;
        return IndexingAlgorithmChunkImpl.unScale(scaledCoordinate, decimalPrecisionScale);
    }

    public static <VECTOR> IndexingAlgorithmChunkImpl<VECTOR> of(int power) {
        return new IndexingAlgorithmChunkImpl<>(power, 0);
    }

    public static <VECTOR> IndexingAlgorithmChunkImpl<VECTOR> of(int power, int decimalPrecisionScale) {
        return new IndexingAlgorithmChunkImpl<>(power, decimalPrecisionScale);
    }

    private static final int LIMIT_DECIMAL_PRECISION_SCALE = 2;
    private static final int LIMIT_DECIMAL_PRECISION_SCALE_AFTER_BORDER = 100;
    private static final int DIVISIONS_CAPACITY = 2;

    public static <VECTOR> IndexingAlgorithmChunkImpl<VECTOR> optimal(double expectedBorderSize, double expectedBoxSize) {
        int decimalPlacesPrecisionScale = Math.max(IndexingAlgorithmChunkImpl.decimalPlaces(expectedBorderSize), IndexingAlgorithmChunkImpl.decimalPlaces(expectedBoxSize));

        if (expectedBorderSize > LIMIT_DECIMAL_PRECISION_SCALE_AFTER_BORDER) {
            decimalPlacesPrecisionScale = 0;
        }

        if (decimalPlacesPrecisionScale > LIMIT_DECIMAL_PRECISION_SCALE) {
            decimalPlacesPrecisionScale = LIMIT_DECIMAL_PRECISION_SCALE;
        }

        int scaledBorderSize = (int) IndexingAlgorithmChunkImpl.scale(expectedBorderSize, decimalPlacesPrecisionScale);
        double scaledBoxSize = IndexingAlgorithmChunkImpl.scale(expectedBoxSize, decimalPlacesPrecisionScale);

        if (scaledBoxSize == 0) {
            throw new IllegalArgumentException("Box wall size cannot be 0");
        }

        double divisions = scaledBorderSize / scaledBoxSize;
        double biggerDivisions = divisions / DIVISIONS_CAPACITY;
        int power = 0;

        while (scaledBorderSize >> power > biggerDivisions) {
            power++;
        }

        return new IndexingAlgorithmChunkImpl<>(power, decimalPlacesPrecisionScale);
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
