package net.kwas.acore.antlr.resolver.context;

import net.kwas.acore.antlr.resolver.NumberResolver;

import java.util.Map;

public class SpellContext {

    private final long spellId;
    private final Map<Long, SpellInfo> spellInfoMap;
    private final CharacterInfo characterInfo;
    private final Map<String, NumberResolver> variableMap;

    private long lastRenderedNumber = 0L;

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

    public Map<String, NumberResolver> getVariableMap() {
        return variableMap;
    }

    public long getLastRenderedNumber() {
        return lastRenderedNumber;
    }

    public void setLastRenderedNumber(long lastRenderedNumber) {
        this.lastRenderedNumber = lastRenderedNumber;
    }

    @Override
    public String toString() {
        return "SpellContext{" +
            "spellId=" + spellId +
            ", spellInfoMap=" + spellInfoMap +
            ", characterInfo=" + characterInfo +
            ", variableMap=" + variableMap +
            ", lastRenderedNumber=" + lastRenderedNumber +
            '}';
    }

}
