
package com.github.fashionbrot.controller;


import com.github.fashionbrot.annotation.Range;
import com.github.fashionbrot.annotation.Validated;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class RangeTest {


    @GetMapping("range1")
    @ResponseBody
    @Validated
    public String requestTest(@Range(min = 1,max = 2)Integer test1){

        return "ok";
    }

}
