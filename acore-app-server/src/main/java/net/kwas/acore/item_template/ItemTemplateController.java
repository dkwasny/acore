package net.kwas.acore.item_template;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class ItemTemplateController {

    private final ItemTemplateDatabase db;

    public ItemTemplateController(ItemTemplateDatabase db) {
        this.db = db;
    }

    @GetMapping("/api/item-template")
    public Collection<ItemTemplate> getItemTemplates() {
        return db.getAll();
    }

    @GetMapping("/api/item-template/{id}")
    public ItemTemplate getItemTemplate(@PathVariable long id) {
        return db.getById(id);
    }

    @GetMapping("/api/item-template/{id}/name")
    public String getItemTemplateName(@PathVariable long id) {
        return db.getById(id).name();
    }

    @GetMapping("/api/item-template/{id}/description")
    public String getItemTemplateDescription(@PathVariable long id) {
        return db.getById(id).description();
    }

}


