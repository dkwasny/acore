package net.kwas.acore.dbc.model.record;

import net.kwas.acore.dbc.model.DbcField;
import net.kwas.acore.dbc.model.DbcType;

public class DbcItemSubClass {

  @DbcField(DbcType.UINT32)
  public long classId;

  @DbcField(DbcType.UINT32)
  public long subClassId;

  @DbcField(DbcType.INT32)
  public int prerequisiteProficiency;

  @DbcField(DbcType.INT32)
  public int postrequisiteProficiency;

  @DbcField(DbcType.UINT32)
  public long flags;

  @DbcField(DbcType.UINT32)
  public long displayFlags;

  @DbcField(DbcType.UINT32)
  public long weaponParrySeq;

  @DbcField(DbcType.UINT32)
  public long weaponReadySeq;

  @DbcField(DbcType.UINT32)
  public long weaponAttackSeq;

  @DbcField(DbcType.UINT32)
  public long weaponSwingSize;

  @DbcField(DbcType.STRING)
  public String displayName0;

  @DbcField(DbcType.STRING)
  public String displayName1;

  @DbcField(DbcType.STRING)
  public String displayName2;

  @DbcField(DbcType.STRING)
  public String displayName3;

  @DbcField(DbcType.STRING)
  public String displayName4;

  @DbcField(DbcType.STRING)
  public String displayName5;

  @DbcField(DbcType.STRING)
  public String displayName6;

  @DbcField(DbcType.STRING)
  public String displayName7;

  @DbcField(DbcType.STRING)
  public String displayName8;

  @DbcField(DbcType.STRING)
  public String displayName9;

  @DbcField(DbcType.STRING)
  public String displayName10;

  @DbcField(DbcType.STRING)
  public String displayName11;

  @DbcField(DbcType.STRING)
  public String displayName12;

  @DbcField(DbcType.STRING)
  public String displayName13;

  @DbcField(DbcType.STRING)
  public String displayName14;

  @DbcField(DbcType.STRING)
  public String displayName15;

  @DbcField(DbcType.UINT32)
  public long displayNamelangmask;

  @DbcField(DbcType.STRING)
  public String verboseName0;

  @DbcField(DbcType.STRING)
  public String verboseName1;

  @DbcField(DbcType.STRING)
  public String verboseName2;

  @DbcField(DbcType.STRING)
  public String verboseName3;

  @DbcField(DbcType.STRING)
  public String verboseName4;

  @DbcField(DbcType.STRING)
  public String verboseName5;

  @DbcField(DbcType.STRING)
  public String verboseName6;

  @DbcField(DbcType.STRING)
  public String verboseName7;

  @DbcField(DbcType.STRING)
  public String verboseName8;

  @DbcField(DbcType.STRING)
  public String verboseName9;

  @DbcField(DbcType.STRING)
  public String verboseName10;

  @DbcField(DbcType.STRING)
  public String verboseName11;

  @DbcField(DbcType.STRING)
  public String verboseName12;

  @DbcField(DbcType.STRING)
  public String verboseName13;

  @DbcField(DbcType.STRING)
  public String verboseName14;

  @DbcField(DbcType.STRING)
  public String verboseName15;

  @DbcField(DbcType.UINT32)
  public long verboseNamelangmask;

  @Override
  public String toString() {
    return "DbcItemSubClass{" +
      "classId=" + classId +
      ", subClassId=" + subClassId +
      ", prerequisiteProficiency=" + prerequisiteProficiency +
      ", postrequisiteProficiency=" + postrequisiteProficiency +
      ", flags=" + flags +
      ", displayFlags=" + displayFlags +
      ", weaponParrySeq=" + weaponParrySeq +
      ", weaponReadySeq=" + weaponReadySeq +
      ", weaponAttackSeq=" + weaponAttackSeq +
      ", weaponSwingSize=" + weaponSwingSize +
      ", displayName0='" + displayName0 + '\'' +
      ", displayName1='" + displayName1 + '\'' +
      ", displayName2='" + displayName2 + '\'' +
      ", displayName3='" + displayName3 + '\'' +
      ", displayName4='" + displayName4 + '\'' +
      ", displayName5='" + displayName5 + '\'' +
      ", displayName6='" + displayName6 + '\'' +
      ", displayName7='" + displayName7 + '\'' +
      ", displayName8='" + displayName8 + '\'' +
      ", displayName9='" + displayName9 + '\'' +
      ", displayName10='" + displayName10 + '\'' +
      ", displayName11='" + displayName11 + '\'' +
      ", displayName12='" + displayName12 + '\'' +
      ", displayName13='" + displayName13 + '\'' +
      ", displayName14='" + displayName14 + '\'' +
      ", displayName15='" + displayName15 + '\'' +
      ", displayNamelangmask=" + displayNamelangmask +
      ", verboseName0='" + verboseName0 + '\'' +
      ", verboseName1='" + verboseName1 + '\'' +
      ", verboseName2='" + verboseName2 + '\'' +
      ", verboseName3='" + verboseName3 + '\'' +
      ", verboseName4='" + verboseName4 + '\'' +
      ", verboseName5='" + verboseName5 + '\'' +
      ", verboseName6='" + verboseName6 + '\'' +
      ", verboseName7='" + verboseName7 + '\'' +
      ", verboseName8='" + verboseName8 + '\'' +
      ", verboseName9='" + verboseName9 + '\'' +
      ", verboseName10='" + verboseName10 + '\'' +
      ", verboseName11='" + verboseName11 + '\'' +
      ", verboseName12='" + verboseName12 + '\'' +
      ", verboseName13='" + verboseName13 + '\'' +
      ", verboseName14='" + verboseName14 + '\'' +
      ", verboseName15='" + verboseName15 + '\'' +
      ", verboseNamelangmask=" + verboseNamelangmask +
      '}';
  }

}
