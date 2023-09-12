package dev.rollczi.liteindex;

import dev.rollczi.liteindex.mock.Area;
import dev.rollczi.liteindex.mock.Vector3d;
import dev.rollczi.liteindex.space.SpaceIndex;

import java.util.Random;

public class Main {

    private final static Random RANDOM = new Random();
    private final static int MAP_SIZE = 5_000;

    public static void main(String[] args) {
        // test space index time

        SpaceIndex<Area, Vector3d> spaceIndex = SpaceIndex.<Area, Vector3d>builder()
                .axisX(vector3d -> vector3d.getX())
                .axisY(vector3d -> vector3d.getY())
                .axisZ(vector3d -> vector3d.getZ())
                .space(area -> area.getMin(), area -> area.getMax())
                .build();

        for (int index = 0; index < 100_000; index++) {
            spaceIndex.put(randomArea());
        }

        long start = System.nanoTime();

        int findCount = 10_000;
        for (int index = 0; index < findCount; index++) {
            spaceIndex.get(randomVector());
        }

        long end = System.nanoTime();
        long millis = (end - start) / 1_000_000;
        double average = (double) (end - start) / findCount / 1000000;

        System.out.println("Map size: " + MAP_SIZE);
        System.out.println("All time: " + millis);
        System.out.println("Areas count: " + spaceIndex.getAll().size() + "ms");
        System.out.println("Average find plot response: " + average + "ms");

        // test space index memory

        long memory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

        System.out.println("Memory: " + memory / 1024 / 1024 + "MB");
    }

    private static Area randomArea() {
        Vector3d min = new Vector3d(RANDOM.nextDouble() * MAP_SIZE, RANDOM.nextDouble() * MAP_SIZE, RANDOM.nextDouble() * MAP_SIZE);
        Vector3d max = new Vector3d(min.getX() + 80, min.getY() + 80, min.getZ() + 80);

        return new Area(min, max);
    }

    private static Vector3d randomVector() {
        return new Vector3d(RANDOM.nextDouble() * MAP_SIZE, RANDOM.nextDouble() * MAP_SIZE, RANDOM.nextDouble() * MAP_SIZE);
    }

}
