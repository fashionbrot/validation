package com.github.fashionbrot.controller;

import com.alibaba.fastjson2.JSON;
import com.github.fashionbrot.entity.DemoModel;
import com.github.fashionbrot.entity.NotNullModel2;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Controller
@Validated
public class HibernateController {

    @RequestMapping("/demo1")
    @ResponseBody
    public String demo1(@RequestParam @Valid @Validated @NotNull(message = "验证码长度应该在4-6位之间") String abc){

        return abc;
    }

    @RequestMapping("/demo12")
    @ResponseBody
    public String demo12(@Valid @Validated NotNullModel2 m, BindingResult bindingResult){

        return m.getAbc();
    }

}
