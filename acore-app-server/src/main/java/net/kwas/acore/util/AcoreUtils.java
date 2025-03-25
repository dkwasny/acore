package net.kwas.acore.util;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class AcoreUtils {

    public static <T> Stream<T> iterableToStream(Iterable<T> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false);
    }

}
