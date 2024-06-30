package com.github.fashionbrot.controller;

import com.alibaba.fastjson2.JSON;
import com.github.fashionbrot.ValidationConfiguration;
import com.github.fashionbrot.consts.ValidatedConst;
import com.github.fashionbrot.entity.DemoModel;
import com.github.fashionbrot.exception.ValidatedException;
import com.github.fashionbrot.util.ValidatorUtils;
import org.springframework.boot.autoconfigure.data.couchbase.CouchbaseDataAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;
import java.util.Set;

/**
 * @author fashionbrot
 */
@RestController
@RequestMapping("compare")
public class CompareController {


    @GetMapping("hibernate")
    public String hibernateValid(int count){
        long l =System.currentTimeMillis();
        DemoModel demoModel=new DemoModel();
        for (int i = 0; i < count; i++) {
            Set<ConstraintViolation<DemoModel>> constraintViolations = ValidatorUtils.validateAll(demoModel);
//            System.out.println(JSON.toJSONString(constraintViolations));
        }
        return System.currentTimeMillis()-l+"毫秒";
    }

    @GetMapping("fashionbrot")
    public String fashionbrotValid(int count){
        long l =System.currentTimeMillis();
        DemoModel demoModel=new DemoModel();
        for (int i = 0; i < count; i++) {
            try {
                ValidationConfiguration valid=new ValidationConfiguration(null,false,"","");
                valid.validReturnValue(demoModel);
            }catch (ValidatedException e){
//                System.out.println(JSON.toJSONString(e));
            }

        }
        return System.currentTimeMillis()-l+"毫秒";
    }

    public static void main(String[] args) {
        long startTime = System.nanoTime();

        // 等待1秒钟
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long endTime = System.nanoTime();

        long duration = endTime - startTime;
        System.out.println("Elapsed time in nanoseconds: " + duration);
    }

}
