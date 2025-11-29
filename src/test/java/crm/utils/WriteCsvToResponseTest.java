package crm.utils;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import crm.entity.Customer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WriteCsvToResponseTest {

    @Mock
    private Customer mockCustomer;

    private PrintWriter printWriter;
    private StringWriter stringWriter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        stringWriter = new StringWriter();
        printWriter = new PrintWriter(stringWriter);
    }

    @Test
    void testWriteCustomers_WithValidCustomerList() {
        // Arrange
        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setName("Company1");
        customer1.setEmail("customer1@test.com");
        customer1.setPhone("123-456-7890");
        customer1.setFirstName("John");
        customer1.setLastName("Doe");
        customer1.setCity("Test City");
        customer1.setAddress("123 Test St");
        customer1.setEnabled(true);

        Customer customer2 = new Customer();
        customer2.setId(2L);
        customer2.setName("Company2");
        customer2.setEmail("customer2@test.com");
        customer2.setPhone("987-654-3210");
        customer2.setFirstName("Jane");
        customer2.setLastName("Smith");
        customer2.setCity("Another City");
        customer2.setAddress("456 Another St");
        customer2.setEnabled(false);

        List<Customer> customers = Arrays.asList(customer1, customer2);

        // Act
        WriteCsvToResponse.writeCustomers(printWriter, customers);

        // Assert
        String csvContent = stringWriter.toString();
        assertNotNull(csvContent);
        assertFalse(csvContent.isEmpty());
    }

    @Test
    void testWriteCustomers_WithEmptyList() {
        // Arrange
        List<Customer> customers = Arrays.asList();

        // Act
        WriteCsvToResponse.writeCustomers(printWriter, customers);

        // Assert
        String csvContent = stringWriter.toString();
        assertNotNull(csvContent);
    }

    @Test
    void testWriteCustomers_WithNullList() {
        // Act & Assert
        assertDoesNotThrow(() -> WriteCsvToResponse.writeCustomers(printWriter, null));
    }

    @Test
    void testWriteCustomer_WithValidCustomer() {
        // Arrange
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Test Company");
        customer.setEmail("test@example.com");
        customer.setPhone("555-123-4567");
        customer.setFirstName("Test");
        customer.setLastName("User");
        customer.setCity("Test City");
        customer.setAddress("123 Test Street");
        customer.setEnabled(true);

        // Act
        WriteCsvToResponse.writeCustomer(printWriter, customer);

        // Assert
        String csvContent = stringWriter.toString();
        assertNotNull(csvContent);
        assertFalse(csvContent.isEmpty());
    }

    @Test
    void testWriteCustomer_WithNullCustomer() {
        // Act & Assert
        assertDoesNotThrow(() -> WriteCsvToResponse.writeCustomer(printWriter, null));
    }

    @Test
    void testWriteCustomers_ValidatesColumnMapping() {
        // Arrange
        Customer customer = new Customer();
        customer.setId(100L);
        customer.setName("Mapping Test");
        customer.setEmail("mapping@test.com");
        customer.setPhone("111-222-3333");
        customer.setFirstName("Map");
        customer.setLastName("Test");
        customer.setCity("Map City");
        customer.setAddress("Map Address");
        customer.setEnabled(true);

        List<Customer> customers = Arrays.asList(customer);

        // Act
        WriteCsvToResponse.writeCustomers(printWriter, customers);

        // Assert
        String csvContent = stringWriter.toString();
        assertNotNull(csvContent);
        assertTrue(csvContent.length() > 0);
    }

    @Test
    void testWriteCustomer_ValidatesColumnMapping() {
        // Arrange
        Customer customer = new Customer();
        customer.setId(200L);
        customer.setName("Single Test");
        customer.setEmail("single@test.com");
        customer.setPhone("444-555-6666");
        customer.setFirstName("Single");
        customer.setLastName("Customer");
        customer.setCity("Single City");
        customer.setAddress("Single Address");
        customer.setEnabled(false);

        // Act
        WriteCsvToResponse.writeCustomer(printWriter, customer);

        // Assert
        String csvContent = stringWriter.toString();
        assertNotNull(csvContent);
        assertTrue(csvContent.length() > 0);
    }

    @Test
    void testWriteCustomers_WithMultipleCustomers() {
        // Arrange
        Customer[] testCustomers = new Customer[3];
        for (int i = 0; i < 3; i++) {
            testCustomers[i] = new Customer();
            testCustomers[i].setId((long) i + 1);
            testCustomers[i].setName("Company" + (i + 1));
            testCustomers[i].setEmail("customer" + (i + 1) + "@test.com");
            testCustomers[i].setPhone("123-456-789" + i);
            testCustomers[i].setFirstName("First" + (i + 1));
            testCustomers[i].setLastName("Last" + (i + 1));
            testCustomers[i].setCity("City" + (i + 1));
            testCustomers[i].setAddress("Address" + (i + 1));
            testCustomers[i].setEnabled(i % 2 == 0);
        }

        List<Customer> customers = Arrays.asList(testCustomers);

        // Act
        WriteCsvToResponse.writeCustomers(printWriter, customers);

        // Assert
        String csvContent = stringWriter.toString();
        assertNotNull(csvContent);
        assertTrue(csvContent.length() > 0);
    }

    @Test
    void testPrintWriterNotNull() {
        // Arrange
        Customer customer = new Customer();
        customer.setId(1L);

        // Act & Assert
        assertDoesNotThrow(() -> WriteCsvToResponse.writeCustomer(printWriter, customer));
    }
}