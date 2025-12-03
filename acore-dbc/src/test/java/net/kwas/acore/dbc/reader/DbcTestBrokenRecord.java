package net.kwas.acore.dbc.reader;

import net.kwas.acore.dbc.model.DbcField;
import net.kwas.acore.dbc.model.DbcType;

public class DbcTestBrokenRecord {

  @DbcField(DbcType.UINT32)
  public long field1;

  @DbcField(DbcType.INT32)
  public int field2;

  // Field intentionally missing annotation to break test
  public float field3;

  @DbcField(DbcType.STRING)
  public String field4;

}
