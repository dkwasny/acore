package net.kwas.acore.server.spell;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SpellQueries {

    private static final String SPELL_IDS_FOR_CHARACTER = """
        SELECT spell
        FROM acore_characters.character_spell
        WHERE guid = :characterId
        """;

    private final NamedParameterJdbcTemplate jdbc;

    public SpellQueries(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<Long> getSpellIdsForCharacter(long characterId) {
        var parameters = new MapSqlParameterSource()
            .addValue("characterId", characterId);
        return jdbc.queryForList(SPELL_IDS_FOR_CHARACTER, parameters, Long.class);
    }
}
