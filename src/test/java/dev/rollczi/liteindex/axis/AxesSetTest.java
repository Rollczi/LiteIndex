package dev.rollczi.liteindex.axis;

import dev.rollczi.liteindex.mock.Vector2d;
import dev.rollczi.liteindex.mock.Vector3d;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AxesSetTest {

    private static final AxesSet<Vector3d> AXES_XYZ = AxesSet.create(
            Axis.of("x", Vector3d::getX),
            Axis.of("y", Vector3d::getY),
            Axis.of("z", Vector3d::getZ)
    );

    @Test
    void testValueOrderExtracting() {
        AxesIterator<Vector3d> result = new AxesIterator<>(AXES_XYZ, new Vector3d(10, 20, 30));

        assertEquals(10, result.next().getCoordinate());
        assertEquals(20, result.next().getCoordinate());
        assertEquals(30, result.next().getCoordinate());

        assertThrows(NoSuchElementException.class, () -> result.next());
    }

    @Test
    void testNameOrder() {
        AxesIterator<Vector3d> result = new AxesIterator<>(AXES_XYZ, new Vector3d(10, 20, 30));

        assertEquals("x", result.next().getAxis().getName());
        assertEquals("y", result.next().getAxis().getName());
        assertEquals("z", result.next().getAxis().getName());
    }

    @Test
    void testCreateWithNullArray() {
        assertThrows(IllegalArgumentException.class, () -> AxesSet.create((Axis<?>) null));
    }

    @Test
    void testCreateWithNullElement() {
        assertThrows(IllegalArgumentException.class, () -> AxesSet.create(Axis.of("x", Vector2d::getX), null));
    }

}
