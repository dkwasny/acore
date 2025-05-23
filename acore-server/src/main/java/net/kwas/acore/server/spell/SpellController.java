package net.kwas.acore.server.spell;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

// TODO: 404 if not found for all methods
@RestController
public class SpellController {

    private final SpellDatabase spellDb;

    public SpellController(SpellDatabase spellDb) {
        this.spellDb = spellDb;
    }

    @GetMapping("/api/spell")
    public Collection<Spell> getSpells() {
        return spellDb.getSpells();
    }

    @GetMapping("/api/spell/{id}")
    public Spell getSpell(@PathVariable long id) {
        return spellDb.getSpell(id);
    }

    @GetMapping("/api/spell/{id}/name")
    public String getSpellName(@PathVariable long id) {
        return spellDb.getSpell(id).name();
    }

    @GetMapping("/api/spell/{id}/description")
    public String getSpellDescription(@PathVariable long id) {
        return spellDb.getSpell(id).description();
    }

    @GetMapping("/api/spell/search")
    public List<Spell> searchSpells(@RequestParam String searchField, @RequestParam String query) {
        var spells = spellDb.getSpells();

        var stringRetriever = getStringRetriever(searchField);

        return spells.stream()
            .filter(x -> stringRetriever.apply(x).contains(query))
            .toList();
    }

    private Function<Spell, String> getStringRetriever(String searchField) {
        switch (searchField) {
            case "name":
                return Spell::name;
            case "description":
                return Spell::description;
            default:
                throw new RuntimeException("Unexpected search field: " + searchField);
        }
    }

}
