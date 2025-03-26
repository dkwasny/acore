package net.kwas.acore.item;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ItemRepository extends CrudRepository<SqlItemInstance, Long> {

    String ITEM_QUERY = """
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

    String FILTERED_ITEM_QUERY = ITEM_QUERY
        + " WHERE ii.guid = :id";

    String CHARACTER_FILTERED_ITEM_QUERY = ITEM_QUERY
        + " WHERE ii.owner_guid = :characterId";

    @Query(ITEM_QUERY)
    List<SqlItem> getItems();

    @Query(FILTERED_ITEM_QUERY)
    SqlItem getItem(long id);

    @Query(CHARACTER_FILTERED_ITEM_QUERY)
    List<SqlItem> getItemsForCharacter(long characterId);

}
