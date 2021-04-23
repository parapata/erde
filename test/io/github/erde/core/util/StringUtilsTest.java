/*
 * Project : erde
 */

package io.github.erde.core.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * StringUtils Test.
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
        String[] strs = StringUtils.splitByWholeSeparator("xxaxxandyynyyandzzdzz", "and");
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

    @ParameterizedTest
    @CsvSource({
            "Abcde,A,true",
            "Abcde,a,false",
            "Abcde,b,false"
    })
    void startsWithTest(String str, String prefix, Boolean actual) {
        assertEquals(StringUtils.startsWith(str, prefix), actual);
    }

    @ParameterizedTest
    @CsvSource({
            "Abcde,A,true",
            "Abcde,a,true",
            "Abcde,b,false"
    })
    void startsWithIgnoreCase(String str, String prefix, Boolean actual) {
        assertEquals(StringUtils.startsWithIgnoreCase(str, prefix), actual);
    }
}
