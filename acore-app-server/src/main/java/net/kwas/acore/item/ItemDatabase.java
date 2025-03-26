package net.kwas.acore.item;

import net.kwas.acore.util.AcoreUtils;
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

    public Collection<SqlItem> getItems() {
        return AcoreUtils.iterableToStream(repo.findAll())
            .collect(Collectors.toList());
    }

    public SqlItem getItem(long id) {
        return repo.findById(id).get();
    }

    public Collection<ItemSummary> getItemSummaries() {
        return repo.getItemSummaries().stream()
            .map(this::createItemSummary)
            .collect(Collectors.toList());
    }

    public ItemSummary getItemSummary(long id) {
        var sqlItemSummary = repo.getItemSummary(id);
        return createItemSummary(sqlItemSummary);
    }

    private ItemSummary createItemSummary(SqlItemSummary sqlItemSummary) {
        var iconRef = iconMap.get(sqlItemSummary.itemTemplateId());
        var iconUrl = Icons.getIconUrl(iconRef);
        return new ItemSummary(
            sqlItemSummary.id(),
            sqlItemSummary.itemTemplateId(),
            sqlItemSummary.name(),
            sqlItemSummary.description(),
            iconUrl,
            sqlItemSummary.ownerId(),
            sqlItemSummary.ownerName(),
            sqlItemSummary.count()
        );
    }

}
