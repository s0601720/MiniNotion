package de.htw_berlin.mininotion;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class PageController {

    @GetMapping("/api/pages")
    public List<Page> getAllPages() {
        return List.of(
                new Page(1L, "Erste Notiz", "Inhalt"),
                new Page(2L, "Zweite Notiz", "Inhalt")
        );
    }
}

