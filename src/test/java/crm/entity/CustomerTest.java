package crm.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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
    void customer_defaultConstructor_shouldCreateInstance() {
        // Act
        Customer newCustomer = new Customer();

        // Assert
        assertNotNull(newCustomer, "Customer should be created");
        assertNull(newCustomer.getId(), "Default id should be null");
        assertNull(newCustomer.getName(), "Default name should be null");
        assertNull(newCustomer.getEmail(), "Default email should be null");
        assertEquals(0, newCustomer.getPhone(), "Default phone should be 0");
        assertNull(newCustomer.getCategories(), "Default categories should be null");
        assertNull(newCustomer.getFirstName(), "Default firstName should be null");
        assertNull(newCustomer.getLastName(), "Default lastName should be null");
        assertNull(newCustomer.getCity(), "Default city should be null");
        assertNull(newCustomer.getAddress(), "Default address should be null");
        assertEquals(0, newCustomer.getEnabled(), "Default enabled should be 0");
    }

    @Test
    void customer_allArgsConstructor_shouldCreateInstanceWithAllFields() {
        // Arrange
        Long id = 1L;
        String name = "Test Company";
        String email = "test@example.com";
        int phone = 123456789;
        Set<Category> categories = new HashSet<>();
        String firstName = "John";
        String lastName = "Doe";
        String city = "Test City";
        String address = "123 Test St";
        int enabled = 1;

        // Act
        Customer newCustomer = new Customer(id, name, email, phone, categories, firstName, lastName, city, address, enabled);

        // Assert
        assertNotNull(newCustomer, "Customer should be created");
        assertEquals(id, newCustomer.getId());
        assertEquals(name, newCustomer.getName());
        assertEquals(email, newCustomer.getEmail());
        assertEquals(phone, newCustomer.getPhone());
        assertEquals(categories, newCustomer.getCategories());
        assertEquals(firstName, newCustomer.getFirstName());
        assertEquals(lastName, newCustomer.getLastName());
        assertEquals(city, newCustomer.getCity());
        assertEquals(address, newCustomer.getAddress());
        assertEquals(enabled, newCustomer.getEnabled());
    }

    @Test
    void customer_builder_shouldCreateInstanceWithSpecifiedFields() {
        // Arrange
        String name = "Test Company";
        String email = "test@example.com";
        int phone = 123456789;

        // Act
        Customer newCustomer = Customer.builder()
                .name(name)
                .email(email)
                .phone(phone)
                .build();

        // Assert
        assertNotNull(newCustomer, "Customer should be created");
        assertEquals(name, newCustomer.getName());
        assertEquals(email, newCustomer.getEmail());
        assertEquals(phone, newCustomer.getPhone());
    }

    @Test
    void setId_withValidId_shouldSetId() {
        // Arrange
        Long expectedId = 123L;

        // Act
        customer.setId(expectedId);

        // Assert
        assertEquals(expectedId, customer.getId(), "Id should be set correctly");
    }

    @Test
    void setName_withValidName_shouldSetName() {
        // Arrange
        String expectedName = "Test Company";

        // Act
        customer.setName(expectedName);

        // Assert
        assertEquals(expectedName, customer.getName(), "Name should be set correctly");
    }

    @Test
    void setEmail_withValidEmail_shouldSetEmail() {
        // Arrange
        String expectedEmail = "test@example.com";

        // Act
        customer.setEmail(expectedEmail);

        // Assert
        assertEquals(expectedEmail, customer.getEmail(), "Email should be set correctly");
    }

    @Test
    void setPhone_withValidPhone_shouldSetPhone() {
        // Arrange
        int expectedPhone = 123456789;

        // Act
        customer.setPhone(expectedPhone);

        // Assert
        assertEquals(expectedPhone, customer.getPhone(), "Phone should be set correctly");
    }

    @Test
    void setCategories_withValidCategories_shouldSetCategories() {
        // Arrange
        Set<Category> expectedCategories = new HashSet<>();
        Category category = new Category();
        category.setName("Test Category");
        expectedCategories.add(category);

        // Act
        customer.setCategories(expectedCategories);

        // Assert
        assertEquals(expectedCategories, customer.getCategories(), "Categories should be set correctly");
    }

    @Test
    void setFirstName_withValidFirstName_shouldSetFirstName() {
        // Arrange
        String expectedFirstName = "John";

        // Act
        customer.setFirstName(expectedFirstName);

        // Assert
        assertEquals(expectedFirstName, customer.getFirstName(), "FirstName should be set correctly");
    }

    @Test
    void setLastName_withValidLastName_shouldSetLastName() {
        // Arrange
        String expectedLastName = "Doe";

        // Act
        customer.setLastName(expectedLastName);

        // Assert
        assertEquals(expectedLastName, customer.getLastName(), "LastName should be set correctly");
    }

    @Test
    void setCity_withValidCity_shouldSetCity() {
        // Arrange
        String expectedCity = "Test City";

        // Act
        customer.setCity(expectedCity);

        // Assert
        assertEquals(expectedCity, customer.getCity(), "City should be set correctly");
    }

    @Test
    void setAddress_withValidAddress_shouldSetAddress() {
        // Arrange
        String expectedAddress = "123 Test Street";

        // Act
        customer.setAddress(expectedAddress);

        // Assert
        assertEquals(expectedAddress, customer.getAddress(), "Address should be set correctly");
    }

    @Test
    void setEnabled_withValidEnabled_shouldSetEnabled() {
        // Arrange
        int expectedEnabled = 1;

        // Act
        customer.setEnabled(expectedEnabled);

        // Assert
        assertEquals(expectedEnabled, customer.getEnabled(), "Enabled should be set correctly");
    }

    @Test
    void setPhone_withZeroPhone_shouldSetZeroPhone() {
        // Act
        customer.setPhone(0);

        // Assert
        assertEquals(0, customer.getPhone(), "Phone should be set to 0");
    }

    @Test
    void setCategories_withEmptySet_shouldSetEmptyCategories() {
        // Arrange
        Set<Category> emptyCategories = new HashSet<>();

        // Act
        customer.setCategories(emptyCategories);

        // Assert
        assertEquals(emptyCategories, customer.getCategories(), "Categories should be empty set");
        assertTrue(customer.getCategories().isEmpty(), "Categories set should be empty");
    }

    @Test
    void equals_withSameValues_shouldBeEqual() {
        // Arrange
        Customer customer1 = Customer.builder()
                .id(1L)
                .name("Test Company")
                .email("test@example.com")
                .phone(123456789)
                .build();

        Customer customer2 = Customer.builder()
                .id(1L)
                .name("Test Company")
                .email("test@example.com")
                .phone(123456789)
                .build();

        // Act & Assert
        assertEquals(customer1, customer2, "Customers with same values should be equal");
        assertEquals(customer1.hashCode(), customer2.hashCode(), "Hash codes should be equal");
    }

    @Test
    void toString_shouldReturnStringRepresentation() {
        // Arrange
        customer.setId(1L);
        customer.setName("Test Company");
        customer.setEmail("test@example.com");

        // Act
        String result = customer.toString();

        // Assert
        assertNotNull(result, "toString should not return null");
        assertTrue(result.contains("1"), "toString should contain id");
        assertTrue(result.contains("Test Company"), "toString should contain name");
        assertTrue(result.contains("test@example.com"), "toString should contain email");
    }

    @Test
    void customerClass_shouldHaveCorrectJPAAnnotations() {
        // Assert
        assertTrue(Customer.class.isAnnotationPresent(Entity.class), "Customer should have @Entity annotation");
    }

    @Test
    void idField_shouldHaveCorrectJPAAnnotations() throws NoSuchFieldException {
        // Arrange
        var idField = Customer.class.getDeclaredField("id");

        // Assert
        assertTrue(idField.isAnnotationPresent(Id.class), "id field should have @Id annotation");
        assertTrue(idField.isAnnotationPresent(GeneratedValue.class), "id field should have @GeneratedValue annotation");

        GeneratedValue generatedValue = idField.getAnnotation(GeneratedValue.class);
        assertEquals(GenerationType.IDENTITY, generatedValue.strategy(), "GeneratedValue strategy should be IDENTITY");
    }

    @Test
    void nameField_shouldHaveCorrectValidationAnnotations() throws NoSuchFieldException {
        // Arrange
        var nameField = Customer.class.getDeclaredField("name");

        // Assert
        assertTrue(nameField.isAnnotationPresent(Column.class), "name field should have @Column annotation");
        assertTrue(nameField.isAnnotationPresent(Size.class), "name field should have @Size annotation");

        Column column = nameField.getAnnotation(Column.class);
        assertFalse(column.nullable(), "Column should not be nullable");
        assertTrue(column.unique(), "Column should be unique");

        Size size = nameField.getAnnotation(Size.class);
        assertEquals(2, size.min(), "Size min should be 2");
    }

    @Test
    void emailField_shouldHaveCorrectValidationAnnotations() throws NoSuchFieldException {
        // Arrange
        var emailField = Customer.class.getDeclaredField("email");

        // Assert
        assertTrue(emailField.isAnnotationPresent(Column.class), "email field should have @Column annotation");
        assertTrue(emailField.isAnnotationPresent(Email.class), "email field should have @Email annotation");
        assertTrue(emailField.isAnnotationPresent(NotEmpty.class), "email field should have @NotEmpty annotation");

        Column column = emailField.getAnnotation(Column.class);
        assertEquals("email", column.name(), "Column name should be 'email'");
        assertFalse(column.nullable(), "Column should not be nullable");
        assertTrue(column.unique(), "Column should be unique");
    }

    @Test
    void phoneField_shouldHaveCorrectValidationAnnotations() throws NoSuchFieldException {
        // Arrange
        var phoneField = Customer.class.getDeclaredField("phone");

        // Assert
        assertTrue(phoneField.isAnnotationPresent(Digits.class), "phone field should have @Digits annotation");

        Digits digits = phoneField.getAnnotation(Digits.class);
        assertEquals(0, digits.fraction(), "Digits fraction should be 0");
        assertEquals(20, digits.integer(), "Digits integer should be 20");
    }

    @Test
    void categoriesField_shouldHaveCorrectJPAAnnotations() throws NoSuchFieldException {
        // Arrange
        var categoriesField = Customer.class.getDeclaredField("categories");

        // Assert
        assertTrue(categoriesField.isAnnotationPresent(ManyToMany.class), "categories field should have @ManyToMany annotation");
        assertTrue(categoriesField.isAnnotationPresent(JoinTable.class), "categories field should have @JoinTable annotation");

        ManyToMany manyToMany = categoriesField.getAnnotation(ManyToMany.class);
        assertEquals(CascadeType.ALL, manyToMany.cascade()[0], "Cascade type should be ALL");
        assertEquals(FetchType.EAGER, manyToMany.fetch(), "Fetch type should be EAGER");

        JoinTable joinTable = categoriesField.getAnnotation(JoinTable.class);
        assertEquals("customer_category", joinTable.name(), "Join table name should be 'customer_category'");
    }

    @Test
    void customerClass_shouldHaveLombokAnnotations() {
        // Assert
        assertTrue(Customer.class.isAnnotationPresent(lombok.Data.class), "Customer should have @Data annotation");
        assertTrue(Customer.class.isAnnotationPresent(lombok.Builder.class), "Customer should have @Builder annotation");
        assertTrue(Customer.class.isAnnotationPresent(lombok.NoArgsConstructor.class), "Customer should have @NoArgsConstructor annotation");
        assertTrue(Customer.class.isAnnotationPresent(lombok.AllArgsConstructor.class), "Customer should have @AllArgsConstructor annotation");
    }
}