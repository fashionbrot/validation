package com.github.fashionbrot;


import com.github.fashionbrot.annotation.NotEmpty;
import com.github.fashionbrot.annotation.NotNull;
import com.github.fashionbrot.annotation.Valid;
import lombok.Data;

import java.util.List;

@Data
public class TestModel extends TestModeSuper{

    @NotEmpty(msg = "test1 error")
    private String test1;

    private String test2;

    @NotNull(msg = "test3 error")
    private String test3;

    private String test4;


}
