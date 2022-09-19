package io.github.erde.dialect.loader;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

class JdbcMetaData4H2Test extends JdbcMetaDataTest {

    @BeforeAll
    static void beforeAll() throws Exception {
        init("jdbc/H2.properties");
    }

    @AfterAll
    static void afterAll() {
    }

    @BeforeEach
    void beforeEach() {
    }

    @AfterEach
    void afterEach() {
    }
}
