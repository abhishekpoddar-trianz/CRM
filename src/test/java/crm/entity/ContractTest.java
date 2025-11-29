package crm.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ContractTest {

    private Contract contract;
    private Customer testCustomer;
    private User testUser;

    @BeforeEach
    void setUp() {
        contract = new Contract();

        testCustomer = new Customer();
        testCustomer.setId(1L);
        testCustomer.setName("Test Customer");

        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testuser");
    }

    @Test
    void contract_defaultConstructor_shouldCreateInstance() {
        // Act
        Contract newContract = new Contract();

        // Assert
        assertNotNull(newContract, "Contract should be created");
        assertNull(newContract.getId(), "Default id should be null");
        assertNull(newContract.getName(), "Default name should be null");
        assertNull(newContract.getContent(), "Default content should be null");
        assertNull(newContract.getValue(), "Default value should be null");
        assertNull(newContract.getBeginDate(), "Default beginDate should be null");
        assertNull(newContract.getEndDate(), "Default endDate should be null");
        assertNull(newContract.getStatus(), "Default status should be null");
        assertNull(newContract.getCustomer(), "Default customer should be null");
        assertNull(newContract.getUser(), "Default user should be null");
    }

    @Test
    void contract_allArgsConstructor_shouldCreateInstanceWithAllFields() {
        // Arrange
        Long id = 1L;
        String name = "Test Contract";
        String content = "Contract content";
        BigDecimal value = new BigDecimal("1000.00");
        LocalDate beginDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 12, 31);
        Status status = Status.PROPOSED;

        // Act
        Contract newContract = new Contract(id, name, content, value, beginDate, endDate, status, testCustomer, testUser);

        // Assert
        assertNotNull(newContract, "Contract should be created");
        assertEquals(id, newContract.getId());
        assertEquals(name, newContract.getName());
        assertEquals(content, newContract.getContent());
        assertEquals(value, newContract.getValue());
        assertEquals(beginDate, newContract.getBeginDate());
        assertEquals(endDate, newContract.getEndDate());
        assertEquals(status, newContract.getStatus());
        assertEquals(testCustomer, newContract.getCustomer());
        assertEquals(testUser, newContract.getUser());
    }

    @Test
    void contract_builder_shouldCreateInstanceWithSpecifiedFields() {
        // Arrange
        String name = "Test Contract";
        BigDecimal value = new BigDecimal("2500.00");
        Status status = Status.PROPOSED;

        // Act
        Contract newContract = Contract.builder()
                .name(name)
                .value(value)
                .status(status)
                .customer(testCustomer)
                .user(testUser)
                .build();

        // Assert
        assertNotNull(newContract, "Contract should be created");
        assertEquals(name, newContract.getName());
        assertEquals(value, newContract.getValue());
        assertEquals(status, newContract.getStatus());
        assertEquals(testCustomer, newContract.getCustomer());
        assertEquals(testUser, newContract.getUser());
    }

    @Test
    void setId_withValidId_shouldSetId() {
        // Arrange
        Long expectedId = 123L;

        // Act
        contract.setId(expectedId);

        // Assert
        assertEquals(expectedId, contract.getId(), "Id should be set correctly");
    }

    @Test
    void setName_withValidName_shouldSetName() {
        // Arrange
        String expectedName = "Service Contract";

        // Act
        contract.setName(expectedName);

        // Assert
        assertEquals(expectedName, contract.getName(), "Name should be set correctly");
    }

    @Test
    void setContent_withValidContent_shouldSetContent() {
        // Arrange
        String expectedContent = "This is the contract content with terms and conditions";

        // Act
        contract.setContent(expectedContent);

        // Assert
        assertEquals(expectedContent, contract.getContent(), "Content should be set correctly");
    }

    @Test
    void setValue_withValidValue_shouldSetValue() {
        // Arrange
        BigDecimal expectedValue = new BigDecimal("5000.50");

        // Act
        contract.setValue(expectedValue);

        // Assert
        assertEquals(expectedValue, contract.getValue(), "Value should be set correctly");
    }

    @Test
    void setBeginDate_withValidDate_shouldSetBeginDate() {
        // Arrange
        LocalDate expectedDate = LocalDate.of(2023, 6, 1);

        // Act
        contract.setBeginDate(expectedDate);

        // Assert
        assertEquals(expectedDate, contract.getBeginDate(), "BeginDate should be set correctly");
    }

    @Test
    void setEndDate_withValidDate_shouldSetEndDate() {
        // Arrange
        LocalDate expectedDate = LocalDate.of(2024, 5, 31);

        // Act
        contract.setEndDate(expectedDate);

        // Assert
        assertEquals(expectedDate, contract.getEndDate(), "EndDate should be set correctly");
    }

    @Test
    void setStatus_withValidStatus_shouldSetStatus() {
        // Arrange
        Status expectedStatus = Status.DONE;

        // Act
        contract.setStatus(expectedStatus);

        // Assert
        assertEquals(expectedStatus, contract.getStatus(), "Status should be set correctly");
    }

    @Test
    void setCustomer_withValidCustomer_shouldSetCustomer() {
        // Act
        contract.setCustomer(testCustomer);

        // Assert
        assertEquals(testCustomer, contract.getCustomer(), "Customer should be set correctly");
    }

    @Test
    void setUser_withValidUser_shouldSetUser() {
        // Act
        contract.setUser(testUser);

        // Assert
        assertEquals(testUser, contract.getUser(), "User should be set correctly");
    }

    @Test
    void setValue_withZeroValue_shouldSetZeroValue() {
        // Arrange
        BigDecimal zeroValue = BigDecimal.ZERO;

        // Act
        contract.setValue(zeroValue);

        // Assert
        assertEquals(zeroValue, contract.getValue(), "Value should be set to zero");
    }

    @Test
    void setValue_withNegativeValue_shouldSetNegativeValue() {
        // Arrange
        BigDecimal negativeValue = new BigDecimal("-100.00");

        // Act
        contract.setValue(negativeValue);

        // Assert
        assertEquals(negativeValue, contract.getValue(), "Value should be set to negative value");
    }

    @Test
    void setBeginDate_afterEndDate_shouldSetDate() {
        // Arrange
        LocalDate endDate = LocalDate.of(2023, 6, 1);
        LocalDate beginDate = LocalDate.of(2023, 12, 1);
        contract.setEndDate(endDate);

        // Act
        contract.setBeginDate(beginDate);

        // Assert
        assertEquals(beginDate, contract.getBeginDate(), "BeginDate should be set even if after end date");
        assertTrue(contract.getBeginDate().isAfter(contract.getEndDate()), "BeginDate should be after EndDate");
    }

    @Test
    void setName_withNullName_shouldSetNullName() {
        // Act
        contract.setName(null);

        // Assert
        assertNull(contract.getName(), "Name should be null when set to null");
    }

    @Test
    void setContent_withNullContent_shouldSetNullContent() {
        // Act
        contract.setContent(null);

        // Assert
        assertNull(contract.getContent(), "Content should be null when set to null");
    }

    @Test
    void setValue_withNullValue_shouldSetNullValue() {
        // Act
        contract.setValue(null);

        // Assert
        assertNull(contract.getValue(), "Value should be null when set to null");
    }

    @Test
    void equals_withSameValues_shouldBeEqual() {
        // Arrange
        Contract contract1 = Contract.builder()
                .id(1L)
                .name("Test Contract")
                .value(new BigDecimal("1000.00"))
                .status(Status.PROPOSED)
                .build();

        Contract contract2 = Contract.builder()
                .id(1L)
                .name("Test Contract")
                .value(new BigDecimal("1000.00"))
                .status(Status.PROPOSED)
                .build();

        // Act & Assert
        assertEquals(contract1, contract2, "Contracts with same values should be equal");
        assertEquals(contract1.hashCode(), contract2.hashCode(), "Hash codes should be equal");
    }

    @Test
    void toString_shouldReturnStringRepresentation() {
        // Arrange
        contract.setId(1L);
        contract.setName("Test Contract");
        contract.setValue(new BigDecimal("1500.00"));

        // Act
        String result = contract.toString();

        // Assert
        assertNotNull(result, "toString should not return null");
        assertTrue(result.contains("1"), "toString should contain id");
        assertTrue(result.contains("Test Contract"), "toString should contain name");
        assertTrue(result.contains("1500.00"), "toString should contain value");
    }

    @Test
    void contractClass_shouldHaveCorrectJPAAnnotations() {
        // Assert
        assertTrue(Contract.class.isAnnotationPresent(Entity.class), "Contract should have @Entity annotation");
    }

    @Test
    void idField_shouldHaveCorrectJPAAnnotations() throws NoSuchFieldException {
        // Arrange
        var idField = Contract.class.getDeclaredField("id");

        // Assert
        assertTrue(idField.isAnnotationPresent(Id.class), "id field should have @Id annotation");
        assertTrue(idField.isAnnotationPresent(GeneratedValue.class), "id field should have @GeneratedValue annotation");

        GeneratedValue generatedValue = idField.getAnnotation(GeneratedValue.class);
        assertEquals(GenerationType.IDENTITY, generatedValue.strategy(), "GeneratedValue strategy should be IDENTITY");
    }

    @Test
    void nameField_shouldHaveCorrectJPAAnnotations() throws NoSuchFieldException {
        // Arrange
        var nameField = Contract.class.getDeclaredField("name");

        // Assert
        assertTrue(nameField.isAnnotationPresent(Column.class), "name field should have @Column annotation");

        Column column = nameField.getAnnotation(Column.class);
        assertFalse(column.nullable(), "Column should not be nullable");
        assertTrue(column.unique(), "Column should be unique");
    }

    @Test
    void beginDateField_shouldHaveCorrectAnnotations() throws NoSuchFieldException {
        // Arrange
        var beginDateField = Contract.class.getDeclaredField("beginDate");

        // Assert
        assertTrue(beginDateField.isAnnotationPresent(org.springframework.format.annotation.DateTimeFormat.class),
                "beginDate field should have @DateTimeFormat annotation");

        var dateTimeFormat = beginDateField.getAnnotation(org.springframework.format.annotation.DateTimeFormat.class);
        assertEquals(org.springframework.format.annotation.DateTimeFormat.ISO.DATE, dateTimeFormat.iso(),
                "DateTimeFormat should be ISO.DATE");
    }

    @Test
    void endDateField_shouldHaveCorrectAnnotations() throws NoSuchFieldException {
        // Arrange
        var endDateField = Contract.class.getDeclaredField("endDate");

        // Assert
        assertTrue(endDateField.isAnnotationPresent(org.springframework.format.annotation.DateTimeFormat.class),
                "endDate field should have @DateTimeFormat annotation");

        var dateTimeFormat = endDateField.getAnnotation(org.springframework.format.annotation.DateTimeFormat.class);
        assertEquals(org.springframework.format.annotation.DateTimeFormat.ISO.DATE, dateTimeFormat.iso(),
                "DateTimeFormat should be ISO.DATE");
    }

    @Test
    void statusField_shouldHaveCorrectJPAAnnotations() throws NoSuchFieldException {
        // Arrange
        var statusField = Contract.class.getDeclaredField("status");

        // Assert
        assertTrue(statusField.isAnnotationPresent(Enumerated.class), "status field should have @Enumerated annotation");

        Enumerated enumerated = statusField.getAnnotation(Enumerated.class);
        assertEquals(EnumType.STRING, enumerated.value(), "Enumerated type should be STRING");
    }

    @Test
    void customerField_shouldHaveCorrectJPAAnnotations() throws NoSuchFieldException {
        // Arrange
        var customerField = Contract.class.getDeclaredField("customer");

        // Assert
        assertTrue(customerField.isAnnotationPresent(ManyToOne.class), "customer field should have @ManyToOne annotation");
    }

    @Test
    void userField_shouldHaveCorrectJPAAnnotations() throws NoSuchFieldException {
        // Arrange
        var userField = Contract.class.getDeclaredField("user");

        // Assert
        assertTrue(userField.isAnnotationPresent(ManyToOne.class), "user field should have @ManyToOne annotation");
    }

    @Test
    void contractClass_shouldHaveLombokAnnotations() {
        // Assert
        assertTrue(Contract.class.isAnnotationPresent(lombok.Data.class), "Contract should have @Data annotation");
        assertTrue(Contract.class.isAnnotationPresent(lombok.Builder.class), "Contract should have @Builder annotation");
        assertTrue(Contract.class.isAnnotationPresent(lombok.NoArgsConstructor.class), "Contract should have @NoArgsConstructor annotation");
        assertTrue(Contract.class.isAnnotationPresent(lombok.AllArgsConstructor.class), "Contract should have @AllArgsConstructor annotation");
    }
}