package com.github.fashionbrot;

import com.github.fashionbrot.annotation.AssertTrue;
import com.github.fashionbrot.util.ClassUtil;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ClassTest {

    @Test
    public void CollectionTest(){
        assertTrue(ClassUtil.isCollection(Collection.class));
        assertTrue(ClassUtil.isCollection(List.class));
        assertTrue(ClassUtil.isCollection(Set.class));

        // Test non-collection classes
        assertFalse(ClassUtil.isCollection(String.class));
        assertFalse(ClassUtil.isCollection(null));

        // Test array and primitive types
        assertFalse(ClassUtil.isCollection(String[].class));
        assertFalse(ClassUtil.isCollection(int.class));

        // Test custom collection type (assuming MyCustomList extends Collection)
        assertTrue(ClassUtil.isCollection(ArrayList.class));
    }

}
