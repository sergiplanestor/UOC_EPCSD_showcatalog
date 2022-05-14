package edu.uoc.epcsd.showcatalog.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ListUtils {

    private ListUtils() {
    }

    public static <T, R> List<R> map(List<T> list, Function<? super T, ? extends R> mapper) {
        return stream(list).map(mapper).collect(Collectors.toList());
    }

    @SafeVarargs
    public static <T> boolean all(Predicate<T> predicate, T... array) {
        return all(Arrays.asList(array), predicate);
    }

    public static <T> boolean all(List<T> list, Predicate<T> predicate) {
        return stream(list).allMatch(predicate);
    }

    public static <T> boolean any(List<T> list, Predicate<T> predicate) {
        return stream(list).anyMatch(predicate);
    }

    public static <T> T find(List<T> list, Predicate<T> predicate) {
        return find(list, null, predicate);
    }

    public static <T> T find(List<T> list, T fallback, Predicate<? super T> predicate) {
        List<T> matches = filter(list, predicate);
        if (matches.isEmpty()) {
            return fallback;
        } else {
            return matches.get(0);
        }
    }

    public static <T> List<T> filter(List<T> list, Predicate<? super T> predicate) {
        return stream(list).filter(predicate).collect(Collectors.toList());
    }

    private static <T> Stream<T> stream(List<T> list) {
        return (list == null ? new ArrayList<T>() : list).stream();
    }
}
