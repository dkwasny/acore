package net.kwas.acore.dbc.model.record;

import net.kwas.acore.dbc.model.DbcField;
import net.kwas.acore.dbc.model.DbcType;

public class DbcSpellDescriptionVariables {

  @DbcField(DbcType.UINT32)
  public long id;

  @DbcField(DbcType.STRING)
  public String variable;

  @Override
  public String toString() {
    return "DbcSpellDescriptionVariables{" +
      "id=" + id +
      ", variable=" + variable +
      '}';
  }
}
