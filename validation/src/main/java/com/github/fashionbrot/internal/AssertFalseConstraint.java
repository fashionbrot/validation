
package com.github.fashionbrot.internal;


import com.github.fashionbrot.annotation.AssertFalse;
import com.github.fashionbrot.constraint.ConstraintValidator;

/**
 * Validates that the value passed is false
 */
public class AssertFalseConstraint implements ConstraintValidator<AssertFalse, Boolean> {

	@Override
	public boolean isValid(AssertFalse annotation, Boolean bool, Class<?> valueType) {
		return bool == null || !bool;
	}
}
