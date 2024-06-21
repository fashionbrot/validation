package com.github.fashionbrot.ognl;

import ognl.MemberAccess;
import ognl.Ognl;
import ognl.OgnlException;

import java.util.HashMap;
import java.util.Map;

public class OgnlParse {

    private static final MemberAccess MEMBER_ACCESS = new DefaultMemberAccess(false);

    public static Object getValue(String expression,Object root) throws OgnlException {
        Map defaultContext = Ognl.createDefaultContext(root, MEMBER_ACCESS);
        return Ognl.getValue(expression,defaultContext,root);
    }

    public static boolean validationExpression(String expression,Object root){
        try {
            Map<String,Object> rootMap = new HashMap<>();
            rootMap.put("this",root);
            return (boolean) getValue(expression,rootMap);
        }catch (Exception e){
            return true;
        }
    }

}
