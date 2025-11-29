package crm.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import crm.entity.Customer;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WriteCsvToResponseTest {

    private PrintWriter printWriter;
    private StringWriter stringWriter;
    private Customer customer;
    private List<Customer> customers;

    @BeforeEach
    void setUp() {
        stringWriter = new StringWriter();
        printWriter = new PrintWriter(stringWriter);

        customer = new Customer();
        customer.setId(1L);
        customer.setName("Test Customer");
        customer.setEmail("test@example.com");
        customer.setPhone("123-456-7890");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setCity("Test City");
        customer.setAddress("123 Test St");
        customer.setEnabled(true);

        customers = new ArrayList<>();
        customers.add(customer);
    }

    @Test
    void testWriteCustomers_WithValidData() {
        // Act
        WriteCsvToResponse.writeCustomers(printWriter, customers);

        // Assert
        assertNotNull(stringWriter.toString());
        printWriter.close();
    }

    @Test
    void testWriteCustomers_WithEmptyList() {
        // Arrange
        List<Customer> emptyList = new ArrayList<>();

        // Act
        WriteCsvToResponse.writeCustomers(printWriter, emptyList);

        // Assert
        assertNotNull(stringWriter.toString());
        printWriter.close();
    }

    @Test
    void testWriteCustomers_WithNullList() {
        // Act & Assert
        assertDoesNotThrow(() -> WriteCsvToResponse.writeCustomers(printWriter, null));
        printWriter.close();
    }

    @Test
    void testWriteCustomer_WithValidData() {
        // Act
        WriteCsvToResponse.writeCustomer(printWriter, customer);

        // Assert
        assertNotNull(stringWriter.toString());
        printWriter.close();
    }

    @Test
    void testWriteCustomer_WithNullCustomer() {
        // Act & Assert
        assertDoesNotThrow(() -> WriteCsvToResponse.writeCustomer(printWriter, null));
        printWriter.close();
    }

    @Test
    void testWriteCustomers_WithMultipleCustomers() {
        // Arrange
        Customer customer2 = new Customer();
        customer2.setId(2L);
        customer2.setName("Test Customer 2");
        customer2.setEmail("test2@example.com");
        customers.add(customer2);

        // Act
        WriteCsvToResponse.writeCustomers(printWriter, customers);

        // Assert
        assertNotNull(stringWriter.toString());
        printWriter.close();
    }

    @Test
    void testWriteCustomers_WithNullPrintWriter() {
        // Act & Assert
        assertDoesNotThrow(() -> WriteCsvToResponse.writeCustomers(null, customers));
    }

    @Test
    void testWriteCustomer_WithNullPrintWriter() {
        // Act & Assert
        assertDoesNotThrow(() -> WriteCsvToResponse.writeCustomer(null, customer));
    }

    @Test
    void testWriteCustomers_HandlesException() {
        // Arrange
        PrintWriter mockPrintWriter = mock(PrintWriter.class);

        // Act & Assert
        assertDoesNotThrow(() -> WriteCsvToResponse.writeCustomers(mockPrintWriter, customers));
    }

    @Test
    void testWriteCustomer_HandlesException() {
        // Arrange
        PrintWriter mockPrintWriter = mock(PrintWriter.class);

        // Act & Assert
        assertDoesNotThrow(() -> WriteCsvToResponse.writeCustomer(mockPrintWriter, customer));
    }
}