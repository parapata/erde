package io.github.parapata.erde.editor.diagram.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import io.github.parapata.erde.Resource;
import io.github.parapata.erde.dialect.DialectProvider;
import io.github.parapata.erde.dialect.type.ColumnType;

class DomainModelTest {

    @Test
    void testClone() {
        DialectProvider provider = DialectProvider.MySQL;
        ColumnType columnType = ColumnType.newInstance(provider, "physicaName", Resource.TYPE_STRING, true, 5);
        DomainModel expected = DomainModel.newInstance(provider, "1", "domain_name", columnType, 10, 3, true);

        DomainModel actual = expected.clone();
        assertEquals(expected.getColumnSize(), actual.getColumnSize());
        assertEquals(expected.getDecimal(), actual.getDecimal());
        assertEquals(expected.getType(), actual.getType());
        assertEquals(expected.getDomainName(), actual.getDomainName());
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getLogicalName(), actual.getLogicalName());
        assertEquals(expected.getPhysicalName(), actual.getPhysicalName());
        assertEquals(expected.isDecimalSupported(), actual.isDecimalSupported());
        assertEquals(expected.isDomain(), actual.isDomain());
        assertEquals(expected.isSizeSupported(), actual.isSizeSupported());
        assertEquals(expected.isUnsigned(), actual.isUnsigned());
        assertEquals(expected.isUnsignedSupported(), actual.isUnsignedSupported());

        assertNotSame(expected, actual);
    }

}
