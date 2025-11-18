package net.kwas.acore.common;

public enum CharacterClass {

    WARRIOR(1),
    PALADIN(2),
    HUNTER(3),
    ROGUE(4),
    PRIEST(5),
    DEATH_KNIGHT(6),
    SHAMAN(7),
    MAGE(8),
    WARLOCK(9),
    DRUID(11);

    private final int intValue;

    CharacterClass(int intValue) {
        this.intValue = intValue;
    }

    public int getIntValue() {
        return intValue;
    }

    public static CharacterClass fromInt(int value) {
        for (var charClass : CharacterClass.values()) {
            if (value == charClass.getIntValue()) {
                return charClass;
            }
        }

        throw new RuntimeException("Unexpected CharacterClass value: " + value);
    }

}
