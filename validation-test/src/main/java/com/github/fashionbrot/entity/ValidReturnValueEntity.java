package com.github.fashionbrot.entity;

import com.github.fashionbrot.annotation.NotEmpty;
import com.github.fashionbrot.annotation.ValidatedParam;
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
@ValidatedParam("v")
public class ValidReturnValueEntity {


    private Integer type;

    @NotEmpty(msg = "smsCode不能为空",expression = "v.type!=null and v.type==1")
    private String smsCode;

    @NotEmpty(msg = "password不能为空",expression = "v.type!=null and v.type==2")
    private String password;
}
