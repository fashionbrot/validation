package com.github.fashionbrot.entity;

import com.github.fashionbrot.annotation.Default;
import com.github.fashionbrot.annotation.ValidatedParam;
import lombok.Data;

/**
 * @author fashionbrot
 */
@Data
@ValidatedParam("v")
public class Default1Entity {

    private Integer type;

    @Default(value = "123",expression = "v.type!=null and v.type==1 and springProfilesActive=='prod'")
    private String smsCode;


    @Default(value = "456",expression = "v.type!=null and v.type==2 and springProfilesActive=='default'")
    private String password;

}
