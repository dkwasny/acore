package net.kwas.acore.server.character;

import net.kwas.acore.antlr.resolver.context.CharacterInfo;
import net.kwas.acore.common.Gender;
import net.kwas.acore.server.spell.Spell;
import net.kwas.acore.server.spell.SpellDatabase;
import net.kwas.acore.server.spell.SpellQueries;
import net.kwas.acore.server.util.AcoreUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CharacterDatabase {

    private final CharacterRepository repo;
    private final CharacterQueries queries;

    private final SpellQueries spellQueries;

    private final Map<Integer, String> raceMap;
    private final Map<Integer, String> classMap;
    private final Map<Integer, String> zoneMap;

    public CharacterDatabase(
        CharacterRepository repo,
        CharacterQueries queries,
        SpellQueries spellQueries,
        @Qualifier("RaceMap")
        Map<Integer, String> raceMap,
        @Qualifier("ClassMap")
        Map<Integer, String> classMap,
        @Qualifier("ZoneMap")
        Map<Integer, String> zoneMap
    ) {
        this.repo = repo;
        this.queries = queries;
        this.spellQueries = spellQueries;
        this.raceMap = raceMap;
        this.classMap = classMap;
        this.zoneMap = zoneMap;
    }

    public Collection<Character> getCharacters() {
        return AcoreUtils.iterableToStream(repo.findAll())
            .map(this::createCharacter)
            .toList();
    }

    public Character getCharacter(long id) {
        // TODO Nasty null...check other places too
        var sqlCharacter = repo.findById(id).get();
        return createCharacter(sqlCharacter);
    }

    public CharacterInfo getCharacterInfo(long id) {
        var learnedSpellIds = new HashSet<>(spellQueries.getSpellIdsForCharacter(id));
        var sqlCharacterInfo = queries.getCharacterInfo(id);
        return createCharacterInfo(sqlCharacterInfo, learnedSpellIds);
    }

    private Character createCharacter(SqlCharacter sqlCharacter) {
        var race = raceMap.get(sqlCharacter.race());
        var chrClass = classMap.get(sqlCharacter.chrClass());
        var gender = Gender.fromInt(sqlCharacter.gender());
        var online = AcoreUtils.isOnline(sqlCharacter.online());
        var zone = zoneMap.get(sqlCharacter.zone());

        return new Character(
            sqlCharacter.guid(),
            sqlCharacter.account(),
            sqlCharacter.name(),
            race,
            chrClass,
            gender.getHumanReadable(),
            sqlCharacter.level(),
            sqlCharacter.xp(),
            sqlCharacter.money(),
            online,
            sqlCharacter.totaltime(),
            zone
        );
    }

    private CharacterInfo createCharacterInfo(SqlCharacterInfo sqlCharacterInfo, Set<Long> learnedSpellIds) {
        var hearthStoneLocation = zoneMap.get(sqlCharacterInfo.hearthstoneLocation());

        return new CharacterInfo(
            sqlCharacterInfo.characterLevel(),
            Gender.fromInt(sqlCharacterInfo.gender()),
            sqlCharacterInfo.spirit(),
            sqlCharacterInfo.attackPower(),
            sqlCharacterInfo.rangedAttackPower(),
            sqlCharacterInfo.spellPower(),
            sqlCharacterInfo.mainWeaponMinDamage(),
            sqlCharacterInfo.mainWeaponMaxDamage(),
            sqlCharacterInfo.mainWeaponBaseMinDamage(),
            sqlCharacterInfo.mainWeaponBaseMaxDamage(),
            sqlCharacterInfo.mainWeaponSpeed(),
            sqlCharacterInfo.isMainWeaponTwoHanded(),
            learnedSpellIds,
            hearthStoneLocation
        );
    }

}
