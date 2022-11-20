package dev.rollczi.liteindex.space;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IndexSizeTest {

    @Test
    void testNormal() {
        IndexSize indexSize = IndexSize.of(4);

        assertEquals(0, indexSize.toIndex(0));
        assertEquals(0, indexSize.toIndex(15));
        assertEquals(1, indexSize.toIndex(16));
        assertEquals(1, indexSize.toIndex(31));
        assertEquals(2, indexSize.toIndex(32));

        assertEquals(0, indexSize.toCoordinate(0));
        assertEquals(16, indexSize.toCoordinate(1));
        assertEquals(32, indexSize.toCoordinate(2));
    }

    @Test
    void testDecimal() {
        IndexSize indexSize = IndexSize.of(4, 2);

        assertEquals(0, indexSize.toIndex(0));
        assertEquals(0, indexSize.toIndex(0.15));
        assertEquals(1, indexSize.toIndex(0.16));
        assertEquals(1, indexSize.toIndex(0.31));
        assertEquals(2, indexSize.toIndex(0.32));

        assertEquals(0, indexSize.toCoordinate(0));
        assertEquals(0.16, indexSize.toCoordinate(1));
        assertEquals(0.32, indexSize.toCoordinate(2));
    }

    @Test
    void testOptimal() {
        IndexSize indexSize = IndexSize.optimal(100, 0.04);

        assertEquals(0, indexSize.toIndex(0));
        assertEquals(0, indexSize.toIndex(0.07));
        assertEquals(1, indexSize.toIndex(0.08));
        assertEquals(1, indexSize.toIndex(0.15));
        assertEquals(2, indexSize.toIndex(0.16));

        assertEquals(0, indexSize.toCoordinate(0));
        assertEquals(0.08, indexSize.toCoordinate(1));
        assertEquals(0.16, indexSize.toCoordinate(2));
    }

    @Test
    void testOptimalLimitBorderWithDecimal() {
        IndexSize indexSize = IndexSize.optimal(100000, 0.04);

        assertEquals(0, indexSize.toIndex(0.00));
        assertEquals(0, indexSize.toIndex(0.99));
        assertEquals(1, indexSize.toIndex(1.00));
        assertEquals(1, indexSize.toIndex(1.99));
        assertEquals(2, indexSize.toIndex(2.00));

        assertEquals(0, indexSize.toCoordinate(0));
        assertEquals(1.00, indexSize.toCoordinate(1));
        assertEquals(2.00, indexSize.toCoordinate(2));
    }

}
