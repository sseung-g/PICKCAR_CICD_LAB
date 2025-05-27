package com.pickcar.presentation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/emulator")
public class EmulatorController {

    @GetMapping
    public String emulator() {
        return "index";
    }
}
