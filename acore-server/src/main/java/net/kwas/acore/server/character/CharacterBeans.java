package net.kwas.acore.server.character;

import net.kwas.acore.dbc.model.record.DbcAreaTable;
import net.kwas.acore.server.dbc.DbcMgr;
import net.kwas.acore.server.util.Stopwatch;
import org.apache.commons.csv.CSVFormat;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStreamReader;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
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
      .collect(Collectors.toMap(x -> (int) x.id, x -> x.areaName0));

    stopwatch.stop();
    return retVal;
  }

  @Bean
  @Qualifier("CharacterXpMap")
  public static Map<Integer, Long> createCharacterXpMap() throws IOException, ParseException {
    var stopwatch = Stopwatch.start("ReadCharacterXp");

    var csvFormat = CSVFormat.TDF.builder()
      .setCommentMarker('#')
      .setHeader()
      .get();
    var numberFormat = NumberFormat.getNumberInstance(Locale.US);
    var retVal = new HashMap<Integer, Long>();

    try (
      var stream = CharacterBeans.class.getResourceAsStream("/character-xp-table.tsv");
      var reader = new InputStreamReader(Objects.requireNonNull(stream));
      var parser = csvFormat.parse(reader)
    ) {
      for (var record : parser) {
        var level = numberFormat.parse(record.get("character-level")).intValue();
        var xpToNextLevel = numberFormat.parse(record.get("xp-to-next-level")).longValue();
        retVal.put(level, xpToNextLevel);
      }
    }

    stopwatch.stop();
    return Collections.unmodifiableMap(retVal);
  }

}
