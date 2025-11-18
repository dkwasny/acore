package net.kwas.acore.common;

public enum Gender {

    MALE(0),
    FEMALE(1);

    private final int intValue;

    Gender(int intValue) {
        this.intValue = intValue;
    }

    public int getIntValue() {
        return intValue;
    }

    public static Gender fromInt(int value) {
        for (var gender : Gender.values()) {
            if (value == gender.getIntValue()) {
                return gender;
            }
        }

        throw new RuntimeException("Unexpected Gender value: " + value);
    }

}
