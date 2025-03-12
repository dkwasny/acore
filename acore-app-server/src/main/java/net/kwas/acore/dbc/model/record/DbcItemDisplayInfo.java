package net.kwas.acore.dbc.model.record;

import net.kwas.acore.dbc.model.DbcField;
import net.kwas.acore.dbc.model.DbcType;

public class DbcItemDisplayInfo {

    @DbcField(DbcType.UINT32)
    public long id;

    @DbcField(DbcType.STRING)
    public String modelName0;

    @DbcField(DbcType.STRING)
    public String modelName1;

    @DbcField(DbcType.STRING)
    public String modelTexture0;

    @DbcField(DbcType.STRING)
    public String modelTexture1;

    @DbcField(DbcType.STRING)
    public String inventoryIcon0;

    @DbcField(DbcType.STRING)
    public String inventoryIcon1;

    @DbcField(DbcType.UINT32)
    public long geosetGroup0;

    @DbcField(DbcType.UINT32)
    public long geosetGroup1;

    @DbcField(DbcType.UINT32)
    public long geosetGroup2;

    @DbcField(DbcType.UINT32)
    public long flags;

    @DbcField(DbcType.UINT32)
    public long spellVisualId;

    @DbcField(DbcType.UINT32)
    public long groupSoundIndex;

    @DbcField(DbcType.UINT32)
    public long helmetGeosetVisId0;

    @DbcField(DbcType.UINT32)
    public long helmetGeosetVisId1;

    @DbcField(DbcType.STRING)
    public String texture0;

    @DbcField(DbcType.STRING)
    public String texture1;

    @DbcField(DbcType.STRING)
    public String texture2;

    @DbcField(DbcType.STRING)
    public String texture3;

    @DbcField(DbcType.STRING)
    public String texture4;

    @DbcField(DbcType.STRING)
    public String texture5;

    @DbcField(DbcType.STRING)
    public String texture6;

    @DbcField(DbcType.STRING)
    public String texture7;

    @DbcField(DbcType.INT32)
    public int itemVisual;

    @DbcField(DbcType.UINT32)
    public long particleColorId;

    @Override
    public String toString() {
        return "ItemDisplayInfoDbc{" +
            "id=" + id +
            ", modelName0='" + modelName0 + '\'' +
            ", modelName1='" + modelName1 + '\'' +
            ", modelTexture0='" + modelTexture0 + '\'' +
            ", modelTexture1='" + modelTexture1 + '\'' +
            ", inventoryIcon0='" + inventoryIcon0 + '\'' +
            ", inventoryIcon1='" + inventoryIcon1 + '\'' +
            ", geosetGroup0=" + geosetGroup0 +
            ", geosetGroup1=" + geosetGroup1 +
            ", geosetGroup2=" + geosetGroup2 +
            ", flags=" + flags +
            ", spellVisualId=" + spellVisualId +
            ", groupSoundIndex=" + groupSoundIndex +
            ", helmetGeosetVisId0=" + helmetGeosetVisId0 +
            ", helmetGeosetVisId1=" + helmetGeosetVisId1 +
            ", texture0='" + texture0 + '\'' +
            ", texture1='" + texture1 + '\'' +
            ", texture2='" + texture2 + '\'' +
            ", texture3='" + texture3 + '\'' +
            ", texture4='" + texture4 + '\'' +
            ", texture5='" + texture5 + '\'' +
            ", texture6='" + texture6 + '\'' +
            ", texture7='" + texture7 + '\'' +
            ", itemVisual=" + itemVisual +
            ", particleColorId=" + particleColorId +
            '}';
    }

}
