package com.github.fashionbrot;

import com.github.fashionbrot.annotation.NotNull;
import com.github.fashionbrot.annotation.Validated;
import com.github.fashionbrot.exception.ValidatedException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ObjectTest {


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class Test2Req{
        @NotNull(message =  "abc不能为null")
        private String abc;
    }



    @Test
    public void test2(){

        Test2Req test2Req =new Test2Req();
        test2Req.setAbc(null);

        try {
            ValidHelper.validated(test2Req);
        }catch (ValidatedException e){
            String collect = e.getViolations().stream().map(m -> m.getMessage()).collect(Collectors.joining(","));
            System.out.println(collect);
        }


    }

}
