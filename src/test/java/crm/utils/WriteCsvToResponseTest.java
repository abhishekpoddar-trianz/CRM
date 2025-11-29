package crm.utils;

import com.opencsv.exceptions.CsvException;
import crm.entity.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WriteCsvToResponseTest {

    private StringWriter stringWriter;
    private PrintWriter printWriter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        stringWriter = new StringWriter();
        printWriter = new PrintWriter(stringWriter);
    }

    @Test
    void writeCustomers_withValidCustomerList_shouldWriteCSV() {
        // Arrange
        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setName("Test Company");
        customer1.setEmail("test@example.com");
        customer1.setPhone(123456789);
        customer1.setFirstName("John");
        customer1.setLastName("Doe");
        customer1.setCity("Test City");
        customer1.setAddress("123 Test St");
        customer1.setEnabled(1);

        Customer customer2 = new Customer();
        customer2.setId(2L);
        customer2.setName("Test Company 2");
        customer2.setEmail("test2@example.com");
        customer2.setPhone(987654321);
        customer2.setFirstName("Jane");
        customer2.setLastName("Smith");
        customer2.setCity("Test City 2");
        customer2.setAddress("456 Test Ave");
        customer2.setEnabled(0);

        List<Customer> customers = Arrays.asList(customer1, customer2);

        // Act
        WriteCsvToResponse.writeCustomers(printWriter, customers);
        printWriter.flush();

        // Assert
        String output = stringWriter.toString();
        assertNotNull(output, "Output should not be null");
        assertFalse(output.isEmpty(), "Output should not be empty");
    }

    @Test
    void writeCustomers_withEmptyList_shouldHandleGracefully() {
        // Arrange
        List<Customer> customers = Arrays.asList();

        // Act & Assert
        assertDoesNotThrow(() -> {
            WriteCsvToResponse.writeCustomers(printWriter, customers);
            printWriter.flush();
        });

        String output = stringWriter.toString();
        assertNotNull(output, "Output should not be null");
    }

    @Test
    void writeCustomers_withNullPrintWriter_shouldHandleException() {
        // Arrange
        Customer customer = new Customer();
        customer.setId(1L);
        List<Customer> customers = Arrays.asList(customer);

        // Act & Assert
        assertDoesNotThrow(() -> {
            WriteCsvToResponse.writeCustomers(null, customers);
        });
    }

    @Test
    void writeCustomer_withValidCustomer_shouldWriteCSV() {
        // Arrange
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Test Company");
        customer.setEmail("test@example.com");
        customer.setPhone(123456789);
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setCity("Test City");
        customer.setAddress("123 Test St");
        customer.setEnabled(1);

        // Act
        WriteCsvToResponse.writeCustomer(printWriter, customer);
        printWriter.flush();

        // Assert
        String output = stringWriter.toString();
        assertNotNull(output, "Output should not be null");
        assertFalse(output.isEmpty(), "Output should not be empty");
    }

    @Test
    void writeCustomer_withNullCustomer_shouldHandleException() {
        // Act & Assert
        assertDoesNotThrow(() -> {
            WriteCsvToResponse.writeCustomer(printWriter, null);
        });
    }

    @Test
    void writeCustomer_withNullPrintWriter_shouldHandleException() {
        // Arrange
        Customer customer = new Customer();
        customer.setId(1L);

        // Act & Assert
        assertDoesNotThrow(() -> {
            WriteCsvToResponse.writeCustomer(null, customer);
        });
    }

    @Test
    void writeCustomers_withPartiallyNullCustomerData_shouldHandleGracefully() {
        // Arrange
        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setName(null);
        customer1.setEmail("test@example.com");

        Customer customer2 = new Customer();
        customer2.setId(2L);
        customer2.setName("Test Company");
        customer2.setEmail(null);

        List<Customer> customers = Arrays.asList(customer1, customer2);

        // Act & Assert
        assertDoesNotThrow(() -> {
            WriteCsvToResponse.writeCustomers(printWriter, customers);
            printWriter.flush();
        });

        String output = stringWriter.toString();
        assertNotNull(output, "Output should not be null");
    }

    @Test
    void writeCustomer_withPartiallyNullCustomerData_shouldHandleGracefully() {
        // Arrange
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName(null);
        customer.setEmail("test@example.com");
        customer.setPhone(0);

        // Act & Assert
        assertDoesNotThrow(() -> {
            WriteCsvToResponse.writeCustomer(printWriter, customer);
            printWriter.flush();
        });

        String output = stringWriter.toString();
        assertNotNull(output, "Output should not be null");
    }
}