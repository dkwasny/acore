package net.kwas.acore.server.item_template;

import net.kwas.acore.dbc.model.record.DbcItem;
import net.kwas.acore.dbc.model.record.DbcItemDisplayInfo;
import net.kwas.acore.server.dbc.DbcMgr;
import net.kwas.acore.server.util.Stopwatch;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class ItemTemplateBeans {

    @Bean
    @Qualifier("ItemIconMap")
    public static Map<Long, String> generateIconMap(DbcMgr dbcMgr) {
        var stopwatch = Stopwatch.start("ReadItemIcons");

        var reader = dbcMgr.getReader();
        var items = reader.readDbc(DbcItem.class);
        var itemDisplayInfos = reader.readDbc(DbcItemDisplayInfo.class);

        var itemDisplayInfoMap = itemDisplayInfos.stream()
            .collect(Collectors.toMap(x -> x.id, x -> x));

        var retVal = items.stream()
            .collect(
                Collectors.toMap(
                    x -> x.id,
                    x -> itemDisplayInfoMap.get(x.displayInfoId).inventoryIcon0
                )
            );

        stopwatch.stop();
        return Collections.unmodifiableMap(retVal);
    }

}
