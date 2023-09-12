package dev.rollczi.liteindex.axis;

import dev.rollczi.liteindex.mock.Vector2d;
import dev.rollczi.liteindex.mock.Vector3d;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AxesSetTest {

    private static final AxesSet<Vector3d> AXES_XYZ = AxesSet.create(
            Axis.of("x", Vector3d::getX),
            Axis.of("y", Vector3d::getY),
            Axis.of("z", Vector3d::getZ)
    );

//    @Test
//    void testValueOrderExtracting() {
//        AxisResult<Vector3d> result = AxisResult.from(AXES_XYZ, new Vector3d(10, 20, 30));
//        Iterator<AxisResultEntry<Vector3d>> entries = result.iterator();
//
//        assertEquals(10, entries.next().getCoordinate());
//        assertEquals(20, entries.next().getCoordinate());
//        assertEquals(30, entries.next().getCoordinate());
//    }
//
//    @Test
//    void testNameOrder() {
//        AxisResult<Vector3d> result = AxisResult.from(AXES_XYZ, new Vector3d(10, 20, 30));
//        Iterator<AxisResultEntry<Vector3d>> entries = result.iterator();
//
//        assertEquals("x", entries.next().getAxis().getName());
//        assertEquals("y", entries.next().getAxis().getName());
//        assertEquals("z", entries.next().getAxis().getName());
//    }

    @Test
    void testCreateWithNullArray() {
        assertThrows(IllegalArgumentException.class, () -> AxesSet.create((Axis<?>) null));
    }

    @Test
    void testCreateWithNullElement() {
        assertThrows(IllegalArgumentException.class, () -> AxesSet.create(Axis.of("x", Vector2d::getX), null));
    }

}
