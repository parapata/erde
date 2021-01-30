package io.github.erde.dialect;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.github.erde.Activator;

/**
 * DialectProvider.
 *
 * @author modified by parapata
 */
public enum DialectProvider {

    /** H2. */
    H2("H2"),

    /** HSQLDB. */
    HSQLDB("HSQLDB"),

    /** MSSQL. */
    MSSQL("MSSQL"),

    /** MySQL. */
    MySQL("MySQL"),

    /** Oracle. */
    Oracle("Oracle"),

    /** PostgreSQL. */
    PostgreSQL("PostgreSQL");

    private static Map<String, IDialect> dialectMap = Activator.getDefault().getContributedDialects();

    public static List<String> getDialectNames() {
        return new ArrayList<>(dialectMap.keySet());
    }

    public static IDialect getDialect(String dialectName) {
        return dialectMap.get(dialectName);
    }

    private String dialectName;

    /**
     * Constructor.
     *
     * @param dialectName the dialect name
     */
    private DialectProvider(String dialectName) {
        this.dialectName = dialectName;
    }

    public String getDialectName() {
        return dialectName;
    }
}
