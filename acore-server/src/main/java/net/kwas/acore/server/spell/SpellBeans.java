package net.kwas.acore.server.spell;

import net.kwas.acore.dbc.model.record.DbcSpell;
import net.kwas.acore.dbc.model.record.DbcSpellIcon;
import net.kwas.acore.server.dbc.DbcMgr;
import net.kwas.acore.server.util.Icons;
import net.kwas.acore.server.util.Stopwatch;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class SpellBeans {

    @Bean
    @Qualifier("RawSpellMap")
    public static Map<Long, RawSpell> createRawSpellMap(DbcMgr dbcMgr) {
        var stopwatch = Stopwatch.start("CreateRawSpellMap");
        var dbcReader = dbcMgr.getReader();
        var dbcSpells = dbcReader.readDbc(DbcSpell.class);
        var dbcSpellIcons = dbcReader.readDbc(DbcSpellIcon.class);

        var dbcSpellIconMap = dbcSpellIcons.stream()
            .collect(Collectors.toMap(x -> x.id, x -> x.filename));

        var retVal = Collections.synchronizedMap(new LinkedHashMap<Long, RawSpell>());
        for (var dbcSpell : dbcSpells) {
            var dbcIcon = dbcSpellIconMap.get(dbcSpell.spellIconId);
            var iconUrl = Icons.getIconUrl(dbcIcon);
            var spell = new RawSpell(
                dbcSpell.id,
                dbcSpell.name0,
                dbcSpell.nameSubtext0,
                dbcSpell.description0,
                iconUrl,
                dbcSpell.descriptionVariablesId
            );
            retVal.put(spell.id(), spell);
        }

        stopwatch.stop();
        return Collections.unmodifiableMap(retVal);
    }

}
