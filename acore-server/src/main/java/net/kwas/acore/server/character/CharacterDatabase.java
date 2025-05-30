package net.kwas.acore.server.character;

import net.kwas.acore.common.Gender;
import net.kwas.acore.server.util.AcoreUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CharacterDatabase {

    private final CharacterRepository repo;

    private final Map<Long, String> raceMap;
    private final Map<Long, String> classMap;
    private final Map<Long, String> zoneMap;

    public CharacterDatabase(
        CharacterRepository repo,
        @Qualifier("RaceMap")
        Map<Long, String> raceMap,
        @Qualifier("ClassMap")
        Map<Long, String> classMap,
        @Qualifier("ZoneMap")
        Map<Long, String> zoneMap
    ) {
        this.repo = repo;
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
            gender,
            sqlCharacter.level(),
            sqlCharacter.xp(),
            sqlCharacter.money(),
            online,
            sqlCharacter.totaltime(),
            zone
        );
    }

}
