package net.kwas.acore.common;

public enum Race {

    HUMAN(1),
    ORC(2),
    DWARF(3),
    NIGHT_ELF(4),
    UNDEAD(5),
    TAUREN(6),
    GNOME(7),
    TROLL(8),
    BLOOD_ELF(10),
    DRANEI(11);

    private final int intValue;

    Race(int intValue) {
        this.intValue = intValue;
    }

    public int getIntValue() {
        return intValue;
    }

    public static Race fromInt(int value) {
        for (var race : Race.values()) {
            if (value == race.getIntValue()) {
                return race;
            }
        }

        throw new RuntimeException("Unexpected Race value: " + value);
    }

}
