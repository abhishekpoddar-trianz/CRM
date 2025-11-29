package crm.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        testCustomer.setEmail("customer@test.com");

        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testuser");
        testUser.setEmail("user@test.com");
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
        BigDecimal value = new BigDecimal("10000.00");
        LocalDate beginDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 12, 31);
        Status status = Status.ACTIVE;

        // Act
        Contract newContract = new Contract(id, name, content, value, beginDate, endDate, status, testCustomer, testUser);

        // Assert
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
    void testBuilderPattern() {
        // Arrange
        BigDecimal value = new BigDecimal("25000.50");
        LocalDate beginDate = LocalDate.of(2023, 6, 1);
        LocalDate endDate = LocalDate.of(2024, 5, 31);

        // Act
        Contract builtContract = Contract.builder()
                .id(1L)
                .name("Builder Contract")
                .content("Built contract content")
                .value(value)
                .beginDate(beginDate)
                .endDate(endDate)
                .status(Status.PENDING)
                .customer(testCustomer)
                .user(testUser)
                .build();

        // Assert
        assertEquals(1L, builtContract.getId());
        assertEquals("Builder Contract", builtContract.getName());
        assertEquals("Built contract content", builtContract.getContent());
        assertEquals(value, builtContract.getValue());
        assertEquals(beginDate, builtContract.getBeginDate());
        assertEquals(endDate, builtContract.getEndDate());
        assertEquals(Status.PENDING, builtContract.getStatus());
        assertEquals(testCustomer, builtContract.getCustomer());
        assertEquals(testUser, builtContract.getUser());
    }

    @Test
    void testSetAndGetId() {
        // Arrange
        Long expectedId = 100L;

        // Act
        contract.setId(expectedId);

        // Assert
        assertEquals(expectedId, contract.getId());
    }

    @Test
    void testSetAndGetName() {
        // Arrange
        String expectedName = "Service Agreement";

        // Act
        contract.setName(expectedName);

        // Assert
        assertEquals(expectedName, contract.getName());
    }

    @Test
    void testSetAndGetContent() {
        // Arrange
        String expectedContent = "This is the contract content describing terms and conditions.";

        // Act
        contract.setContent(expectedContent);

        // Assert
        assertEquals(expectedContent, contract.getContent());
    }

    @Test
    void testSetAndGetValue() {
        // Arrange
        BigDecimal expectedValue = new BigDecimal("15000.75");

        // Act
        contract.setValue(expectedValue);

        // Assert
        assertEquals(expectedValue, contract.getValue());
    }

    @Test
    void testSetAndGetBeginDate() {
        // Arrange
        LocalDate expectedBeginDate = LocalDate.of(2023, 3, 15);

        // Act
        contract.setBeginDate(expectedBeginDate);

        // Assert
        assertEquals(expectedBeginDate, contract.getBeginDate());
    }

    @Test
    void testSetAndGetEndDate() {
        // Arrange
        LocalDate expectedEndDate = LocalDate.of(2024, 3, 14);

        // Act
        contract.setEndDate(expectedEndDate);

        // Assert
        assertEquals(expectedEndDate, contract.getEndDate());
    }

    @Test
    void testSetAndGetStatus() {
        // Test all Status enum values
        Status[] statuses = Status.values();

        for (Status status : statuses) {
            // Act
            contract.setStatus(status);

            // Assert
            assertEquals(status, contract.getStatus());
        }
    }

    @Test
    void testSetAndGetCustomer() {
        // Act
        contract.setCustomer(testCustomer);

        // Assert
        assertEquals(testCustomer, contract.getCustomer());
        assertEquals(1L, contract.getCustomer().getId());
        assertEquals("Test Customer", contract.getCustomer().getName());
    }

    @Test
    void testSetAndGetUser() {
        // Act
        contract.setUser(testUser);

        // Assert
        assertEquals(testUser, contract.getUser());
        assertEquals(1L, contract.getUser().getId());
        assertEquals("testuser", contract.getUser().getUsername());
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
    void testValuePrecision() {
        // Arrange
        BigDecimal[] testValues = {
            new BigDecimal("0.00"),
            new BigDecimal("0.01"),
            new BigDecimal("1000000.99"),
            new BigDecimal("999999999.99")
        };

        for (BigDecimal value : testValues) {
            // Act
            contract.setValue(value);

            // Assert
            assertEquals(value, contract.getValue());
        }
    }

    @Test
    void testDateRanges() {
        // Arrange
        LocalDate beginDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 12, 31);

        // Act
        contract.setBeginDate(beginDate);
        contract.setEndDate(endDate);

        // Assert
        assertEquals(beginDate, contract.getBeginDate());
        assertEquals(endDate, contract.getEndDate());
        assertTrue(contract.getBeginDate().isBefore(contract.getEndDate()));
    }

    @Test
    void testDatesBoundaryValues() {
        // Test with extreme date values
        LocalDate minDate = LocalDate.MIN;
        LocalDate maxDate = LocalDate.MAX;

        // Act
        contract.setBeginDate(minDate);
        contract.setEndDate(maxDate);

        // Assert
        assertEquals(minDate, contract.getBeginDate());
        assertEquals(maxDate, contract.getEndDate());
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        BigDecimal value = new BigDecimal("10000.00");
        LocalDate beginDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 12, 31);

        Contract contract1 = Contract.builder()
                .id(1L)
                .name("Test Contract")
                .content("Test content")
                .value(value)
                .beginDate(beginDate)
                .endDate(endDate)
                .status(Status.ACTIVE)
                .build();

        Contract contract2 = Contract.builder()
                .id(1L)
                .name("Test Contract")
                .content("Test content")
                .value(value)
                .beginDate(beginDate)
                .endDate(endDate)
                .status(Status.ACTIVE)
                .build();

        // Act & Assert
        assertEquals(contract1, contract2);
        assertEquals(contract1.hashCode(), contract2.hashCode());
    }

    @Test
    void testToString() {
        // Arrange
        contract.setId(1L);
        contract.setName("Test Contract");
        contract.setContent("Contract content");
        contract.setValue(new BigDecimal("5000.00"));
        contract.setStatus(Status.ACTIVE);

        // Act
        String toString = contract.toString();

        // Assert
        assertNotNull(toString);
        assertTrue(toString.contains("id"));
        assertTrue(toString.contains("name"));
        assertTrue(toString.contains("content"));
        assertTrue(toString.contains("value"));
        assertTrue(toString.contains("status"));
    }

    @Test
    void testStatusEnumValues() {
        // Verify that all Status enum values can be set
        for (Status status : Status.values()) {
            // Act
            contract.setStatus(status);

            // Assert
            assertEquals(status, contract.getStatus());
            assertNotNull(status.name());
        }
    }

    @Test
    void testBigDecimalComparisons() {
        // Arrange
        BigDecimal value1 = new BigDecimal("1000.00");
        BigDecimal value2 = new BigDecimal("1000.0000");

        // Act
        contract.setValue(value1);

        // Assert
        assertEquals(0, contract.getValue().compareTo(value2));
    }

    @Test
    void testContractNameWithSpecialCharacters() {
        // Arrange
        String[] specialNames = {
            "Contract #123", "Service-Agreement_2023", "Contract (Updated)",
            "Annual-Service.Contract", "Contract: Special Terms"
        };

        for (String name : specialNames) {
            // Act
            contract.setName(name);

            // Assert
            assertEquals(name, contract.getName());
        }
    }

    @Test
    void testContractContentLengths() {
        // Test with various content lengths
        String shortContent = "Short";
        String mediumContent = "A".repeat(1000);
        String longContent = "B".repeat(10000);

        // Test short content
        contract.setContent(shortContent);
        assertEquals(shortContent, contract.getContent());

        // Test medium content
        contract.setContent(mediumContent);
        assertEquals(mediumContent, contract.getContent());

        // Test long content
        contract.setContent(longContent);
        assertEquals(longContent, contract.getContent());
    }

    @Test
    void testCompleteContractSetup() {
        // Arrange & Act - Set all properties
        contract.setId(1L);
        contract.setName("Complete Contract");
        contract.setContent("Complete contract with all fields set");
        contract.setValue(new BigDecimal("50000.00"));
        contract.setBeginDate(LocalDate.of(2023, 1, 1));
        contract.setEndDate(LocalDate.of(2024, 1, 1));
        contract.setStatus(Status.ACTIVE);
        contract.setCustomer(testCustomer);
        contract.setUser(testUser);

        // Assert - Verify all properties are set correctly
        assertEquals(1L, contract.getId());
        assertEquals("Complete Contract", contract.getName());
        assertEquals("Complete contract with all fields set", contract.getContent());
        assertEquals(new BigDecimal("50000.00"), contract.getValue());
        assertEquals(LocalDate.of(2023, 1, 1), contract.getBeginDate());
        assertEquals(LocalDate.of(2024, 1, 1), contract.getEndDate());
        assertEquals(Status.ACTIVE, contract.getStatus());
        assertNotNull(contract.getCustomer());
        assertNotNull(contract.getUser());
    }
}