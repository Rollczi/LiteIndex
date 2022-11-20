package dev.rollczi.liteindex.axis;

import dev.rollczi.liteindex.shared.Validation;

import java.util.Objects;

public final class Axis<VECTOR> {

    private final String name;
    private final AxisCoordinateProvider<VECTOR> axisCoordinateProvider;

    private Axis(String name, AxisCoordinateProvider<VECTOR> axisCoordinateProvider) {
        this.name = name;
        this.axisCoordinateProvider = axisCoordinateProvider;
    }

    public String getName() {
        return name;
    }

    public double getAxisCoordinate(VECTOR vector) {
        return axisCoordinateProvider.getAxisCoordinate(vector);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Axis)) return false;
        Axis<?> axis = (Axis<?>) o;
        return name.equals(axis.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public static <T> Axis<T> of(String name, AxisCoordinateProvider<T> extractor) {
        Validation.isNotNull(name, "name can not be null");
        Validation.isNotNull(extractor, "extractor can not be null");

        return new Axis<>(name, extractor);
    }

}
