package net.kwas.acore.item;

import net.kwas.acore.util.Icons;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ItemDatabase {

    private final ItemRepository repo;
    private final Map<Long, String> iconMap;

    public ItemDatabase(ItemRepository repo, @Qualifier("ItemIconMap") Map<Long, String> iconMap) {
        this.repo = repo;
        this.iconMap = iconMap;
    }

    public Collection<Item> getItems() {
        return repo.getItemSummaries().stream()
            .map(this::createItem)
            .collect(Collectors.toList());
    }

    public Item getItem(long id) {
        var sqlItemSummary = repo.getItemSummary(id);
        return createItem(sqlItemSummary);
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
