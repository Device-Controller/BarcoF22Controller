package no.ntnu.vislab.barko.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainViewController {
    @RequestMapping("/projector")
    public String f22() {
        return "forward:/f22/f22.html";
    }
}
