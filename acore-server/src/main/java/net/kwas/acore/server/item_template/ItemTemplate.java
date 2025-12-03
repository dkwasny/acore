package net.kwas.acore.server.item_template;

import java.net.URI;

public record ItemTemplate(
  long id,
  String name,
  String description,
  URI iconUrl
) {
}
