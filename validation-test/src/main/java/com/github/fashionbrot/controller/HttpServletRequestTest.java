package com.github.fashionbrot.controller;


import com.github.fashionbrot.annotation.NotNull;
import com.github.fashionbrot.annotation.Validated;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HttpServletRequestTest {


    @GetMapping("requestTest")
    @ResponseBody
    @Validated
    public String requestTest(@NotNull HttpServletRequest request){

        return "ok";
    }

}
