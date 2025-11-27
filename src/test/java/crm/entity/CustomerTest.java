package crm.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerTest {

    private Customer customer;
    private Set<Category> categories;

    @BeforeEach
    public void setUp() {
        customer = new Customer();
        categories = new HashSet<>();
        Category category = new Category();
        category.setId(1L);
        category.setName("VIP");
        categories.add(category);
    }

    @Test
    public void testCustomerConstructor() {
        assertNotNull(customer);
    }

    @Test
    public void testCustomerBuilderConstructor() {
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
    }

    @Test
    public void testAllArgsConstructor() {
        Customer customerAll = new Customer(1L, "Test", "test@test.com", 999, categories, "Test", "User", "City", "Address", 1);
        assertNotNull(customerAll);
        assertEquals(1L, customerAll.getId());
        assertEquals("Test", customerAll.getName());
    }

    @Test
    public void testSetAndGetId() {
        customer.setId(100L);
        assertEquals(100L, customer.getId());
    }

    @Test
    public void testSetAndGetName() {
        customer.setName("Customer Name");
        assertEquals("Customer Name", customer.getName());
    }

    @Test
    public void testSetAndGetEmail() {
        customer.setEmail("customer@example.com");
        assertEquals("customer@example.com", customer.getEmail());
    }

    @Test
    public void testSetAndGetPhone() {
        customer.setPhone(123456789);
        assertEquals(123456789, customer.getPhone());
    }

    @Test
    public void testSetAndGetCategories() {
        customer.setCategories(categories);
        assertNotNull(customer.getCategories());
        assertEquals(1, customer.getCategories().size());
    }

    @Test
    public void testSetAndGetFirstName() {
        customer.setFirstName("Alice");
        assertEquals("Alice", customer.getFirstName());
    }

    @Test
    public void testSetAndGetLastName() {
        customer.setLastName("Smith");
        assertEquals("Smith", customer.getLastName());
    }

    @Test
    public void testSetAndGetCity() {
        customer.setCity("Los Angeles");
        assertEquals("Los Angeles", customer.getCity());
    }

    @Test
    public void testSetAndGetAddress() {
        customer.setAddress("456 Oak Ave");
        assertEquals("456 Oak Ave", customer.getAddress());
    }

    @Test
    public void testSetAndGetEnabled() {
        customer.setEnabled(1);
        assertEquals(1, customer.getEnabled());
    }

    @Test
    public void testSetNameNull() {
        customer.setName(null);
        assertNull(customer.getName());
    }

    @Test
    public void testSetEmailNull() {
        customer.setEmail(null);
        assertNull(customer.getEmail());
    }

    @Test
    public void testSetPhoneZero() {
        customer.setPhone(0);
        assertEquals(0, customer.getPhone());
    }

    @Test
    public void testSetEnabledZero() {
        customer.setEnabled(0);
        assertEquals(0, customer.getEnabled());
    }

    @Test
    public void testSetCategoriesNull() {
        customer.setCategories(null);
        assertNull(customer.getCategories());
    }

    @Test
    public void testToString() {
        customer.setName("Test Customer");
        String result = customer.toString();
        assertNotNull(result);
    }
}
