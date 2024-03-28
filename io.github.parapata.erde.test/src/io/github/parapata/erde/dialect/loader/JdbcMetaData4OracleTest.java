package io.github.parapata.erde.dialect.loader;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

class JdbcMetaData4OracleTest extends JdbcMetaDataTest {

    @BeforeAll
    static void beforeAll() throws Exception {
        init("jdbc/Oracle.properties");
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
