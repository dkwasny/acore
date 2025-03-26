package net.kwas.acore.item;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(schema = "acore_characters", value = "item_instance")
public record SqlItem(
    @Id
    @Column("guid")
    long id,
    @Column("itemEntry")
    long itemTemplateId,
    @Column("owner_guid")
    long ownerId,
    long count
) {
}
