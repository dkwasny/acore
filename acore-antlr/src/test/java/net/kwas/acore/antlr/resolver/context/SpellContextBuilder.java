package net.kwas.acore.antlr.resolver.context;

import net.kwas.acore.antlr.resolver.NumberResolver;

import java.util.Map;

/**
 * This builder was generated with IntelliJ's Replace Constructor with Builder functionality.
 */
public class SpellContextBuilder {
  private long spellId;
  private Map<Long, SpellInfo> spellInfoMap;
  private CharacterInfo characterInfo;
  private Map<String, NumberResolver> variables;

  public SpellContextBuilder spellId(long spellId) {
    this.spellId = spellId;
    return this;
  }

  public SpellContextBuilder spellInfoMap(Map<Long, SpellInfo> spellInfoMap) {
    this.spellInfoMap = spellInfoMap;
    return this;
  }

  public SpellContextBuilder characterInfo(CharacterInfo characterInfo) {
    this.characterInfo = characterInfo;
    return this;
  }

  public SpellContextBuilder variables(Map<String, NumberResolver> variables) {
    this.variables = variables;
    return this;
  }

  public SpellContext createSpellContext() {
    return new SpellContext(spellId, spellInfoMap, characterInfo, variables);
  }
}
