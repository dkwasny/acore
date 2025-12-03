package net.kwas.acore.dbc.model.record;

import net.kwas.acore.dbc.model.DbcField;
import net.kwas.acore.dbc.model.DbcType;

public class DbcAreaTable {

  @DbcField(DbcType.UINT32)
  public long id;

  @DbcField(DbcType.UINT32)
  public long continentId;

  @DbcField(DbcType.UINT32)
  public long parentAreaId;

  @DbcField(DbcType.UINT32)
  public long areaBit;

  @DbcField(DbcType.UINT32)
  public long flags;

  @DbcField(DbcType.UINT32)
  public long soundProviderPref;

  @DbcField(DbcType.UINT32)
  public long soundProviderPrefUnderwater;

  @DbcField(DbcType.UINT32)
  public long ambienceId;

  @DbcField(DbcType.UINT32)
  public long zoneMusic;

  @DbcField(DbcType.UINT32)
  public long introSound;

  @DbcField(DbcType.INT32)
  public int explorationLevel;

  @DbcField(DbcType.STRING)
  public String areaName0;

  @DbcField(DbcType.STRING)
  public String areaName1;

  @DbcField(DbcType.STRING)
  public String areaName2;

  @DbcField(DbcType.STRING)
  public String areaName3;

  @DbcField(DbcType.STRING)
  public String areaName4;

  @DbcField(DbcType.STRING)
  public String areaName5;

  @DbcField(DbcType.STRING)
  public String areaName6;

  @DbcField(DbcType.STRING)
  public String areaName7;

  @DbcField(DbcType.STRING)
  public String areaName8;

  @DbcField(DbcType.STRING)
  public String areaName9;

  @DbcField(DbcType.STRING)
  public String areaName10;

  @DbcField(DbcType.STRING)
  public String areaName11;

  @DbcField(DbcType.STRING)
  public String areaName12;

  @DbcField(DbcType.STRING)
  public String areaName13;

  @DbcField(DbcType.STRING)
  public String areaName14;

  @DbcField(DbcType.STRING)
  public String areaName15;

  @DbcField(DbcType.UINT32)
  public long areaNamelangmask;

  @DbcField(DbcType.UINT32)
  public long factionGroupMask;

  @DbcField(DbcType.UINT32)
  public long liquidTypeId0;

  @DbcField(DbcType.UINT32)
  public long liquidTypeId1;

  @DbcField(DbcType.UINT32)
  public long liquidTypeId2;

  @DbcField(DbcType.UINT32)
  public long liquidTypeId3;

  @DbcField(DbcType.FLOAT)
  public float minElevation;

  @DbcField(DbcType.FLOAT)
  public float ambientMultiplier;

  @DbcField(DbcType.UINT32)
  public long lightId;

  @Override
  public String toString() {
    return "DbcAreaTable{" +
      "id=" + id +
      ", continentId=" + continentId +
      ", parentAreaId=" + parentAreaId +
      ", areaBit=" + areaBit +
      ", flags=" + flags +
      ", soundProviderPref=" + soundProviderPref +
      ", soundProviderPrefUnderwater=" + soundProviderPrefUnderwater +
      ", ambienceId=" + ambienceId +
      ", zoneMusic=" + zoneMusic +
      ", introSound=" + introSound +
      ", explorationLevel=" + explorationLevel +
      ", areaName0='" + areaName0 + '\'' +
      ", areaName1='" + areaName1 + '\'' +
      ", areaName2='" + areaName2 + '\'' +
      ", areaName3='" + areaName3 + '\'' +
      ", areaName4='" + areaName4 + '\'' +
      ", areaName5='" + areaName5 + '\'' +
      ", areaName6='" + areaName6 + '\'' +
      ", areaName7='" + areaName7 + '\'' +
      ", areaName8='" + areaName8 + '\'' +
      ", areaName9='" + areaName9 + '\'' +
      ", areaName10='" + areaName10 + '\'' +
      ", areaName11='" + areaName11 + '\'' +
      ", areaName12='" + areaName12 + '\'' +
      ", areaName13='" + areaName13 + '\'' +
      ", areaName14='" + areaName14 + '\'' +
      ", areaName15='" + areaName15 + '\'' +
      ", areaNamelangmask=" + areaNamelangmask +
      ", factionGroupMask=" + factionGroupMask +
      ", liquidTypeId0=" + liquidTypeId0 +
      ", liquidTypeId1=" + liquidTypeId1 +
      ", liquidTypeId2=" + liquidTypeId2 +
      ", liquidTypeId3=" + liquidTypeId3 +
      ", minElevation=" + minElevation +
      ", ambientMultiplier=" + ambientMultiplier +
      ", lightId=" + lightId +
      '}';
  }
}
