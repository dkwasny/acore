package net.kwas.acore.antlr.resolver.context;

import net.kwas.acore.antlr.resolver.NumberResolver;

import java.util.Map;

public class SpellContext {

  private final long spellId;
  private final Map<Long, SpellInfo> spellInfoMap;
  private final CharacterInfo characterInfo;
  private final Map<String, NumberResolver> variableMap;

  private boolean isNextLocalizedStringPlural = false;

  public SpellContext(long spellId, Map<Long, SpellInfo> spellInfoMap, CharacterInfo characterInfo, Map<String, NumberResolver> variables) {
    this.spellId = spellId;
    this.spellInfoMap = spellInfoMap;
    this.characterInfo = characterInfo;
    this.variableMap = variables;
  }

  public long getSpellId() {
    return spellId;
  }

  public SpellInfo getSpellInfo(long id) {
    return spellInfoMap.getOrDefault(id, SpellInfo.EMPTY);
  }

  public CharacterInfo getCharacterInfo() {
    return characterInfo;
  }

  public NumberResolver getVariable(String key) {
    return variableMap.get(key);
  }

  public boolean isNextLocalizedStringPlural() {
    return isNextLocalizedStringPlural;
  }

  public void setNextLocalizedStringPlural(boolean nextLocalizedStringPlural) {
    isNextLocalizedStringPlural = nextLocalizedStringPlural;
  }
}
