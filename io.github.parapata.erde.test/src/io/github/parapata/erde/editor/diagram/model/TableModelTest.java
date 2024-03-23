package io.github.parapata.erde.editor.diagram.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import io.github.parapata.erde.dialect.type.IndexType;

class TableModelTest {

    @Test
    void test() {
        TableModel expected = new TableModel();
        expected.setPhysicalName("physical_name");
        expected.setLogicalName("logical_name");
        expected.setDescription("description");
        expected.setSchema("schema");
        expected.setBackgroundColor(null);

        ColumnModel expColumn = new ColumnModel();
        expColumn.setAutoIncrement(true);
        expColumn.setColumnSize(100);
        expColumn.setDecimal(3);
        expColumn.setDefaultValue("1");
        expColumn.setDescription("description");
        expColumn.setLogicalName("logical_name");
        expColumn.setNotNull(true);
        expColumn.setPhysicalName("physical_name");
        expColumn.setPrimaryKey(true);
        expColumn.setUniqueKey(true);
        expColumn.setUnsigned(true);
        expected.setColumns(Arrays.asList(expColumn));

        IndexModel expIndex = new IndexModel();
        expIndex.setIndexName("index_name");
        expIndex.setIndexType(IndexType.INDEX);
        expIndex.setColumns(Arrays.asList("column_name"));
        expected.setIndices(Arrays.asList(expIndex));

        TableModel actual = expected.clone();

        assertEquals(expected.getPhysicalName(), actual.getPhysicalName());
        assertEquals(expected.getLogicalName(), actual.getLogicalName());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getSchema(), actual.getSchema());
        assertEquals(expected.getBackgroundColor(), actual.getBackgroundColor());

        assertNotSame(expected, actual);
        assertNotSame(expected.getColumns(), actual.getColumns());
        assertNotSame(expected.getIndices(), actual.getIndices());

        assertEquals(expected.getColumns().size(), actual.getColumns().size());
        ColumnModel actColumn = actual.getColumns().get(0);

        assertNotSame(expColumn, actColumn);
        assertEquals(expColumn.isAutoIncrement(), actColumn.isAutoIncrement());
        assertEquals(expColumn.getColumnSize(), actColumn.getColumnSize());
        assertEquals(expColumn.getDecimal(), actColumn.getDecimal());
        assertEquals(expColumn.getDefaultValue(), actColumn.getDefaultValue());
        assertEquals(expColumn.getDescription(), actColumn.getDescription());
        assertEquals(expColumn.getLogicalName(), actColumn.getLogicalName());
        assertEquals(expColumn.isNotNull(), actColumn.isNotNull());
        assertEquals(expColumn.getPhysicalName(), actColumn.getPhysicalName());
        assertEquals(expColumn.isPrimaryKey(), actColumn.isPrimaryKey());
        assertEquals(expColumn.isUnsigned(), actColumn.isUnsigned());

        assertEquals(expected.getIndices().size(), actual.getIndices().size());
        IndexModel actIndex = actual.getIndices().get(0);
        assertNotSame(expIndex, actIndex);
        assertEquals(expIndex.getIndexName(), actIndex.getIndexName());
        assertEquals(expIndex.getIndexType(), actIndex.getIndexType());
        assertNotSame(expIndex.getColumns(), actIndex.getColumns());
        assertEquals(expIndex.getIndexType().toString(), actIndex.getIndexType().toString());
    }
}
