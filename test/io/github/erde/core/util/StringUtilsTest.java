/*
 * Project : erde
 */

package io.github.erde.core.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * .
 *
 * @author parapata
 */
class StringUtilsTest {

    @Test
    void isEmptyTest() {
        assertTrue(StringUtils.isEmpty(null));
        assertTrue(StringUtils.isEmpty(""));
        assertFalse(StringUtils.isEmpty("1"));
    }

    @Test
    void joinTest() {
        assertEquals(StringUtils.join(new String[] { "aaa", "bbb" }, ","), "aaa,bbb");
    }

    @Test
    void join2Test() {
        List<String> list = Arrays.asList(new String[] { "aaa", "bbb" });
        assertEquals(StringUtils.join(list, ","), "aaa,bbb");
    }

    @Test
    void splitTest() {
        String[] strs = StringUtils.split("aaa,bbb", ",");
        assertEquals(strs[0], "aaa");
        assertEquals(strs[1], "bbb");
    }

    @Test
    void splitByWholeSeparatorTest() {
        //String[] strs = StringUtils.splitByWholeSeparator("xxaxxandyynyyandzzdzz", "and");
        String[] strs = StringUtils.split("xxaxxandyynyyandzzdzz", "and");
        assertEquals(strs[0], "xxaxx");
        assertEquals(strs[1], "yynyy");
        assertEquals(strs[2], "zzdzz");
    }

    @Test
    void splitByCharacterTypeCamelCaseTest() {
        String[] strs = StringUtils.splitByCharacterTypeCamelCase("AbcDefGhi");
        assertEquals(strs[0], "Abc");
        assertEquals(strs[1], "Def");
        assertEquals(strs[2], "Ghi");
    }

    @Test
    void startsWithTest() {
        assertTrue(StringUtils.startsWith("Abcde", "A"));
        assertFalse(StringUtils.startsWith("Abcde", "a"));
    }

    @Test
    void startsWithIgnoreCaseTest() {
        assertTrue(StringUtils.startsWith("Abcde", "A"));
        assertFalse(StringUtils.startsWith("Abcde", "a"));
        assertFalse(StringUtils.startsWith("Abcde", "b"));
    }

}
