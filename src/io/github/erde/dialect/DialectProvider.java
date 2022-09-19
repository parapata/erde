package io.github.erde.dialect;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.github.erde.ERDPlugin;

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

    private static Map<String, IDialect> dialectMap = ERDPlugin.getDefault().getContributedDialects();

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
