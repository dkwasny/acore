package net.kwas.acore.item_template;

import net.kwas.acore.util.AcoreUtils;
import net.kwas.acore.util.Icons;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ItemTemplateDatabase {

    private final Map<Long, String> iconMap;
    private final ItemTemplateRepository repo;

    public ItemTemplateDatabase(@Qualifier("ItemIconMap") Map<Long, String> iconMap, ItemTemplateRepository repo) {
        this.iconMap = iconMap;
        this.repo = repo;
    }

    public Collection<ItemTemplate> getAll() {
        var sqlData = repo.findAll();
        return AcoreUtils.iterableToStream(sqlData)
            .map(this::toItemTemplate)
            .collect(Collectors.toList());
    }

    public ItemTemplate getById(long id) {
        var sqlData = repo.findById(id);
        if (sqlData.isEmpty()) {
            throw new RuntimeException("Invalid item template ID: " + id);
        }
        return toItemTemplate(sqlData.get());
    }

    private ItemTemplate toItemTemplate(SqlItemTemplate sqlItemTemplate) {
        var itemId = sqlItemTemplate.entry();
        var iconValue = iconMap.get(itemId);
        var iconUrl = Icons.getIconUrl(iconValue);
        return new ItemTemplate(
            sqlItemTemplate.entry(),
            sqlItemTemplate.name(),
            sqlItemTemplate.description(),
            iconUrl
        );
    }

}
