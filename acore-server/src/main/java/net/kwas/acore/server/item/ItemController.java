package net.kwas.acore.server.item;

import net.kwas.acore.server.model.Item;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RestController
public class ItemController {

  private final ItemDatabase db;

  public ItemController(ItemDatabase db) {
    this.db = db;
  }

  @GetMapping("/api/item")
  public List<Item> getItems() {
    return db.getItems();
  }

  @GetMapping("/api/item/{id}")
  public Item getItem(@PathVariable long id) {
    return db.getItem(id);
  }

}
