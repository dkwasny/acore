package net.kwas.acore.spell;

import net.kwas.acore.dbc.model.record.DbcSpell;
import net.kwas.acore.dbc.model.record.DbcSpellIcon;
import net.kwas.acore.dbc.reader.DbcReader;
import net.kwas.acore.dbc.spring.DbcMgr;
import net.kwas.acore.util.Stopwatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class SpellDatabase {

    private final Map<Long, Spell> records;

    @Autowired
    public SpellDatabase(DbcMgr dbcMgr) {
        this(createRecordMap(dbcMgr));
    }

    public SpellDatabase(Map<Long, Spell> records) {
        this.records = records;
    }

    public Spell getById(long id) {
        return records.get(id);
    }

    public Collection<Spell> getAll() {
        return records.values();
    }

    public Collection<Spell> searchName(String query) {
        return records.values().stream()
            .filter(x -> x.name().contains(query))
            .collect(Collectors.toList());
    }

    public Collection<Spell> searchDescription(String query) {
        return records.values().stream()
            .filter(x -> x.description().contains(query))
            .collect(Collectors.toList());
    }

    private static Map<Long, Spell> createRecordMap(DbcMgr dbcMgr) {
        var stopwatch = Stopwatch.start("ReadSpells");
        var dbcReader = dbcMgr.getReader();
        var dbcSpells = dbcReader.readDbc(DbcSpell.class);
        var dbcSpellIcons = dbcReader.readDbc(DbcSpellIcon.class);

        var dbcSpellIconMap = dbcSpellIcons.stream()
            .collect(Collectors.toMap(x -> x.id, x -> x.filename));

        var retVal = Collections.synchronizedMap(new LinkedHashMap<Long, Spell>());
        for (var dbcSpell : dbcSpells) {
            var dbcIcon = dbcSpellIconMap.get(dbcSpell.spellIconId);
            var iconUrl = getIconUrl(dbcIcon);
            var spell = new Spell(
                dbcSpell.id,
                dbcSpell.name0,
                dbcSpell.nameSubtext0,
                dbcSpell.description0,
                iconUrl
            );
            retVal.put(spell.id(), spell);
        }
        stopwatch.stop();
        return retVal;
    }

    private static String getIconUrl(String dbcIcon) {
        var split = dbcIcon.split("\\\\");
        var iconName = split[split.length - 1];
        var lowerCase = iconName.toLowerCase();
        return "https://wow.zamimg.com/images/wow/icons/small/" + lowerCase + ".jpg";
    }

}
