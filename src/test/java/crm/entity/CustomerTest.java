package crm.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    void testCustomerBuilder() {
        Set<Category> categories = new HashSet<>();
        Category category = new Category();
        category.setId(1L);
        category.setName("Technology");
        categories.add(category);

        Customer builtCustomer = Customer.builder()
                .id(1L)
                .name("John Doe")
                .email("john@example.com")
                .phone(123456789)
                .categories(categories)
                .firstName("John")
                .lastName("Doe")
                .city("New York")
                .address("123 Main St")
                .enabled(1)
                .build();

        assertNotNull(builtCustomer);
        assertEquals(1L, builtCustomer.getId());
        assertEquals("John Doe", builtCustomer.getName());
        assertEquals("john@example.com", builtCustomer.getEmail());
        assertEquals(123456789, builtCustomer.getPhone());
        assertEquals(categories, builtCustomer.getCategories());
        assertEquals("John", builtCustomer.getFirstName());
        assertEquals("Doe", builtCustomer.getLastName());
        assertEquals("New York", builtCustomer.getCity());
        assertEquals("123 Main St", builtCustomer.getAddress());
        assertEquals(1, builtCustomer.getEnabled());
    }

    @Test
    void testNoArgsConstructor() {
        Customer customer = new Customer();
        assertNotNull(customer);
    }

    @Test
    void testAllArgsConstructor() {
        Set<Category> categories = new HashSet<>();
        Customer customer = new Customer(1L, "Test", "test@example.com", 123456,
                categories, "First", "Last", "City", "Address", 1);

        assertNotNull(customer);
        assertEquals(1L, customer.getId());
        assertEquals("Test", customer.getName());
    }

    @Test
    void testSetAndGetId() {
        customer.setId(100L);
        assertEquals(100L, customer.getId());
    }

    @Test
    void testSetAndGetName() {
        customer.setName("Jane Smith");
        assertEquals("Jane Smith", customer.getName());
    }

    @Test
    void testSetAndGetEmail() {
        customer.setEmail("jane@example.com");
        assertEquals("jane@example.com", customer.getEmail());
    }

    @Test
    void testSetAndGetPhone() {
        customer.setPhone(987654321);
        assertEquals(987654321, customer.getPhone());
    }

    @Test
    void testSetAndGetCategories() {
        Set<Category> categories = new HashSet<>();
        Category cat1 = new Category();
        cat1.setId(1L);
        cat1.setName("Finance");
        categories.add(cat1);

        customer.setCategories(categories);
        assertEquals(categories, customer.getCategories());
        assertEquals(1, customer.getCategories().size());
    }

    @Test
    void testSetAndGetFirstName() {
        customer.setFirstName("Alice");
        assertEquals("Alice", customer.getFirstName());
    }

    @Test
    void testSetAndGetLastName() {
        customer.setLastName("Johnson");
        assertEquals("Johnson", customer.getLastName());
    }

    @Test
    void testSetAndGetCity() {
        customer.setCity("Los Angeles");
        assertEquals("Los Angeles", customer.getCity());
    }

    @Test
    void testSetAndGetAddress() {
        customer.setAddress("456 Oak Avenue");
        assertEquals("456 Oak Avenue", customer.getAddress());
    }

    @Test
    void testSetAndGetEnabled() {
        customer.setEnabled(1);
        assertEquals(1, customer.getEnabled());

        customer.setEnabled(0);
        assertEquals(0, customer.getEnabled());
    }

    @Test
    void testNameMinimumSize() {
        String shortName = "AB";
        customer.setName(shortName);
        assertEquals(shortName, customer.getName());
        assertTrue(customer.getName().length() >= 2);
    }

    @Test
    void testEmailValidation() {
        String validEmail = "valid@example.com";
        customer.setEmail(validEmail);
        assertEquals(validEmail, customer.getEmail());
    }

    @Test
    void testPhoneDigits() {
        int validPhone = 1234567890;
        customer.setPhone(validPhone);
        assertEquals(validPhone, customer.getPhone());
    }

    @Test
    void testMultipleCategories() {
        Set<Category> categories = new HashSet<>();

        Category cat1 = new Category();
        cat1.setId(1L);
        cat1.setName("Tech");

        Category cat2 = new Category();
        cat2.setId(2L);
        cat2.setName("Finance");

        categories.add(cat1);
        categories.add(cat2);

        customer.setCategories(categories);
        assertEquals(2, customer.getCategories().size());
    }

    @Test
    void testNullValues() {
        customer.setName(null);
        assertNull(customer.getName());

        customer.setEmail(null);
        assertNull(customer.getEmail());

        customer.setCategories(null);
        assertNull(customer.getCategories());
    }

    @Test
    void testEnabledFlag() {
        customer.setEnabled(1);
        assertTrue(customer.getEnabled() == 1);

        customer.setEnabled(0);
        assertTrue(customer.getEnabled() == 0);
    }

    @Test
    void testEmptyCategoriesSet() {
        Set<Category> emptyCategories = new HashSet<>();
        customer.setCategories(emptyCategories);
        assertNotNull(customer.getCategories());
        assertEquals(0, customer.getCategories().size());
    }
}
