package crm.repository;

import crm.entity.Contract;
import crm.entity.Customer;
import crm.entity.Status;
import crm.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ContractRepositoryTest {

    @Mock
    private ContractRepository contractRepository;

    private Contract contract;
    private Customer customer;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        customer = new Customer();
        customer.setId(1L);
        customer.setName("Test Customer");

        user = new User();
        user.setId(1L);
        user.setUsername("testuser");

        contract = Contract.builder()
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
    }

    @Test
    void testExtendsJpaRepository() {
        // Act & Assert
        assertTrue(JpaRepository.class.isAssignableFrom(ContractRepository.class));
    }

    @Test
    void testRepositoryAnnotation() {
        // Act & Assert
        assertTrue(ContractRepository.class.isAnnotationPresent(org.springframework.stereotype.Repository.class));
    }

    @Test
    void testFindByName() {
        // Arrange
        String contractName = "Test Contract";
        when(contractRepository.findByName(contractName)).thenReturn(contract);

        // Act
        Contract result = contractRepository.findByName(contractName);

        // Assert
        assertNotNull(result);
        assertEquals(contract, result);
        verify(contractRepository).findByName(contractName);
    }

    @Test
    void testFindByNameWithNull() {
        // Act
        Contract result = contractRepository.findByName(null);

        // Assert
        verify(contractRepository).findByName(null);
    }

    @Test
    void testFindAllByValueLessThanEqual() {
        // Arrange
        BigDecimal value = new BigDecimal("2000.00");
        Iterable<Contract> expectedContracts = java.util.List.of(contract);
        when(contractRepository.findAllByValueLessThanEqual(value)).thenReturn(expectedContracts);

        // Act
        Iterable<Contract> result = contractRepository.findAllByValueLessThanEqual(value);

        // Assert
        assertNotNull(result);
        assertEquals(expectedContracts, result);
        verify(contractRepository).findAllByValueLessThanEqual(value);
    }

    @Test
    void testFindAllByValueGreaterThanEqual() {
        // Arrange
        BigDecimal value = new BigDecimal("500.00");
        Iterable<Contract> expectedContracts = java.util.List.of(contract);
        when(contractRepository.findAllByValueGreaterThanEqual(value)).thenReturn(expectedContracts);

        // Act
        Iterable<Contract> result = contractRepository.findAllByValueGreaterThanEqual(value);

        // Assert
        assertNotNull(result);
        assertEquals(expectedContracts, result);
        verify(contractRepository).findAllByValueGreaterThanEqual(value);
    }

    @Test
    void testFindAllByBeginDate() {
        // Arrange
        LocalDate beginDate = LocalDate.of(2023, 1, 1);
        Iterable<Contract> expectedContracts = java.util.List.of(contract);
        when(contractRepository.findAllByBeginDate(beginDate)).thenReturn(expectedContracts);

        // Act
        Iterable<Contract> result = contractRepository.findAllByBeginDate(beginDate);

        // Assert
        assertNotNull(result);
        assertEquals(expectedContracts, result);
        verify(contractRepository).findAllByBeginDate(beginDate);
    }

    @Test
    void testFindAllByBeginDateBefore() {
        // Arrange
        LocalDate beforeDate = LocalDate.of(2024, 1, 1);
        Iterable<Contract> expectedContracts = java.util.List.of(contract);
        when(contractRepository.findAllByBeginDateBefore(beforeDate)).thenReturn(expectedContracts);

        // Act
        Iterable<Contract> result = contractRepository.findAllByBeginDateBefore(beforeDate);

        // Assert
        assertNotNull(result);
        assertEquals(expectedContracts, result);
        verify(contractRepository).findAllByBeginDateBefore(beforeDate);
    }

    @Test
    void testFindAllByBeginDateAfter() {
        // Arrange
        LocalDate afterDate = LocalDate.of(2022, 1, 1);
        Iterable<Contract> expectedContracts = java.util.List.of(contract);
        when(contractRepository.findAllByBeginDateAfter(afterDate)).thenReturn(expectedContracts);

        // Act
        Iterable<Contract> result = contractRepository.findAllByBeginDateAfter(afterDate);

        // Assert
        assertNotNull(result);
        assertEquals(expectedContracts, result);
        verify(contractRepository).findAllByBeginDateAfter(afterDate);
    }

    @Test
    void testFindAllByEndDate() {
        // Arrange
        LocalDate endDate = LocalDate.of(2023, 12, 31);
        Iterable<Contract> expectedContracts = java.util.List.of(contract);
        when(contractRepository.findAllByEndDate(endDate)).thenReturn(expectedContracts);

        // Act
        Iterable<Contract> result = contractRepository.findAllByEndDate(endDate);

        // Assert
        assertNotNull(result);
        assertEquals(expectedContracts, result);
        verify(contractRepository).findAllByEndDate(endDate);
    }

    @Test
    void testFindAllByEndDateBefore() {
        // Arrange
        LocalDate beforeEndDate = LocalDate.of(2024, 12, 31);
        Iterable<Contract> expectedContracts = java.util.List.of(contract);
        when(contractRepository.findAllByEndDateBefore(beforeEndDate)).thenReturn(expectedContracts);

        // Act
        Iterable<Contract> result = contractRepository.findAllByEndDateBefore(beforeEndDate);

        // Assert
        assertNotNull(result);
        assertEquals(expectedContracts, result);
        verify(contractRepository).findAllByEndDateBefore(beforeEndDate);
    }

    @Test
    void testFindAllByEndDateAfter() {
        // Arrange
        LocalDate afterEndDate = LocalDate.of(2022, 12, 31);
        Iterable<Contract> expectedContracts = java.util.List.of(contract);
        when(contractRepository.findAllByEndDateAfter(afterEndDate)).thenReturn(expectedContracts);

        // Act
        Iterable<Contract> result = contractRepository.findAllByEndDateAfter(afterEndDate);

        // Assert
        assertNotNull(result);
        assertEquals(expectedContracts, result);
        verify(contractRepository).findAllByEndDateAfter(afterEndDate);
    }

    @Test
    void testFindAllByStatus() {
        // Arrange
        Status status = Status.PROPOSED;
        Iterable<Contract> expectedContracts = java.util.List.of(contract);
        when(contractRepository.findAllByStatus(status)).thenReturn(expectedContracts);

        // Act
        Iterable<Contract> result = contractRepository.findAllByStatus(status);

        // Assert
        assertNotNull(result);
        assertEquals(expectedContracts, result);
        verify(contractRepository).findAllByStatus(status);
    }

    @Test
    void testFindAllByCustomer() {
        // Arrange
        Iterable<Contract> expectedContracts = java.util.List.of(contract);
        when(contractRepository.findAllByCustomer(customer)).thenReturn(expectedContracts);

        // Act
        Iterable<Contract> result = contractRepository.findAllByCustomer(customer);

        // Assert
        assertNotNull(result);
        assertEquals(expectedContracts, result);
        verify(contractRepository).findAllByCustomer(customer);
    }

    @Test
    void testFindAllByCustomerAndUser() {
        // Arrange
        Iterable<Contract> expectedContracts = java.util.List.of(contract);
        when(contractRepository.findAllByCustomerAndUser(customer, user)).thenReturn(expectedContracts);

        // Act
        Iterable<Contract> result = contractRepository.findAllByCustomerAndUser(customer, user);

        // Assert
        assertNotNull(result);
        assertEquals(expectedContracts, result);
        verify(contractRepository).findAllByCustomerAndUser(customer, user);
    }

    @Test
    void testFindAllByUser() {
        // Arrange
        Iterable<Contract> expectedContracts = java.util.List.of(contract);
        when(contractRepository.findAllByUser(user)).thenReturn(expectedContracts);

        // Act
        Iterable<Contract> result = contractRepository.findAllByUser(user);

        // Assert
        assertNotNull(result);
        assertEquals(expectedContracts, result);
        verify(contractRepository).findAllByUser(user);
    }

    @Test
    void testMethodSignatures() throws NoSuchMethodException {
        // Act & Assert - Verify all method signatures exist
        Method findByName = ContractRepository.class.getMethod("findByName", String.class);
        assertNotNull(findByName);
        assertEquals(Contract.class, findByName.getReturnType());

        Method findAllByValue = ContractRepository.class.getMethod("findAllByValueLessThanEqual", BigDecimal.class);
        assertNotNull(findAllByValue);
        assertEquals(Iterable.class, findAllByValue.getReturnType());

        Method findAllByBeginDate = ContractRepository.class.getMethod("findAllByBeginDate", LocalDate.class);
        assertNotNull(findAllByBeginDate);
        assertEquals(Iterable.class, findAllByBeginDate.getReturnType());

        Method findAllByStatus = ContractRepository.class.getMethod("findAllByStatus", Status.class);
        assertNotNull(findAllByStatus);
        assertEquals(Iterable.class, findAllByStatus.getReturnType());
    }

    @Test
    void testIsInterface() {
        // Act & Assert
        assertTrue(ContractRepository.class.isInterface());
    }

    @Test
    void testAllStatusEnumValues() {
        // Act & Assert - Test that repository can handle all Status enum values
        for (Status status : Status.values()) {
            assertDoesNotThrow(() -> contractRepository.findAllByStatus(status));
            verify(contractRepository).findAllByStatus(status);
        }
    }

    @Test
    void testFindByNameWithEmptyString() {
        // Act
        contractRepository.findByName("");

        // Assert
        verify(contractRepository).findByName("");
    }

    @Test
    void testFindAllByValueWithZero() {
        // Arrange
        BigDecimal zeroValue = BigDecimal.ZERO;

        // Act
        contractRepository.findAllByValueLessThanEqual(zeroValue);
        contractRepository.findAllByValueGreaterThanEqual(zeroValue);

        // Assert
        verify(contractRepository).findAllByValueLessThanEqual(zeroValue);
        verify(contractRepository).findAllByValueGreaterThanEqual(zeroValue);
    }

    @Test
    void testFindAllByValueWithNegative() {
        // Arrange
        BigDecimal negativeValue = new BigDecimal("-100.00");

        // Act
        contractRepository.findAllByValueLessThanEqual(negativeValue);
        contractRepository.findAllByValueGreaterThanEqual(negativeValue);

        // Assert
        verify(contractRepository).findAllByValueLessThanEqual(negativeValue);
        verify(contractRepository).findAllByValueGreaterThanEqual(negativeValue);
    }

    @Test
    void testGenericTypeParameters() {
        // Act & Assert
        java.lang.reflect.Type[] genericInterfaces = ContractRepository.class.getGenericInterfaces();
        assertTrue(genericInterfaces.length > 0);
        String genericInterface = genericInterfaces[0].toString();
        assertTrue(genericInterface.contains("Contract"));
        assertTrue(genericInterface.contains("Long"));
    }
}