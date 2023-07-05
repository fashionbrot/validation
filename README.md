# mars-validated springmvc springboot springcloud dubbo  参数校验
###简单好用的 springmvc springboot springcloud dubbo  参数校验
### validation 基于纯java 开发
### validation-spring 基于spring框架封装的validation
### spring-boot-starter-validation 基于springboot封装的validation-spring

##### github:https://github.com/fashionbrot/validation.git
##### gitee :https://gitee.com/fashionbrot/validation.git



## 使用环境

spring4.0 及以上
jdk1.8    及以上


| 注解             | 作用类型                                                                                             |作用
|----------------|----------------------------------|---|
| NotBlank       | String |验证String 字符串是否为空|
| NotNull        | String,Object,Integer,Long,Double,Short,Float,BigDecimal, BigInteger| 验证对象是否为空|
| NotEmpty       | String |验证字符串不能为空|
| AssertFalse    | Boolean,boolean,String|只能为false|
| AssertTrue     | Boolean,boolean,String |只能为true|
| BankCard       | String |验证银行卡|
| CreditCard     | String |验证信用卡|
| Default        | Integer,Double,Long,Short,Float,BigDecimal,String|设置默认值|
| Digits         | String  |验证是否是数字|
| Email          | String  |验证是否是邮箱|
| IdCard         | String   |验证是否是身份证，验证18岁|
| Length         | int,long,short,double,Integer,Long,Float,Double,Short,String |验证长度|
| Pattern        | String  |正则表达式验证|
| Phone          | String  |验证手机号是否正确|
| Size           | object[],boolean[],byte[],char[],double[],float[],int[],long[],short[],String length,Collection,Map |验证大小值|
| NotEqualLength | String |验证长度|
| Range          | BigDecimal,BigInteger,byte,short,int,long,float,double | 判断值是否在一个范围之间                                                                                        |
| Contain        | String,CharSequence,BigDecimal,BigInteger,short,int,long,float,double |判断内容是否包含指定的值 |
## ！！！自定义注解实现类中，可以注入spring容器中的bean 如下
```java
/**
 *  自定义注解实现接口，调用顺序 isValid,modify
 * @param <A> Annotation
 * @param <T> T
 */
public  interface ConstraintValidator<A extends Annotation, T> {

    /**
     * annotation all
     * @param annotation annotation
     * @param value value
     * @param valueType valueType
     * @return boolean
     */
    boolean isValid(A annotation, T value,Class<?> valueType);

    /**
     * 修改 value 值
     * @param annotation annotation
     * @param value value
     * @param valueType valueType
     * @return T
     */
    default T modify(A annotation,T value,Class<?> valueType){
        return value;
    }

}
```
```java
package com.github.fashionbrot.constraint;

import java.lang.annotation.*;

@Documented
@Target({ElementType.PARAMETER,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ObjectBeanConstraintValidator.class})
public @interface ObjectBean {
    //没有任何参数
    String msg() default "~";
}
```
```java
package com.github.fashionbrot.constraint;


import com.github.fashionbrot.entity.Test1Entity;
import com.github.fashionbrot.util.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

public class ObjectBeanConstraintValidator implements ConstraintValidator<ObjectBean, Object> {
    //也可以注入其他spring容器内的bean
    @Autowired
    private Environment environment;

    @Override
    public boolean isValid(ObjectBean annotation, Object value, Class<?> valueType) {
        if (environment!=null){
            System.out.println("111");
        }
        if (value instanceof Test1Entity){
            Test1Entity req= (Test1Entity) value;
            if (ObjectUtil.isEmpty(req.getA1())){
                return false;
            }
        }
        return true;
    }

    @Override
    public Object modify(ObjectBean annotation, Object value, Class<?> valueType) {
        System.out.println("CustomConstraintValidator:"+value);
        if (value instanceof Test1Entity){
            Test1Entity req= (Test1Entity) value;
            if (ObjectUtil.isEmpty(req.getA1())){
                req.setA1("A1");
            }
        }else{
            return "A111";
        }
        return value;
    }

}
```

## @Validated 注解支持功能说明： 接口方法添加此注解开启参数验证
|方法| 默认值                  | 说明                       |
|------|----------------------|--------------------------|
|Class<?>[] validClass() default {}| {}                   | 需要校验的 class,只对填写的class验证 |
|Class<?>[] groups() default { }| {} | 校验组                      |
|boolean failFast() default true| true                 | true 快速失败                |
|boolean validReturnValue() default false| false                | 验证返回值 默认false            |

## @Valid 注解支持功能说明，作用于 参数、属性上使用
### 用于参数如： List《UserReq》 userListReq 、UserReq[] userReqs 、List<基本类型> reqList、基本类型[] reqs


## 1、springboot、springcloud 使用配置
### 1.1、导入jar
#### maven 依赖
```xml
        <!-- springboot 依赖-->
        <dependency>
               <groupId>com.github.fashionbrot</groupId>
               <artifactId>spring-boot-starter-validation</artifactId>
               <version>2.1.1</version>
        </dependency>
```
#### gradle 依赖
```bash
implementation 'com.github.fashionbrot:spring-boot-starter-validation:2.1.1'
```

### 1.2、参数验证
```java
@Controller
public class NotEmptyController {

    @RequestMapping("/notEmpty")
    @ResponseBody
    @Validated       //开启接口注解
    public String  test(@NotEmpty(msg = "入参 abc is null") String abc){
        return "ok";
    }

}
```


## 2、springmvc  使用配置
### 2.1、导入jar
#### maven依赖
```xml
        <!-- springmvc 依赖-->
        <dependency>
            <groupId>com.github.fashionbrot</groupId>
            <artifactId>validation-spring</artifactId>
            <version>2.1.1</version>
        </dependency>
```
#### gradle 依赖
```bash
implementation 'com.github.fashionbrot:validation-spring:2.1.1
```

### 2.2、配置扫描类
```java
@Component
@Configuration
@EnableValidatedConfig
public class ValidConfig {

}
```
### 2.3、使用配置
```java
@Controller
public class NotEmptyController {

    @RequestMapping("/notEmpty")
    @ResponseBody
    @Validated(failFast = true) //开启验证
    public NotEmptyModel  test(@NotEmpty(msg = "入参 abc is null") String abc){

        NotEmptyModel b=new NotEmptyModel();

        return b;
    }
    @RequestMapping("/notEmpty2")
    @ResponseBody
    @Validated(failFast = false)//开启验证
    public String test(@NotEmpty NotEmptyModel notNullModel){
        return notNullModel.getAbc();
    }
}

```

## 3、配置全局异常处理

```java
@RestControllerAdvice
public class GlobalExceptionHandler {

    //用于全局异常
    @ExceptionHandler(ValidatedException.class)
    @ResponseStatus(HttpStatus.OK)
    public Object ValidatedException(ValidatedException e) {
        List<MarsViolation> violations = e.getViolations();
        if (ObjectUtil.isEmpty(violations)){
            return e.getMsg();
        }else {
            String msg = String.join(",", violations.stream().map(m -> m.getMsg()).collect(Collectors.toList()));
            return msg;
        }
    }

}
```



# 以下是介绍各种配置

| 配置项                      | 默认值   | 说明                                                                                     |
|--------------------------|-------|----------------------------------------------------------------------------------------|
|validated.locale-param-name|""| 浏览器参数配置 lang=zh_CN`  ` 则读取的配置文件为valid_zh_CN.properties语言包 |


#### springboot配置方式
```properties
validated.locale-param-name=lang
```

#### springmvc 配置方式
```java
@Component
@Configuration
@EnableValidatedConfig(localeParamName="lang")
public class ValidConfig {

}
```

## 支持 默认值设置   hibernate默认不支持
```java
import Default;
@Data
public class BaseModel {

    @Default("1")
    private Integer pageNo;

    @Default("20")
    private Integer pageSize;
}
```





## ** 通过 group 来分组验证参数  **
```bash
访问：http://localhost:8080/group/add  返回：name 不能为空
访问：http://localhost:8080/group/edit 返回：id 不能为空,name 不能为空
```
```java
package com.github.fashion.test.controller;
import com.github.fashion.test.groups.EditGroup;
import com.github.fashion.test.model.GroupModel;
import Validated;
import DefaultGroup;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/group")
public class GroupController {

    @GetMapping("add")
    @ResponseBody
    @Validated(failFast = false)
    public String add(GroupModel groupModel){
        return "ok";
    }

    @GetMapping("edit")
    @ResponseBody
    @Validated(groups ={EditGroup.class,DefaultGroup.class},failFast = false)
    public String edit(GroupModel groupModel){
        return "ok";
    }

}
```
```java
package com.github.fashion.test.model;

import com.github.fashion.test.groups.EditGroup;
import NotEmpty;
import NotNull;
import lombok.Data;

@Data
public class GroupModel {


    private String abc;

    @NotNull(msg = "id 不能为空",groups = {EditGroup.class})
    private Long id;

    @NotEmpty(msg = "name 不能为空")
    private String name;

}

```





## ** 自定义注解看这里 **
```java
@RequestMapping("/valid/bean")
@Controller
public class ValidBeanController {

    @RequestMapping("/test")
    @ResponseBody
    @Validated
    public String test(@CustomBean ValidBeanModel validBeanModel){
        return validBeanModel.getA1()+validBeanModel.getA2();
    }
}
```
#### 定义注解
```java
package com.github.fashion.test.annotation;

import Constraint;

import java.lang.annotation.*;

@Documented
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {CustomBeanConstraintValidatorBean.class}) //设置自定义注解实现类
public @interface CustomBean {

    String msg() default "CustomBean 验证失败";

    /**
     * true 快速失败
     * @return boolean
     */
    boolean failFast() default true;
}
```
#### 自定义注解实现
```java
package com.github.fashion.test.annotation;

import com.github.fashion.test.model.ValidBeanModel;
import ConstraintValidator;
import com.github.fashion.test.test.CustomModel;
import ValidatedException;
import ExceptionUtil;
import ObjectUtil;
import lombok.Data;
import java.util.StringJoiner;

public class CustomBeanConstraintValidatorBean implements ConstraintValidator<CustomBean, Object> {

    @Override
    public boolean isValid(CustomBean annotation, Object value, Class<?> valueType) {
        if (value instanceof ValidBeanModel){
            ValidBeanModel beanModel= (ValidBeanModel) value;
            StringJoiner msg=new StringJoiner(",");
            if (ObjectUtil.isEmpty(beanModel.getA1())){
                msg.add("A1 不能为空") ;
                if (annotation.failFast()){
                    ValidatedException.throwMsg("A1",msg.toString());
                }
            }
            if (ObjectUtil.isEmpty(beanModel.getA2())){
                msg.add("A2 不能为空") ;
                if (annotation.failFast()){
                    ValidatedException.throwMsg("A2",msg.toString());
                }
            }
            if (msg.length()>0){
                ValidatedException.throwMsg("req",msg.toString());
            }
        }
        return true;
    }
    @Override
    public Object modify(CustomBean annotation, Object value, Class<?> valueType) {
        System.out.println("CustomConstraintValidator:"+value);
        if (value instanceof ValidBeanModel){
            ValidBeanModel beanModel= (ValidBeanModel) value;
            beanModel.setA1("1");
            beanModel.setA2("2");
            return beanModel;
        }if (value instanceof CustomModel){
            CustomModel customModel=(CustomModel)value;
            if (customModel!=null){
                customModel.setAbc("在 valid modify 中修改的abc");
            }
            return customModel;
        }
        return value;
    }

}

```


## 支持 国际化消息提示（现支持中英文两种）其他语言，请按照 mars-validated  resources 下面的 valid_zh_CN.properties 添加其他语言
## 在自己项目下面增加 valid_语言.properties 并且设置读取语言参数 localeParamName=lang  lang=语言  即可访问对应的 valid_语言.properties错误信息
```html
请求：http://localhost:8080/l18n/test?lang=en_us
提示：must not be empty
请求:http://localhost:8080/l18n/test?lang=zh_CN
提示：不能为空
```
```java

@RequestMapping("/l18n")
@Controller
public class I18nController {

    @RequestMapping("/test")
    @ResponseBody
    @Validated
    public String test(@NotEmpty() String abc){
        return abc;
    }

}
```


## 支持 dubbo 接口、实现类上方法上添加 @Validated ,设置 dubbo DubboProviderFilter 拦截器做统一处理

```java

public class DubboProviderFilter implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        Result result =  invoker.invoke(invocation);
        if(result.hasException() && result.getException() instanceof ValidationException){
            throw new CustomException(result.getException()); //自定义异常，全局拦截控制,或抛出 RpcException 自行拦截
        }
        return result;
    }
}
```




####  使用参数验证方式@Validated 开启接口验证 @Email验证邮箱格式,其他注解不在一一介绍

```bash
@Controller
public class DemoController {

    @Autowired
    private TestService testService;

    @RequestMapping("/emailCheck")
    @ResponseBody
    @Validated //注意此处
    public String demo(@Email String abc,){
        return testService.test(abc);
    }
}
@Controller
public class DemoController {

    @Autowired
    private TestService testService;

    @RequestMapping("/idcardCheck")
    @ResponseBody
    @Validated //注意此处
    public String demo(IdCardModel idCardModel){
        return testService.test("ac");
    }
}


**此处支持多继承验证***

public class IdCardModel extends BaseModel{

    @IdCard
    private String idCardModel;

    public String getIdCardModel() {
        return idCardModel;
    }

    public void setIdCardModel(String idCardModel) {
        this.idCardModel = idCardModel;
    }
}

@Service
public class TestService{
    @Validated
    public void test2(@IdCard String abc){
    }
}
```

#### 按照 @Validated(groups = {EditGroup.class}) valid 校验
```java
@Controller
public class DemoController {
     @RequestMapping("/test1")
     @ResponseBody
     @Validated(groups = {EditGroup.class})
     public String test1( @Custom(min = 1,groups = {EditGroup.class,AddGroup.class}) String abc1){
         return abc1;
     }


     @RequestMapping("/test2")
     @ResponseBody
     @Validated(groups = AddGroup.class)
     public String test2(GroupModel groupModel){
         return groupModel.getAbc();
     }
}
```


#### 可通过项目中的 https://github.com/fashionbrot/validation/tree/master/validation-test 参考使用demo
#### 如有问题请通过 https://github.com/fashionbrot/validation/issues 提出 告诉我们。我们非常认真地对待错误和缺陷，在产品面前没有不重要的问题。不过在创建错误报告之前，请检查是否存在报告相同问题的issues。


### 如有问题请联系邮箱 fashionbrot@163.com 反馈问题
