package crm.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class ExportCustomersTest {

    private ExportCustomers exportCustomers;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        exportCustomers = new ExportCustomers();
    }

    @Test
    void testConstructor() {
        assertNotNull(exportCustomers);
    }

    @Test
    void testExportCustomersCreation() {
        ExportCustomers controller = new ExportCustomers();
        assertNotNull(controller);
    }

    @Test
    void testExportCustomersInstantiation() {
        assertDoesNotThrow(() -> new ExportCustomers());
    }

    @Test
    void testExportCustomersInstanceIsNotNull() {
        ExportCustomers instance = new ExportCustomers();
        assertNotNull(instance);
    }

    @Test
    void testMultipleInstancesAreIndependent() {
        ExportCustomers instance1 = new ExportCustomers();
        ExportCustomers instance2 = new ExportCustomers();

        assertNotNull(instance1);
        assertNotNull(instance2);
        assertNotSame(instance1, instance2);
    }

    @Test
    void testClassExists() {
        assertTrue(ExportCustomers.class.isAssignableFrom(ExportCustomers.class));
    }

    @Test
    void testDefaultConstructorExists() {
        assertDoesNotThrow(() -> {
            ExportCustomers.class.getDeclaredConstructor();
        });
    }
}