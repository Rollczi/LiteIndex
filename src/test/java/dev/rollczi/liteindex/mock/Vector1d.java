package dev.rollczi.liteindex.mock;

import dev.rollczi.liteindex.axis.Axis;

public class Vector1d {

    public static final Axis<Vector1d> AXIS_X = Axis.of("x", Vector1d::getX);

    private final double x;

    public Vector1d(double x) {
        this.x = x;
    }

    public static Vector1d of(double x) {
        return new Vector1d(x);
    }

    public double getX() {
        return x;
    }

}
