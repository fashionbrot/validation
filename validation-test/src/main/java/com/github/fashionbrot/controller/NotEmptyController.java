package com.github.fashionbrot.controller;

import com.github.fashionbrot.annotation.NotEmpty;
import com.github.fashionbrot.annotation.Validated;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class NotEmptyController {

    @RequestMapping("/notEmpty")
    @ResponseBody
    @Validated
    public String  test(@NotEmpty(message = "入参 abc is null") String abc){
        return "ok";
    }

    @RequestMapping("/notEmpty2")
    @ResponseBody
    @Validated
    public String  notEmpty2(@NotEmpty String abc){
        return "ok";
    }

}
