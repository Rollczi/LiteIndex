package dev.rollczi.liteindex.cases;

import dev.rollczi.liteindex.mock.Area;
import dev.rollczi.liteindex.mock.Vector3d;
import dev.rollczi.liteindex.space.SpaceIndex;
import dev.rollczi.liteindex.space.indexing.IndexingAlgorithm;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class TestCases {

    @Test
    void vLuckyCase() {
        SpaceIndex<Area, Vector3d> index = SpaceIndex.<Area, Vector3d>builder()
                .axisX(vector3d -> vector3d.getX())
                .axisZ(vector3d -> vector3d.getZ())
                .axisY(vector3d -> vector3d.getY())
                .space(area -> area.getMin(), area -> area.getMax())
                .indexing(IndexingAlgorithm.calculateOptimal(10_000, 75))
                .build();

        Vector3d min = new Vector3d(823, 0, 917);
        Vector3d max = new Vector3d(min.getX() + 21, 255, min.getZ() + 21);

        Area area = new Area(min, max);

        index.put(area);

        List<Area> areas = index.get(new Vector3d(823, 134, 927));

        assertThat(areas)
                .hasSize(1)
                .containsOnly(area);
    }

}
