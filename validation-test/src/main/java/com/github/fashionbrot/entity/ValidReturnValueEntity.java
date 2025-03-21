package com.github.fashionbrot.entity;

import com.github.fashionbrot.annotation.NotEmpty;
import com.github.fashionbrot.annotation.ValidatedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author fashionbrot
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ValidatedName("v")
public class ValidReturnValueEntity {


    private Integer type;

    @NotEmpty(message =  "smsCode不能为空",expression = "v.type!=null and v.type==1")
    private String smsCode;

    @NotEmpty(message = "password不能为空",expression = "v.type!=null and v.type==2")
    private String password;
}
