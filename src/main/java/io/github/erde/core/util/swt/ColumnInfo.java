package io.github.erde.core.util.swt;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ColumnInfo.
 *
 * @author modified by parapata
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface ColumnInfo {

    String PHYSICAL_NAME = "columninfo.physicalName";
    String LOGICAL_NAME = "columninfo.logicalName";
    String PARTIAL_MATCH = "columninfo.partialMatch";

    int index();

    String label();

    int width();
}
