package net.kwas.acore.spell;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class SpellDatabase {

    private final Map<Long, Spell> records;

    public SpellDatabase(@Qualifier("SpellMap") Map<Long, Spell> records) {
        this.records = records;
    }

    public Spell getById(long id) {
        return records.get(id);
    }

    public Collection<Spell> getAll() {
        return records.values();
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
