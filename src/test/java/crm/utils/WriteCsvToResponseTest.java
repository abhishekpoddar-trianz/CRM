package crm.utils;

import crm.entity.Category;
import crm.entity.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class WriteCsvToResponseTest {

    private Customer customer1;
    private Customer customer2;
    private List<Customer> customers;

    @BeforeEach
    public void setUp() {
        customer1 = Customer.builder()
                .id(1L)
                .name("Customer 1")
                .email("customer1@test.com")
                .phone(1234567890)
                .firstName("John")
                .lastName("Doe")
                .city("City1")
                .address("Address1")
                .enabled(1)
                .categories(new HashSet<>())
                .build();

        customer2 = Customer.builder()
                .id(2L)
                .name("Customer 2")
                .email("customer2@test.com")
                .phone(987654321)
                .firstName("Jane")
                .lastName("Smith")
                .city("City2")
                .address("Address2")
                .enabled(1)
                .categories(new HashSet<>())
                .build();

        customers = Arrays.asList(customer1, customer2);
    }

    @Test
    public void testWriteCustomers() {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        assertDoesNotThrow(() -> {
            WriteCsvToResponse.writeCustomers(printWriter, customers);
        });

        String result = stringWriter.toString();
        assertNotNull(result);
    }

    @Test
    public void testWriteCustomer() {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        assertDoesNotThrow(() -> {
            WriteCsvToResponse.writeCustomer(printWriter, customer1);
        });

        String result = stringWriter.toString();
        assertNotNull(result);
    }

    @Test
    public void testWriteCustomersEmptyList() {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        assertDoesNotThrow(() -> {
            WriteCsvToResponse.writeCustomers(printWriter, Arrays.asList());
        });
    }

    @Test
    public void testWriteCustomerWithNullValues() {
        Customer nullCustomer = Customer.builder()
                .id(null)
                .name(null)
                .email(null)
                .build();

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        assertDoesNotThrow(() -> {
            WriteCsvToResponse.writeCustomer(printWriter, nullCustomer);
        });
    }
}
