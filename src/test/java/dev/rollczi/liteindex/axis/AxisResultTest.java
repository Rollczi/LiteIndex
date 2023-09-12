package dev.rollczi.liteindex.axis;

import dev.rollczi.liteindex.mock.Vector3d;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AxisResultTest {

    private static final AxesSet<Vector3d> AXES_XYZ = AxesSet.create(
            Axis.of("x", Vector3d::getX),
            Axis.of("y", Vector3d::getY),
            Axis.of("z", Vector3d::getZ)
    );


    @Test
    void testOrderOfAxes() {
        AxesIterator<Vector3d> result = new AxesIterator<>(AXES_XYZ, new Vector3d(10, 20, 30));

        AxisResultEntry<Vector3d> resultX = result.next();
        AxisResultEntry<Vector3d> resultY = result.next();
        AxisResultEntry<Vector3d> resultZ = result.next();

        assertEquals(Vector3d.AXIS_X, resultX.getAxis());
        assertEquals(Vector3d.AXIS_Y, resultY.getAxis());
        assertEquals(Vector3d.AXIS_Z, resultZ.getAxis());

        assertEquals(10, resultX.getCoordinate());
        assertEquals(20, resultY.getCoordinate());
        assertEquals(30, resultZ.getCoordinate());
    }

}
