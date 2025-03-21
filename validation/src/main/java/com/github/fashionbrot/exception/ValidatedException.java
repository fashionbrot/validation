package com.github.fashionbrot.exception;

import com.github.fashionbrot.constraint.Violation;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper=false)
@Data
public class ValidatedException extends RuntimeException  {

    private List<Violation> violations;


    public ValidatedException(List<Violation> violations) {
        super();
        this.violations = violations;
    }

    public ValidatedException(String fieldName, String message, String annotationName, Object value, Integer valueIndex) {
        super();
        this.violations = new ArrayList<>(1);
        this.violations.add(Violation.builder().fieldName(fieldName).message(message).annotationName(annotationName).value(value).valueIndex(valueIndex).build());
    }


    public static void throwMessage(String fieldName,String message,String annotationName,Object value,Integer valueIndex){
        throw new ValidatedException(fieldName,message,annotationName,value,valueIndex);
    }
}
