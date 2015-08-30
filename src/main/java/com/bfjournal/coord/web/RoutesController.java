package com.bfjournal.coord.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoutesController {

    @RequestMapping({
            "/bikes",
            "/milages",
            "/gallery",
            "/tracks",
            "/tracks/{id:\\w+}",
            "/location",
            "/about"
    })
    public String forwardToIndex() {
        return "forward:/index.html";
    }

}