package net.kwas.acore.server.spell;

import net.kwas.acore.antlr.resolver.NumberResolver;
import net.kwas.acore.antlr.resolver.StringResolver;
import net.kwas.acore.antlr.resolver.context.CharacterInfo;
import net.kwas.acore.antlr.resolver.context.SpellContext;
import net.kwas.acore.antlr.resolver.context.SpellInfo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class SpellDatabase {

    private final SpellQueries queries;
    private final Map<Long, SpellSummary> spellSummaries;
    private final Map<Long, StringResolver> spellDescriptionResolvers;
    private final Map<Long, Map<String, NumberResolver>> spellDescriptionVariableResolvers;
    private final Map<Long, SpellInfo> spellInfos;

    private final CharacterInfo dummyCharacterInfo = new CharacterInfo(
        20,
        "Male",
        20,
        100,
        110,
        120,
        10,
        20,
        1.0f,
        false,
        Set.of(50L),
        "HearthstoneLocation"
    );

    public SpellDatabase(
        SpellQueries queries,
        @Qualifier("SpellSummaryMap") Map<Long, SpellSummary> spellSummaries,
        @Qualifier("SpellDescriptionMap") Map<Long, StringResolver> spellDescriptionResolvers,
        @Qualifier("SpellDescriptionVariableMap") Map<Long, Map<String, NumberResolver>> spellDescriptionVariableResolvers,
        @Qualifier("SpellInfoMap") Map<Long, SpellInfo> spellInfos
    ) {
        this.queries = queries;
        this.spellSummaries = spellSummaries;
        this.spellDescriptionResolvers = spellDescriptionResolvers;
        this.spellDescriptionVariableResolvers = spellDescriptionVariableResolvers;
        this.spellInfos = spellInfos;
    }

    public Spell getSpell(long id) {
        return resolveSpell(id);
    }

    public Collection<Spell> getSpells() {
        return spellSummaries.keySet().stream()
            .map(this::resolveSpell)
            .toList();
    }

    public Collection<Spell> getSpellsForCharacter(long characterId) {
        // TODO: Create character context for character!
        return queries.getSpellIdsForCharacter(characterId).stream()
            .map(this::resolveSpell)
            .toList();
    }

    public Collection<Spell> searchName(String query) {
        return spellSummaries.values().stream()
            .filter(x -> x.name().contains(query))
            .map(SpellSummary::id)
            .map(this::resolveSpell)
            .toList();
    }

    public Collection<Spell> searchDescription(String query) {
        return spellSummaries.values().stream()
            .filter(x -> x.rawDescription().contains(query))
            .map(SpellSummary::id)
            .map(this::resolveSpell)
            .toList();
    }

    private Spell resolveSpell(long id) {
        var spellSummary = spellSummaries.get(id);
        var descriptionResolver = spellDescriptionResolvers.get(id);
        var variableResolvers = spellDescriptionVariableResolvers.get((long)spellSummary.spellDescriptionVariablesId());
        var resolverContext = new SpellContext(
            id,
            spellInfos,
            dummyCharacterInfo,
            variableResolvers
        );
        var description = descriptionResolver.resolveString(resolverContext);

        return new Spell(
            id,
            spellSummary.name(),
            spellSummary.subtext(),
            description,
            spellSummary.iconUrl()
        );
    }

}
