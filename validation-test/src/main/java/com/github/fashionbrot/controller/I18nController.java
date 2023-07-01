package com.github.fashionbrot.controller;

import com.github.fashionbrot.annotation.NotEmpty;
import com.github.fashionbrot.annotation.Validated;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author fashionbrot
 */
@RequestMapping("/l18n")
@Controller
public class I18nController {

    @RequestMapping("/test")
    @ResponseBody
    @Validated
    public String test(@NotEmpty() String abc){
        return abc;
    }

}
