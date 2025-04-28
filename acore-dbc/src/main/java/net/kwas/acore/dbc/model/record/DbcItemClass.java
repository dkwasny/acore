package net.kwas.acore.dbc.model.record;

import net.kwas.acore.dbc.model.DbcField;
import net.kwas.acore.dbc.model.DbcType;

public class DbcItemClass {

    @DbcField(DbcType.UINT32)
    public long id;

    @DbcField(DbcType.UINT32)
    public long subClassMapId;

    @DbcField(DbcType.UINT32)
    public long flags;

    @DbcField(DbcType.STRING)
    public String name0;

    @DbcField(DbcType.STRING)
    public String name1;

    @DbcField(DbcType.STRING)
    public String name2;

    @DbcField(DbcType.STRING)
    public String name3;

    @DbcField(DbcType.STRING)
    public String name4;

    @DbcField(DbcType.STRING)
    public String name5;

    @DbcField(DbcType.STRING)
    public String name6;

    @DbcField(DbcType.STRING)
    public String name7;

    @DbcField(DbcType.STRING)
    public String name8;

    @DbcField(DbcType.STRING)
    public String name9;

    @DbcField(DbcType.STRING)
    public String name10;

    @DbcField(DbcType.STRING)
    public String name11;

    @DbcField(DbcType.STRING)
    public String name12;

    @DbcField(DbcType.STRING)
    public String name13;

    @DbcField(DbcType.STRING)
    public String name14;

    @DbcField(DbcType.STRING)
    public String name15;

    @DbcField(DbcType.UINT32)
    public long namelangmask;

    @Override
    public String toString() {
        return "DbcItemClass{" +
            "id=" + id +
            ", subClassMapId=" + subClassMapId +
            ", flags=" + flags +
            ", name0='" + name0 + '\'' +
            ", name1='" + name1 + '\'' +
            ", name2='" + name2 + '\'' +
            ", name3='" + name3 + '\'' +
            ", name4='" + name4 + '\'' +
            ", name5='" + name5 + '\'' +
            ", name6='" + name6 + '\'' +
            ", name7='" + name7 + '\'' +
            ", name8='" + name8 + '\'' +
            ", name9='" + name9 + '\'' +
            ", name10='" + name10 + '\'' +
            ", name11='" + name11 + '\'' +
            ", name12='" + name12 + '\'' +
            ", name13='" + name13 + '\'' +
            ", name14='" + name14 + '\'' +
            ", name15='" + name15 + '\'' +
            ", namelangmask=" + namelangmask +
            '}';
    }

}
