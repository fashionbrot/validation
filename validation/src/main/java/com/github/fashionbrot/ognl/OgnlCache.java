
package com.github.fashionbrot.ognl;

import lombok.extern.slf4j.Slf4j;
import ognl.MemberAccess;
import ognl.Ognl;
import ognl.OgnlException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public final class OgnlCache {

    private static final MemberAccess MEMBER_ACCESS = new DefaultMemberAccess(false);
    private static final OgnlClassResolver CLASS_RESOLVER = new OgnlClassResolver();
    private static final Map<String, Object> expressionCache = new ConcurrentHashMap<>();

    private OgnlCache() {
        // Prevent Instantiation of Static Class
    }

    public static Object getValue(String expression, Object root) {
        try {
            Map context = Ognl.createDefaultContext(root, MEMBER_ACCESS, CLASS_RESOLVER, null);
            return Ognl.getValue(parseExpression(expression), context, root);
        } catch (OgnlException e) {
            e.printStackTrace();
            log.error("Error evaluating expression '" + expression + "'. Cause: " + e, e);
        }
        return null;
    }

    private static Object parseExpression(String expression) throws OgnlException {
        Object node = expressionCache.get(expression);
        if (node == null) {
            node = Ognl.parseExpression(expression);
            expressionCache.put(expression, node);
        }
        return node;
    }

    public static Boolean executeExpression(String expression, Object root){
        Object value = getValue(expression, root);
        if (value==null){
            return null;
        }
        return (Boolean) value;
    }

}
