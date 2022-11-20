package dev.rollczi.liteindex.axis;

public class AxisResultEntry<VECTOR> {

    private final Axis<VECTOR> axis;
    private final double coordinate;

    AxisResultEntry(Axis<VECTOR> axis, double coordinate) {
        this.axis = axis;
        this.coordinate = coordinate;
    }

    public Axis<VECTOR> getAxis() {
        return axis;
    }

    public double getCoordinate() {
        return coordinate;
    }

}
