package net.kwas.acore.dbc.model.record;

import net.kwas.acore.dbc.model.DbcField;
import net.kwas.acore.dbc.model.DbcType;

public class DbcCreatureDisplayInfo {

    @DbcField(DbcType.UINT32)
    public long id;

    @DbcField(DbcType.UINT32)
    public long modelId;

    @DbcField(DbcType.UINT32)
    public long soundId;

    @DbcField(DbcType.UINT32)
    public long extendedDisplayInfoId;

    @DbcField(DbcType.FLOAT)
    public float creatureModelScale;

    @DbcField(DbcType.UINT32)
    public long creatureModelAlpha;

    @DbcField(DbcType.STRING)
    public String textureVariation0;

    @DbcField(DbcType.STRING)
    public String textureVariation1;

    @DbcField(DbcType.STRING)
    public String textureVariation2;

    @DbcField(DbcType.STRING)
    public String portraitTextureName;

    @DbcField(DbcType.UINT32)
    public long sizeClass;

    @DbcField(DbcType.UINT32)
    public long bloodId;

    @DbcField(DbcType.UINT32)
    public long nPCSoundId;

    @DbcField(DbcType.UINT32)
    public long particleColorId;

    @DbcField(DbcType.UINT32)
    public long creatureGeosetData;

    @DbcField(DbcType.UINT32)
    public long objectEffectPackageId;

    @Override
    public String toString() {
        return "DbcCreatureDisplayInfo{" +
            "id=" + id +
            ", modelId=" + modelId +
            ", soundId=" + soundId +
            ", extendedDisplayInfoId=" + extendedDisplayInfoId +
            ", creatureModelScale=" + creatureModelScale +
            ", creatureModelAlpha=" + creatureModelAlpha +
            ", textureVariation0='" + textureVariation0 + '\'' +
            ", textureVariation1='" + textureVariation1 + '\'' +
            ", textureVariation2='" + textureVariation2 + '\'' +
            ", portraitTextureName='" + portraitTextureName + '\'' +
            ", sizeClass=" + sizeClass +
            ", bloodId=" + bloodId +
            ", nPCSoundId=" + nPCSoundId +
            ", particleColorId=" + particleColorId +
            ", creatureGeosetData=" + creatureGeosetData +
            ", objectEffectPackageId=" + objectEffectPackageId +
            '}';
    }
}
