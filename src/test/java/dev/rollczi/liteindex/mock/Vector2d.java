package dev.rollczi.liteindex.mock;

import dev.rollczi.liteindex.axis.Axis;

public class Vector2d {

    public static final Axis<Vector2d> AXIS_X = Axis.of("x", Vector2d::getX);
    public static final Axis<Vector2d> AXIS_Y = Axis.of("y", Vector2d::getY);

    private final double x;
    private final double y;

    public Vector2d(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

}
