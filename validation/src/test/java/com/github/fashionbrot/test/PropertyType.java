package com.github.fashionbrot.test;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author fashionbrot
 */
@Getter
@AllArgsConstructor
public enum PropertyType {
    PRIMITIVE,
    ARRAY,
    COLLECTION,
    ENTITY
}
