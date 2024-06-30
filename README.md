

# 1、项目介绍

  <a href="https://www.apache.org/licenses/LICENSE-2.0">
    <img alt="code style" src="https://img.shields.io/badge/license-Apache%202-4EB1BA.svg?style=flat-square">
  </a>

### 简单好用的 java、spring、springmvc、springboot、springcloud 参数校验框架

#### validation 基于纯java 开发

#### validation-spring 基于spring框架封装的validation

#### spring-boot-starter-validation 基于springboot封装的validation-spring

##### github:https://github.com/fashionbrot/validation.git

##### gitee :https://gitee.com/fashionbrot/validation.git

# 2、使用环境介绍

#### 使用环境介绍

##### jdk8 => validation

##### spring4.0 >= validation-spring

##### springboot2.0 >= spring-boot-starter-validation


# 3、springmvc 引入
### 3.1 引入maven 配置 和 gradle配置
```xml
        <!-- springmvc 依赖-->
<dependency>
    <groupId>com.github.fashionbrot</groupId>
    <artifactId>validation-spring</artifactId>
    <version>3.0.1</version>
</dependency>
```
```gradle
implementation 'com.github.fashionbrot:validation-spring:3.0.1'
```
### 3.2  springmvc 配置方式
```java
@Component
@Configuration
//localeParamName="lang" 开启国际化
//springProfilesActive="prod" springboot启动的环境
//@EnableValidatedConfig 开启validation 功能
@EnableValidatedConfig(localeParamName="lang",springProfilesActive="prod")
public class ValidConfig {

}
```
### 3.3 配置全局异常拦截
```java
package com.github.fashionbrot.exception;
import com.github.fashionbrot.constraint.Violation;
import com.github.fashionbrot.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ValidatedException.class)
    @ResponseStatus(HttpStatus.OK)
    public Object ValidatedException(ValidatedException e) {
        List<Violation> violations = e.getViolations();
        if (ObjectUtil.isEmpty(violations)){// 快速失败抛出异常信息
            return e.getMsg();
        }else {
            return violations.stream().map(m -> m.getMsg()).collect(Collectors.joining(","));// 慢速失败抛出异常信息
        }
    }
}

```

### 3.4 使用
```java
@RequestMapping("/expression")
@RestController
public class ExpressionController {

    @GetMapping("/test1")
    @ResponseBody
    @Validated(failFast = false) //开启参数验证
    public String test1(Integer type,
                        @NotEmpty(expression = "type!=null and type==1 and springProfilesActive=='prod'",msg = "验证码不能为空") String smsCode,
                        @NotEmpty(expression = "type!=null and type==2 and springProfilesActive=='default'",msg = "密码不能为空") String password){
        return "ok";
    }

    @GetMapping("/test2")
    @ResponseBody
    @Validated(failFast = false)//开启参数验证
    public String test2( ExpressionEntity expression){
        return expression.toString();
    }

    @GetMapping("/test3")
    @ResponseBody
    @Validated(failFast = false)//开启参数验证
    public String test3( Default1Entity entity){
        return entity.toString();
    }

    @Data
    //@ValidatedParam("expression")//没有注解默认读取类名首字母小写,例如expressionEntity.type ; 有改注解则表达式中使用例如: expression.type
    public class ExpressionEntity {

        private Integer type;
        //如果expression 条件=true,则进行@NotEmpty的验证
        @NotEmpty(expression = "expressionEntity.type!=null and expressionEntity.type==1 and springProfilesActive=='prod'" ,msg = "验证码不能为空")
        private String smsCode;
        //如果expression 条件=true,则进行@NotEmpty的验证
        @NotEmpty(expression = "expressionEntity.type!=null and expressionEntity.type==2 and springProfilesActive=='default'",msg = "密码不能为空")
        private String password;

    }

}

```



# 4、springboot 接入
### 4.1 Maven引入,gradle引入

```xml
    <!-- springboot 依赖-->
<dependency>
    <groupId>com.github.fashionbrot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
    <version>3.0.1</version>
</dependency>
```
```bash
 #springboot 依赖
implementation 'com.github.fashionbrot:spring-boot-starter-validation:3.0.1'
```

### 4.2 配置方式
##### application.properties 中配置国际化默认中文
```properties
# 开启国际化 优先header读取lang，其次读取参数lang
validated.locale-param-name=lang
```

### 4.3配置全局异常拦截
```java
package com.github.fashionbrot.exception;
import com.github.fashionbrot.constraint.Violation;
import com.github.fashionbrot.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ValidatedException.class)
    @ResponseStatus(HttpStatus.OK)
    public Object ValidatedException(ValidatedException e) {
        List<Violation> violations = e.getViolations();
        if (ObjectUtil.isEmpty(violations)){// 快速失败抛出异常信息
            return e.getMsg();
        }else {
            return violations.stream().map(m -> m.getMsg()).collect(Collectors.joining(","));// 慢速失败抛出异常信息
        }
    }
}
```
### 4.4 在接口中使用

```java
/**
* @author fashionbrot
  */
@RestController
@RequestMapping("/vrv")
public class ValidReturnValueController {


    @GetMapping("test1")
    @Validated(failFast = false,validReturnValue = true)// 开启参数验证，failFast = false全部校验后抛出异常，validReturnValue = true 验证返回值
    public ValidReturnValueEntity test1(Integer type){
        return ValidReturnValueEntity.builder().type(type).build();
    }

}
```



# 4、注解介绍

#### @Validated 添加到方法上开启
|方法| 默认值                  | 说明                       |
|------|----------------------|--------------------------|
|Class<?>[] groups() default { }| {} | 校验组                      |
|boolean failFast() default true| true                 | true 快速失败                |
|boolean validReturnValue() default false| false                | 验证返回值 默认false            |


#### @Valid 注解支持功能说明，作用于 参数、属性上使用
#### 用于参数如： `List<UserReq>` userListReq 、UserReq[] userReqs 、List<基本类型> reqList、基本类型[] reqs


#### 参数注解
| 注解          | 作用类型           | 注解说明           |
|-------------|----------------|----------------|
| AssertFalse | boolean、String | 只能为false       |
| AssertTrue  | boolean String    | 只能为true        |
| BankCard    | String、CharSequence | 验证银行卡          |
| CreditCard  | String、CharSequence | 验证信用卡          |
| IdCard      | String、CharSequence | 验证身份证          |
| Email       | String、CharSequence | 验证邮箱格式         |
| Digits      | String、CharSequence | 验证是否是数字        |
| Default     | BigDecimal、BigInteger、Short、Integer、Long、Float、Double、String、CharSequence | 给属性设置默认值       |
| Length      | String、CharSequence | 验证字符串长度        |
| Max         | Long、Integer、Short、Float、Double、BigDecimal、BigInteger | 验证属性值不能大于设定值   |
| Min         | Long、Integer、Short、Float、Double、BigDecimal、BigInteger | 验证属性值不能小于设定值   |
 | NotBlank   | String、CharSequence | 元素值不为空         |
| NotEmpty | String、CharSequence、Collection、Map、Array | 验证对象不为空        |
| NotEqualLength | String、CharSequence、Collection、Map、Array | 验证对象长度必须是设定值   |
 | NotNull     | Object | 验证对象不能为空       |
|Pattern      | String、CharSequence | 验证正则表达式        |
 | Phone      | String、CharSequence | 验证手机号格式        |
 | Range      | BigDecimal、BigInteger、Short、Integer、Long、Float、Double  | 验证属性值必须在 设定值之间 |
 | Contain    | BigDecimal、BigInteger、Short、Integer、Long、Float、Double、String、CharSequence | 验证属性值包含设定值 |
 | Size       | Collection、Map、Array | 验证集合、数组 size 在设定值值之间 |


### 固定注解内的方法

| 方法名                                             | 示例                                                                                                        | 方法说明                                                                                      |
|-----------------------------------------------------|-------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------|
| `Class<?>[] groups() default  {}`                   | `@NotNull(msg = "不能为空", groups = {InsertGroup.class})`                                                  | 用于分组校验，指定注解适用的校验组，例如 `InsertGroup.class`，以便在特定场景下执行相应的校验逻辑                                |
| `String msg() default "${validated.Length.msg}";`   | `@NotNull(msg = "不能为空")`                                                                                | 指定在验证失败时显示的错误消息内容                                                                         |
| `String expression() default ""`                    | `@NotEmpty(expression = "type!=null and type==1 and springProfilesActive=='prod'", msg = "验证码不能为空") String smsCode` | 当 `expression` 条件为 `true` 时，执行 `@NotEmpty` 验证，适用于基于表达式的条件校验;表达式语法和mybatis xml if 中表达式语法一样 |


# 6、自定义注解介绍

#### (1)实现自定义注解的"注解"，需要在自定义注解上添加该注解
```java
package com.github.fashionbrot.constraint;
import java.lang.annotation.*;
@Documented
@Target({ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Constraint {
    /**
     * 属性 参数 实现接口
     * @return Class
     */
    Class<? extends ConstraintValidator<? extends Annotation, ?>>[] validatedBy() default {};
}
```
#### (2)自定义注解必须实现该类
```java
package com.github.fashionbrot.constraint;
import java.lang.annotation.Annotation;

/**
 *  自定义注解实现接口，调用顺序 isValid,modify
 * @param <A> Annotation
 * @param <T> T
 */
public  interface ConstraintValidator<A extends Annotation, T> {

    /**
     * annotation all  返回false 抛出异常
     * @param annotation annotation
     * @param value value
     * @param valueType valueType
     * @return boolean
     */
    boolean isValid(A annotation, T value,Class<?> valueType);
    /**
     * 修改 value 值,常用于 加密解密字段、脱敏字段 、动态给定默认值、及各种自定义逻辑
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
#### (1)创建注解
```java
package com.github.fashionbrot.annotation;
import com.github.fashionbrot.constraint.Constraint;
import com.github.fashionbrot.constraint.CustomAnnotationConstraintValidator;
import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD,  ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {CustomAnnotationConstraintValidator.class}) //实现类
public @interface CustomAnnotation {

    boolean modify() default true;//自己定义
    String defaultValue() default "";//自己定义

    /**
     * 固定方法  验证失败返回的错误信息
     * @return
     */
    String msg() default "validated.AssertTrue.msg";
    /**
     * 固定方法  验证组
     * default @see com.github.fashionbrot.groups.DefaultGroup
     * @return groups
     */
    Class<?>[] groups() default  {};
}
```
#### 创建注解实现类
```java
package com.github.fashionbrot.constraint;


import com.alibaba.fastjson2.JSON;
import com.github.fashionbrot.TestEntity;
import com.github.fashionbrot.annotation.CustomAnnotation;
import com.github.fashionbrot.util.ObjectUtil;

public class CustomAnnotationConstraintValidator implements ConstraintValidator<CustomAnnotation, Object> {

    //TODO 自定义注解可以注入spring 容器 bean
    @Resource
    private Environment environment;


    @Override
    public boolean isValid(CustomAnnotation annotation, Object value, Class<?> valueType) {
        //可根据自己逻辑进行拦截
        return true;
    }

    @Override
    public Object modify(CustomAnnotation annotation, Object value, Class<?> valueType) {
        if (annotation.modify() && value==null){
            if (ObjectUtil.isNotEmpty(annotation.defaultValue())){
                TestEntity entity = JSON.parseObject(annotation.defaultValue(), TestEntity.class);
                return entity;
            }else{
                //可以根据需求动态修改
                //TODO 比如加密解密、数据脱敏、增加默认值 等等
                if (valueType == TestEntity.class){
                    TestEntity entity = new TestEntity();
                    entity.setId(2L);
                    entity.setName("李四");
                    return entity;
                }
            }
        }
        return value;
    }
}
```
#### (3) 在接口上使用
```java
    @Controller
    public class Controller1{

        @Validated(failFast = false)//TODO 添加该注解开启参数验证
        public void test(@CustomAnnotation(defaultValue = "{\"id\":1,\"name\":\"张三\"}",modify = true) TestEntity test){

        }
    }
```


# 7使用国际化 【默认支持中文、英文,其他语言自己可添加】
```properties
# 开启国际化 优先header读取lang，其次读取参数lang
validated.locale-param-name=lang
```

##### 如果需要添加新语言，可以在 resources 目录下增加对应的属性文件。例如，添加俄语支持：
##### valid_ru_RU.properties
```properties
#valid_ru_RU.properties
validated.NotEmpty.msg= `не должно быть пустым
```
#####在我们header 中可以配置参数  lang=ru_RU  并且msg等于默认的${validated.NotEmpty.msg}
```shell
lang=ru_RU  msg=${validated.NotEmpty.msg}   则会提示：`не должно быть пустым`
lang=zh_CN  msg=${validated.NotEmpty.msg}   则会提示：`不能为空`
lang=en_US  msg=${validated.NotEmpty.msg}   则会提示：`must not be empty`
```





# 版权 | License

[Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0)



# 捐赠 | Donate

> [捐赠记录,感谢你们的支持！](https://gitee.com/fashionbrot/validation/wikis/%E6%8D%90%E8%B5%A0%E8%AE%B0%E5%BD%95)
<p >
<img alt="Image text" height="450" width="350"  src="./doc/zfb.jpg" title="捐赠给 validation" />
<img alt="Image text" height="450" width="350" src="./doc/wx.jpg" title="捐赠给 validation" />
</p>


