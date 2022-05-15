package edu.uoc.epcsd.showcatalog.utils;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CollectionUtils {

    private CollectionUtils() {
    }

    @SafeVarargs
    public static <T> List<T> listOf(T... array) {
        return Arrays.asList(array);
    }

    public static <T> List<T> listOf(Set<T> set) {
        return collectStreamAsList(streamOf(set));
    }

    public static <T, K, V> Map<K, V> associate(List<T> list, Function<T, K> keyMapper, Function<T, V> valueMapper) {
        Map<K, V> result = new HashMap<>();
        list.forEach(e -> result.put(keyMapper.apply(e), valueMapper.apply(e)));
        return result;
    }

    public static <K, V> Map<K, V> associate(List<V> list, Function<V, K> keyMapper) {
        return associate(list, keyMapper, e -> e);
    }

    public static <T, R> List<R> map(List<T> list, Function<T, R> mapper) {
        return collectStreamAsList(streamOf(list).map(mapper));
    }

    public static <T, R> List<R> flatMap(List<T> list, Function<T, List<R>> mapper) {
        List<R> r = new ArrayList<>();
        list.forEach(e -> r.addAll(mapper.apply(e)));
        return r;
    }

    @SafeVarargs
    public static <T> boolean all(Predicate<T> predicate, T... array) {
        return all(Arrays.asList(array), predicate);
    }

    public static <T> boolean all(List<T> list, Predicate<T> predicate) {
        return streamOf(list).allMatch(predicate);
    }

    @SafeVarargs
    public static <T> boolean any(Predicate<T> predicate, T... array) {
        return any(Arrays.asList(array), predicate);
    }
    public static <T> boolean any(List<T> list, Predicate<T> predicate) {
        return streamOf(list).anyMatch(predicate);
    }

    public static <T> T find(List<T> list, Predicate<T> predicate) {
        return find(list, null, predicate);
    }

    public static <T> T find(List<T> list, T fallback, Predicate<T> predicate) {
        List<T> matches = filter(list, predicate);
        if (matches.isEmpty()) {
            return fallback;
        } else {
            return matches.get(0);
        }
    }

    public static <T> List<T> filter(List<T> list, Predicate<T> predicate) {
        return collectStreamAsList(streamOf(list).filter(predicate));
    }

    public static <T> List<T> filterNotNull(List<T> list, Predicate<T> predicate) {
        return filter(list, e -> e != null && predicate.test(e));
    }

    private static <T, C extends Collection<T>> Stream<T> streamOf(C collection) {
        return (collection == null ? new ArrayList<T>() : collection).stream();
    }

    private static <T> List<T> collectStreamAsList(Stream<T> stream) {
        return stream.collect(Collectors.toList());
    }
}
