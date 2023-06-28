package com.github.fashionbrot.exception;

import com.github.fashionbrot.util.ObjectUtil;
import com.github.fashionbrot.constraint.MarsViolation;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper=false)
@Data
public class ValidatedException extends RuntimeException  {

    private String fieldName;

    private String msg;

    private String annotationName;

    private Object value;

    /**
     * Array or List
     * value index
     */
    private Integer valueIndex;

    private List<MarsViolation> violations;

    public ValidatedException(List<MarsViolation> violations) {
        super();
        if (ObjectUtil.isNotEmpty(violations) && violations.size()==1){
            MarsViolation marsViolation=  violations.get(0);
            this.fieldName = marsViolation.getFieldName();
            this.msg = marsViolation.getMsg();
            this.annotationName = marsViolation.getAnnotationName();
            this.value = marsViolation.getValue();
        }
        this.violations = violations;
    }

    public ValidatedException(String fieldName,String msg,String annotationName,Object value,Integer valueIndex){
        super();
        this.fieldName = fieldName;
        this.msg = msg;
        this.annotationName = annotationName;
        this.value = value;
        this.valueIndex = valueIndex;
    }


    public static void throwMsg(String fieldName,String msg,String annotationName,Object value,Integer valueIndex){
        throw new ValidatedException(fieldName,msg,annotationName,value,valueIndex);
    }
}
