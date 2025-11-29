package crm.utils;

import crm.entity.Customer;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WriteCsvToResponseTest {

    @Test
    void testWriteCustomersMethod() throws Exception {
        var method = WriteCsvToResponse.class.getDeclaredMethod("writeCustomers",
                PrintWriter.class, List.class);
        assertNotNull(method);
        assertTrue(java.lang.reflect.Modifier.isStatic(method.getModifiers()));
        assertTrue(java.lang.reflect.Modifier.isPublic(method.getModifiers()));
    }

    @Test
    void testWriteCustomerMethod() throws Exception {
        var method = WriteCsvToResponse.class.getDeclaredMethod("writeCustomer",
                PrintWriter.class, Customer.class);
        assertNotNull(method);
        assertTrue(java.lang.reflect.Modifier.isStatic(method.getModifiers()));
        assertTrue(java.lang.reflect.Modifier.isPublic(method.getModifiers()));
    }

    @Test
    void testWriteCustomersWithEmptyList() {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        List<Customer> emptyList = new ArrayList<>();

        assertDoesNotThrow(() -> {
            WriteCsvToResponse.writeCustomers(printWriter, emptyList);
        });
    }

    @Test
    void testWriteCustomerWithNullCustomer() {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        assertDoesNotThrow(() -> {
            WriteCsvToResponse.writeCustomer(printWriter, null);
        });
    }
}