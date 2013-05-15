package org.baeldung.spring.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BarController {

    public BarController() {
        super();
    }

    // API

    // with @RequestParam

    @RequestMapping(value = "/bars")
    @ResponseBody
    public String getBarBySimplePathWithRequestParam(@RequestParam("id") final long id) {
        return "Get a specific Bar with id=" + id;
    }

    @RequestMapping(value = "/bars", params = "id")
    @ResponseBody
    public String getBarBySimplePathWithExplicitRequestParam(@RequestParam("id") final long id) {
        return "Get a specific Bar with id=" + id;
    }

    @RequestMapping(value = "/bars", params = { "id", "second" })
    @ResponseBody
    public String getBarBySimplePathWithExplicitRequestParams(@RequestParam("id") final long id) {
        return "Get a specific Bar with id=" + id;
    }

}