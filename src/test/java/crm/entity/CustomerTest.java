package crm.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {

    private Customer customer;

    @BeforeEach
    void setUp() {
        customer = new Customer();
    }

    @Test
    void testDefaultConstructor() {
        // Act
        Customer newCustomer = new Customer();

        // Assert
        assertNotNull(newCustomer);
        assertNull(newCustomer.getId());
        assertNull(newCustomer.getName());
        assertNull(newCustomer.getEmail());
        assertEquals(0, newCustomer.getPhone());
        assertNull(newCustomer.getCategories());
        assertNull(newCustomer.getFirstName());
        assertNull(newCustomer.getLastName());
        assertNull(newCustomer.getCity());
        assertNull(newCustomer.getAddress());
        assertEquals(0, newCustomer.getEnabled());
    }

    @Test
    void testAllArgsConstructor() {
        // Arrange
        Long id = 1L;
        String name = "Test Customer";
        String email = "test@example.com";
        int phone = 123456789;
        Set<Category> categories = new HashSet<>();
        String firstName = "John";
        String lastName = "Doe";
        String city = "Test City";
        String address = "123 Test St";
        int enabled = 1;

        // Act
        Customer customer = new Customer(id, name, email, phone, categories, firstName, lastName, city, address, enabled);

        // Assert
        assertEquals(id, customer.getId());
        assertEquals(name, customer.getName());
        assertEquals(email, customer.getEmail());
        assertEquals(phone, customer.getPhone());
        assertEquals(categories, customer.getCategories());
        assertEquals(firstName, customer.getFirstName());
        assertEquals(lastName, customer.getLastName());
        assertEquals(city, customer.getCity());
        assertEquals(address, customer.getAddress());
        assertEquals(enabled, customer.getEnabled());
    }

    @Test
    void testBuilder() {
        // Act
        Customer customer = Customer.builder()
                .id(1L)
                .name("Test Customer")
                .email("test@example.com")
                .phone(123456789)
                .firstName("John")
                .lastName("Doe")
                .city("Test City")
                .address("123 Test St")
                .enabled(1)
                .build();

        // Assert
        assertEquals(1L, customer.getId());
        assertEquals("Test Customer", customer.getName());
        assertEquals("test@example.com", customer.getEmail());
        assertEquals(123456789, customer.getPhone());
        assertEquals("John", customer.getFirstName());
        assertEquals("Doe", customer.getLastName());
        assertEquals("Test City", customer.getCity());
        assertEquals("123 Test St", customer.getAddress());
        assertEquals(1, customer.getEnabled());
    }

    @Test
    void testSetAndGetId() {
        // Arrange
        Long expectedId = 123L;

        // Act
        customer.setId(expectedId);

        // Assert
        assertEquals(expectedId, customer.getId());
    }

    @Test
    void testSetAndGetName() {
        // Arrange
        String expectedName = "Test Customer";

        // Act
        customer.setName(expectedName);

        // Assert
        assertEquals(expectedName, customer.getName());
    }

    @Test
    void testSetAndGetEmail() {
        // Arrange
        String expectedEmail = "test@example.com";

        // Act
        customer.setEmail(expectedEmail);

        // Assert
        assertEquals(expectedEmail, customer.getEmail());
    }

    @Test
    void testSetAndGetPhone() {
        // Arrange
        int expectedPhone = 123456789;

        // Act
        customer.setPhone(expectedPhone);

        // Assert
        assertEquals(expectedPhone, customer.getPhone());
    }

    @Test
    void testSetAndGetCategories() {
        // Arrange
        Set<Category> expectedCategories = new HashSet<>();
        Category category = new Category();
        category.setName("Test Category");
        expectedCategories.add(category);

        // Act
        customer.setCategories(expectedCategories);

        // Assert
        assertEquals(expectedCategories, customer.getCategories());
        assertEquals(1, customer.getCategories().size());
    }

    @Test
    void testSetAndGetFirstName() {
        // Arrange
        String expectedFirstName = "John";

        // Act
        customer.setFirstName(expectedFirstName);

        // Assert
        assertEquals(expectedFirstName, customer.getFirstName());
    }

    @Test
    void testSetAndGetLastName() {
        // Arrange
        String expectedLastName = "Doe";

        // Act
        customer.setLastName(expectedLastName);

        // Assert
        assertEquals(expectedLastName, customer.getLastName());
    }

    @Test
    void testSetAndGetCity() {
        // Arrange
        String expectedCity = "Test City";

        // Act
        customer.setCity(expectedCity);

        // Assert
        assertEquals(expectedCity, customer.getCity());
    }

    @Test
    void testSetAndGetAddress() {
        // Arrange
        String expectedAddress = "123 Test St";

        // Act
        customer.setAddress(expectedAddress);

        // Assert
        assertEquals(expectedAddress, customer.getAddress());
    }

    @Test
    void testSetAndGetEnabled() {
        // Arrange
        int expectedEnabled = 1;

        // Act
        customer.setEnabled(expectedEnabled);

        // Assert
        assertEquals(expectedEnabled, customer.getEnabled());
    }

    @Test
    void testSetNullValues() {
        // Act
        customer.setId(null);
        customer.setName(null);
        customer.setEmail(null);
        customer.setCategories(null);
        customer.setFirstName(null);
        customer.setLastName(null);
        customer.setCity(null);
        customer.setAddress(null);

        // Assert
        assertNull(customer.getId());
        assertNull(customer.getName());
        assertNull(customer.getEmail());
        assertNull(customer.getCategories());
        assertNull(customer.getFirstName());
        assertNull(customer.getLastName());
        assertNull(customer.getCity());
        assertNull(customer.getAddress());
    }

    @Test
    void testSetEmptyStringValues() {
        // Act
        customer.setName("");
        customer.setEmail("");
        customer.setFirstName("");
        customer.setLastName("");
        customer.setCity("");
        customer.setAddress("");

        // Assert
        assertEquals("", customer.getName());
        assertEquals("", customer.getEmail());
        assertEquals("", customer.getFirstName());
        assertEquals("", customer.getLastName());
        assertEquals("", customer.getCity());
        assertEquals("", customer.getAddress());
    }

    @Test
    void testSetNegativePhone() {
        // Act
        customer.setPhone(-1);

        // Assert
        assertEquals(-1, customer.getPhone());
    }

    @Test
    void testSetNegativeEnabled() {
        // Act
        customer.setEnabled(-1);

        // Assert
        assertEquals(-1, customer.getEnabled());
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        Customer customer1 = Customer.builder()
                .id(1L)
                .name("Test")
                .email("test@example.com")
                .build();

        Customer customer2 = Customer.builder()
                .id(1L)
                .name("Test")
                .email("test@example.com")
                .build();

        // Act & Assert
        assertEquals(customer1, customer2);
        assertEquals(customer1.hashCode(), customer2.hashCode());
    }

    @Test
    void testNotEquals() {
        // Arrange
        Customer customer1 = Customer.builder()
                .id(1L)
                .name("Test1")
                .build();

        Customer customer2 = Customer.builder()
                .id(2L)
                .name("Test2")
                .build();

        // Act & Assert
        assertNotEquals(customer1, customer2);
    }

    @Test
    void testToString() {
        // Arrange
        customer.setId(1L);
        customer.setName("Test Customer");
        customer.setEmail("test@example.com");

        // Act
        String result = customer.toString();

        // Assert
        assertNotNull(result);
        assertTrue(result.contains("1"));
        assertTrue(result.contains("Test Customer"));
        assertTrue(result.contains("test@example.com"));
    }

    @Test
    void testAnnotationsPresent() {
        // Act & Assert
        assertTrue(Customer.class.isAnnotationPresent(jakarta.persistence.Entity.class));
        assertTrue(Customer.class.isAnnotationPresent(lombok.Data.class));
        assertTrue(Customer.class.isAnnotationPresent(lombok.Builder.class));
        assertTrue(Customer.class.isAnnotationPresent(lombok.NoArgsConstructor.class));
        assertTrue(Customer.class.isAnnotationPresent(lombok.AllArgsConstructor.class));
    }

    @Test
    void testEmptyCategories() {
        // Arrange
        Set<Category> emptyCategories = new HashSet<>();

        // Act
        customer.setCategories(emptyCategories);

        // Assert
        assertNotNull(customer.getCategories());
        assertTrue(customer.getCategories().isEmpty());
    }
}