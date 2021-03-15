/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.erde.core.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * StringUtils.
 *
 * @author modified by parapata
 * @since 1.0.12
 */
public class StringUtils {

    public static final String EMPTY = "";

    private StringUtils() {
        throw new AssertionError();
    }

    public static boolean isEmpty(String value) {
        return value == null || value.isEmpty() ? true : false;
    }

    public static boolean isNotEmpty(String value) {
        return value != null && !value.isEmpty() ? true : false;
    }

    public static boolean isNoneEmpty(String value) {
        return value == null || value.trim().isEmpty() ? true : false;
    }

    public static String defaultString(String value) {
        return isEmpty(value) ? EMPTY : value;
    }

    public static String defaultString(String value, String defaultValue) {
        return isEmpty(value) ? defaultValue : value;
    }

    public static boolean equals(String value1, String value2) {
        if (value1 == null) {
            return value2 == null;
        } else {
            return value1.equals(value2);
        }
    }

    public static boolean equalsIgnoreCase(String value1, String value2) {
        if (value1 == null) {
            return value2 == null;
        } else {
            return value1.equalsIgnoreCase(value2);
        }
    }

    public static String trim(String value) {
        return value == null ? null : value.trim();
    }

    public static String upperCase(String value) {
        return value == null ? null : value.toUpperCase();
    }

    public static String lowerCase(String value) {
        return value == null ? null : value.toLowerCase();
    }

    public static boolean isNumeric(String value) {
        if (value == null) {
            return false;
        } else {
            for (char ch : value.toCharArray()) {
                if (!Character.isDigit(ch)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static Boolean toBoolean(String value) {
        if (equalsIgnoreCase("TRUE", value) || equalsIgnoreCase("YES", value)) {
            return Boolean.TRUE;
        } else if (equalsIgnoreCase("FALSE", value) || equalsIgnoreCase("NO", value)) {
            return Boolean.FALSE;
        } else {
            return Boolean.FALSE;
        }
    }

    public static String join(String[] strs, String delimiter) {
        if (strs == null) {
            return EMPTY;
        }
        return join(Arrays.asList(strs), delimiter);
    }

    public static String join(List<String> array, String delimiter) {
        if (array == null) {
            return EMPTY;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.size(); i++) {
            sb.append(array.get(i));
            if (i < array.size() - 1) {
                sb.append(delimiter);
            }
        }
        return sb.toString();
    }

    public static String[] split(String value, String separatorChars) {
        return splitWorker(value, separatorChars, false);
    }

    public static String[] splitByWholeSeparator(String str, String separator) {
        return splitByWholeSeparatorWorker(str, separator, false);
    }

    public static String[] splitByCharacterTypeCamelCase(String value) {
        return splitByCharacterType(value, true);
    }

    public static boolean startsWith(CharSequence value, CharSequence prefix) {
        return startsWith(value, prefix, false);
    }

    public static boolean startsWithIgnoreCase(CharSequence value, CharSequence prefix) {
        return startsWith(value, prefix, true);
    }

    private static boolean startsWith(CharSequence str, CharSequence prefix, boolean ignoreCase) {
        if (str == null || prefix == null) {
            return str == prefix;
        }
        // Get length once instead of twice in the unlikely case that it changes.
        int preLen = prefix.length();
        if (preLen > str.length()) {
            return false;
        }
        return regionMatches(str, ignoreCase, 0, prefix, 0, preLen);
    }

    private static String[] splitByWholeSeparatorWorker(String value, String separator, boolean preserveAllTokens) {

        if (isEmpty(value)) {
            return new String[0];
        }

        if (separator == null || EMPTY.equals(separator)) {
            // Split on whitespace.
            return splitWorker(value, null, preserveAllTokens);
        }

        int separatorLength = separator.length();

        List<String> substrings = new ArrayList<>();
        int numberOfSubstrings = 0;
        int beg = 0;
        int end = 0;
        int len = value.length();
        while (end < len) {
            end = value.indexOf(separator, beg);

            if (end > -1) {
                if (end > beg) {
                    numberOfSubstrings += 1;

                    // The following is OK, because String.substring( beg, end ) excludes
                    // the character at the position 'end'.
                    substrings.add(value.substring(beg, end));

                    // Set the starting point for the next search.
                    // The following is equivalent to beg = end + (separatorLength - 1) + 1,
                    // which is the right calculation:
                    beg = end + separatorLength;
                } else {
                    // We found a consecutive occurrence of the separator, so skip it.
                    if (preserveAllTokens) {
                        numberOfSubstrings += 1;
                        substrings.add(EMPTY);
                    }
                    beg = end + separatorLength;
                }
            } else {
                // String.substring( beg ) goes from 'beg' to the end of the String.
                substrings.add(value.substring(beg));
                end = len;
            }
        }
        return substrings.toArray(new String[0]);
    }

    private static String[] splitByCharacterType(String value, boolean camelCase) {
        if (isEmpty(value)) {
            return new String[0];
        }

        List<String> list = new ArrayList<>();
        int tokenStart = 0;

        char[] ch = value.toCharArray();
        int currentType = Character.getType(ch[tokenStart]);

        for (int pos = tokenStart + 1; pos < ch.length; pos++) {
            int type = Character.getType(ch[pos]);
            if (type == currentType) {
                continue;
            }
            if (camelCase && type == Character.LOWERCASE_LETTER && currentType == Character.UPPERCASE_LETTER) {
                int newTokenStart = pos - 1;
                if (newTokenStart != tokenStart) {
                    list.add(new String(ch, tokenStart, newTokenStart - tokenStart));
                    tokenStart = newTokenStart;
                }
            } else {
                list.add(new String(ch, tokenStart, pos - tokenStart));
                tokenStart = pos;
            }
            currentType = type;
        }
        list.add(new String(ch, tokenStart, ch.length - tokenStart));
        return list.toArray(new String[0]);
    }

    private static String[] splitWorker(String value, String separatorChars, boolean preserveAllTokens) {

        if (isEmpty(value)) {
            return new String[0];
        }

        List<String> list = new ArrayList<>();

        boolean match = false;
        boolean lastMatch = false;

        int start = 0;
        int i = 0;
        if (separatorChars == null) {
            // Null separator means use whitespace
            for (i = 0; i < value.length(); i++) {
                if (Character.isWhitespace(value.charAt(i))) {
                    if (match || preserveAllTokens) {
                        lastMatch = true;
                        list.add(value.substring(start, i));
                        match = false;
                    }
                    start = ++i;
                    continue;
                }
                lastMatch = false;
                match = true;
            }
        } else if (separatorChars.length() == 1) {
            // Optimise 1 character case
            char sep = separatorChars.charAt(0);
            for (i = 0; i < value.length(); i++) {
                if (value.charAt(i) == sep) {
                    if (match || preserveAllTokens) {
                        lastMatch = true;
                        list.add(value.substring(start, i));
                        match = false;
                    }
                    start = ++i;
                    continue;
                }
                lastMatch = false;
                match = true;
            }
        } else {
            // standard case
            for (i = 0; i < value.length(); i++) {
                if (separatorChars.indexOf(value.charAt(i)) >= 0) {
                    if (match || preserveAllTokens) {
                        lastMatch = true;
                        list.add(value.substring(start, i));
                        match = false;
                    }
                    start = ++i;
                    continue;
                }
                lastMatch = false;
                match = true;
            }
        }
        if (match || preserveAllTokens && lastMatch) {
            list.add(value.substring(start, i));
        }
        return list.toArray(new String[0]);
    }

    private static boolean regionMatches(CharSequence cs, boolean ignoreCase, int thisStart, CharSequence substring,
            int start, int length) {
        if (cs instanceof String && substring instanceof String) {
            return ((String) cs).regionMatches(ignoreCase, thisStart, (String) substring, start, length);
        }
        int index1 = thisStart;
        int index2 = start;
        int tmpLen = length;

        // Extract these first so we detect NPEs the same as the java.lang.String version
        int srcLen = cs.length() - thisStart;
        int otherLen = substring.length() - start;

        // Check for invalid parameters
        if (thisStart < 0 || start < 0 || length < 0) {
            return false;
        }

        // Check that the regions are long enough
        if (srcLen < length || otherLen < length) {
            return false;
        }

        while (tmpLen-- > 0) {
            char c1 = cs.charAt(index1++);
            char c2 = substring.charAt(index2++);

            if (c1 == c2) {
                continue;
            }

            if (!ignoreCase) {
                return false;
            }

            // The real same check as in String.regionMatches():
            char u1 = Character.toUpperCase(c1);
            char u2 = Character.toUpperCase(c2);
            if (u1 != u2 && Character.toLowerCase(u1) != Character.toLowerCase(u2)) {
                return false;
            }
        }
        return true;
    }
}
