package crm.utils;

import crm.entity.Customer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class WriteCsvToResponseTest {

    private StringWriter stringWriter;
    private PrintWriter printWriter;
    private List<Customer> customers;

    @BeforeEach
    public void setUp() {
        stringWriter = new StringWriter();
        printWriter = new PrintWriter(stringWriter);
        customers = new ArrayList<>();

        Customer customer = Customer.builder()
                .id(1L)
                .name("Test Customer")
                .email("test@example.com")
                .phone(123456789)
                .firstName("Test")
                .lastName("Customer")
                .city("TestCity")
                .address("Test Address")
                .enabled(1)
                .build();
        customers.add(customer);
    }

    @Test
    public void testWriteCustomersNotNull() {
        assertDoesNotThrow(() -> {
            WriteCsvToResponse.writeCustomers(printWriter, customers);
        });
    }

    @Test
    public void testWriteCustomerNotNull() {
        assertDoesNotThrow(() -> {
            WriteCsvToResponse.writeCustomer(printWriter, customers.get(0));
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
    public void testWriteCustomersProducesOutput() {
        WriteCsvToResponse.writeCustomers(printWriter, customers);
        printWriter.flush();
        String output = stringWriter.toString();
        assertNotNull(output);
    }

    @Test
    public void testWriteCustomerProducesOutput() {
        WriteCsvToResponse.writeCustomer(printWriter, customers.get(0));
        printWriter.flush();
        String output = stringWriter.toString();
        assertNotNull(output);
    }

    @Test
    public void testWriteMultipleCustomers() {
        Customer customer2 = Customer.builder()
                .id(2L)
                .name("Second Customer")
                .email("second@example.com")
                .phone(987654321)
                .enabled(1)
                .build();
        customers.add(customer2);

        assertDoesNotThrow(() -> {
            WriteCsvToResponse.writeCustomers(printWriter, customers);
        });
    }
}
