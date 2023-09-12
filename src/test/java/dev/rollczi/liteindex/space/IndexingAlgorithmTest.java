package dev.rollczi.liteindex.space;

import dev.rollczi.liteindex.mock.Vector1d;
import dev.rollczi.liteindex.space.indexing.IndexingAlgorithm;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IndexingAlgorithmTest {

    // TODO: Add parametrized tests

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
    void testOptimal() {
        IndexingAlgorithm<Vector1d> spaceIndexSize = IndexingAlgorithm.optimalChunk(100, 0.04);

        assertEquals(0, spaceIndexSize.toIndex(Vector1d.AXIS_X, Vector1d.of(0.00)));
        assertEquals(0, spaceIndexSize.toIndex(Vector1d.AXIS_X, Vector1d.of(0.07)));
        assertEquals(1, spaceIndexSize.toIndex(Vector1d.AXIS_X, Vector1d.of(0.08)));
        assertEquals(1, spaceIndexSize.toIndex(Vector1d.AXIS_X, Vector1d.of(0.15)));
        assertEquals(2, spaceIndexSize.toIndex(Vector1d.AXIS_X, Vector1d.of(0.16)));

        assertEquals(0, spaceIndexSize.toCoordinate(0));
        assertEquals(0.08, spaceIndexSize.toCoordinate(1));
        assertEquals(0.16, spaceIndexSize.toCoordinate(2));
    }

    @Test
    void testOptimalLimitBorderWithDecimal() {
        IndexingAlgorithm<Vector1d> spaceIndexSize = IndexingAlgorithm.optimalChunk(100000, 0.04);

        assertEquals(0, spaceIndexSize.toIndex(Vector1d.AXIS_X, Vector1d.of(0.00)));
        assertEquals(0, spaceIndexSize.toIndex(Vector1d.AXIS_X, Vector1d.of(0.99)));
        assertEquals(1, spaceIndexSize.toIndex(Vector1d.AXIS_X, Vector1d.of(1.00)));
        assertEquals(1, spaceIndexSize.toIndex(Vector1d.AXIS_X, Vector1d.of(1.99)));
        assertEquals(2, spaceIndexSize.toIndex(Vector1d.AXIS_X, Vector1d.of(2.00)));

        assertEquals(0, spaceIndexSize.toCoordinate(0));
        assertEquals(1.00, spaceIndexSize.toCoordinate(1));
        assertEquals(2.00, spaceIndexSize.toCoordinate(2));
    }

}
