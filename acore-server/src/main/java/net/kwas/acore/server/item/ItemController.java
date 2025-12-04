package net.kwas.acore.server.item;

import net.kwas.acore.server.api.ItemApi;
import net.kwas.acore.server.model.Item;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RestController
public class ItemController implements ItemApi {

  private final ItemDatabase db;

  public ItemController(ItemDatabase db) {
    this.db = db;
  }

  @Override
  public Item getItem(Long itemId) {
    return db.getItem(itemId);
  }

  @Override
  public List<Item> getItems() {
    return db.getItems();
  }

}
