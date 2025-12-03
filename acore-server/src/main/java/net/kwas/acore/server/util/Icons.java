package net.kwas.acore.server.util;

import net.kwas.acore.common.CharacterClass;
import net.kwas.acore.common.Gender;
import net.kwas.acore.common.Race;

public class Icons {

  private static final String BASE_URL = "https://wow.zamimg.com/images/wow/icons/small/";
  private static final String FILE_EXTENSION = ".jpg";

  public static String getIconUrl(String rawReference) {
    if (rawReference == null) {
      return "";
    }

    var split = rawReference.split("\\\\");
    var iconName = split[split.length - 1];
    var lowerCase = iconName.toLowerCase();

    return getUrl(lowerCase);
  }

  public static String getCharacterIconUrl(Race race, Gender gender) {
    var normalizedRace = normalizeEnumName(race.name());
    var normalizedGender = normalizeEnumName(gender.name());
    var iconName = "race_" + normalizedRace + "_" + normalizedGender;

    return getUrl(iconName);
  }

  public static String getCharacterClassIconUrl(CharacterClass charClass) {
    var normalizedCharClass = normalizeEnumName(charClass.name());
    var iconName = "class_" + normalizedCharClass;

    return getUrl(iconName);
  }

  private static String normalizeEnumName(String value) {
    return value.replace("_", "").toLowerCase();
  }

  private static String getUrl(String iconName) {
    return BASE_URL + iconName + FILE_EXTENSION;
  }

}
