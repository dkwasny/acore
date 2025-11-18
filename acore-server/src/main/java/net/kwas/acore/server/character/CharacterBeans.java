package net.kwas.acore.server.character;

import net.kwas.acore.dbc.model.record.DbcAreaTable;
import net.kwas.acore.server.dbc.DbcMgr;
import net.kwas.acore.server.util.Stopwatch;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class CharacterBeans {

    // Most of these maps cast unsigned int (i.e. long) IDs to normal
    // ints.  This is due to the acore database storing their references to
    // said IDs as ints (or small ints) instead of unsigned ints.

    @Bean
    @Qualifier("ZoneMap")
    public static Map<Integer, String> createZoneMap(DbcMgr dbcMgr) {
        var stopwatch = Stopwatch.start("ReadZones");

        var reader = dbcMgr.getReader();
        var areaTables = reader.readDbc(DbcAreaTable.class);

        var retVal = areaTables.stream()
            .collect(Collectors.toMap(x -> (int)x.id, x -> x.areaName0));

        stopwatch.stop();
        return retVal;
    }

}
