package net.kwas.acore.item;

public record SqlItemSummary(
    long id,
    long itemTemplateId,
    String name,
    String description,
    long ownerId,
    String ownerName,
    long count
) {
}
