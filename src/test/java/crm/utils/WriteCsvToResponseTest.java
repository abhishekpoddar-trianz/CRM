package crm.utils;

import crm.entity.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WriteCsvToResponseTest {

    private Customer customer;
    private List<Customer> customers;
    private PrintWriter printWriter;
    private StringWriter stringWriter;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setId(1L);
        customer.setName("TestCustomer");
        customer.setEmail("test@example.com");
        customer.setPhone(123456789);
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setCity("New York");
        customer.setAddress("123 Main St");
        customer.setEnabled(1);

        customers = new ArrayList<>();
        customers.add(customer);

        stringWriter = new StringWriter();
        printWriter = new PrintWriter(stringWriter);
    }

    @Test
    void testWriteCustomers() {
        assertDoesNotThrow(() -> {
            WriteCsvToResponse.writeCustomers(printWriter, customers);
        });
    }

    @Test
    void testWriteCustomersWithMultipleCustomers() {
        Customer customer2 = new Customer();
        customer2.setId(2L);
        customer2.setName("Customer2");
        customer2.setEmail("customer2@example.com");
        customers.add(customer2);

        assertDoesNotThrow(() -> {
            WriteCsvToResponse.writeCustomers(printWriter, customers);
        });
    }

    @Test
    void testWriteCustomer() {
        assertDoesNotThrow(() -> {
            WriteCsvToResponse.writeCustomer(printWriter, customer);
        });
    }

    @Test
    void testWriteCustomerWithNullValues() {
        Customer nullCustomer = new Customer();
        assertDoesNotThrow(() -> {
            WriteCsvToResponse.writeCustomer(printWriter, nullCustomer);
        });
    }

    @Test
    void testWriteCustomersEmptyList() {
        List<Customer> emptyList = new ArrayList<>();
        assertDoesNotThrow(() -> {
            WriteCsvToResponse.writeCustomers(printWriter, emptyList);
        });
    }
}
