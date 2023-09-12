package dev.rollczi.liteindex.axis;

import dev.rollczi.liteindex.mock.Vector2d;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AxisTest {

    @Test
    void testCreateAxis() {
        Axis<Vector2d> axis = Axis.of("name", Vector2d::getX);

        assertEquals("name", axis.getName());
    }

    @Test
    void testCreateAxisWithNull() {
        assertThrows(IllegalArgumentException.class, () -> Axis.of(null, null));
        assertThrows(IllegalArgumentException.class, () -> Axis.of(null, Vector2d::getX));
        assertThrows(IllegalArgumentException.class, () -> Axis.of("name", null));
    }

    @Test
    void testExtractCoordinates() {
        Vector2d vector = new Vector2d(10, 20);

        Axis<Vector2d> axisX = Axis.of("x", Vector2d::getX);
        double coordinateX = axisX.getAxisCoordinate(vector);

        Axis<Vector2d> axisY = Axis.of("y", Vector2d::getY);
        double coordinateY = axisY.getAxisCoordinate(vector);

        assertEquals(10, coordinateX);
        assertEquals(20, coordinateY);
    }

    @Test
    void testEquals() {
        EqualsVerifier.forClass(Axis.class)
                .withNonnullFields("name", "axisCoordinateProvider")
                .withIgnoredFields("axisCoordinateProvider")
                .verify();
    }

}
