package net.kwas.acore.server.spell;

import java.net.URI;

public record RawSpell(
  long id,
  String name,
  String subtext,
  String rawDescription,
  URI iconUrl,
  int spellDescriptionVariablesId
) {
}
