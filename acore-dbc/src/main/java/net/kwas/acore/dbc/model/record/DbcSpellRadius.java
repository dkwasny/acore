package net.kwas.acore.dbc.model.record;

import net.kwas.acore.dbc.model.DbcField;
import net.kwas.acore.dbc.model.DbcType;

public class DbcSpellRadius {

    @DbcField(DbcType.UINT32)
    public long id;

    @DbcField(DbcType.FLOAT)
    public float radius;

    @DbcField(DbcType.FLOAT)
    public float radiusPerLevel;

    @DbcField(DbcType.FLOAT)
    public float radiusMax;

    @Override
    public String toString() {
        return "DbcSpellRadius{" +
            "id=" + id +
            ", radius=" + radius +
            ", radiusPerLevel=" + radiusPerLevel +
            ", radiusMax=" + radiusMax +
            '}';
    }

}
