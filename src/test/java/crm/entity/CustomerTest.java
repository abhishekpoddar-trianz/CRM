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
    public void testNoArgsConstructor() {
        Customer newCustomer = new Customer();
        assertNotNull(newCustomer);
    }

    @Test
    public void testAllArgsConstructor() {
        Set<Category> categories = new HashSet<>();
        Category category = new Category();
        category.setId(1L);
        category.setName("VIP");
        categories.add(category);

        Customer newCustomer = new Customer(1L, "Test Company", "test@company.com", 123456789,
                categories, "John", "Doe", "New York", "123 Main St", 1);

        assertEquals(1L, newCustomer.getId());
        assertEquals("Test Company", newCustomer.getName());
        assertEquals("test@company.com", newCustomer.getEmail());
        assertEquals(123456789, newCustomer.getPhone());
        assertEquals(categories, newCustomer.getCategories());
        assertEquals("John", newCustomer.getFirstName());
        assertEquals("Doe", newCustomer.getLastName());
        assertEquals("New York", newCustomer.getCity());
        assertEquals("123 Main St", newCustomer.getAddress());
        assertEquals(1, newCustomer.getEnabled());
    }

    @Test
    public void testBuilder() {
        Set<Category> categories = new HashSet<>();
        Category category = new Category();
        category.setId(1L);
        category.setName("Premium");
        categories.add(category);

        Customer builtCustomer = Customer.builder()
                .id(2L)
                .name("Builder Company")
                .email("builder@test.com")
                .phone(987654321)
                .categories(categories)
                .firstName("Jane")
                .lastName("Smith")
                .city("Los Angeles")
                .address("456 Oak Ave")
                .enabled(1)
                .build();

        assertEquals(2L, builtCustomer.getId());
        assertEquals("Builder Company", builtCustomer.getName());
        assertEquals("builder@test.com", builtCustomer.getEmail());
        assertEquals(987654321, builtCustomer.getPhone());
        assertEquals(categories, builtCustomer.getCategories());
        assertEquals("Jane", builtCustomer.getFirstName());
        assertEquals("Smith", builtCustomer.getLastName());
        assertEquals("Los Angeles", builtCustomer.getCity());
        assertEquals("456 Oak Ave", builtCustomer.getAddress());
        assertEquals(1, builtCustomer.getEnabled());
    }

    @Test
    public void testSettersAndGetters() {
        customer.setId(1L);
        customer.setName("Test Customer");
        customer.setEmail("customer@test.com");
        customer.setPhone(555123456);
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setCity("Chicago");
        customer.setAddress("789 Pine St");
        customer.setEnabled(1);

        assertEquals(1L, customer.getId());
        assertEquals("Test Customer", customer.getName());
        assertEquals("customer@test.com", customer.getEmail());
        assertEquals(555123456, customer.getPhone());
        assertEquals("John", customer.getFirstName());
        assertEquals("Doe", customer.getLastName());
        assertEquals("Chicago", customer.getCity());
        assertEquals("789 Pine St", customer.getAddress());
        assertEquals(1, customer.getEnabled());
    }

    @Test
    public void testCategoriesRelationship() {
        Set<Category> categories = new HashSet<>();

        Category category1 = new Category();
        category1.setId(1L);
        category1.setName("VIP");
        categories.add(category1);

        Category category2 = new Category();
        category2.setId(2L);
        category2.setName("Premium");
        categories.add(category2);

        customer.setCategories(categories);

        assertEquals(categories, customer.getCategories());
        assertEquals(2, customer.getCategories().size());
        assertTrue(customer.getCategories().contains(category1));
        assertTrue(customer.getCategories().contains(category2));
    }

    @Test
    public void testEntityAnnotation() {
        assertTrue(Customer.class.isAnnotationPresent(jakarta.persistence.Entity.class));
    }

    @Test
    public void testLombokAnnotations() {
        assertTrue(Customer.class.isAnnotationPresent(lombok.Data.class));
        assertTrue(Customer.class.isAnnotationPresent(lombok.Builder.class));
        assertTrue(Customer.class.isAnnotationPresent(lombok.NoArgsConstructor.class));
        assertTrue(Customer.class.isAnnotationPresent(lombok.AllArgsConstructor.class));
    }

    @Test
    public void testValidationAnnotations() throws NoSuchFieldException {
        java.lang.reflect.Field nameField = Customer.class.getDeclaredField("name");
        assertTrue(nameField.isAnnotationPresent(jakarta.validation.constraints.Size.class));

        java.lang.reflect.Field emailField = Customer.class.getDeclaredField("email");
        assertTrue(emailField.isAnnotationPresent(jakarta.validation.constraints.Email.class));
        assertTrue(emailField.isAnnotationPresent(jakarta.validation.constraints.NotEmpty.class));

        java.lang.reflect.Field phoneField = Customer.class.getDeclaredField("phone");
        assertTrue(phoneField.isAnnotationPresent(jakarta.validation.constraints.Digits.class));
    }

    @Test
    public void testJpaAnnotations() throws NoSuchFieldException {
        java.lang.reflect.Field idField = Customer.class.getDeclaredField("id");
        assertTrue(idField.isAnnotationPresent(jakarta.persistence.Id.class));
        assertTrue(idField.isAnnotationPresent(jakarta.persistence.GeneratedValue.class));

        java.lang.reflect.Field nameField = Customer.class.getDeclaredField("name");
        assertTrue(nameField.isAnnotationPresent(jakarta.persistence.Column.class));

        java.lang.reflect.Field categoriesField = Customer.class.getDeclaredField("categories");
        assertTrue(categoriesField.isAnnotationPresent(jakarta.persistence.ManyToMany.class));
        assertTrue(categoriesField.isAnnotationPresent(jakarta.persistence.JoinTable.class));
    }

    @Test
    public void testEqualsAndHashCode() {
        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setName("Test Customer");

        Customer customer2 = new Customer();
        customer2.setId(1L);
        customer2.setName("Test Customer");

        assertEquals(customer1, customer2);
        assertEquals(customer1.hashCode(), customer2.hashCode());
    }

    @Test
    public void testToString() {
        customer.setId(1L);
        customer.setName("Test Customer");
        customer.setEmail("test@customer.com");

        String customerString = customer.toString();
        assertNotNull(customerString);
        assertTrue(customerString.contains("Test Customer"));
        assertTrue(customerString.contains("test@customer.com"));
    }

    @Test
    public void testEnabledField() {
        customer.setEnabled(0);
        assertEquals(0, customer.getEnabled());

        customer.setEnabled(1);
        assertEquals(1, customer.getEnabled());
    }

    @Test
    public void testPhoneField() {
        customer.setPhone(0);
        assertEquals(0, customer.getPhone());

        customer.setPhone(999999999);
        assertEquals(999999999, customer.getPhone());

        customer.setPhone(-1);
        assertEquals(-1, customer.getPhone());
    }

    @Test
    public void testNullValues() {
        customer.setName(null);
        customer.setEmail(null);
        customer.setFirstName(null);
        customer.setLastName(null);
        customer.setCity(null);
        customer.setAddress(null);
        customer.setCategories(null);

        assertNull(customer.getName());
        assertNull(customer.getEmail());
        assertNull(customer.getFirstName());
        assertNull(customer.getLastName());
        assertNull(customer.getCity());
        assertNull(customer.getAddress());
        assertNull(customer.getCategories());
    }

    @Test
    public void testEmptyStringValues() {
        customer.setName("");
        customer.setEmail("");
        customer.setFirstName("");
        customer.setLastName("");
        customer.setCity("");
        customer.setAddress("");

        assertEquals("", customer.getName());
        assertEquals("", customer.getEmail());
        assertEquals("", customer.getFirstName());
        assertEquals("", customer.getLastName());
        assertEquals("", customer.getCity());
        assertEquals("", customer.getAddress());
    }

    @Test
    public void testCategoriesEmptySet() {
        Set<Category> emptyCategories = new HashSet<>();
        customer.setCategories(emptyCategories);

        assertNotNull(customer.getCategories());
        assertTrue(customer.getCategories().isEmpty());
        assertEquals(0, customer.getCategories().size());
    }

    @Test
    public void testFieldCount() {
        java.lang.reflect.Field[] declaredFields = Customer.class.getDeclaredFields();
        assertEquals(10, declaredFields.length); // All declared fields
    }
}