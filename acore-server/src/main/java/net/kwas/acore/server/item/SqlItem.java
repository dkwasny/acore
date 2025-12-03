package net.kwas.acore.server.item;

public record SqlItem(
  long id,
  long itemTemplateId,
  String name,
  String description,
  long ownerId,
  String ownerName,
  long count
) {
}
