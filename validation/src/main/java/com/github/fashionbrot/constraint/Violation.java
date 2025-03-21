package com.github.fashionbrot.constraint;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Violation {
    /**
     * 验证字段
     */
    private String fieldName;
    /**
     * 错误信息
     */
    private String message;
    /**
     * 注解名
     */
    private String annotationName;
    /**
     * value
     */
    private Object value;
    /**
     * Array or List
     * value index
     */
    private Integer valueIndex;
}
