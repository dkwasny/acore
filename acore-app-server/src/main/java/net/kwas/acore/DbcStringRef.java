package net.kwas.acore;

import java.util.EnumMap;

public record DbcStringRef(EnumMap<DbcLocale, String> data, long flags) {

    public String get(DbcLocale locale) {
        return data.get(locale);
    }

    public String get() {
        return get(DbcLocale.EN_US);
    }

}
