package net.kwas.acore.server.util;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class AcoreUtils {

    public static <T> Stream<T> iterableToStream(Iterable<T> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false);
    }

    public static String getGender(long value) {
        if (value == 0L) {
            return "Male";
        }
        else if (value == 1L) {
            return "Female";
        }
        else {
            return "Unknown";
        }
    }

    public static boolean isOnline(long value) {
        return value == 1;
    }

}
