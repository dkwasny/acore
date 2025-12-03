package net.kwas.acore.server.util;

import net.kwas.acore.common.CharacterClass;
import net.kwas.acore.common.Gender;
import net.kwas.acore.common.Race;

import java.net.URI;

public class Icons {

  private static final String BASE_URL = "https://wow.zamimg.com/images/wow/icons/small/";
  private static final String FILE_EXTENSION = ".jpg";

  public static URI getIconUrl(String rawReference) {
    if (rawReference == null) {
      // TODO: is this valid and necessary?
      return URI.create("");
    }

    var split = rawReference.split("\\\\");
    var iconName = split[split.length - 1];
    var lowerCase = iconName.toLowerCase();

    return getUrl(lowerCase);
  }

  public static URI getCharacterIconUrl(Race race, Gender gender) {
    var normalizedRace = normalizeEnumName(race.name());
    var normalizedGender = normalizeEnumName(gender.name());
    var iconName = "race_" + normalizedRace + "_" + normalizedGender;

    return getUrl(iconName);
  }

  public static URI getCharacterClassIconUrl(CharacterClass charClass) {
    var normalizedCharClass = normalizeEnumName(charClass.name());
    var iconName = "class_" + normalizedCharClass;

    return getUrl(iconName);
  }

  private static String normalizeEnumName(String value) {
    return value.replace("_", "").toLowerCase();
  }

  private static URI getUrl(String iconName) {
    var unsanitized = BASE_URL + iconName + FILE_EXTENSION;
    // There are a few icon names that have unnecessary spaces both around and within the string.
    var sanitized = unsanitized.replace(" ", "");
    return URI.create(sanitized);
  }

}
