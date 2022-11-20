package dev.rollczi.liteindex.mock;

import java.util.UUID;

public class Area {

    private final UUID uuid;

    public static final Area ZERO_TO_ONE = new Area(
            new Vector3d(0, 0, 0),
            new Vector3d(1, 1, 1)
    );

    private final Vector3d min;
    private final Vector3d max;

    public Area(Vector3d min, Vector3d max) {
        this.min = min;
        this.max = max;
        this.uuid = UUID.randomUUID();
    }

    public Area(UUID uuid, Vector3d min, Vector3d max) {
        this.min = min;
        this.max = max;
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Vector3d getMin() {
        return min;
    }

    public Vector3d getMax() {
        return max;
    }

    public boolean isInside(Vector3d vector) {
        return vector.getX() >= min.getX() && vector.getX() <= max.getX()
                && vector.getY() >= min.getY() && vector.getY() <= max.getY()
                && vector.getZ() >= min.getZ() && vector.getZ() <= max.getZ();
    }

}
