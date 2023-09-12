package dev.rollczi.liteindex.space;

import dev.rollczi.liteindex.mock.Vector1d;
import dev.rollczi.liteindex.space.indexing.IndexingAlgorithm;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IndexingAlgorithmTest {

    @Test
    void testNormal() {
        IndexingAlgorithm<Vector1d> spaceIndexSize = IndexingAlgorithm.chunk(4);

        assertEquals(0, spaceIndexSize.toIndex(Vector1d.AXIS_X, Vector1d.of(0)));
        assertEquals(0, spaceIndexSize.toIndex(Vector1d.AXIS_X, Vector1d.of(15)));
        assertEquals(1, spaceIndexSize.toIndex(Vector1d.AXIS_X, Vector1d.of(16)));
        assertEquals(1, spaceIndexSize.toIndex(Vector1d.AXIS_X, Vector1d.of(31)));
        assertEquals(2, spaceIndexSize.toIndex(Vector1d.AXIS_X, Vector1d.of(32)));

        assertEquals(0, spaceIndexSize.toCoordinate(0));
        assertEquals(16, spaceIndexSize.toCoordinate(1));
        assertEquals(32, spaceIndexSize.toCoordinate(2));
    }

    @Test
    void testDecimal() {
        IndexingAlgorithm<Vector1d> spaceIndexSize = IndexingAlgorithm.chunk(4, 2);

        assertEquals(0, spaceIndexSize.toIndex(Vector1d.AXIS_X, Vector1d.of(0.00)));
        assertEquals(0, spaceIndexSize.toIndex(Vector1d.AXIS_X, Vector1d.of(0.15)));
        assertEquals(1, spaceIndexSize.toIndex(Vector1d.AXIS_X, Vector1d.of(0.16)));
        assertEquals(1, spaceIndexSize.toIndex(Vector1d.AXIS_X, Vector1d.of(0.31)));
        assertEquals(2, spaceIndexSize.toIndex(Vector1d.AXIS_X, Vector1d.of(0.32)));

        assertEquals(0, spaceIndexSize.toCoordinate(0));
        assertEquals(0.16, spaceIndexSize.toCoordinate(1));
        assertEquals(0.32, spaceIndexSize.toCoordinate(2));
    }

    @Test
    void testOptimalSmallBorderAndVerySmallBox() {
        IndexingAlgorithm<Vector1d> spaceIndexSize = IndexingAlgorithm.calculateOptimal(100, 0.04);

        assertEquals(0, spaceIndexSize.toCoordinate(0));
        assertEquals(0.16, spaceIndexSize.toCoordinate(1));
        assertEquals(0.32, spaceIndexSize.toCoordinate(2));

        assertEquals(0, spaceIndexSize.toIndex(Vector1d.AXIS_X, Vector1d.of(0.00)));
        assertEquals(0, spaceIndexSize.toIndex(Vector1d.AXIS_X, Vector1d.of(0.15)));
        assertEquals(1, spaceIndexSize.toIndex(Vector1d.AXIS_X, Vector1d.of(0.16)));
        assertEquals(1, spaceIndexSize.toIndex(Vector1d.AXIS_X, Vector1d.of(0.31)));
        assertEquals(2, spaceIndexSize.toIndex(Vector1d.AXIS_X, Vector1d.of(0.32)));
    }


    @Test
    void testOptimalBigBorderAndVerySmallBox() {
        IndexingAlgorithm<Vector1d> spaceIndexSize = IndexingAlgorithm.calculateOptimal(100000, 0.04);

        assertEquals(0, spaceIndexSize.toCoordinate(0));
        assertEquals(163.84, spaceIndexSize.toCoordinate(1));
        assertEquals(327.68, spaceIndexSize.toCoordinate(2));

        assertEquals(0, spaceIndexSize.toIndex(Vector1d.AXIS_X, Vector1d.of(0.00)));
        assertEquals(0, spaceIndexSize.toIndex(Vector1d.AXIS_X, Vector1d.of(163.83)));
        assertEquals(1, spaceIndexSize.toIndex(Vector1d.AXIS_X, Vector1d.of(163.84)));
        assertEquals(1, spaceIndexSize.toIndex(Vector1d.AXIS_X, Vector1d.of(327.67)));
        assertEquals(2, spaceIndexSize.toIndex(Vector1d.AXIS_X, Vector1d.of(327.68)));
    }

    @Test
    void testOptimalSmallBorderAndSmallBox() {
        IndexingAlgorithm<Vector1d> spaceIndexSize = IndexingAlgorithm.calculateOptimal(100, 1);

        assertEquals(0, spaceIndexSize.toCoordinate(0));
        assertEquals(2.0, spaceIndexSize.toCoordinate(1));
        assertEquals(4.0, spaceIndexSize.toCoordinate(2));

        assertEquals(0, spaceIndexSize.toIndex(Vector1d.AXIS_X, Vector1d.of(0)));
        assertEquals(0, spaceIndexSize.toIndex(Vector1d.AXIS_X, Vector1d.of(1)));
        assertEquals(1, spaceIndexSize.toIndex(Vector1d.AXIS_X, Vector1d.of(2)));
        assertEquals(1, spaceIndexSize.toIndex(Vector1d.AXIS_X, Vector1d.of(3)));
        assertEquals(2, spaceIndexSize.toIndex(Vector1d.AXIS_X, Vector1d.of(4)));
    }

    @Test
    void testOptimalMiddleBorderAndSmallBox() {
        IndexingAlgorithm<Vector1d> spaceIndexSize = IndexingAlgorithm.calculateOptimal(10_000, 1);

        assertEquals(0, spaceIndexSize.toCoordinate(0));
        assertEquals(64, spaceIndexSize.toCoordinate(1));
        assertEquals(128, spaceIndexSize.toCoordinate(2));

        assertEquals(0, spaceIndexSize.toIndex(Vector1d.AXIS_X, Vector1d.of(0)));
        assertEquals(0, spaceIndexSize.toIndex(Vector1d.AXIS_X, Vector1d.of(63)));
        assertEquals(1, spaceIndexSize.toIndex(Vector1d.AXIS_X, Vector1d.of(64)));
        assertEquals(1, spaceIndexSize.toIndex(Vector1d.AXIS_X, Vector1d.of(127)));
        assertEquals(2, spaceIndexSize.toIndex(Vector1d.AXIS_X, Vector1d.of(128)));
    }


    @Test
    void testOptimalBigBorderAndMiddleBox() {
        IndexingAlgorithm<Vector1d> spaceIndexSize = IndexingAlgorithm.calculateOptimal(100_000_000, 100);

        assertEquals(0, spaceIndexSize.toCoordinate(0));
        assertEquals(8192, spaceIndexSize.toCoordinate(1));
        assertEquals(16384, spaceIndexSize.toCoordinate(2));

        assertEquals(0, spaceIndexSize.toIndex(Vector1d.AXIS_X, Vector1d.of(0)));
        assertEquals(0, spaceIndexSize.toIndex(Vector1d.AXIS_X, Vector1d.of(8191)));
        assertEquals(1, spaceIndexSize.toIndex(Vector1d.AXIS_X, Vector1d.of(8192)));
        assertEquals(1, spaceIndexSize.toIndex(Vector1d.AXIS_X, Vector1d.of(16383)));
        assertEquals(2, spaceIndexSize.toIndex(Vector1d.AXIS_X, Vector1d.of(16384)));
    }

}
