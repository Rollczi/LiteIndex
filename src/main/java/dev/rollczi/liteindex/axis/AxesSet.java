package dev.rollczi.liteindex.axis;

import dev.rollczi.liteindex.shared.Validation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class AxesSet<VECTOR> {

    private final List<Axis<VECTOR>> axes = new ArrayList<>();

    private AxesSet() {}

    public AxisResult<VECTOR> createResult(VECTOR vector) {
        AxisResult<VECTOR> result = new AxisResult<>(vector);

        for (Axis<VECTOR> axis : axes) {
            result = result.withAxis(axis);
        }

        return result;
    }

    Optional<Axis<VECTOR>> getAxis(int index) {
        if (index >= axes.size()) {
            return Optional.empty();
        }

        return Optional.of(axes.get(index));
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
