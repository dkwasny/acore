package net.kwas.acore.server.character;

import net.kwas.acore.dbc.model.record.DbcAreaTable;
import net.kwas.acore.dbc.model.record.DbcChrClasses;
import net.kwas.acore.dbc.model.record.DbcChrRaces;
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
    @Qualifier("RaceMap")
    public static Map<Integer, String> createRaceMap(DbcMgr dbcMgr) {
        var stopwatch = Stopwatch.start("ReadRaces");
        var reader = dbcMgr.getReader();
        var chrRaces = reader.readDbc(DbcChrRaces.class);

        var retVal = chrRaces.stream()
            .collect(Collectors.toMap(x -> (int)x.id, x -> x.name0));

        stopwatch.stop();
        return retVal;
    }

    @Bean
    @Qualifier("ClassMap")
    public static Map<Integer, String> createClassMap(DbcMgr dbcMgr) {
        var stopwatch = Stopwatch.start("ReadClasses");

        var reader = dbcMgr.getReader();
        var chrClasses = reader.readDbc(DbcChrClasses.class);

        var retVal = chrClasses.stream()
            .collect(Collectors.toMap(x -> (int)x.id, x -> x.name0));

        stopwatch.stop();
        return retVal;
    }

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
