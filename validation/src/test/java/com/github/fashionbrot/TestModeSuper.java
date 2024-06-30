package com.github.fashionbrot;

import com.github.fashionbrot.annotation.NotNull;
import lombok.Data;

/**
 * @author fashionbrot
 */
@Data
public class TestModeSuper {

    @NotNull(msg = "pageNum 不能为空")
    private Integer pageNum;

    private Integer pageSize;
}
