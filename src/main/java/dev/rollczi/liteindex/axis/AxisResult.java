package dev.rollczi.liteindex.axis;

import dev.rollczi.liteindex.shared.Validation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class AxisResult<VECTOR> implements Iterable<AxisResultEntry<VECTOR>> {

    private final VECTOR vector;
    private final List<Axis<VECTOR>> axes = new ArrayList<>();

    AxisResult(VECTOR vector) {
        this.vector = vector;
    }

    AxisResult<VECTOR> withAxis(Axis<VECTOR> axis) {
        Validation.isNotNull(axis, "axis can not be null");

        AxisResult<VECTOR> newResult = new AxisResult<>(this.vector);

        newResult.axes.addAll(this.axes);
        newResult.axes.add(axis);

        return newResult;
    }

    @Override
    public Iterator<AxisResultEntry<VECTOR>> iterator() {
        return new AxesIterator();
    }

    private class AxesIterator implements Iterator<AxisResultEntry<VECTOR>> {

        private int nextIndex = 0;

        @Override
        public boolean hasNext() {
            return axes.size() > nextIndex;
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

}
