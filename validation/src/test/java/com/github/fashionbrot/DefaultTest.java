package com.github.fashionbrot;


import com.alibaba.fastjson2.JSON;
import com.github.fashionbrot.annotation.Default;
import com.github.fashionbrot.annotation.Valid;
import com.github.fashionbrot.annotation.Validated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Assert;
import org.junit.Test;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

public class DefaultTest {


    public class TestController{

        @Validated
        private void test(@Default(value = "abc") String string,
                           @Default(value = "abc") CharSequence charSequence,
                           @Default(value = "11.11") BigDecimal bigDecimal,
                           @Default(value = "12") BigInteger bigInteger,
                           @Default(value = "2")Short s1,
                           @Default(value = "3")Integer s2,
                           @Default(value = "4")Long s3,
                           @Default(value = "5.5")Float s4,
                           @Default(value = "6.5")Double s5){
        }
    }

    @Test
    public void test1(){

        String result="[\"abc\",\"abc\",11.11,12,2,3,4,5.5,6.5]";
        Object[] param=new Object[]{null,null,null,null,null,null,null,null,null};

        String returnResult ="" ;

        MethodUtil.getMsg(DefaultTest.TestController.class,"test",param);

        returnResult=JSON.toJSONString(param);
        System.out.println(returnResult);

        Assert.assertEquals(result,returnResult);
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class Test2Req{
        @Default(value = "abc")
        private String abc;
    }
    public class TestController2{
        @Validated
        private void test( Test2Req req){
        }
    }

    @Test
    public void test2(){
        String result="[{\"abc\":\"abc\"}]";
        String returnResult="";

        Test2Req test2Req =new Test2Req();
        test2Req.setAbc(null);
        Object[] params = {test2Req};

        MethodUtil.getMsg(DefaultTest.TestController2.class,"test",params);

        returnResult = JSON.toJSONString(params);
        System.out.println(returnResult);
        Assert.assertEquals(result,returnResult);
    }


    public class TestController3{
        @Validated
        private void test(@Valid List<Test2Req> req){

        }
    }

    @Test
    public void test3(){
        String result="[[{\"abc\":\"abc1\"},{\"abc\":\"abc\"}]]";
        String returnResult="";

        Test2Req test2Req =new Test2Req();
        test2Req.setAbc("abc1");

        Test2Req test22Req =new Test2Req();
        test22Req.setAbc(null);
        Object[] params = {Arrays.asList(test2Req, test22Req)};

        MethodUtil.getMsg(DefaultTest.TestController3.class,"test",params);


        returnResult = JSON.toJSONString(params);
        System.out.println(JSON.toJSONString(params));
        Assert.assertEquals(result,returnResult);
    }

    public class TestController4{
        @Validated
        private void test(@Valid Test2Req[] req){

        }
    }

    @Test
    public void test4(){
        String result="[[{\"abc\":\"abc1\"},{\"abc\":\"abc\"}]]";
        String returnResult="";

        Test2Req test2Req =new Test2Req();
        test2Req.setAbc("abc1");

        Test2Req test22Req =new Test2Req();
        test22Req.setAbc(null);
        Object[] params = {new Object[]{test2Req, test22Req}};

        MethodUtil.getMsg(DefaultTest.TestController4.class,"test",params);

        returnResult = JSON.toJSONString(params);
        System.out.println(JSON.toJSONString(params));
        Assert.assertEquals(result,returnResult);
    }



    @Data
    public class TestReq5{

        @Valid
        private List<TestReq55> testReq55s;
    }
    @Data
    public class TestReq55{
        @Default(value = "abc")
        private String abc;
    }
    public class TestController5{
        @Validated
        private void test(TestReq5 testReq5){

        }
    }

    @Test
    public void test5(){
        String result="[{\"testReq55s\":[{\"abc\":\"abc\"},{\"abc\":\"abc\"}]}]";
        String returnResult="";

        TestReq55 r1 =new TestReq55();
        r1.setAbc("abc");

        TestReq55 r2 =new TestReq55();
        r2.setAbc(null);

        TestReq5 testReq5=new TestReq5();
        testReq5.setTestReq55s(Arrays.asList(r1,r2));
        Object[] params =new Object[] {testReq5};


        MethodUtil.getMsg(DefaultTest.TestController5.class,"test",params);

        returnResult=JSON.toJSONString(params);
        System.out.println(returnResult);
        Assert.assertEquals(result,returnResult);
    }

}
