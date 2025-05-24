package net.kwas.acore.dbc.model.record;

import net.kwas.acore.dbc.model.DbcField;
import net.kwas.acore.dbc.model.DbcType;

public class DbcSpellRange {

    @DbcField(DbcType.UINT32)
    public long id;

    @DbcField(DbcType.FLOAT)
    public float rangeMin0;

    @DbcField(DbcType.FLOAT)
    public float rangeMin1;

    @DbcField(DbcType.FLOAT)
    public float rangeMax0;

    @DbcField(DbcType.FLOAT)
    public float rangeMax1;

    @DbcField(DbcType.UINT32)
    public long flags;

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
    public String displayNameShort0;

    @DbcField(DbcType.STRING)
    public String displayNameShort1;

    @DbcField(DbcType.STRING)
    public String displayNameShort2;

    @DbcField(DbcType.STRING)
    public String displayNameShort3;

    @DbcField(DbcType.STRING)
    public String displayNameShort4;

    @DbcField(DbcType.STRING)
    public String displayNameShort5;

    @DbcField(DbcType.STRING)
    public String displayNameShort6;

    @DbcField(DbcType.STRING)
    public String displayNameShort7;

    @DbcField(DbcType.STRING)
    public String displayNameShort8;

    @DbcField(DbcType.STRING)
    public String displayNameShort9;

    @DbcField(DbcType.STRING)
    public String displayNameShort10;

    @DbcField(DbcType.STRING)
    public String displayNameShort11;

    @DbcField(DbcType.STRING)
    public String displayNameShort12;

    @DbcField(DbcType.STRING)
    public String displayNameShort13;

    @DbcField(DbcType.STRING)
    public String displayNameShort14;

    @DbcField(DbcType.STRING)
    public String displayNameShort15;

    @DbcField(DbcType.UINT32)
    public long displayNameShortlangmask;

    @Override
    public String toString() {
        return "DbcSpellRange{" +
            "id=" + id +
            ", rangeMin0=" + rangeMin0 +
            ", rangeMin1=" + rangeMin1 +
            ", rangeMax0=" + rangeMax0 +
            ", rangeMax1=" + rangeMax1 +
            ", flags=" + flags +
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
            ", displayNameShort0='" + displayNameShort0 + '\'' +
            ", displayNameShort1='" + displayNameShort1 + '\'' +
            ", displayNameShort2='" + displayNameShort2 + '\'' +
            ", displayNameShort3='" + displayNameShort3 + '\'' +
            ", displayNameShort4='" + displayNameShort4 + '\'' +
            ", displayNameShort5='" + displayNameShort5 + '\'' +
            ", displayNameShort6='" + displayNameShort6 + '\'' +
            ", displayNameShort7='" + displayNameShort7 + '\'' +
            ", displayNameShort8='" + displayNameShort8 + '\'' +
            ", displayNameShort9='" + displayNameShort9 + '\'' +
            ", displayNameShort10='" + displayNameShort10 + '\'' +
            ", displayNameShort11='" + displayNameShort11 + '\'' +
            ", displayNameShort12='" + displayNameShort12 + '\'' +
            ", displayNameShort13='" + displayNameShort13 + '\'' +
            ", displayNameShort14='" + displayNameShort14 + '\'' +
            ", displayNameShort15='" + displayNameShort15 + '\'' +
            ", displayNameShortlangmask=" + displayNameShortlangmask +
            '}';
    }

}
