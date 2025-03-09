package net.kwas.acore.dbc.model.record;

import net.kwas.acore.dbc.model.DbcField;
import net.kwas.acore.dbc.model.DbcType;

public record ItemDbc(
    @DbcField(type = DbcType.UINT32)
    Long itemId,

    @DbcField(type = DbcType.UINT32)
    Long itemClass,

    @DbcField(type = DbcType.UINT32)
    Long itemSubClass,

    @DbcField(type = DbcType.INT32)
    Integer soundOverrideSubclassId,

    @DbcField(type = DbcType.UINT32)
    Long materialId,

    @DbcField(type = DbcType.UINT32)
    Long itemDisplayInfo,

    @DbcField(type = DbcType.INT32)
    Integer inventorySlotId,

    @DbcField(type = DbcType.INT32)
    Integer sheathId
) {
}
