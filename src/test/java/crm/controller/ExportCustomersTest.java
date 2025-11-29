package crm.controller;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ExportCustomersTest {

    @Test
    public void testExportCustomersClass() {
        ExportCustomers exportCustomers = new ExportCustomers();
        assertNotNull(exportCustomers);
    }

    @Test
    public void testExportCustomersNoArgsConstructor() {
        assertDoesNotThrow(() -> {
            new ExportCustomers();
        });
    }
}
