
package com.github.fashionbrot.internal;


import com.github.fashionbrot.annotation.AssertTrue;
import com.github.fashionbrot.constraint.ConstraintValidator;

/**
 * Validates that the value passed is true
 *
 * @author Alaa Nassef
 */
public class AssertTrueConstraint implements ConstraintValidator<AssertTrue, Boolean> {

	@Override
	public boolean isValid(AssertTrue annotation, Boolean bool, Class<?> valueType) {
		return bool == null || bool;
	}
}
