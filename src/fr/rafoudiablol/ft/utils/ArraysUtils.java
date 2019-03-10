package fr.rafoudiablol.ft.utils;

import java.util.*;
import java.util.function.BiConsumer;

public class ArraysUtils {

    public static<T> T[] removeNullFromArray(T[] a)
    {
        List<T> l = new ArrayList<>(Arrays.asList(a));
        l.removeIf(Objects::isNull);
        return l.toArray(Arrays.copyOf(a, 0));
    }

    public static <T>void forEach(Iterable<T> ts, BiConsumer<Integer, T> c) {

        int i = 0;
        for(T t : ts) {

            c.accept(i++, t);
        }
    }
}
