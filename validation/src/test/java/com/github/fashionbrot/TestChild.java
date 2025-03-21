package com.github.fashionbrot;

import com.github.fashionbrot.annotation.NotEmpty;
import lombok.Data;

/**
 * @author fashionbrot
 */
@Data
public class TestChild {

    @NotEmpty(message =  "name 不能为空")
    private String name;
}
