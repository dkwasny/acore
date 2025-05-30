package net.kwas.acore.common;

public enum Gender {

    MALE(0, "Male"),
    FEMALE(1, "Female");

    private final int intValue;
    private final String humanReadable;

    Gender(int intValue, String humanReadable) {
        this.intValue = intValue;
        this.humanReadable = humanReadable;
    }

    public int getIntValue() {
        return intValue;
    }

    public String getHumanReadable() {
        return humanReadable;
    }

    public static Gender fromInt(int value) {
        if (value == MALE.getIntValue()) {
            return MALE;
        }
        else if (value == FEMALE.getIntValue()) {
            return FEMALE;
        }
        else {
            throw new RuntimeException("Unexpected gender value: " + value);
        }
    }

}
