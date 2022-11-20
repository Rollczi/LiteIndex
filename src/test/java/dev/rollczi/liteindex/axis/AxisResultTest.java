package dev.rollczi.liteindex.axis;

import dev.rollczi.liteindex.mock.Vector3d;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AxisResultTest {

    @Test
    void testOrderOfAxes() {
        AxisResult<Vector3d> result = new AxisResult<>(new Vector3d(10, 20, 30))
                .withAxis(Vector3d.AXIS_X)
                .withAxis(Vector3d.AXIS_Y)
                .withAxis(Vector3d.AXIS_Z);

        Iterator<AxisResultEntry<Vector3d>> results = result.iterator();
        AxisResultEntry resultX = results.next();
        AxisResultEntry resultY = results.next();
        AxisResultEntry resultZ = results.next();

        assertEquals(Vector3d.AXIS_X, resultX.getAxis());
        assertEquals(Vector3d.AXIS_Y, resultY.getAxis());
        assertEquals(Vector3d.AXIS_Z, resultZ.getAxis());

        assertEquals(10, resultX.getCoordinate());
        assertEquals(20, resultY.getCoordinate());
        assertEquals(30, resultZ.getCoordinate());
    }

    @Test
    void testNull() {
        assertThrows(IllegalArgumentException.class, () -> new AxisResult<>(new Vector3d(10, 20, 30))
                .withAxis(null));
    }

}
