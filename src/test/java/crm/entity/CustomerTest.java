package crm.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.HashSet;
import java.util.Set;

public class CustomerTest {

    private Customer customer;

    @BeforeEach
    public void setUp() {
        customer = new Customer();
    }

    @Test
    public void testSetAndGetId() {
        customer.setId(1L);
        assertEquals(1L, customer.getId());
    }

    @Test
    public void testSetAndGetName() {
        customer.setName("John Doe");
        assertEquals("John Doe", customer.getName());
    }

    @Test
    public void testSetAndGetEmail() {
        customer.setEmail("john@example.com");
        assertEquals("john@example.com", customer.getEmail());
    }

    @Test
    public void testSetAndGetPhone() {
        customer.setPhone(1234567890);
        assertEquals(1234567890, customer.getPhone());
    }

    @Test
    public void testSetAndGetFirstName() {
        customer.setFirstName("John");
        assertEquals("John", customer.getFirstName());
    }

    @Test
    public void testSetAndGetLastName() {
        customer.setLastName("Doe");
        assertEquals("Doe", customer.getLastName());
    }

    @Test
    public void testSetAndGetCity() {
        customer.setCity("New York");
        assertEquals("New York", customer.getCity());
    }

    @Test
    public void testSetAndGetAddress() {
        customer.setAddress("123 Main St");
        assertEquals("123 Main St", customer.getAddress());
    }

    @Test
    public void testSetAndGetEnabled() {
        customer.setEnabled(1);
        assertEquals(1, customer.getEnabled());
    }

    @Test
    public void testSetAndGetCategories() {
        Set<Category> categories = new HashSet<>();
        Category category = new Category();
        category.setId(1L);
        category.setName("VIP");
        categories.add(category);

        customer.setCategories(categories);
        assertEquals(categories, customer.getCategories());
        assertEquals(1, customer.getCategories().size());
    }

    @Test
    public void testBuilderPattern() {
        Set<Category> categories = new HashSet<>();
        Customer customerBuilt = Customer.builder()
                .id(1L)
                .name("Test Customer")
                .email("test@example.com")
                .phone(123456)
                .firstName("Test")
                .lastName("Customer")
                .city("TestCity")
                .address("Test Address")
                .enabled(1)
                .categories(categories)
                .build();

        assertEquals(1L, customerBuilt.getId());
        assertEquals("Test Customer", customerBuilt.getName());
        assertEquals("test@example.com", customerBuilt.getEmail());
        assertEquals(123456, customerBuilt.getPhone());
    }

    @Test
    public void testAllArgsConstructor() {
        Set<Category> categories = new HashSet<>();
        Customer customerWithArgs = new Customer(
            1L, "Customer Name", "email@test.com", 123456789,
            categories, "First", "Last", "City", "Address", 1
        );

        assertEquals(1L, customerWithArgs.getId());
        assertEquals("Customer Name", customerWithArgs.getName());
        assertEquals("email@test.com", customerWithArgs.getEmail());
        assertEquals(123456789, customerWithArgs.getPhone());
    }

    @Test
    public void testNoArgsConstructor() {
        Customer emptyCustomer = new Customer();
        assertNull(emptyCustomer.getId());
        assertNull(emptyCustomer.getName());
        assertNull(emptyCustomer.getEmail());
    }

    @Test
    public void testEnabledZero() {
        customer.setEnabled(0);
        assertEquals(0, customer.getEnabled());
    }

    @Test
    public void testPhoneZero() {
        customer.setPhone(0);
        assertEquals(0, customer.getPhone());
    }
}
