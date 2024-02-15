package com.drcome.project.common.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController {

    @RequestMapping("/error/403")
    public String error403() {
        return "error/403";
    }

    @RequestMapping("/error/500")
    public String error500() {
        return "error/500";
    }
}
