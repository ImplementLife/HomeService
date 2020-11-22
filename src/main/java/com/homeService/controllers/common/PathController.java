package com.homeService.controllers.common;

import com.homeService.lib.Path;
import org.springframework.ui.Model;

import java.util.ArrayList;

public class PathController {
    public void getPath(Model model, ArrayList<Path> path) {
        model.addAttribute("path", path);
    }
}
