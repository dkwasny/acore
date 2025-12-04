package net.kwas.acore.server.item_template;

import net.kwas.acore.server.api.ItemTemplateApi;
import net.kwas.acore.server.model.ItemTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RestController
public class ItemTemplateController implements ItemTemplateApi {

  private final ItemTemplateDatabase db;

  public ItemTemplateController(ItemTemplateDatabase db) {
    this.db = db;
  }

  @Override
  public ItemTemplate getItemTemplate(Long itemTemplateId) {
    return db.getById(itemTemplateId);
  }

  @Override
  public List<ItemTemplate> getItemTemplates() {
    return db.getAll();
  }

}


