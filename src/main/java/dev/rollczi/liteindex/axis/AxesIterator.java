package dev.rollczi.liteindex.axis;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class AxesIterator<VECTOR> implements Iterator<AxisResultEntry<VECTOR>> {

    private final VECTOR vector;
    private final List<Axis<VECTOR>> axes;
    private final int size;

    private int nextIndex = 0;

    public AxesIterator(AxesSet<VECTOR> axesSet, VECTOR vector) {
        this.vector = vector;
        this.axes = axesSet.getAxes();
        this.size = axes.size();
    }

    @Override
    public boolean hasNext() {
        return size > nextIndex;
    }

    @Override
    public AxisResultEntry<VECTOR> next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        Axis<VECTOR> vectorAxis = axes.get(nextIndex++);

        return new AxisResultEntry<>(vectorAxis, vectorAxis.getAxisCoordinate(vector));
    }

}
