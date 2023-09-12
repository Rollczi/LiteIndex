package dev.rollczi.liteindex.mock;

import dev.rollczi.liteindex.axis.Axis;

public class Vector3d {

    public static final Vector3d ZERO = new Vector3d(0, 0, 0);
    public static final Vector3d ONE = new Vector3d(1, 1, 1);

    public static final Axis<Vector3d> AXIS_X = Axis.of("x", Vector3d::getX);
    public static final Axis<Vector3d> AXIS_Y = Axis.of("y", Vector3d::getY);
    public static final Axis<Vector3d> AXIS_Z = Axis.of("z", Vector3d::getZ);

    private final double x;
    private final double y;
    private final double z;

    public Vector3d(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

}
