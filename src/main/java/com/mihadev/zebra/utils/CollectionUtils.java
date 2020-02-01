package com.mihadev.zebra.utils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class CollectionUtils {

    public static <T> List<T> toList(Iterable<T> all) {
        return toStream(all).collect(Collectors.toList());
    }

    public static <T> Set<T> toSet(Iterable<T> all) {
        return toStream(all).collect(Collectors.toSet());
    }

    public static <T> Stream<T> toStream(Iterable<T> all) {
        return StreamSupport.stream(all.spliterator(), false);
    }
}
