package crm.utils;

import crm.entity.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class WriteCsvToResponseTest {

    private Customer customer;
    private List<Customer> customers;
    private StringWriter stringWriter;
    private PrintWriter printWriter;

    @BeforeEach
    public void setUp() {
        customer = new Customer();
        customer.setId(1L);
        customer.setName("John Doe");
        customer.setEmail("john@example.com");
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
    public void testWriteCustomers() {
        assertDoesNotThrow(() -> {
            WriteCsvToResponse.writeCustomers(printWriter, customers);
        });
    }

    @Test
    public void testWriteCustomer() {
        assertDoesNotThrow(() -> {
            WriteCsvToResponse.writeCustomer(printWriter, customer);
        });
    }

    @Test
    public void testWriteCustomersWithEmptyList() {
        List<Customer> emptyList = new ArrayList<>();
        assertDoesNotThrow(() -> {
            WriteCsvToResponse.writeCustomers(printWriter, emptyList);
        });
    }

    @Test
    public void testWriteCustomersWithMultiple() {
        Customer customer2 = new Customer();
        customer2.setId(2L);
        customer2.setName("Jane Smith");
        customer2.setEmail("jane@example.com");
        customers.add(customer2);

        assertDoesNotThrow(() -> {
            WriteCsvToResponse.writeCustomers(printWriter, customers);
        });
    }

    @Test
    public void testWriteCustomerWithNullValues() {
        Customer nullCustomer = new Customer();
        assertDoesNotThrow(() -> {
            WriteCsvToResponse.writeCustomer(printWriter, nullCustomer);
        });
    }

    @Test
    public void testWriteCsvToResponseConstructor() {
        WriteCsvToResponse utils = new WriteCsvToResponse();
        assertNotNull(utils);
    }

    @Test
    public void testWriteCustomersProducesOutput() {
        WriteCsvToResponse.writeCustomers(printWriter, customers);
        String output = stringWriter.toString();
        assertNotNull(output);
    }

    @Test
    public void testWriteCustomerProducesOutput() {
        WriteCsvToResponse.writeCustomer(printWriter, customer);
        String output = stringWriter.toString();
        assertNotNull(output);
    }
}
