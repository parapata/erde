package io.github.parapata.erde.sqleditor;

import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.IWordDetector;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.WhitespaceRule;
import org.eclipse.jface.text.rules.WordRule;

import io.github.parapata.erde.ErdePlugin;
import io.github.parapata.erde.preference.ErdePreferenceKey;

/**
 * SQLKeywordPartitionScanner.
 *
 * @author modified by parapata
 */
public class SQLKeywordPartitionScanner extends RuleBasedScanner {

    private static String[] KEYWORDS = { "select", "update", "insert",
            "into", "delete", "from", "where", "values", "set", "order", "by",
            "left", "outer", "join", "having", "group", "create", "alter", "drop", "table" };

    public SQLKeywordPartitionScanner() {
        IToken keyword = ErdePlugin.getDefault().getEditorColorProvider().getToken(ErdePreferenceKey.COLOR_KEYWORD);
        IToken other = ErdePlugin.getDefault().getEditorColorProvider().getToken(ErdePreferenceKey.COLOR_DEFAULT);

        WordRule wordRule = new WordRule(new IWordDetector() {
            @Override
            public boolean isWordPart(char c) {
                return Character.isJavaIdentifierPart(c);
            }

            @Override
            public boolean isWordStart(char c) {
                return Character.isJavaIdentifierStart(c);
            }
        }, other);
        for (String element : KEYWORDS) {
            wordRule.addWord(element, keyword);
            wordRule.addWord(element.toUpperCase(), keyword);
        }
        IRule[] rules = new IRule[2];
        rules[0] = wordRule;
        rules[1] = new WhitespaceRule(character -> Character.isWhitespace(character));

        setRules(rules);
    }
}
