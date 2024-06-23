package com.github.fashionbrot.controller;

import com.github.fashionbrot.annotation.NotEmpty;
import com.github.fashionbrot.annotation.Validated;
import com.github.fashionbrot.entity.Default1Entity;
import com.github.fashionbrot.entity.ExpressionEntity;
import org.springframework.web.bind.annotation.*;


/**
 * @author fashionbrot
 */
@RequestMapping("/expression")
@RestController
public class ExpressionController {

    @GetMapping("/test1")
    @ResponseBody
    @Validated(failFast = false)
    public String test1(Integer type,
                       @NotEmpty(expression = "type!=null and type==1",msg = "验证码不能为空") String smsCode,
                       @NotEmpty(expression = "type!=null and type==2",msg = "密码不能为空") String password){
        return "ok";
    }

    @GetMapping("/test2")
    @ResponseBody
    @Validated(failFast = false)
    public String test2( ExpressionEntity expression){
        return expression.toString();
    }

    @GetMapping("/test3")
    @ResponseBody
    @Validated(failFast = false)
    public String test3( Default1Entity entity){
        return entity.toString();
    }

}
