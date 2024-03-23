package io.github.parapata.erde.dialect;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.github.parapata.erde.ErdePlugin;

/**
 * DialectProvider.
 *
 * @author modified by parapata
 */
public enum DialectProvider {

    /** H2. */
    H2,

    /** HSQLDB. */
    HSQLDB,

    /** MSSQL. */
    MSSQL,

    /** MySQL. */
    MySQL,

    /** Oracle. */
    Oracle,

    /** PostgreSQL. */
    PostgreSQL;

    private static Map<String, IDialect> dialectMap = ErdePlugin.getDefault().getContributedDialects();

    public static List<String> getDialectNames() {
        return new ArrayList<>(dialectMap.keySet());
    }

    public static IDialect getDialect(String dialectName) {
        return dialectMap.get(dialectName);
    }

    public IDialect getDialect() {
        return dialectMap.get(name());
    }
}
