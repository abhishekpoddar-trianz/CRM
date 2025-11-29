package crm.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {

    private Customer customer;
    private Validator validator;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
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
        assertFalse(newCustomer.isEnabled());
    }

    @Test
    void testAllArgsConstructor() {
        // Arrange
        Long id = 1L;
        String name = "Test Company";
        String email = "test@company.com";
        int phone = 1234567890;
        Set<Category> categories = new HashSet<>();
        String firstName = "John";
        String lastName = "Doe";
        String city = "Test City";
        String address = "123 Test Street";
        boolean enabled = true;

        // Act
        Customer newCustomer = new Customer(id, name, email, phone, categories, firstName, lastName, city, address, enabled);

        // Assert
        assertEquals(id, newCustomer.getId());
        assertEquals(name, newCustomer.getName());
        assertEquals(email, newCustomer.getEmail());
        assertEquals(phone, newCustomer.getPhone());
        assertEquals(categories, newCustomer.getCategories());
        assertEquals(firstName, newCustomer.getFirstName());
        assertEquals(lastName, newCustomer.getLastName());
        assertEquals(city, newCustomer.getCity());
        assertEquals(address, newCustomer.getAddress());
        assertEquals(enabled, newCustomer.isEnabled());
    }

    @Test
    void testBuilderPattern() {
        // Act
        Customer builtCustomer = Customer.builder()
                .id(1L)
                .name("Builder Company")
                .email("builder@test.com")
                .phone(987654321)
                .firstName("Jane")
                .lastName("Builder")
                .city("Builder City")
                .address("456 Builder Ave")
                .enabled(true)
                .build();

        // Assert
        assertEquals(1L, builtCustomer.getId());
        assertEquals("Builder Company", builtCustomer.getName());
        assertEquals("builder@test.com", builtCustomer.getEmail());
        assertEquals(987654321, builtCustomer.getPhone());
        assertEquals("Jane", builtCustomer.getFirstName());
        assertEquals("Builder", builtCustomer.getLastName());
        assertEquals("Builder City", builtCustomer.getCity());
        assertEquals("456 Builder Ave", builtCustomer.getAddress());
        assertTrue(builtCustomer.isEnabled());
    }

    @Test
    void testSetAndGetId() {
        // Arrange
        Long expectedId = 100L;

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
        String expectedEmail = "customer@test.com";

        // Act
        customer.setEmail(expectedEmail);

        // Assert
        assertEquals(expectedEmail, customer.getEmail());
    }

    @Test
    void testSetAndGetPhone() {
        // Arrange
        int expectedPhone = 1234567890;

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
        category.setId(1L);
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
        String expectedCity = "New York";

        // Act
        customer.setCity(expectedCity);

        // Assert
        assertEquals(expectedCity, customer.getCity());
    }

    @Test
    void testSetAndGetAddress() {
        // Arrange
        String expectedAddress = "123 Main Street";

        // Act
        customer.setAddress(expectedAddress);

        // Assert
        assertEquals(expectedAddress, customer.getAddress());
    }

    @Test
    void testSetAndGetEnabled() {
        // Act
        customer.setEnabled(true);

        // Assert
        assertTrue(customer.isEnabled());

        // Act
        customer.setEnabled(false);

        // Assert
        assertFalse(customer.isEnabled());
    }

    @Test
    void testEmailValidation() {
        // Arrange
        customer.setName("Valid Name");
        customer.setEmail("invalid-email");

        // Act
        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("valid e-mail")));
    }

    @Test
    void testValidEmailValidation() {
        // Arrange
        customer.setName("Valid Name");
        customer.setEmail("valid@email.com");

        // Act
        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);

        // Assert
        assertTrue(violations.stream().noneMatch(v -> v.getMessage().contains("valid e-mail")));
    }

    @Test
    void testEmptyEmailValidation() {
        // Arrange
        customer.setName("Valid Name");
        customer.setEmail("");

        // Act
        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("provide an e-mail")));
    }

    @Test
    void testNameSizeValidation() {
        // Arrange
        customer.setName("A");
        customer.setEmail("valid@email.com");

        // Act
        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("name")));
    }

    @Test
    void testValidNameSize() {
        // Arrange
        customer.setName("Valid Name");
        customer.setEmail("valid@email.com");

        // Act
        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);

        // Assert
        assertTrue(violations.stream().noneMatch(v -> v.getPropertyPath().toString().equals("name")));
    }

    @Test
    void testPhoneBoundaryValues() {
        // Test with various phone numbers
        int[] testPhones = {0, 1, 1234567890, Integer.MAX_VALUE};

        for (int phone : testPhones) {
            // Act
            customer.setPhone(phone);

            // Assert
            assertEquals(phone, customer.getPhone());
        }
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        Customer customer1 = Customer.builder()
                .id(1L)
                .name("Test")
                .email("test@test.com")
                .phone(123456789)
                .enabled(true)
                .build();

        Customer customer2 = Customer.builder()
                .id(1L)
                .name("Test")
                .email("test@test.com")
                .phone(123456789)
                .enabled(true)
                .build();

        // Act & Assert
        assertEquals(customer1, customer2);
        assertEquals(customer1.hashCode(), customer2.hashCode());
    }

    @Test
    void testToString() {
        // Arrange
        customer.setId(1L);
        customer.setName("Test Customer");
        customer.setEmail("test@customer.com");

        // Act
        String toString = customer.toString();

        // Assert
        assertNotNull(toString);
        assertTrue(toString.contains("id"));
        assertTrue(toString.contains("name"));
        assertTrue(toString.contains("email"));
    }

    @Test
    void testCategoriesEmptySet() {
        // Arrange
        Set<Category> emptyCategories = new HashSet<>();

        // Act
        customer.setCategories(emptyCategories);

        // Assert
        assertEquals(emptyCategories, customer.getCategories());
        assertTrue(customer.getCategories().isEmpty());
    }

    @Test
    void testAllFieldsWithNullValues() {
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
}