package com.github.fashionbrot.entity;


import com.github.fashionbrot.annotation.*;
import com.github.fashionbrot.util.PatternSts;

public class DemoModel {

    @NotEmpty(message =  "test1 error")
    @javax.validation.constraints.NotEmpty(message = "test1 error")
    private String test1;

    @NotNull(message =  "test2 error")
    @javax.validation.constraints.NotNull(message = "test2 error")
    private String test2;

    @NotNull(message = "test3 error")
    @javax.validation.constraints.NotNull(message = "test3 error")
    private String test3;


    @NotNull(message = "test4 error")
    @javax.validation.constraints.NotNull(message = "test4 error")
    private String test4;

    @NotNull(message = "test5 error")
    @javax.validation.constraints.NotNull(message = "test5 error")
    private String test5;

    @NotEmpty(message = "test6 is null")
    @javax.validation.constraints.NotEmpty(message = "test6 is null")
    private String test6;

    @NotEmpty(message = "test7 is null")
    @javax.validation.constraints.NotEmpty(message = "test7 is null")
    private String test7;

    @NotEmpty(message = "test8 is null")
    @javax.validation.constraints.NotEmpty(message = "test8 is null")
    private String test8;

    @NotEmpty(message = "test9 is null")
    @javax.validation.constraints.NotEmpty(message = "test9 is null")
    private String test9;

    @NotEmpty(message = "test10 is null")
    @javax.validation.constraints.NotEmpty(message = "test10 is null")
    private String test10;
}
