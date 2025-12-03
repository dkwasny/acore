package net.kwas.acore.server.item;

import net.kwas.acore.server.util.Icons;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;

@Component
public class ItemDatabase {

  private final ItemQueries queries;
  private final Map<Long, String> iconMap;

  public ItemDatabase(ItemQueries queries, @Qualifier("ItemIconMap") Map<Long, String> iconMap) {
    this.queries = queries;
    this.iconMap = iconMap;
  }

  public Collection<Item> getItems() {
    return queries.getItems().stream()
      .map(this::createItem)
      .toList();
  }

  public Item getItem(long id) {
    var sqlItemSummary = queries.getItem(id);
    return createItem(sqlItemSummary);
  }

  public Collection<Item> getItemsForCharacter(long characterId) {
    return queries.getItemsForCharacter(characterId).stream()
      .map(this::createItem)
      .toList();
  }

  private Item createItem(SqlItem sqlItem) {
    var iconRef = iconMap.get(sqlItem.itemTemplateId());
    var iconUrl = Icons.getIconUrl(iconRef);
    return new Item(
      sqlItem.id(),
      sqlItem.itemTemplateId(),
      sqlItem.name(),
      sqlItem.description(),
      iconUrl,
      sqlItem.ownerId(),
      sqlItem.ownerName(),
      sqlItem.count()
    );
  }

}
