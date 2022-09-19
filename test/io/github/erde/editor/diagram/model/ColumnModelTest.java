package io.github.erde.editor.diagram.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import io.github.erde.Resource;
import io.github.erde.dialect.DialectProvider;
import io.github.erde.dialect.type.ColumnType;

class ColumnModelTest {

    @Test
    void test() throws CloneNotSupportedException {
        ColumnModel expected = new ColumnModel();
        expected.setAutoIncrement(true);
        expected.setColumnSize(100);
        expected.setDecimal(3);
        expected.setDefaultValue("1");
        expected.setDescription("description");
        expected.setLogicalName("logical_name");
        expected.setNotNull(true);
        expected.setPhysicalName("physical_name");
        expected.setPrimaryKey(true);
        expected.setUniqueKey(true);
        expected.setUnsigned(true);

        ColumnType columnType = ColumnType.newInstance(DialectProvider.MySQL, "physicaName", Resource.TYPE_STRING, true, 5);
        expected.setColumnType(columnType);

        ColumnModel actual = expected.clone();

        assertEquals(expected.isAutoIncrement(), actual.isAutoIncrement());
        assertEquals(expected.getColumnSize(), actual.getColumnSize());
        assertEquals(expected.getDecimal(), actual.getDecimal());
        assertEquals(expected.getDefaultValue(), actual.getDefaultValue());
        assertEquals(expected.getLogicalName(), actual.getLogicalName());
        assertEquals(expected.isNotNull(), actual.isNotNull());
        assertEquals(expected.getPhysicalName(), actual.getPhysicalName());
        assertEquals(expected.isPrimaryKey(), actual.isPrimaryKey());
        assertEquals(expected.isUniqueKey(), actual.isUniqueKey());
        assertEquals(expected.isUnsigned(), actual.isUnsigned());

        assertNotSame(expected, actual);
        assertNotSame(expected.getColumnType(), actual.getColumnType());

        ColumnType expColumnType = (ColumnType) expected.getColumnType();
        ColumnType actColumnType = (ColumnType) actual.getColumnType();

        assertEquals(expColumnType.getType(), actColumnType.getType());
        assertEquals(expColumnType.getLogicalName(), actColumnType.getLogicalName());
        assertEquals(expColumnType.getPhysicalName(), actColumnType.getPhysicalName());
        assertEquals(expColumnType.isDecimalSupported(), actColumnType.isDecimalSupported());
        assertEquals(expColumnType.isDomain(), actColumnType.isDomain());
        assertEquals(expColumnType.isSizeSupported(), actColumnType.isSizeSupported());
        assertEquals(expColumnType.isUnsignedSupported(), actColumnType.isUnsignedSupported());
    }
}
