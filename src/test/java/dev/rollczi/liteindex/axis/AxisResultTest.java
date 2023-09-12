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

//
//    @Test
//    void testOrderOfAxes() {
//        AxisResult<Vector3d> result = AxisResult.from(AXES_XYZ, new Vector3d(10, 20, 30));
//
//        Iterator<AxisResultEntry<Vector3d>> results = result.iterator();
//        AxisResultEntry<Vector3d> resultX = results.next();
//        AxisResultEntry<Vector3d> resultY = results.next();
//        AxisResultEntry<Vector3d> resultZ = results.next();
//
//        assertEquals(Vector3d.AXIS_X, resultX.getAxis());
//        assertEquals(Vector3d.AXIS_Y, resultY.getAxis());
//        assertEquals(Vector3d.AXIS_Z, resultZ.getAxis());
//
//        assertEquals(10, resultX.getCoordinate());
//        assertEquals(20, resultY.getCoordinate());
//        assertEquals(30, resultZ.getCoordinate());
//    }

}
