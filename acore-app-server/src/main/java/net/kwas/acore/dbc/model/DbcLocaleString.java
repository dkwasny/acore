package net.kwas.acore.dbc.model;

import java.util.EnumMap;

public record DbcLocaleString(EnumMap<DbcLocale, String> data, long flags) {

    public String get(DbcLocale locale) {
        return data.get(locale);
    }

    public String get() {
        return get(DbcLocale.EN_US);
    }

}
