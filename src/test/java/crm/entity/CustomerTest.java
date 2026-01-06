package crm.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {

    private Customer customer;
    private Set<Category> categories;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        categories = new HashSet<>();

        Category category = new Category();
        category.setId(1L);
        category.setName("Tech");
        categories.add(category);
    }

    @Test
    void testCustomerBuilder() {
        Customer built = Customer.builder()
                .id(1L)
                .name("Test Customer")
                .email("customer@test.com")
                .phone(1234567890)
                .categories(categories)
                .firstName("John")
                .lastName("Doe")
                .city("New York")
                .address("123 Main St")
                .enabled(1)
                .build();

        assertNotNull(built);
        assertEquals(1L, built.getId());
        assertEquals("Test Customer", built.getName());
        assertEquals("customer@test.com", built.getEmail());
        assertEquals(1234567890, built.getPhone());
        assertEquals("John", built.getFirstName());
        assertEquals("Doe", built.getLastName());
        assertEquals("New York", built.getCity());
        assertEquals("123 Main St", built.getAddress());
        assertEquals(1, built.getEnabled());
    }

    @Test
    void testSettersAndGetters() {
        customer.setId(2L);
        customer.setName("Customer Name");
        customer.setEmail("test@email.com");
        customer.setPhone(987654321);
        customer.setCategories(categories);
        customer.setFirstName("Jane");
        customer.setLastName("Smith");
        customer.setCity("Los Angeles");
        customer.setAddress("456 Oak Ave");
        customer.setEnabled(1);

        assertEquals(2L, customer.getId());
        assertEquals("Customer Name", customer.getName());
        assertEquals("test@email.com", customer.getEmail());
        assertEquals(987654321, customer.getPhone());
        assertEquals(categories, customer.getCategories());
        assertEquals("Jane", customer.getFirstName());
        assertEquals("Smith", customer.getLastName());
        assertEquals("Los Angeles", customer.getCity());
        assertEquals("456 Oak Ave", customer.getAddress());
        assertEquals(1, customer.getEnabled());
    }

    @Test
    void testNoArgsConstructor() {
        Customer newCustomer = new Customer();
        assertNotNull(newCustomer);
        assertNull(newCustomer.getId());
        assertNull(newCustomer.getName());
    }

    @Test
    void testAllArgsConstructor() {
        Customer fullCustomer = new Customer(
                3L,
                "Full Customer",
                "full@test.com",
                112233445,
                categories,
                "Full",
                "Name",
                "Chicago",
                "789 Pine St",
                1
        );

        assertEquals(3L, fullCustomer.getId());
        assertEquals("Full Customer", fullCustomer.getName());
        assertEquals("full@test.com", fullCustomer.getEmail());
        assertEquals(112233445, fullCustomer.getPhone());
        assertEquals(categories, fullCustomer.getCategories());
        assertEquals("Full", fullCustomer.getFirstName());
        assertEquals("Name", fullCustomer.getLastName());
        assertEquals("Chicago", fullCustomer.getCity());
        assertEquals("789 Pine St", fullCustomer.getAddress());
        assertEquals(1, fullCustomer.getEnabled());
    }

    @Test
    void testCategoriesRelationship() {
        customer.setCategories(categories);
        assertNotNull(customer.getCategories());
        assertEquals(1, customer.getCategories().size());
    }

    @Test
    void testEnabledField() {
        customer.setEnabled(1);
        assertEquals(1, customer.getEnabled());

        customer.setEnabled(0);
        assertEquals(0, customer.getEnabled());
    }

    @Test
    void testNullValues() {
        customer.setName(null);
        customer.setEmail(null);
        customer.setCategories(null);

        assertNull(customer.getName());
        assertNull(customer.getEmail());
        assertNull(customer.getCategories());
    }
}
