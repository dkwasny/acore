package net.kwas.acore.dbc.model.record;

import net.kwas.acore.dbc.model.DbcField;
import net.kwas.acore.dbc.model.DbcType;

public class DbcSpellDuration {

  @DbcField(DbcType.UINT32)
  public long id;

  @DbcField(DbcType.INT32)
  public int duration;

  @DbcField(DbcType.INT32)
  public int durationPerLevel;

  @DbcField(DbcType.INT32)
  public int maxDuration;

  @Override
  public String toString() {
    return "DbcSpellDuration{" +
      "id=" + id +
      ", duration=" + duration +
      ", durationPerLevel=" + durationPerLevel +
      ", maxDuration=" + maxDuration +
      '}';
  }

}
