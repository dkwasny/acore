package net.kwas.acore.server;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UiController {

    // Redirect any ui-specific routes to the React main page.
    // This should work as long as we keep using a single page app.
    @GetMapping({"/app/**", "/swagger/**"})
    public String ui() {
        return "index.html";
    }

}
