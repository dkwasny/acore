package net.kwas.acore.server.spell;

import net.kwas.acore.server.api.SpellApi;
import net.kwas.acore.server.model.Spell;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.function.Function;

// TODO: 404 if not found for all methods
@RestController
public class SpellController implements SpellApi {

  private final SpellDatabase spellDb;

  public SpellController(SpellDatabase spellDb) {
    this.spellDb = spellDb;
  }

  @Override
  public Spell getSpell(Long spellId) {
    return spellDb.getSpell(spellId);
  }

  @Override
  public List<Spell> getSpells(String searchField, String searchQuery) {
    var retVal = spellDb.getSpells();

    if (searchField != null && searchQuery != null) {
      var stringRetriever = getStringRetriever(searchField);

      retVal = retVal.stream()
        .filter(x -> stringRetriever.apply(x).contains(searchQuery))
        .toList();
    }

    return retVal;
  }

  private Function<Spell, String> getStringRetriever(String searchField) {
    return switch (searchField) {
      case "name" -> Spell::getName;
      case "description" -> Spell::getDescription;
      default -> throw new RuntimeException("Unexpected search field: " + searchField);
    };
  }

}
