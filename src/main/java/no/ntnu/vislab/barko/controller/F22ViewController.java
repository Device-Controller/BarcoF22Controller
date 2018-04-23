package no.ntnu.vislab.barko.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class F22ViewController {
    @RequestMapping("/f22")
    public String f22() {
        return "forward:/f22/f22.html";
    }
}
