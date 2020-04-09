package org.cfx.utils;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.cfx.utils.Strings.capitaliseFirstLetter;
import static org.cfx.utils.Strings.isEmpty;
import static org.cfx.utils.Strings.join;
import static org.cfx.utils.Strings.lowercaseFirstLetter;
import static org.cfx.utils.Strings.repeat;
import static org.cfx.utils.Strings.toCsv;
import static org.cfx.utils.Strings.zeros;

public class StringsTest {

    @Test
    public void testToCsv() {
        assertEquals(toCsv(Collections.<String>emptyList()), (""));
        assertEquals(toCsv(Collections.singletonList("a")), ("a"));
        assertEquals(toCsv(Arrays.asList("a", "b", "c")), ("a, b, c"));
    }

    @Test
    public void testJoin() {
        assertEquals(join(Arrays.asList("a", "b"), "|"), ("a|b"));
        assertNull(join(null, "|"));
        assertEquals(join(Collections.singletonList("a"), "|"), ("a"));
    }

    @Test
    public void testCapitaliseFirstLetter() {
        assertEquals(capitaliseFirstLetter(""), (""));
        assertEquals(capitaliseFirstLetter("a"), ("A"));
        assertEquals(capitaliseFirstLetter("aa"), ("Aa"));
        assertEquals(capitaliseFirstLetter("A"), ("A"));
        assertEquals(capitaliseFirstLetter("Ab"), ("Ab"));
    }

    @Test
    public void testLowercaseFirstLetter() {
        assertEquals(lowercaseFirstLetter(""), (""));
        assertEquals(lowercaseFirstLetter("A"), ("a"));
        assertEquals(lowercaseFirstLetter("AA"), ("aA"));
        assertEquals(lowercaseFirstLetter("a"), ("a"));
        assertEquals(lowercaseFirstLetter("aB"), ("aB"));
    }

    @Test
    public void testRepeat() {
        assertEquals(repeat('0', 0), (""));
        assertEquals(repeat('1', 3), ("111"));
    }

    @Test
    public void testZeros() {
        assertEquals(zeros(0), (""));
        assertEquals(zeros(3), ("000"));
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void testEmptyString() {
        assertTrue(isEmpty(null));
        assertTrue(isEmpty(""));
        assertFalse(isEmpty("hello world"));
    }
}
