package net.kwas.acore.dbc.model.record;

import net.kwas.acore.dbc.model.DbcField;
import net.kwas.acore.dbc.model.DbcType;

public class DbcItem {

  @DbcField(DbcType.UINT32)
  public long id;

  @DbcField(DbcType.UINT32)
  public long classId;

  @DbcField(DbcType.UINT32)
  public long subclassId;

  @DbcField(DbcType.INT32)
  public int soundOverrideSubclassId;

  @DbcField(DbcType.INT32)
  public int material;

  @DbcField(DbcType.UINT32)
  public long displayInfoId;

  @DbcField(DbcType.UINT32)
  public long inventoryType;

  @DbcField(DbcType.UINT32)
  public long sheatheType;

  @Override
  public String toString() {
    return "ItemDbc{" +
      "id=" + id +
      ", classId=" + classId +
      ", subclassId=" + subclassId +
      ", soundOverrideSubclassId=" + soundOverrideSubclassId +
      ", material=" + material +
      ", displayInfoId=" + displayInfoId +
      ", inventoryType=" + inventoryType +
      ", sheatheType=" + sheatheType +
      '}';
  }

}
