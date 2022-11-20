package dev.rollczi.liteindex.space;

public class IndexSize {

    private final int power;
    private final int decimalPrecisionScale;

    private IndexSize(int power, int decimalPrecisionScale) {
        this.power = power;
        this.decimalPrecisionScale = decimalPrecisionScale;
    }

    public int toIndex(double coordinate) {
        int scaledCoordinate = (int) IndexSize.scale(coordinate, decimalPrecisionScale);
        return scaledCoordinate >> power;
    }

    public double toCoordinate(int index) {
        int scaledCoordinate = index << power;
        return IndexSize.unScale(scaledCoordinate, decimalPrecisionScale);
    }

    public static IndexSize of(int power) {
        return new IndexSize(power, 0);
    }

    public static IndexSize of(int power, int decimalPrecisionScale) {
        return new IndexSize(power, decimalPrecisionScale);
    }

    private static final int LIMIT_DECIMAL_PRECISION_SCALE = 2;
    private static final int LIMIT_DECIMAL_PRECISION_SCALE_AFTER_BORDER = 100;
    private static final int DIVISIONS_CAPACITY = 2;

    public static IndexSize optimal(double expectedBorderSize, double expectedBoxSize) {
        int decimalPlacesPrecisionScale = Math.max(IndexSize.decimalPlaces(expectedBorderSize), IndexSize.decimalPlaces(expectedBoxSize));

        if (expectedBorderSize > LIMIT_DECIMAL_PRECISION_SCALE_AFTER_BORDER) {
            decimalPlacesPrecisionScale = 0;
        }

        if (decimalPlacesPrecisionScale > LIMIT_DECIMAL_PRECISION_SCALE) {
            decimalPlacesPrecisionScale = LIMIT_DECIMAL_PRECISION_SCALE;
        }

        int scaledBorderSize = (int) IndexSize.scale(expectedBorderSize, decimalPlacesPrecisionScale);
        double scaledBoxSize = IndexSize.scale(expectedBoxSize, decimalPlacesPrecisionScale);

        if (scaledBoxSize == 0) {
            throw new IllegalArgumentException("Box wall size cannot be 0");
        }

        double divisions = scaledBorderSize / scaledBoxSize;
        double biggerDivisions = divisions / DIVISIONS_CAPACITY;
        int power = 0;

        while (scaledBorderSize >> power > biggerDivisions) {
            power++;
        }

        return new IndexSize(power, decimalPlacesPrecisionScale);
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
