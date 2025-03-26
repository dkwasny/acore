package net.kwas.acore.item;

public record Item(
    long id,
    long itemTemplateId,
    String name,
    String description,
    String iconUrl,
    long ownerId,
    String ownerName,
    long count
) {
}
