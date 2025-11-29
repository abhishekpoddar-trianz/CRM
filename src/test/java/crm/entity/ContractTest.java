package crm.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ContractTest {

    private Contract contract;
    private Customer customer;
    private User user;

    @BeforeEach
    void setUp() {
        contract = new Contract();

        customer = new Customer();
        customer.setId(1L);
        customer.setName("Test Customer");

        user = new User();
        user.setId(1L);
        user.setUsername("testuser");
    }

    @Test
    void testDefaultConstructor() {
        // Act
        Contract newContract = new Contract();

        // Assert
        assertNotNull(newContract);
        assertNull(newContract.getId());
        assertNull(newContract.getName());
        assertNull(newContract.getContent());
        assertNull(newContract.getValue());
        assertNull(newContract.getBeginDate());
        assertNull(newContract.getEndDate());
        assertNull(newContract.getStatus());
        assertNull(newContract.getCustomer());
        assertNull(newContract.getUser());
    }

    @Test
    void testAllArgsConstructor() {
        // Arrange
        Long id = 1L;
        String name = "Test Contract";
        String content = "Contract content";
        BigDecimal value = new BigDecimal("1000.00");
        LocalDate beginDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 12, 31);
        Status status = Status.PROPOSED;

        // Act
        Contract contract = new Contract(id, name, content, value, beginDate, endDate, status, customer, user);

        // Assert
        assertEquals(id, contract.getId());
        assertEquals(name, contract.getName());
        assertEquals(content, contract.getContent());
        assertEquals(value, contract.getValue());
        assertEquals(beginDate, contract.getBeginDate());
        assertEquals(endDate, contract.getEndDate());
        assertEquals(status, contract.getStatus());
        assertEquals(customer, contract.getCustomer());
        assertEquals(user, contract.getUser());
    }

    @Test
    void testBuilder() {
        // Act
        Contract contract = Contract.builder()
                .id(1L)
                .name("Test Contract")
                .content("Contract content")
                .value(new BigDecimal("1000.00"))
                .beginDate(LocalDate.of(2023, 1, 1))
                .endDate(LocalDate.of(2023, 12, 31))
                .status(Status.PROPOSED)
                .customer(customer)
                .user(user)
                .build();

        // Assert
        assertEquals(1L, contract.getId());
        assertEquals("Test Contract", contract.getName());
        assertEquals("Contract content", contract.getContent());
        assertEquals(new BigDecimal("1000.00"), contract.getValue());
        assertEquals(LocalDate.of(2023, 1, 1), contract.getBeginDate());
        assertEquals(LocalDate.of(2023, 12, 31), contract.getEndDate());
        assertEquals(Status.PROPOSED, contract.getStatus());
        assertEquals(customer, contract.getCustomer());
        assertEquals(user, contract.getUser());
    }

    @Test
    void testSetAndGetId() {
        // Arrange
        Long expectedId = 123L;

        // Act
        contract.setId(expectedId);

        // Assert
        assertEquals(expectedId, contract.getId());
    }

    @Test
    void testSetAndGetName() {
        // Arrange
        String expectedName = "Service Contract";

        // Act
        contract.setName(expectedName);

        // Assert
        assertEquals(expectedName, contract.getName());
    }

    @Test
    void testSetAndGetContent() {
        // Arrange
        String expectedContent = "This is the contract content";

        // Act
        contract.setContent(expectedContent);

        // Assert
        assertEquals(expectedContent, contract.getContent());
    }

    @Test
    void testSetAndGetValue() {
        // Arrange
        BigDecimal expectedValue = new BigDecimal("15000.50");

        // Act
        contract.setValue(expectedValue);

        // Assert
        assertEquals(expectedValue, contract.getValue());
    }

    @Test
    void testSetAndGetBeginDate() {
        // Arrange
        LocalDate expectedDate = LocalDate.of(2024, 1, 1);

        // Act
        contract.setBeginDate(expectedDate);

        // Assert
        assertEquals(expectedDate, contract.getBeginDate());
    }

    @Test
    void testSetAndGetEndDate() {
        // Arrange
        LocalDate expectedDate = LocalDate.of(2024, 12, 31);

        // Act
        contract.setEndDate(expectedDate);

        // Assert
        assertEquals(expectedDate, contract.getEndDate());
    }

    @Test
    void testSetAndGetStatus() {
        // Arrange
        Status expectedStatus = Status.NEGOTIATED;

        // Act
        contract.setStatus(expectedStatus);

        // Assert
        assertEquals(expectedStatus, contract.getStatus());
    }

    @Test
    void testSetAndGetCustomer() {
        // Act
        contract.setCustomer(customer);

        // Assert
        assertEquals(customer, contract.getCustomer());
    }

    @Test
    void testSetAndGetUser() {
        // Act
        contract.setUser(user);

        // Assert
        assertEquals(user, contract.getUser());
    }

    @Test
    void testSetNullValues() {
        // Act
        contract.setId(null);
        contract.setName(null);
        contract.setContent(null);
        contract.setValue(null);
        contract.setBeginDate(null);
        contract.setEndDate(null);
        contract.setStatus(null);
        contract.setCustomer(null);
        contract.setUser(null);

        // Assert
        assertNull(contract.getId());
        assertNull(contract.getName());
        assertNull(contract.getContent());
        assertNull(contract.getValue());
        assertNull(contract.getBeginDate());
        assertNull(contract.getEndDate());
        assertNull(contract.getStatus());
        assertNull(contract.getCustomer());
        assertNull(contract.getUser());
    }

    @Test
    void testSetEmptyStringValues() {
        // Act
        contract.setName("");
        contract.setContent("");

        // Assert
        assertEquals("", contract.getName());
        assertEquals("", contract.getContent());
    }

    @Test
    void testSetZeroValue() {
        // Arrange
        BigDecimal zeroValue = BigDecimal.ZERO;

        // Act
        contract.setValue(zeroValue);

        // Assert
        assertEquals(zeroValue, contract.getValue());
    }

    @Test
    void testSetNegativeValue() {
        // Arrange
        BigDecimal negativeValue = new BigDecimal("-1000.00");

        // Act
        contract.setValue(negativeValue);

        // Assert
        assertEquals(negativeValue, contract.getValue());
    }

    @Test
    void testSetLargeValue() {
        // Arrange
        BigDecimal largeValue = new BigDecimal("999999999.99");

        // Act
        contract.setValue(largeValue);

        // Assert
        assertEquals(largeValue, contract.getValue());
    }

    @Test
    void testSetDateInThePast() {
        // Arrange
        LocalDate pastDate = LocalDate.of(2020, 1, 1);

        // Act
        contract.setBeginDate(pastDate);
        contract.setEndDate(pastDate);

        // Assert
        assertEquals(pastDate, contract.getBeginDate());
        assertEquals(pastDate, contract.getEndDate());
    }

    @Test
    void testSetDateInTheFuture() {
        // Arrange
        LocalDate futureDate = LocalDate.of(2030, 12, 31);

        // Act
        contract.setBeginDate(futureDate);
        contract.setEndDate(futureDate);

        // Assert
        assertEquals(futureDate, contract.getBeginDate());
        assertEquals(futureDate, contract.getEndDate());
    }

    @Test
    void testSetEndDateBeforeBeginDate() {
        // Arrange
        LocalDate beginDate = LocalDate.of(2023, 12, 31);
        LocalDate endDate = LocalDate.of(2023, 1, 1);

        // Act
        contract.setBeginDate(beginDate);
        contract.setEndDate(endDate);

        // Assert - Should allow invalid date ranges (business logic validation should be elsewhere)
        assertEquals(beginDate, contract.getBeginDate());
        assertEquals(endDate, contract.getEndDate());
        assertTrue(contract.getEndDate().isBefore(contract.getBeginDate()));
    }

    @Test
    void testAllStatusValues() {
        // Act & Assert
        for (Status status : Status.values()) {
            contract.setStatus(status);
            assertEquals(status, contract.getStatus());
        }
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        Contract contract1 = Contract.builder()
                .id(1L)
                .name("Test Contract")
                .value(new BigDecimal("1000.00"))
                .build();

        Contract contract2 = Contract.builder()
                .id(1L)
                .name("Test Contract")
                .value(new BigDecimal("1000.00"))
                .build();

        // Act & Assert
        assertEquals(contract1, contract2);
        assertEquals(contract1.hashCode(), contract2.hashCode());
    }

    @Test
    void testNotEquals() {
        // Arrange
        Contract contract1 = Contract.builder()
                .id(1L)
                .name("Contract 1")
                .build();

        Contract contract2 = Contract.builder()
                .id(2L)
                .name("Contract 2")
                .build();

        // Act & Assert
        assertNotEquals(contract1, contract2);
    }

    @Test
    void testToString() {
        // Arrange
        contract.setId(1L);
        contract.setName("Test Contract");
        contract.setValue(new BigDecimal("1000.00"));

        // Act
        String result = contract.toString();

        // Assert
        assertNotNull(result);
        assertTrue(result.contains("1"));
        assertTrue(result.contains("Test Contract"));
        assertTrue(result.contains("1000.00"));
    }

    @Test
    void testAnnotationsPresent() {
        // Act & Assert
        assertTrue(Contract.class.isAnnotationPresent(jakarta.persistence.Entity.class));
        assertTrue(Contract.class.isAnnotationPresent(lombok.Data.class));
        assertTrue(Contract.class.isAnnotationPresent(lombok.Builder.class));
        assertTrue(Contract.class.isAnnotationPresent(lombok.NoArgsConstructor.class));
        assertTrue(Contract.class.isAnnotationPresent(lombok.AllArgsConstructor.class));
    }

    @Test
    void testIdFieldAnnotations() throws NoSuchFieldException {
        // Act
        java.lang.reflect.Field idField = Contract.class.getDeclaredField("id");

        // Assert
        assertTrue(idField.isAnnotationPresent(jakarta.persistence.Id.class));
        assertTrue(idField.isAnnotationPresent(jakarta.persistence.GeneratedValue.class));
    }

    @Test
    void testNameFieldAnnotation() throws NoSuchFieldException {
        // Act
        java.lang.reflect.Field nameField = Contract.class.getDeclaredField("name");

        // Assert
        assertTrue(nameField.isAnnotationPresent(jakarta.persistence.Column.class));
    }

    @Test
    void testDateFieldAnnotations() throws NoSuchFieldException {
        // Act
        java.lang.reflect.Field beginDateField = Contract.class.getDeclaredField("beginDate");
        java.lang.reflect.Field endDateField = Contract.class.getDeclaredField("endDate");

        // Assert
        assertTrue(beginDateField.isAnnotationPresent(org.springframework.format.annotation.DateTimeFormat.class));
        assertTrue(endDateField.isAnnotationPresent(org.springframework.format.annotation.DateTimeFormat.class));
    }

    @Test
    void testStatusFieldEnumeratedAnnotation() throws NoSuchFieldException {
        // Act
        java.lang.reflect.Field statusField = Contract.class.getDeclaredField("status");

        // Assert
        assertTrue(statusField.isAnnotationPresent(jakarta.persistence.Enumerated.class));
    }

    @Test
    void testRelationshipFieldAnnotations() throws NoSuchFieldException {
        // Act
        java.lang.reflect.Field customerField = Contract.class.getDeclaredField("customer");
        java.lang.reflect.Field userField = Contract.class.getDeclaredField("user");

        // Assert
        assertTrue(customerField.isAnnotationPresent(jakarta.persistence.ManyToOne.class));
        assertTrue(userField.isAnnotationPresent(jakarta.persistence.ManyToOne.class));
    }
}