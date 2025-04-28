package net.kwas.acore.server.item;

import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ItemQueries {

    private static final String ITEMS = """
        SELECT
            ii.guid as id,
            ii.itemEntry as item_template_id,
            it.name as name,
            it.description as description,
            c.guid as owner_id,
            c.name as owner_name,
            ii.count as count
        FROM acore_characters.item_instance ii
        INNER JOIN acore_world.item_template it
            ON ii.itemEntry = it.entry
        INNER JOIN acore_characters.characters c
            ON ii.owner_guid = c.guid
        """;

    private static final String ITEM = ITEMS
        + " WHERE ii.guid = :itemId";

    private static final String ITEMS_FOR_CHARACTER = ITEMS
        + " WHERE ii.owner_guid = :characterId";

    private final NamedParameterJdbcTemplate jdbc;

    public ItemQueries(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<SqlItem> getItems() {
        return jdbc.query(ITEMS, rowMapper());
    }

    public SqlItem getItem(long itemId) {
        var parameters = new MapSqlParameterSource()
            .addValue("itemId", itemId);
        var results = jdbc.query(ITEM, parameters, rowMapper());
        // TODO Error checking
        return results.get(0);
    }

    public List<SqlItem> getItemsForCharacter(long characterId) {
        var parameters = new MapSqlParameterSource()
            .addValue("characterId", characterId);
        return jdbc.query(ITEMS_FOR_CHARACTER, parameters, rowMapper());
    }

    private RowMapper<SqlItem> rowMapper() {
        return new DataClassRowMapper<>(SqlItem.class);
    }

}
