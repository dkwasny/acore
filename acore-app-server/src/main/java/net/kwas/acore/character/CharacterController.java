package net.kwas.acore.character;

import net.kwas.acore.item.Item;
import net.kwas.acore.item.ItemDatabase;
import net.kwas.acore.spell.Spell;
import net.kwas.acore.spell.SpellDatabase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class CharacterController {

    private final CharacterDatabase characterDb;
    private final ItemDatabase itemDb;
    private final SpellDatabase spellDb;

    public CharacterController(CharacterDatabase characterDb, ItemDatabase itemDb, SpellDatabase spellDb) {
        this.characterDb = characterDb;
        this.itemDb = itemDb;
        this.spellDb = spellDb;
    }

    @GetMapping("/api/character")
    public Collection<Character> getCharacters() {
        return characterDb.getCharacters();
    }

    @GetMapping("/api/character/{id}")
    public Character getCharacter(@PathVariable long id) {
        return characterDb.getCharacter(id);
    }

    @GetMapping("/api/character/{id}/item")
    public Collection<Item> getItemsForCharacter(@PathVariable long id) {
        return itemDb.getItemsForCharacter(id);
    }

    @GetMapping("/api/character/{id}/spell")
    public Collection<Spell> getSpellsForCharacter(@PathVariable long id) {
        return spellDb.getSpellsForCharacter(id);
    }

}
