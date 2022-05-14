package edu.uoc.epcsd.showcatalog.utils;

import java.util.Objects;
import java.util.function.Predicate;

public class ValueValidator {

    public static boolean nonNull(Object... objects) {
        return allSatisfies(Objects::nonNull, objects);
    }

    public static boolean nonNullOrBlank(String... strings) {
        return allSatisfies(s -> s != null && !s.isBlank(), strings);
    }

    @SafeVarargs
    public static <N extends Number> boolean nonNullOrNegative(N... numbers) {
        return nonNullOrNegative(true, numbers);
    }

    @SafeVarargs
    public static <N extends Number> boolean nonNullOrNegativeZeroInclusive(N... numbers) {
        return nonNullOrNegative(false, numbers);
    }

    @SafeVarargs
    private static <N extends Number> boolean nonNullOrNegative(boolean isZeroInclusive, N... numbers) {
        return allSatisfies(n -> n.doubleValue() >= 0 && (isZeroInclusive || n.doubleValue() != 0), numbers);
    }

    @SafeVarargs
    public static <T> boolean allSatisfies(Predicate<T> predicate, T... objects) {
        return ListUtils.all(predicate, objects);
    }
}
