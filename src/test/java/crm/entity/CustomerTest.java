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
        Category category = new Category();
        category.setId(1L);
        category.setName("VIP");

        categories = new HashSet<>();
        categories.add(category);

        customer = Customer.builder()
                .id(1L)
                .name("Test Customer")
                .email("customer@example.com")
                .phone(123456789)
                .categories(categories)
                .firstName("John")
                .lastName("Doe")
                .city("New York")
                .address("123 Main St")
                .enabled(1)
                .build();
    }

    @Test
    void testCustomerBuilder() {
        assertNotNull(customer);
        assertEquals(1L, customer.getId());
        assertEquals("Test Customer", customer.getName());
        assertEquals("customer@example.com", customer.getEmail());
        assertEquals(123456789, customer.getPhone());
        assertEquals(categories, customer.getCategories());
        assertEquals("John", customer.getFirstName());
        assertEquals("Doe", customer.getLastName());
        assertEquals("New York", customer.getCity());
        assertEquals("123 Main St", customer.getAddress());
        assertEquals(1, customer.getEnabled());
    }

    @Test
    void testNoArgsConstructor() {
        Customer emptyCustomer = new Customer();
        assertNotNull(emptyCustomer);
        assertNull(emptyCustomer.getId());
        assertNull(emptyCustomer.getName());
    }

    @Test
    void testAllArgsConstructor() {
        Customer newCustomer = new Customer(2L, "Customer2", "customer2@example.com",
                987654321, categories, "Jane", "Smith", "Boston", "456 Elm St", 0);

        assertNotNull(newCustomer);
        assertEquals(2L, newCustomer.getId());
        assertEquals("Customer2", newCustomer.getName());
        assertEquals("customer2@example.com", newCustomer.getEmail());
        assertEquals(987654321, newCustomer.getPhone());
    }

    @Test
    void testSettersAndGetters() {
        customer.setId(10L);
        customer.setName("Updated Customer");
        customer.setEmail("updated@example.com");
        customer.setPhone(111222333);
        customer.setFirstName("Jane");
        customer.setLastName("Smith");
        customer.setCity("Los Angeles");
        customer.setAddress("789 Oak Ave");
        customer.setEnabled(0);

        assertEquals(10L, customer.getId());
        assertEquals("Updated Customer", customer.getName());
        assertEquals("updated@example.com", customer.getEmail());
        assertEquals(111222333, customer.getPhone());
        assertEquals("Jane", customer.getFirstName());
        assertEquals("Smith", customer.getLastName());
        assertEquals("Los Angeles", customer.getCity());
        assertEquals("789 Oak Ave", customer.getAddress());
        assertEquals(0, customer.getEnabled());
    }

    @Test
    void testCategoriesRelationship() {
        Category newCategory = new Category();
        newCategory.setId(2L);
        newCategory.setName("Premium");

        Set<Category> newCategories = new HashSet<>();
        newCategories.add(newCategory);

        customer.setCategories(newCategories);
        assertEquals(1, customer.getCategories().size());
        assertTrue(customer.getCategories().contains(newCategory));
    }

    @Test
    void testMultipleCategories() {
        Category category1 = new Category();
        category1.setId(1L);
        category1.setName("VIP");

        Category category2 = new Category();
        category2.setId(2L);
        category2.setName("Premium");

        Set<Category> multipleCategories = new HashSet<>();
        multipleCategories.add(category1);
        multipleCategories.add(category2);

        customer.setCategories(multipleCategories);
        assertEquals(2, customer.getCategories().size());
    }

    @Test
    void testEnabledToggle() {
        customer.setEnabled(1);
        assertEquals(1, customer.getEnabled());

        customer.setEnabled(0);
        assertEquals(0, customer.getEnabled());
    }

    @Test
    void testNameValidation() {
        customer.setName("AB");
        assertEquals(2, customer.getName().length());
    }

    @Test
    void testPhoneNumber() {
        customer.setPhone(123456789);
        assertEquals(123456789, customer.getPhone());
    }

    @Test
    void testNullCategories() {
        customer.setCategories(null);
        assertNull(customer.getCategories());
    }
}
