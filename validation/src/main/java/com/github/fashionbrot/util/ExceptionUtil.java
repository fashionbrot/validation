package com.github.fashionbrot.util;

import com.github.fashionbrot.common.util.ObjectUtil;
import com.github.fashionbrot.constraint.Violation;
import com.github.fashionbrot.exception.ValidatedException;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ExceptionUtil {

    private static final ThreadLocal<List<Violation>> LOCAL = ThreadLocal.withInitial(ArrayList::new);


    public static void addViolation(Violation Violation){
        LOCAL.get().add(Violation);
    }

    public static List<Violation> getViolationList(){
        return LOCAL.get();
    }

    public static void reset(){
        LOCAL.remove();
    }


    public static void throwException(){
        List<Violation> ViolationList = getViolationList();
        if (ObjectUtil.isNotEmpty(ViolationList)) {
            throw new ValidatedException(ViolationList);
        }
    }

    public static void throwException(List<Violation> ViolationList){
        if (ObjectUtil.isNotEmpty(ViolationList)) {
            throw new ValidatedException(ViolationList);
        }
    }



}
