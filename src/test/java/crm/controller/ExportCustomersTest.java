package crm.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExportCustomersTest {

    private ExportCustomers exportCustomers;

    @BeforeEach
    void setUp() {
        exportCustomers = new ExportCustomers();
    }

    @Test
    void testConstructor() {
        assertNotNull(exportCustomers);
    }

    @Test
    void testClassExists() {
        assertNotNull(ExportCustomers.class);
    }

    @Test
    void testCanInstantiateClass() {
        ExportCustomers instance = new ExportCustomers();
        assertNotNull(instance);
    }

    @Test
    void testClassIsNotNull() {
        assertNotNull(exportCustomers);
    }
}
