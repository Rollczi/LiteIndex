package dev.rollczi.liteindex.axis;

@FunctionalInterface
public interface AxisCoordinateProvider<VECTOR> {

    double getAxisCoordinate(VECTOR vector);

}
