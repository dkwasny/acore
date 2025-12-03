package net.kwas.acore.server.character;

import net.kwas.acore.common.CharacterClass;
import net.kwas.acore.common.Gender;
import net.kwas.acore.common.Race;

public record Character(
  long id,
  long accountId,
  String name,
  Race race,
  CharacterClass charClass,
  Gender gender,
  int level,
  long xp,
  long xpForNextLevel,
  long money,
  boolean online,
  long totalTime,
  String zone,
  String iconUrl,
  String charClassIconUrl
) {
}
