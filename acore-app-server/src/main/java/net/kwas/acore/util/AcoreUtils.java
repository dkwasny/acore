package net.kwas.acore.util;

import java.util.ArrayList;
import java.util.List;

public class AcoreUtils {

    public static <T> List<T> iterableToList(Iterable<T> iterable) {
        var retVal = new ArrayList<T>();
        iterable.forEach(retVal::add);
        return retVal;
    }

}
