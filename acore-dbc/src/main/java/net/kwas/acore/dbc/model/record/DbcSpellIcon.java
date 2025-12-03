package net.kwas.acore.dbc.model.record;

import net.kwas.acore.dbc.model.DbcField;
import net.kwas.acore.dbc.model.DbcType;

public class DbcSpellIcon {

  @DbcField(DbcType.UINT32)
  public long id;

  @DbcField(DbcType.STRING)
  public String filename;

  @Override
  public String toString() {
    return "SpellIconDbc{" +
      "id=" + id +
      ", filename='" + filename + '\'' +
      '}';
  }

}
