package net.kwas.acore.dbc.model;

public enum DbcType {
    UINT32(4),
    INT32(4),
    FLOAT(4),
    STRING(4),
    // One byte for every locale, and a final byte for the flags.
    LOCALE_STRING((DbcLocale.values().length * 4) + 4);

    private final int size;

    DbcType(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }
}
