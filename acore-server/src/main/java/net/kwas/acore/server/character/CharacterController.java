package net.kwas.acore.server.character;

import net.kwas.acore.server.api.CharacterApi;
import net.kwas.acore.server.item.ItemDatabase;
import net.kwas.acore.server.model.Character;
import net.kwas.acore.server.model.Item;
import net.kwas.acore.server.model.Spell;
import net.kwas.acore.server.spell.SpellDatabase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RestController
public class CharacterController implements CharacterApi {

  private final CharacterDatabase characterDb;
  private final ItemDatabase itemDb;
  private final SpellDatabase spellDb;

  public CharacterController(CharacterDatabase characterDb, ItemDatabase itemDb, SpellDatabase spellDb) {
    this.characterDb = characterDb;
    this.itemDb = itemDb;
    this.spellDb = spellDb;
  }

  @Override
  public Character getCharacter(Long characterId) {
    return characterDb.getCharacter(characterId);
  }

  @Override
  public List<Character> getCharacters() {
    return characterDb.getCharacters();
  }

  // TODO: Add 404 support
  @Override
  public List<Item> getItemsForCharacter(Long characterId) {
    return itemDb.getItemsForCharacter(characterId);
  }

  @Override
  public Spell getSpellForCharacter(Long characterId, Long spellId) {
    var characterInfo = characterDb.getCharacterInfo(characterId);
    return spellDb.getSpellForCharacter(spellId, characterInfo);
  }

  // TODO: Add 404 support
  @Override
  public List<Spell> getSpellsForCharacter(Long characterId) {
    var characterInfo = characterDb.getCharacterInfo(characterId);
    return spellDb.getSpellsForCharacter(characterId, characterInfo);
  }

}
