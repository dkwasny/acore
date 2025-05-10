package net.kwas.acore.antlr.resolver.context;

import net.kwas.acore.antlr.resolver.NumberResolver;

import java.util.Map;

public class SpellContext {

    private final long spellId;
    private final Map<Long, SpellInfo> spellInfos;
    private final CharacterInfo characterInfo;
    private final Map<String, NumberResolver> variables;

    private long lastRenderedNumber = 0L;

    public SpellContext(long spellId, Map<Long, SpellInfo> spellInfos, CharacterInfo characterInfo, Map<String, NumberResolver> variables) {
        this.spellId = spellId;
        this.spellInfos = spellInfos;
        this.characterInfo = characterInfo;
        this.variables = variables;
    }

    public long getSpellId() {
        return spellId;
    }

    public Map<Long, SpellInfo> getSpellInfos() {
        return spellInfos;
    }

    public SpellInfo getCurrentSpellInfo() {
        return spellInfos.get(spellId);
    }

    public CharacterInfo getCharacterInfo() {
        return characterInfo;
    }

    public Map<String, NumberResolver> getVariables() {
        return variables;
    }

    public long getLastRenderedNumber() {
        return lastRenderedNumber;
    }

    public void setLastRenderedNumber(long lastRenderedNumber) {
        this.lastRenderedNumber = lastRenderedNumber;
    }

}
