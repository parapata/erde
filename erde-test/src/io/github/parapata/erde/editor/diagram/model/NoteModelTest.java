package io.github.parapata.erde.editor.diagram.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class NoteModelTest {

    @Test
    void test() {
        NoteModel expected = new NoteModel();
        expected.setId("11111");
        expected.setContent("message");
        NoteModel actual = expected.clone();

        assertNotSame(expected, actual);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getContent(), actual.getContent());
    }
}
