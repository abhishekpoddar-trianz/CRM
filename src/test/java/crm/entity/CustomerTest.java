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
        categories = new HashSet<>();
        Category category = new Category();
        category.setId(1L);
        category.setName("Test Category");
        categories.add(category);

        customer = Customer.builder()
                .id(1L)
                .name("Test Customer")
                .email("test@example.com")
                .phone(1234567890)
                .categories(categories)
                .firstName("John")
                .lastName("Doe")
                .city("Test City")
                .address("123 Test St")
                .enabled(1)
                .build();
    }

    @Test
    public void testCustomerBuilder() {
        assertNotNull(customer);
        assertEquals(1L, customer.getId());
        assertEquals("Test Customer", customer.getName());
        assertEquals("test@example.com", customer.getEmail());
        assertEquals(1234567890, customer.getPhone());
        assertEquals("John", customer.getFirstName());
        assertEquals("Doe", customer.getLastName());
        assertEquals("Test City", customer.getCity());
        assertEquals("123 Test St", customer.getAddress());
        assertEquals(1, customer.getEnabled());
        assertNotNull(customer.getCategories());
    }

    @Test
    public void testCustomerNoArgsConstructor() {
        Customer emptyCustomer = new Customer();
        assertNotNull(emptyCustomer);
        assertNull(emptyCustomer.getId());
        assertNull(emptyCustomer.getName());
    }

    @Test
    public void testCustomerAllArgsConstructor() {
        Customer newCustomer = new Customer(2L, "New Customer", "new@example.com",
                987654321, categories, "Jane", "Smith", "New City", "456 New St", 1);

        assertNotNull(newCustomer);
        assertEquals(2L, newCustomer.getId());
        assertEquals("New Customer", newCustomer.getName());
        assertEquals("new@example.com", newCustomer.getEmail());
    }

    @Test
    public void testSettersAndGetters() {
        customer.setName("Updated Customer");
        customer.setEmail("updated@example.com");
        customer.setPhone(111222333);
        customer.setCity("Updated City");

        assertEquals("Updated Customer", customer.getName());
        assertEquals("updated@example.com", customer.getEmail());
        assertEquals(111222333, customer.getPhone());
        assertEquals("Updated City", customer.getCity());
    }

    @Test
    public void testCustomerWithNullValues() {
        Customer nullCustomer = Customer.builder()
                .id(null)
                .name(null)
                .email(null)
                .phone(0)
                .categories(null)
                .firstName(null)
                .lastName(null)
                .city(null)
                .address(null)
                .enabled(0)
                .build();

        assertNotNull(nullCustomer);
        assertNull(nullCustomer.getId());
        assertNull(nullCustomer.getName());
    }

    @Test
    public void testCustomerCategories() {
        Set<Category> newCategories = new HashSet<>();
        Category category1 = new Category();
        category1.setId(1L);
        category1.setName("Category 1");
        Category category2 = new Category();
        category2.setId(2L);
        category2.setName("Category 2");

        newCategories.add(category1);
        newCategories.add(category2);

        customer.setCategories(newCategories);
        assertEquals(2, customer.getCategories().size());
    }

    @Test
    public void testEnabledField() {
        customer.setEnabled(1);
        assertEquals(1, customer.getEnabled());

        customer.setEnabled(0);
        assertEquals(0, customer.getEnabled());
    }

    @Test
    public void testEmailValidation() {
        customer.setEmail("valid@email.com");
        assertTrue(customer.getEmail().contains("@"));
    }
}
