package dev.rollczi.liteindex.space;

import dev.rollczi.liteindex.mock.Area;
import dev.rollczi.liteindex.mock.Vector1d;
import dev.rollczi.liteindex.mock.Vector3d;
import dev.rollczi.liteindex.space.indexing.IndexingAlgorithm;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SpaceIndexTest {

    @Test
    void testPutAndGet() {
        SpaceIndex<Area, Vector3d> spaceIndex = this.createSpaceIndex(4, 1);

        spaceIndex.put(Area.ZERO_TO_ONE);

        List<Area> spaces = spaceIndex.get(new Vector3d(0.5, 0.5, 0.5));

        assertEquals(1, spaces.size());
    }


    @Test
    void testManySpaces() {
        SpaceIndex<Area, Vector3d> spaceIndex = this.createSpaceIndex(4, 1);

        Area areaFirst = new Area(new Vector3d(0, 0, 0), new Vector3d(1, 1, 1));
        Area areaSecond = new Area(new Vector3d(1, 1, 1), new Vector3d(2, 2, 2));
        Area areaThird = new Area(new Vector3d(2, 2, 2), new Vector3d(3, 3, 3));

        {
            assertFalse(spaceIndex.contains(areaFirst));
            assertFalse(spaceIndex.contains(areaSecond));
            assertFalse(spaceIndex.contains(areaThird));

            spaceIndex.put(areaFirst);
            spaceIndex.put(areaSecond);
            spaceIndex.put(areaThird);

            assertTrue(spaceIndex.contains(areaFirst));
            assertTrue(spaceIndex.contains(areaSecond));
            assertTrue(spaceIndex.contains(areaThird));
        }

        {
            List<Area> spaces = spaceIndex.get(new Vector3d(0.5, 0.5, 0.5));
            assertEquals(1, spaces.size());
        }

        {
            List<Area> spaces = spaceIndex.get(new Vector3d(1.0, 1.0, 1.0));
            assertEquals(2, spaces.size());
        }

        {
            List<Area> spaces = spaceIndex.get(new Vector3d(2.5, 2.5, 2.5));
            assertEquals(1, spaces.size());
        }
    }

    @Test
    void testRemove() {
        SpaceIndex<Area, Vector3d> spaceIndex = this.createSpaceIndex(4, 1);

        Area areaFirst = new Area(new Vector3d(0, 0, 0), new Vector3d(1, 1, 1));
        Area areaSecond = new Area(new Vector3d(1, 1, 1), new Vector3d(2, 2, 2));
        Area areaThird = new Area(new Vector3d(2, 2, 2), new Vector3d(3, 3, 3));

        spaceIndex.put(areaFirst);
        spaceIndex.put(areaSecond);
        spaceIndex.put(areaThird);

        assertTrue(spaceIndex.contains(areaFirst));
        assertTrue(spaceIndex.contains(areaSecond));
        assertTrue(spaceIndex.contains(areaThird));

        spaceIndex.remove(areaSecond);

        assertTrue(spaceIndex.contains(areaFirst));
        assertFalse(spaceIndex.contains(areaSecond));
        assertTrue(spaceIndex.contains(areaThird));
    }

    @Test
    void testRemoveAll() {
        SpaceIndex<Area, Vector3d> spaceIndex = this.createSpaceIndex(4, 1);

        Area areaFirst = new Area(new Vector3d(0, 0, 0), new Vector3d(1, 1, 1));
        Area areaSecond = new Area(new Vector3d(1, 1, 1), new Vector3d(2, 2, 2));
        Area areaThird = new Area(new Vector3d(2, 2, 2), new Vector3d(3, 3, 3));

        spaceIndex.put(areaFirst);
        spaceIndex.put(areaSecond);
        spaceIndex.put(areaThird);

        assertTrue(spaceIndex.contains(areaFirst));
        assertTrue(spaceIndex.contains(areaSecond));
        assertTrue(spaceIndex.contains(areaThird));

        spaceIndex.removeAll();

        assertFalse(spaceIndex.contains(areaFirst));
        assertFalse(spaceIndex.contains(areaSecond));
        assertFalse(spaceIndex.contains(areaThird));
    }

    @Test
    void testGetAll() {
        SpaceIndex<Area, Vector3d> spaceIndex = this.createSpaceIndex(4, 1);

        Area areaFirst = new Area(new Vector3d(0, 0, 0), new Vector3d(1, 1, 1));
        Area areaSecond = new Area(new Vector3d(1, 1, 1), new Vector3d(2, 2, 2));
        Area areaThird = new Area(new Vector3d(2, 2, 2), new Vector3d(3, 3, 3));

        spaceIndex.put(areaFirst);
        spaceIndex.put(areaSecond);
        spaceIndex.put(areaThird);

        Set<Area> spaces = spaceIndex.getAll();
        assertEquals(3, spaces.size());
    }

    @Test
    void testGetFirst() {
        SpaceIndex<Area, Vector3d> spaceIndex = this.createSpaceIndex(4, 1);

        Area areaFirst = new Area(Vector3d.ZERO, Vector3d.ONE);
        spaceIndex.put(areaFirst);

        Optional<Area> optionalArea = spaceIndex.getFirst(new Vector3d(0.5, 0.5, 0.5));

        assertTrue(optionalArea.isPresent());
        assertEquals(areaFirst, optionalArea.get());
    }

    @Test
    void testCustomAxisIndexing() {
        SpaceIndex<Area, Vector3d> spaceIndex = new SpaceIndexBuilder<Area, Vector3d>()
                .axisX(Vector3d::getX, IndexingAlgorithm.chunk(4))
                .axisY(Vector3d::getY, IndexingAlgorithm.chunk(8))
                .axisZ(Vector3d::getZ, IndexingAlgorithm.chunk(4))
                .space(Area::getMin, Area::getMax)
                .build();

        Area areaFirst = Area.of(0, 256, 0, 1, 256, 1);
        Area areaSecond = Area.of(16, 256, 16, 17, 256, 17);
        Area areaThird = Area.of(32, 0, 32, 33, 0, 33);
        Area bigArea = Area.of(0, 0, 0, 64, 256, 64);

        spaceIndex.put(areaFirst);
        spaceIndex.put(areaSecond);
        spaceIndex.put(areaThird);
        spaceIndex.put(bigArea);

        List<Area> spaces = spaceIndex.get(new Vector3d(0.5, 256, 0.5));
        assertEquals(2, spaces.size());

        spaces = spaceIndex.get(new Vector3d(16.5, 256, 16.5));
        assertEquals(2, spaces.size());

        spaces = spaceIndex.get(new Vector3d(32.5, 0, 32.5));
        assertEquals(2, spaces.size());

        spaces = spaceIndex.get(new Vector3d(0.5, 0, 0.5));
        assertEquals(1, spaces.size());

        spaces = spaceIndex.get(new Vector3d(16.5, 0, 16.5));
        assertEquals(1, spaces.size());

        spaces = spaceIndex.get(new Vector3d(32.5, 256, 32.5));
        assertEquals(1, spaces.size());
    }

    private SpaceIndex<Area, Vector3d> createSpaceIndex(int chunkSize, int precision) {
        return new SpaceIndexBuilder<Area, Vector3d>()
                .axisX(Vector3d::getX)
                .axisY(Vector3d::getY)
                .axisZ(Vector3d::getZ)
                .space(Area::getMin, Area::getMax)
                .indexing(IndexingAlgorithm.chunk(chunkSize, precision))
                .build();
    }

}
