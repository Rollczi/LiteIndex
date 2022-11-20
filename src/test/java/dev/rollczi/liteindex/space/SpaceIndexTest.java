package dev.rollczi.liteindex.space;

import dev.rollczi.liteindex.mock.Area;
import dev.rollczi.liteindex.mock.Vector3d;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SpaceIndexTest {

    @Test
    void testSimple() {
        SpaceIndex<Area, Vector3d> spaceIndex = new SpaceIndexBuilder<Area, Vector3d>()
                .axisX(Vector3d::getX)
                .axisY(Vector3d::getY)
                .axisZ(Vector3d::getZ)
                .space(Area::getMin, Area::getMax)
                .indexSize(IndexSize.of(4))
                .build();

        spaceIndex.put(Area.ZERO_TO_ONE);

        Set<Area> spaces = spaceIndex.get(new Vector3d(0.5, 0.5, 0.5));

        assertEquals(1, spaces.size());
    }


    @Test
    void testManySpaces() {
        SpaceIndex<Area, Vector3d> spaceIndex = new SpaceIndexBuilder<Area, Vector3d>()
                .axisX(Vector3d::getX)
                .axisY(Vector3d::getY)
                .axisZ(Vector3d::getZ)
                .space(Area::getMin, Area::getMax)
                .indexSize(IndexSize.of(7, 1))
                .build();

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
            Set<Area> spaces = spaceIndex.get(new Vector3d(0.5, 0.5, 0.5));
            assertEquals(1, spaces.size());
        }

        {
            Set<Area> spaces = spaceIndex.get(new Vector3d(1.0, 1.0, 1.0));
            assertEquals(2, spaces.size());
        }

        {
            Set<Area> spaces = spaceIndex.get(new Vector3d(2.5, 2.5, 2.5));
            assertEquals(1, spaces.size());
        }
    }

}
