package com.github.fashionbrot.entity;

import com.github.fashionbrot.annotation.NotEmpty;
import com.github.fashionbrot.annotation.ValidatedParam;
import lombok.Data;

/**
 * @author fashionbrot
 */
@Data
//@ValidatedParam("expression")
public class ExpressionEntity {

    private Integer type;
    @NotEmpty(expression = "expressionEntity.type!=null and expressionEntity.type==1 and springProfilesActive=='prod'"
        ,msg = "验证码不能为空")
    private String smsCode;
    @NotEmpty(expression = "expressionEntity.type!=null and expressionEntity.type==2 and springProfilesActive=='default'"
        ,msg = "密码不能为空")
    private String password;

}
