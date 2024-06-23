package com.github.fashionbrot.constraint;


import com.github.fashionbrot.annotation.*;
import com.github.fashionbrot.common.util.ObjectUtil;
import com.github.fashionbrot.internal.*;
import com.github.fashionbrot.util.MethodUtil;

import java.lang.annotation.Annotation;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ConstraintHelper {


    private static Map<Class<? extends Annotation>, List<ConstraintValidator>> builtinConstraint = new ConcurrentHashMap<>();

    private static ValidatorContainer validatorContainer;

    static {


        Map<Class<? extends Annotation>, List<ConstraintValidator>> builtinConstraints = new HashMap<>();
        putTemp(builtinConstraints, NotNull.class, NotNullConstraint.class);
        putTemp(builtinConstraints, Default.class, DefaultConstraint.class);
        putTemp(builtinConstraints, AssertFalse.class, AssertFalseConstraint.class);
        putTemp(builtinConstraints, AssertTrue.class, AssertTrueConstraint.class);
        putTemp(builtinConstraints, BankCard.class, BankCardConstraint.class);
        putTemp(builtinConstraints, CreditCard.class, CreditCardConstraint.class);
        putTemp(builtinConstraints, Digits.class, DigitsConstraint.class);
        putTemp(builtinConstraints, Email.class, EmailConstraint.class);
        putTemp(builtinConstraints, IdCard.class, IdCardConstraint.class);
        putTemp(builtinConstraints, Length.class, LengthConstraint.class);
        putTemp(builtinConstraints, NotBlank.class, NotBlankConstraint.class);
        putTemp(builtinConstraints, NotEmpty.class, NotEmptyConstraint.class);
        putTemp(builtinConstraints, NotEqualLength.class, NotEqualsLengthConstraint.class);
        putTemp(builtinConstraints, Pattern.class, PatternConstraint.class);
        putTemp(builtinConstraints, Phone.class, PhoneConstraint.class);
        putTemp(builtinConstraints, Size.class, SizeConstraint.class);
        putTemp(builtinConstraints, Range.class, RangeConstraint.class);
        putTemp(builtinConstraints, Contain.class, ContainConstraint.class);
        putTemp(builtinConstraints, Max.class, MaxConstraint.class);
        putTemp(builtinConstraints, Min.class, MimConstraint.class);
        builtinConstraint.putAll(builtinConstraints);
    }


    private static <A extends Annotation> void putTemp(
        Map<Class<? extends Annotation>, List<ConstraintValidator>> builtinConstraints,
        Class<A> constraintType,
        Class<? extends ConstraintValidator>... constraintValidators) {
        if (constraintValidators != null && constraintValidators.length > 0) {
            List<ConstraintValidator> list = new ArrayList<>(constraintValidators.length);
            for (int i = 0; i < constraintValidators.length; i++) {
                list.add(MethodUtil.newInstance(constraintValidators[i]));
            }
            builtinConstraints.put(constraintType, list);
        }
    }

    public static <A extends Annotation> void putConstraintValidator(
        Class<A> constraintType,
        Class<? extends ConstraintValidator<? extends Annotation, ?>>[] constraintValidators) {
        if (constraintValidators != null && constraintValidators.length > 0) {
            List<ConstraintValidator> list = new ArrayList<>(constraintValidators.length);
            for (int i = 0; i < constraintValidators.length; i++) {
                list.add(inject(constraintValidators[i]));
            }
            builtinConstraint.putIfAbsent(constraintType, list);
        }
    }


    public static <A extends Annotation> List<ConstraintValidator> getConstraint(Class<A> constraintType) {
        return builtinConstraint.get(constraintType);
    }

    public static boolean containsKey(Class constraintType) {
        return builtinConstraint.containsKey(constraintType);
    }

    private static ConstraintValidator inject(Class<? extends ConstraintValidator<? extends Annotation, ?>> constraintValidator) {
        if (validatorContainer == null) {
            return MethodUtil.newInstance(constraintValidator);
        }
        ConstraintValidator injectConstraintValidator = validatorContainer.injectContainer(constraintValidator);
        if (injectConstraintValidator == null) {
            return MethodUtil.newInstance(constraintValidator);
        }
        return injectConstraintValidator;
    }

    public static void setValidatorContainer(ValidatorContainer validatorContainer) {
        if (validatorContainer != null) {
            ConstraintHelper.validatorContainer = validatorContainer;
        }
    }


    public static List<ConstraintValidator> getAnnotationConstraintValidator(Annotation annotation){
        List<ConstraintValidator> constraintValidatorList = ConstraintHelper.getConstraint(annotation.annotationType());
        if (ObjectUtil.isNotEmpty(constraintValidatorList)){
            return constraintValidatorList;
        }
        Constraint constraint = annotation.annotationType().getDeclaredAnnotation(Constraint.class);
        if (constraint==null){
            return null;
        }
        Class<? extends ConstraintValidator<? extends Annotation, ?>>[] classes = constraint.validatedBy();
        if (ObjectUtil.isEmpty(classes)) {
            return null;
        }
        ConstraintHelper.putConstraintValidator(annotation.annotationType(), classes);
        return ConstraintHelper.getConstraint(annotation.annotationType());
    }

}
