

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


# 3、引入方式
#### xml引入方式

```xml
        <!-- springmvc 依赖-->
<dependency>
    <groupId>com.github.fashionbrot</groupId>
    <artifactId>validation-spring</artifactId>
    <version>2.1.5</version>
</dependency>
    <!-- springboot 依赖-->
<dependency>
    <groupId>com.github.fashionbrot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
    <version>2.1.5</version>
</dependency>
```
#### gradle 引入
```bash
 # springmvc 依赖
implementation 'com.github.fashionbrot:validation-spring:2.1.5'
 #springboot 依赖
implementation 'com.github.fashionbrot:spring-boot-starter-validation:2.1.5'
```

#### 配置方式

##### springmvc 配置方式
```java
@Component
@Configuration
//localeParamName="lang" 开启国际化
//@EnableValidatedConfig 开启validation 功能
@EnableValidatedConfig(localeParamName="lang")
public class ValidConfig {

}
```


##### springboot配置方式
```properties
# 开启国际化
validated.locale-param-name=lang
```

##### 配置全局异常拦截
```java
package com.github.fashionbrot.exception;


import com.github.fashionbrot.constraint.MarsViolation;
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
        List<MarsViolation> violations = e.getViolations();
        if (ObjectUtil.isEmpty(violations)){// 快速失败抛出异常信息
            return e.getMsg();
        }else {
            return violations.stream().map(m -> m.getMsg()).collect(Collectors.joining(","));// 慢速失败抛出异常信息
        }
    }
}

```

# 4、使用注解介绍

#### @Validated 添加到方法上开启
|方法| 默认值                  | 说明                       |
|------|----------------------|--------------------------|
|Class<?>[] groups() default { }| {} | 校验组                      |
|boolean failFast() default true| true                 | true 快速失败                |
|boolean validReturnValue() default false| false                | 验证返回值 默认false            |


#### @Valid 注解支持功能说明，作用于 参数、属性上使用
#### 用于参数如： List《UserReq》 userListReq 、UserReq[] userReqs 、List<基本类型> reqList、基本类型[] reqs


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




# 5、自定义注解介绍

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


# 6、创建自定义注解样例

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




# 版权 | License

[Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0)



# 捐赠 | Donate

> [捐赠记录,感谢你们的支持！](https://gitee.com/fashionbrot/validation/wikis/%E6%8D%90%E8%B5%A0%E8%AE%B0%E5%BD%95)
<p >
<img alt="Image text" height="450" width="350"  src="./doc/zfb.jpg" title="捐赠给 validation" />
<img alt="Image text" height="450" width="350" src="./doc/wx.jpg" title="捐赠给 validation" />
</p>


