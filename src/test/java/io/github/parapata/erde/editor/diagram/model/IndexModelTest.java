package io.github.parapata.erde.editor.diagram.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import io.github.parapata.erde.dialect.type.IndexType;

class IndexModelTest {

    @Test
    @Disabled
    void test() throws CloneNotSupportedException {
        IndexModel expected = new IndexModel();
        expected.getColumns().add("column_1");
        expected.setIndexName("index_name");
        expected.setIndexType(IndexType.UNIQUE);

        IndexModel actual = expected.clone();
        assertNotSame(expected.getColumns(), actual.getColumns());
        assertEquals(expected.getIndexName(), actual.getIndexName());
        assertTrue(expected.getIndexType().equals(actual.getIndexType()));
        assertIterableEquals(expected.getColumns(), actual.getColumns());

        expected.setIndexType(IndexType.INDEX);
        assertNotSame(expected.getIndexType(), actual.getIndexType());
    }
}
