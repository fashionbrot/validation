package com.github.fashionbrot.ognl;

import com.github.fashionbrot.util.Resources;
import ognl.DefaultClassResolver;

/**
 * @author fashionbrot
 */
public class OgnlClassResolver extends DefaultClassResolver {


    @Override
    protected Class toClassForName(String className) {
        return Resources.classForName(className);
    }

}
