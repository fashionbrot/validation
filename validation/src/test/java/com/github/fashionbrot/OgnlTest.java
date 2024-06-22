package com.github.fashionbrot;

import com.github.fashionbrot.common.util.ObjectUtil;
import com.github.fashionbrot.ognl.DefaultMemberAccess;
import com.github.fashionbrot.ognl.OgnlCache;
import ognl.*;

import java.lang.reflect.Member;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class OgnlTest {

    public static void main(String[] args) {

        // 创建一个 ClassResolver 实例（使用默认实现）
        ClassResolver classResolver = new DefaultClassResolver();

        // 创建一个 TypeConverter 实例（使用默认实现）
        TypeConverter typeConverter = new DefaultTypeConverter();

        // 创建一个 MemberAccess 实例（使用默认实现）
        MemberAccess memberAccess =  new DefaultMemberAccess(false) ;



        // 创建一个 Person 对象
        Person person = new Person();
        person.setName("Alice");
        person.setAge(30);


        Map<String,Object> root=new HashMap<>();
        root.put("p",person);
        root.put("ObjectUtil",new ObjectUtil());

//        Map defaultContext = Ognl.createDefaultContext(root, memberAccess);
        // 创建一个 OgnlContext 对象
        OgnlContext context = new OgnlContext(classResolver,typeConverter,memberAccess);
//        context.put("ObjectUtil",new ObjectUtil());
        // 定义要执行的表达式
        String expression = "p.name != null && p.age != null";

        // 执行表达式
        try {
//
//            boolean result = (boolean) Ognl.getValue(expression, context, root);
//            System.out.println("Expression result: " + result);

            System.out.println(Ognl.getValue("p.name",context,root));


        } catch (OgnlException e) {
            e.printStackTrace();
        }

        boolean b = OgnlCache.executeExpression("name!=null", person);
        System.out.println(b);
    }

}
