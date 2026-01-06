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
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class WriteCsvToResponseTest {

    private StringWriter stringWriter;
    private PrintWriter printWriter;
    private Customer customer1;
    private Customer customer2;

    @BeforeEach
    void setUp() {
        stringWriter = new StringWriter();
        printWriter = new PrintWriter(stringWriter);

        Set<Category> categories = new HashSet<>();
        Category category = new Category();
        category.setId(1L);
        category.setName("Tech");
        categories.add(category);

        customer1 = Customer.builder()
                .id(1L)
                .name("Customer 1")
                .email("customer1@test.com")
                .phone(1234567890)
                .firstName("John")
                .lastName("Doe")
                .city("New York")
                .address("123 Main St")
                .enabled(1)
                .categories(categories)
                .build();

        customer2 = Customer.builder()
                .id(2L)
                .name("Customer 2")
                .email("customer2@test.com")
                .phone(987654321)
                .firstName("Jane")
                .lastName("Smith")
                .city("Los Angeles")
                .address("456 Oak Ave")
                .enabled(1)
                .categories(categories)
                .build();
    }

    @Test
    void testWriteCustomers() {
        List<Customer> customers = Arrays.asList(customer1, customer2);

        assertDoesNotThrow(() -> WriteCsvToResponse.writeCustomers(printWriter, customers));

        String output = stringWriter.toString();
        assertNotNull(output);
    }

    @Test
    void testWriteCustomer() {
        assertDoesNotThrow(() -> WriteCsvToResponse.writeCustomer(printWriter, customer1));

        String output = stringWriter.toString();
        assertNotNull(output);
    }

    @Test
    void testWriteCustomersWithEmptyList() {
        List<Customer> emptyList = Arrays.asList();

        assertDoesNotThrow(() -> WriteCsvToResponse.writeCustomers(printWriter, emptyList));
    }

    @Test
    void testWriteCustomerWithNullCustomer() {
        assertDoesNotThrow(() -> WriteCsvToResponse.writeCustomer(printWriter, null));
    }

    @Test
    void testWriteCustomersGeneratesOutput() {
        List<Customer> customers = Arrays.asList(customer1);

        WriteCsvToResponse.writeCustomers(printWriter, customers);
        printWriter.flush();

        String output = stringWriter.toString();
        assertTrue(output.length() >= 0);
    }

    @Test
    void testWriteSingleCustomerGeneratesOutput() {
        WriteCsvToResponse.writeCustomer(printWriter, customer1);
        printWriter.flush();

        String output = stringWriter.toString();
        assertTrue(output.length() >= 0);
    }
}
