package net.kwas.acore.item;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ItemRepository extends CrudRepository<SqlItem, Long> {

    String ITEM_SUMMARY_QUERY = """
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

    String FILTERED_ITEM_SUMMARY_QUERY = ITEM_SUMMARY_QUERY
        + " WHERE ii.guid = :id";

    @Query(ITEM_SUMMARY_QUERY)
    List<SqlItemSummary> getItemSummaries();

    @Query(FILTERED_ITEM_SUMMARY_QUERY)
    SqlItemSummary getItemSummary(long id);

}
