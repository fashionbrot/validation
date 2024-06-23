package com.github.fashionbrot.ognl;


import ognl.MemberAccess;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Member;
import java.lang.reflect.Modifier;
import java.util.Map;


public  class DefaultMemberAccess implements MemberAccess {
    public boolean allowPrivateAccess = false;
    public boolean allowProtectedAccess = false;
    public boolean allowPackageProtectedAccess = false;

    public DefaultMemberAccess(boolean allowAllAccess){
        this(allowAllAccess,allowAllAccess,allowAllAccess);
    }

    public DefaultMemberAccess(boolean allowPrivateAccess, boolean allowProtectedAccess, boolean allowPackageProtectedAccess){
        super();
        this.allowPrivateAccess = allowPrivateAccess;
        this.allowProtectedAccess = allowProtectedAccess;
        this.allowPackageProtectedAccess = allowPackageProtectedAccess;
    }

    @Override
    public Object setup(Map context, Object target, Member member, String propertyName) {
        Object result = null;
        if (isAccessible(context, target, member, propertyName)) {
            AccessibleObject    accessible = (AccessibleObject)member;
            if (!accessible.isAccessible()) {
                result = Boolean.FALSE;
                accessible.setAccessible(true);
            }
        }
        return result;
    }

    @Override
    public void restore(Map context, Object target, Member member, String propertyName, Object state) {
        if (state != null) {
            ((AccessibleObject) member).setAccessible(((Boolean) state).booleanValue());
        }
    }

    @Override
    public boolean isAccessible(Map map, Object o, Member member, String s) {
        int         modifiers = member.getModifiers();
        boolean     result = Modifier.isPublic(modifiers);

        if (!result) {
            if (Modifier.isPrivate(modifiers)) {
                result = isAllowPrivateAccess();
            } else {
                if (Modifier.isProtected(modifiers)) {
                    result = isAllowProtectedAccess();
                } else {
                    result = isAllowPackageProtectedAccess();
                }
            }
        }
        return result;
    }

    public boolean isAllowPrivateAccess() {
        return allowPrivateAccess;
    }

    public void setAllowPrivateAccess(boolean allowPrivateAccess) {
        this.allowPrivateAccess = allowPrivateAccess;
    }

    public boolean isAllowProtectedAccess() {
        return allowProtectedAccess;
    }

    public void setAllowProtectedAccess(boolean allowProtectedAccess) {
        this.allowProtectedAccess = allowProtectedAccess;
    }

    public boolean isAllowPackageProtectedAccess() {
        return allowPackageProtectedAccess;
    }

    public void setAllowPackageProtectedAccess(boolean allowPackageProtectedAccess) {
        this.allowPackageProtectedAccess = allowPackageProtectedAccess;
    }


}
