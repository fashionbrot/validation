package com.github.fashionbrot.constraint;


import com.alibaba.fastjson2.JSON;
import com.github.fashionbrot.TestEntity;
import com.github.fashionbrot.annotation.CustomAnnotation;
import com.github.fashionbrot.common.util.ObjectUtil;

public class CustomAnnotationConstraintValidator implements ConstraintValidator<CustomAnnotation, Object> {




    @Override
    public boolean isValid(CustomAnnotation annotation, Object value, Class<?> valueType) {
        return true;
    }

    @Override
    public Object modify(CustomAnnotation annotation, Object value, Class<?> valueType) {
        if (annotation.modify() && value==null){
            if (ObjectUtil.isNotEmpty(annotation.defaultValue())){
                TestEntity entity = JSON.parseObject(annotation.defaultValue(), TestEntity.class);
                return entity;
            }else{
                //可以根据需求动态修改
                //TODO 比如加密解密、数据脱敏、增加默认值 等等
                if (valueType == TestEntity.class){
                    TestEntity entity = new TestEntity();
                    entity.setId(2L);
                    entity.setName("李四");
                    return entity;
                }
            }
        }
        return value;
    }
}
