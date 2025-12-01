package net.kwas.acore.server.character;

import net.kwas.acore.antlr.resolver.context.CharacterInfo;
import net.kwas.acore.common.CharacterClass;
import net.kwas.acore.common.Gender;
import net.kwas.acore.common.Race;
import net.kwas.acore.server.spell.SpellQueries;
import net.kwas.acore.server.util.AcoreUtils;
import net.kwas.acore.server.util.Icons;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class CharacterDatabase {

    private final CharacterRepository repo;
    private final CharacterQueries queries;

    private final SpellQueries spellQueries;

    private final Map<Integer, String> zoneMap;
    private final Map<Integer, Long> characterXpMap;

    public CharacterDatabase(
        CharacterRepository repo,
        CharacterQueries queries,
        SpellQueries spellQueries,
        @Qualifier("ZoneMap")
        Map<Integer, String> zoneMap,
        @Qualifier("CharacterXpMap")
        Map<Integer, Long> characterXpMap
    ) {
        this.repo = repo;
        this.queries = queries;
        this.spellQueries = spellQueries;
        this.zoneMap = zoneMap;
        this.characterXpMap = characterXpMap;
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
        var race = Race.fromInt(sqlCharacter.race());
        var charClass = CharacterClass.fromInt(sqlCharacter.charClass());
        var gender = Gender.fromInt(sqlCharacter.gender());
        var xpForNextLevel = characterXpMap.get(sqlCharacter.level());
        var online = AcoreUtils.isOnline(sqlCharacter.online());
        var zone = zoneMap.get(sqlCharacter.zone());
        var iconUrl = Icons.getCharacterIconUrl(race, gender);
        var charClassIconUrl = Icons.getCharacterClassIconUrl(charClass);

        return new Character(
            sqlCharacter.guid(),
            sqlCharacter.account(),
            sqlCharacter.name(),
            race,
            charClass,
            gender,
            sqlCharacter.level(),
            sqlCharacter.xp(),
            xpForNextLevel,
            sqlCharacter.money(),
            online,
            sqlCharacter.totaltime(),
            zone,
            iconUrl,
            charClassIconUrl
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
