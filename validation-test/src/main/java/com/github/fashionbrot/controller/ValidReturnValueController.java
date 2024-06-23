package com.github.fashionbrot.controller;

import com.github.fashionbrot.annotation.Validated;
import com.github.fashionbrot.entity.ValidReturnValueEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fashionbrot
 */
@RestController
@RequestMapping("/vrv")
public class ValidReturnValueController {


    @GetMapping("test1")
    @Validated(failFast = false,validReturnValue = true)
    public ValidReturnValueEntity test1(Integer type){
        return ValidReturnValueEntity.builder().type(type).build();
    }

}
