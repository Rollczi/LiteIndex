package dev.rollczi.liteindex.axis;

import java.util.Optional;

public class AxesState<VECTOR> {

    private final AxesSet<VECTOR> axesSet;
    private final Axis<VECTOR> currentAxis;
    private final int currentAxisIndex;

    private AxesState(AxesSet<VECTOR> axesSet, Axis<VECTOR> currentAxis, int currentAxisIndex) {
        this.axesSet = axesSet;
        this.currentAxis = currentAxis;
        this.currentAxisIndex = currentAxisIndex;
    }

    public Axis<VECTOR> getCurrentAxis() {
        return axesSet.getAxis(currentAxisIndex)
                .orElseThrow(IllegalStateException::new);
    }

    public Optional<AxesState<VECTOR>> withNextAxis() {
        int nextIndex = currentAxisIndex + 1;

        return axesSet.getAxis(nextIndex)
                .map(vectorAxis -> new AxesState<>(axesSet, currentAxis, nextIndex));
    }

    public static <V> AxesState<V> create(AxesSet<V> axesSet) {
        int startIndex = 0;
        Axis<V> axis = axesSet.getAxis(startIndex)
                .orElseThrow(IllegalStateException::new);

        return new AxesState<>(axesSet, axis, startIndex);
    }

}
