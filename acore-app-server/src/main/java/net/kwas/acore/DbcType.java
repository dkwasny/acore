package net.kwas.acore;

public enum DbcType {
    UINT32(4),
    INT32(4),
    STRING_REF(8),
    FLOAT(4);

    private final int size;

    DbcType(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }
}
