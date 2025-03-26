package net.kwas.acore.spell;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class SpellDatabase {

    private final SpellQueries queries;
    private final Map<Long, Spell> records;

    public SpellDatabase(
        SpellQueries queries,
        @Qualifier("SpellMap") Map<Long, Spell> records
    ) {
        this.queries = queries;
        this.records = records;
    }

    public Spell getSpell(long id) {
        return records.get(id);
    }

    public Collection<Spell> getSpells() {
        return records.values();
    }

    public Collection<Spell> getSpellsForCharacter(long characterId) {
        return queries.getSpellIdsForCharacter(characterId).stream()
            .map(records::get)
            .collect(Collectors.toList());
    }

    public Collection<Spell> searchName(String query) {
        return records.values().stream()
            .filter(x -> x.name().contains(query))
            .collect(Collectors.toList());
    }

    public Collection<Spell> searchDescription(String query) {
        return records.values().stream()
            .filter(x -> x.description().contains(query))
            .collect(Collectors.toList());
    }

}
