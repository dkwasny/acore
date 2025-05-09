package net.kwas.acore.antlr.resolver.context;

import java.util.Map;

public class SpellContext {

    private final long spellId;
    private final Map<Long, SpellInfo> spellInfos;
    private final CharacterInfo characterInfo;

    private long lastRenderedNumber = 0L;

    public SpellContext(long spellId, Map<Long, SpellInfo> spellInfos, CharacterInfo characterInfo) {
        this.spellId = spellId;
        this.spellInfos = spellInfos;
        this.characterInfo = characterInfo;
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

    public long getLastRenderedNumber() {
        return lastRenderedNumber;
    }

    public void setLastRenderedNumber(long lastRenderedNumber) {
        this.lastRenderedNumber = lastRenderedNumber;
    }

}
