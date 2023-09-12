package dev.rollczi.liteindex.axis;

import dev.rollczi.liteindex.shared.Validation;

import java.util.*;

public class AxesSet<VECTOR> {

    private final List<Axis<VECTOR>> axes = new ArrayList<>();

    private AxesSet() {}

    Optional<Axis<VECTOR>> getAxis(int index) {
        if (index >= axes.size()) {
            return Optional.empty();
        }

        return Optional.of(axes.get(index));
    }

    List<Axis<VECTOR>> getAxes() {
        return Collections.unmodifiableList(axes);
    }

    @SafeVarargs
    public static <T> AxesSet<T> create(Axis<T>... axes) {
        Validation.isNotNull(axes, "axes can not be null");

        return create(Arrays.asList(axes));
    }

    public static <T> AxesSet<T> create(Collection<Axis<T>> axes) {
        Validation.isNotNull(axes, "axes can not be null");
        Validation.isNotEmpty(axes, "axes can not be empty");

        AxesSet<T> axesSet = new AxesSet<>();

        for (Axis<T> axis : axes) {
            Validation.isNotNull(axis, "axis can not be null");

            axesSet.axes.add(axis);
        }

        return axesSet;
    }

}
