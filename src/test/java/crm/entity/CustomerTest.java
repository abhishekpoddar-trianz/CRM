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
    void testConstructor() {
        Customer newCustomer = new Customer();
        assertNotNull(newCustomer);
    }

    @Test
    void testBuilderConstructor() {
        Set<Category> categories = new HashSet<>();
        Customer builtCustomer = Customer.builder()
                .id(1L)
                .name("Test Customer")
                .email("customer@test.com")
                .phone(123456789L)
                .firstName("John")
                .lastName("Doe")
                .city("New York")
                .address("123 Main St")
                .enabled(1L)
                .categories(categories)
                .build();

        assertNotNull(builtCustomer);
        assertEquals(1L, builtCustomer.getId());
        assertEquals("Test Customer", builtCustomer.getName());
    }

    @Test
    void testAllArgsConstructor() {
        Set<Category> categories = new HashSet<>();
        Customer newCustomer = new Customer(1L, "Test Customer", "test@test.com",
                                          123456789, categories, "John", "Doe",
                                          "New York", "123 Main St", 1);
        assertNotNull(newCustomer);
        assertEquals(1L, newCustomer.getId());
        assertEquals("Test Customer", newCustomer.getName());
    }

    @Test
    void testGettersAndSetters() {
        Set<Category> categories = new HashSet<>();
        Category category = new Category();
        category.setName("Premium");
        categories.add(category);

        customer.setId(1L);
        customer.setName("Test Customer");
        customer.setEmail("test@test.com");
        customer.setPhone(123456789L);
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setCity("New York");
        customer.setAddress("123 Main St");
        customer.setEnabled(1L);
        customer.setCategories(categories);

        assertEquals(1L, customer.getId());
        assertEquals("Test Customer", customer.getName());
        assertEquals("test@test.com", customer.getEmail());
        assertEquals(123456789, customer.getPhone());
        assertEquals("John", customer.getFirstName());
        assertEquals("Doe", customer.getLastName());
        assertEquals("New York", customer.getCity());
        assertEquals("123 Main St", customer.getAddress());
        assertEquals(1, customer.getEnabled());
        assertEquals(categories, customer.getCategories());
    }

    @Test
    void testCategoriesManagement() {
        Set<Category> categories = new HashSet<>();
        Category category1 = new Category();
        category1.setId(1L);
        category1.setName("Premium");

        Category category2 = new Category();
        category2.setId(2L);
        category2.setName("Gold");

        categories.add(category1);
        categories.add(category2);

        customer.setCategories(categories);

        assertEquals(2, customer.getCategories().size());
        assertTrue(customer.getCategories().contains(category1));
        assertTrue(customer.getCategories().contains(category2));
    }

    @Test
    void testEqualsAndHashCode() {
        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setName("Test Customer");

        Customer customer2 = new Customer();
        customer2.setId(1L);
        customer2.setName("Test Customer");

        assertEquals(customer1, customer2);
        assertEquals(customer1.hashCode(), customer2.hashCode());
    }
}