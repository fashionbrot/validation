package com.github.fashionbrot.controller;


import com.alibaba.fastjson2.JSON;
import com.github.fashionbrot.annotation.Validated;
import com.github.fashionbrot.entity.DemoModel;
import com.github.fashionbrot.util.EnvUtil;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;


@Slf4j
@Controller
public class HibernateCompareMars {




//
//    @RequestMapping("/demo2")
//    @ResponseBody
//    @com.github.fashionbrot.annotation.Validated
//    public String demo2(@com.github.fashionbrot.annotation.NotNull( message =  "验证码长度应该在4-6位之间") String abc){
//        return abc;
//    }
//
//    @RequestMapping("/demo3")
//    @ResponseBody
//    public String demo3(Integer count,String method){
//        long start = System.currentTimeMillis();
//        for(int i=0;i<count;i++){
//            HttpClientUtil.httpGet("http://localhost:8080/compare/"+method+"?i"+i,null,null,false);
//        }
//        long end = System.currentTimeMillis();
//
//
//        long start2 = System.currentTimeMillis();
//        /*for(int j=0;j<count;j++){
//            HttpClientUtil.httpGet("http://localhost:8080/compare/demo1?"+j,null,null,false);
//        }*/
//        long start3 = System.currentTimeMillis();
//        System.out.println(" 一共耗时："+(end-start));
//        System.out.println("mars      一共耗时："+(start3-start2));
//
//        return "ok"+(end-start);
//    }
//
//    @RequestMapping("/demo4")
//    @ResponseBody
//    public String demo4(Integer count,String method){
//        long start = System.currentTimeMillis();
//        for(int i=0;i<count;i++){
//            HttpClientUtil.httpGet("http://localhost:8080/compare/"+method+"?i"+i,null,null,false);
//        }
//        long end = System.currentTimeMillis();
//
//
//        long start2 = System.currentTimeMillis();
//        /*for(int j=0;j<count;j++){
//            HttpClientUtil.httpGet("http://localhost:8080/compare/demo1?"+j,null,null,false);
//        }*/
//        long start3 = System.currentTimeMillis();
//        System.out.println(" 一共耗时："+(end-start));
//        System.out.println("mars      一共耗时："+(start3-start2));
//
//        return "ok"+(end-start);
//    }


    @RequestMapping("/compare")
    @ResponseBody
    public String demo5(Integer count,String method){
        long start = System.currentTimeMillis();
        for(int i=0;i<count;i++){
            HttpResponse<String> s = Unirest.get("http://localhost:8082/" + method + "?i" + i).asString();
//            System.out.println(s.getBody());
        }
        long end = System.currentTimeMillis();
        String msg = (method+": 一共耗时："+(end-start)+" 毫秒 执行次数:"+count +" 系统："+ EnvUtil.getKey("os.name"));
        log.info("compare:"+msg);
        return msg;
    }

    @RequestMapping("/fashionbrot")
    @ResponseBody
    @Validated(failFast = false)
    public String demo5(DemoModel demoModel){
        return JSON.toJSONString(demoModel);
    }



    @RequestMapping("/hibernate")
    @ResponseBody
    @org.springframework.validation.annotation.Validated
    public String hibernate(@Valid DemoModel demoModel){
        return JSON.toJSONString(demoModel);
    }


}
