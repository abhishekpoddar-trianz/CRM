package crm.utils;

import crm.entity.Customer;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WriteCsvToResponseTest {

    @Test
    void testWriteCustomers() {
        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setName("Customer1");
        customer1.setEmail("customer1@example.com");
        customer1.setPhone(123456789);

        Customer customer2 = new Customer();
        customer2.setId(2L);
        customer2.setName("Customer2");
        customer2.setEmail("customer2@example.com");
        customer2.setPhone(987654321);

        List<Customer> customers = Arrays.asList(customer1, customer2);

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        assertDoesNotThrow(() -> {
            WriteCsvToResponse.writeCustomers(printWriter, customers);
        });
    }

    @Test
    void testWriteCustomer() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Test Customer");
        customer.setEmail("test@example.com");
        customer.setPhone(123456789);

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        assertDoesNotThrow(() -> {
            WriteCsvToResponse.writeCustomer(printWriter, customer);
        });
    }

    @Test
    void testWriteCustomersWithEmptyList() {
        List<Customer> emptyList = Arrays.asList();

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

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

    @Test
    void testWriteCustomersWithMultipleCustomers() {
        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setName("Customer1");

        Customer customer2 = new Customer();
        customer2.setId(2L);
        customer2.setName("Customer2");

        Customer customer3 = new Customer();
        customer3.setId(3L);
        customer3.setName("Customer3");

        List<Customer> customers = Arrays.asList(customer1, customer2, customer3);

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        WriteCsvToResponse.writeCustomers(printWriter, customers);

        assertNotNull(stringWriter.toString());
    }

    @Test
    void testWriteCustomerWithCompleteData() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Complete Customer");
        customer.setEmail("complete@example.com");
        customer.setPhone(123456789);
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setCity("New York");
        customer.setAddress("123 Main St");
        customer.setEnabled(1);

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        WriteCsvToResponse.writeCustomer(printWriter, customer);

        assertNotNull(stringWriter.toString());
    }
}
