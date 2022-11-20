package dev.rollczi.liteindex.shared;

import java.util.Collection;

public final class Validation {

    private Validation() {
    }

    public static void isTrue(boolean value, String message) {
        if (!value) throw new IllegalArgumentException(message);
    }

    public static void isFalse(boolean value, String message) {
        if (value) throw new IllegalArgumentException(message);
    }

    public static void isNotNull(Object value, String message) {
        if (value == null) throw new IllegalArgumentException(message);
    }

    public static void isNull(Object value, String message) {
        if (value != null) throw new IllegalArgumentException(message);
    }

    public static void isEmpty(Collection<?> value, String message) {
        if (!value.isEmpty()) throw new IllegalArgumentException(message);
    }

    public static void isNotEmpty(Collection<?> value, String message) {
        if (value.isEmpty()) throw new IllegalArgumentException(message);
    }

}
