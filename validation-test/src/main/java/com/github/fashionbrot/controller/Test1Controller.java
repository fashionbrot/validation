package com.github.fashionbrot.controller;

import com.github.fashionbrot.annotation.Validated;
import com.github.fashionbrot.entity.CompanyPostEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author fashionbrot
 */

@Controller
public class Test1Controller {


    @RequestMapping("add")
    @ResponseBody
    @Validated
    public String add(CompanyPostEntity request){

        return "ok";
    }

}
