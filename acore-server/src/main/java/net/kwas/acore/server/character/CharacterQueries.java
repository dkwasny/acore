package net.kwas.acore.server.character;

import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CharacterQueries {

    private static final String CHARACTER_INFO = """
        SELECT
            c.level AS characterLevel,
            c.gender,
            cs.spirit,
            cs.attackPower,
            cs.rangedAttackPower,
            cs.spellPower,
            it.dmg_min1 + it.dmg_min2 as mainWeaponMinDamage,
            it.dmg_max1 + it.dmg_max2 as mainWeaponMaxDamage,
            it.dmg_min1 AS mainWeaponBaseMinDamage,
            it.dmg_max1 AS mainWeaponBaseMaxDamage,
            CAST(it.delay AS FLOAT) / 1000 AS mainWeaponSpeed,
            it.subclass IN (1, 2, 3, 5, 6, 8, 10, 17, 18) AS isMainWeaponTwoHanded,
            ch.zoneId AS hearthstoneLocation
        FROM acore_characters.characters c
        INNER JOIN acore_characters.character_stats cs
            ON c.guid = cs.guid
        INNER JOIN acore_characters.character_homebind ch
            ON c.guid = ch.guid
        -- KWAS TODO: Can this be INNER JOIN??
        LEFT JOIN acore_characters.character_inventory ci
            ON c.guid = ci.guid
        LEFT JOIN acore_characters.item_instance ii
            ON ci.item = ii.guid
        LEFT JOIN acore_world.item_template it
            ON ii.itemEntry = it.entry
        WHERE c.guid = :characterId
            AND ci.bag = 0
            AND ci.slot = 15
            AND it.class = 2
        """;

    private final NamedParameterJdbcTemplate jdbc;

    public CharacterQueries(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public SqlCharacterInfo getCharacterInfo(long characterId) {
        var parameters = new MapSqlParameterSource()
            .addValue("characterId", characterId);
        var values = jdbc.query(CHARACTER_INFO, parameters, rowMapper());

        if (values.isEmpty()) {
            throw new RuntimeException("Could not get character info: " + characterId);
        }
        else if (values.size() > 1) {
            throw new RuntimeException("Got multiple rows for character info: " + characterId);
        }
        else {
            return values.getFirst();
        }
    }

    private RowMapper<SqlCharacterInfo> rowMapper() {
        return new DataClassRowMapper<>(SqlCharacterInfo.class);
    }

}
