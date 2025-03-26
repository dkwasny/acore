package net.kwas.acore.character;

import net.kwas.acore.dbc.model.record.DbcAreaTable;
import net.kwas.acore.dbc.model.record.DbcChrClasses;
import net.kwas.acore.dbc.model.record.DbcChrRaces;
import net.kwas.acore.dbc.spring.DbcMgr;
import net.kwas.acore.util.Stopwatch;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class CharacterBeans {

    @Bean
    @Qualifier("RaceMap")
    public static Map<Long, String> createRaceMap(DbcMgr dbcMgr) {
        var stopwatch = Stopwatch.start("ReadRaces");
        var reader = dbcMgr.getReader();
        var chrRaces = reader.readDbc(DbcChrRaces.class);

        var retVal = chrRaces.stream()
            .collect(Collectors.toMap(x -> x.id, x -> x.name0));

        stopwatch.stop();
        return retVal;
    }

    @Bean
    @Qualifier("ClassMap")
    public static Map<Long, String> createClassMap(DbcMgr dbcMgr) {
        var stopwatch = Stopwatch.start("ReadClasses");

        var reader = dbcMgr.getReader();
        var chrClasses = reader.readDbc(DbcChrClasses.class);

        var retVal = chrClasses.stream()
            .collect(Collectors.toMap(x -> x.id, x -> x.name0));

        stopwatch.stop();
        return retVal;
    }

    @Bean
    @Qualifier("ZoneMap")
    public static Map<Long, String> createZoneMap(DbcMgr dbcMgr) {
        var stopwatch = Stopwatch.start("ReadZones");

        var reader = dbcMgr.getReader();
        var areaTables = reader.readDbc(DbcAreaTable.class);

        var retVal = areaTables.stream()
            .collect(Collectors.toMap(x -> x.id, x -> x.areaName0));

        stopwatch.stop();
        return retVal;
    }

}
