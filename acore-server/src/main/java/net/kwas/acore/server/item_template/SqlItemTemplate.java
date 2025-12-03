package net.kwas.acore.server.item_template;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(schema = "acore_world", value = "item_template")
public record SqlItemTemplate(
  @Id
  long entry,
  String name,
  String description
) {
}
