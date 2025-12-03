package net.kwas.acore.server.spell;

import net.kwas.acore.antlr.resolver.NumberResolver;
import net.kwas.acore.antlr.resolver.StringResolver;
import net.kwas.acore.antlr.resolver.context.CharacterInfo;
import net.kwas.acore.antlr.resolver.context.SpellContext;
import net.kwas.acore.antlr.resolver.context.SpellInfo;
import net.kwas.acore.common.Gender;
import net.kwas.acore.server.model.Spell;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class SpellDatabase {

  private final SpellQueries queries;

  private final Map<Long, RawSpell> rawSpellMap;
  private final Map<Long, StringResolver> spellDescriptionMap;
  private final Map<Long, Map<String, NumberResolver>> spellDescriptionVariableMap;
  private final Map<Long, SpellInfo> spellInfoMap;

  // TODO: Eventually allow the user to supply a character info
  // The UI should think in actual spells and weapons instead of ids and damage values
  private final CharacterInfo dummyCharacterInfo = new CharacterInfo(
    20,
    Gender.MALE,
    20,
    100,
    110,
    120,
    10,
    20,
    10,
    20,
    1.0f,
    false,
    Set.of(50L),
    "HearthstoneLocation"
  );

  public SpellDatabase(
    SpellQueries queries,
    @Qualifier("RawSpellMap") Map<Long, RawSpell> rawSpellMap,
    @Qualifier("SpellDescriptionMap") Map<Long, StringResolver> spellDescriptionMap,
    @Qualifier("SpellDescriptionVariableMap") Map<Long, Map<String, NumberResolver>> spellDescriptionVariableMap,
    @Qualifier("SpellInfoMap") Map<Long, SpellInfo> spellInfoMap
  ) {
    this.queries = queries;
    this.rawSpellMap = rawSpellMap;
    this.spellDescriptionMap = spellDescriptionMap;
    this.spellDescriptionVariableMap = spellDescriptionVariableMap;
    this.spellInfoMap = spellInfoMap;
  }

  public Spell getSpell(long id) {
    return createSpell(id);
  }

  public List<Spell> getSpells() {
    return rawSpellMap.keySet().stream()
      .map(this::createSpell)
      .toList();
  }

  public Spell getSpellForCharacter(long spellId, CharacterInfo characterInfo) {
    return createSpell(spellId, characterInfo);
  }

  public List<Spell> getSpellsForCharacter(long characterId, CharacterInfo characterInfo) {
    return queries.getSpellIdsForCharacter(characterId).stream()
      .map(x -> createSpell(x, characterInfo))
      .toList();
  }

  public List<Spell> searchName(String query) {
    return rawSpellMap.values().stream()
      .filter(x -> x.name().contains(query))
      .map(RawSpell::id)
      .map(this::createSpell)
      .toList();
  }

  public List<Spell> searchDescription(String query) {
    return rawSpellMap.values().stream()
      .filter(x -> x.rawDescription().contains(query))
      .map(RawSpell::id)
      .map(this::createSpell)
      .toList();
  }

  private Spell createSpell(long id) {
    return createSpell(id, dummyCharacterInfo);
  }

  private Spell createSpell(long id, CharacterInfo characterInfo) {
    var rawSpell = rawSpellMap.get(id);
    var descriptionResolver = spellDescriptionMap.get(id);
    var variableResolvers = spellDescriptionVariableMap.get((long) rawSpell.spellDescriptionVariablesId());
    var resolverContext = new SpellContext(
      id,
      spellInfoMap,
      characterInfo,
      variableResolvers
    );
    var description = descriptionResolver.resolveString(resolverContext);

    return new Spell(
      id,
      rawSpell.name(),
      rawSpell.subtext(),
      description,
      rawSpell.iconUrl()
    );
  }

}
