package net.kwas.acore.item_template;

import net.kwas.acore.dbc.model.record.DbcItem;
import net.kwas.acore.dbc.model.record.DbcItemDisplayInfo;
import net.kwas.acore.dbc.spring.DbcMgr;
import net.kwas.acore.util.AcoreUtils;
import net.kwas.acore.util.Icons;
import net.kwas.acore.util.Stopwatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ItemTemplateDatabase {

    private final Map<Long, String> iconMap;
    private final ItemTemplateRepository itr;

    @Autowired
    public ItemTemplateDatabase(DbcMgr dbcMgr, ItemTemplateRepository itr) {
        this(generateIconMap(dbcMgr), itr);
    }

    public ItemTemplateDatabase(Map<Long, String> iconMap, ItemTemplateRepository itr) {
        this.iconMap = iconMap;
        this.itr = itr;
    }

    public Collection<ItemTemplate> getAll() {
        var sqlData = itr.findAll();
        return AcoreUtils.iterableToList(sqlData).stream()
            .map(this::toItemTemplate)
            .collect(Collectors.toList());
    }

    public ItemTemplate getById(long id) {
        var sqlData = itr.findById(id);
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

    private static Map<Long, String> generateIconMap(DbcMgr dbcMgr) {
        var stopwatch = Stopwatch.start("ReadItemIcons");

        var reader = dbcMgr.getReader();
        var items = reader.readDbc(DbcItem.class);
        var itemDisplayInfos = reader.readDbc(DbcItemDisplayInfo.class);

        var itemDisplayInfoMap = itemDisplayInfos.stream()
            .collect(Collectors.toMap(x -> x.id, x -> x));

        var retVal = items.stream()
                .collect(Collectors.toMap(x -> x.id, x -> itemDisplayInfoMap.get(x.displayInfoId).inventoryIcon0));

        stopwatch.stop();
        return retVal;
    }
}
