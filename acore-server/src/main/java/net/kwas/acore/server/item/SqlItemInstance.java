package net.kwas.acore.server.item;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

/*
 * This is a placeholder class to satisfy the type requirements
 * for `ItemRepository`.  We never really want to select item
 * instances directly.  Join queries are used instead.
 */

@Table(schema = "acore_characters", value = "item_instance")
public record SqlItemInstance(
    @Id
    long guid
) {
}
