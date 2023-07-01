package com.github.fashionbrot.constraint;


import com.github.fashionbrot.entity.Test1Entity;
import com.github.fashionbrot.util.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

public class ObjectBeanConstraintValidator implements ConstraintValidator<ObjectBean, Object> {


    @Autowired
    private Environment environment;

    @Override
    public boolean isValid(ObjectBean annotation, Object value, Class<?> valueType) {
        if (environment!=null){
            System.out.println("111");
        }
        if (value instanceof Test1Entity){
            Test1Entity req= (Test1Entity) value;
            if (ObjectUtil.isEmpty(req.getA1())){
                return false;
            }
        }

        return true;
    }

    @Override
    public Object modify(ObjectBean annotation, Object value, Class<?> valueType) {
        System.out.println("CustomConstraintValidator:"+value);
        if (value instanceof Test1Entity){
            Test1Entity req= (Test1Entity) value;
            if (ObjectUtil.isEmpty(req.getA1())){
                req.setA1("A1");
            }
        }else{
            return "A111";
        }
        return value;
    }


}
