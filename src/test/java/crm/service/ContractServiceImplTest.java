package crm.service;

import crm.entity.*;
import crm.repository.ContractRepository;
import crm.repository.CustomerRepository;
import crm.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContractServiceImplTest {

    @Mock
    private ContractRepository contractRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ContractServiceImpl contractService;

    private Contract contract;
    private Customer customer;
    private User user;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setId(1L);
        customer.setName("TestCustomer");

        user = new User();
        user.setId(1L);
        user.setUsername("testuser");

        contract = new Contract();
        contract.setId(1L);
        contract.setName("TestContract");
        contract.setValue(new BigDecimal("10000"));
        contract.setBeginDate(LocalDate.of(2024, 1, 1));
        contract.setEndDate(LocalDate.of(2024, 12, 31));
        contract.setStatus(Status.PROPOSED);
        contract.setCustomer(customer);
        contract.setUser(user);
    }

    @Test
    void testFindByName() {
        when(contractRepository.findByName("TestContract")).thenReturn(contract);

        Contract result = contractService.findByName("TestContract");

        assertNotNull(result);
        assertEquals("TestContract", result.getName());
        verify(contractRepository).findByName("TestContract");
    }

    @Test
    void testListAllContracts() {
        when(contractRepository.findAll()).thenReturn(Arrays.asList(contract));

        Iterable<Contract> result = contractService.listAllContracts();

        assertNotNull(result);
        assertTrue(result.iterator().hasNext());
        verify(contractRepository).findAll();
    }

    @Test
    void testShowContract() {
        when(contractRepository.findById(1L)).thenReturn(Optional.of(contract));

        Contract result = contractService.showContract(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(contractRepository).findById(1L);
    }

    @Test
    void testFindAllByValueLessThanEqual() {
        BigDecimal value = new BigDecimal("15000");
        when(contractRepository.findAllByValueLessThanEqual(value)).thenReturn(Arrays.asList(contract));

        Iterable<Contract> result = contractService.findAllByValueLessThanEqual(value);

        assertNotNull(result);
        assertTrue(result.iterator().hasNext());
        verify(contractRepository).findAllByValueLessThanEqual(value);
    }

    @Test
    void testFindAllByValueGreaterThanEqual() {
        BigDecimal value = new BigDecimal("5000");
        when(contractRepository.findAllByValueGreaterThanEqual(value)).thenReturn(Arrays.asList(contract));

        Iterable<Contract> result = contractService.findAllByValueGreaterThanEqual(value);

        assertNotNull(result);
        assertTrue(result.iterator().hasNext());
        verify(contractRepository).findAllByValueGreaterThanEqual(value);
    }

    @Test
    void testFindAllByBeginDate() {
        LocalDate beginDate = LocalDate.of(2024, 1, 1);
        when(contractRepository.findAllByBeginDate(beginDate)).thenReturn(Arrays.asList(contract));

        Iterable<Contract> result = contractService.findAllByBeginDate(beginDate);

        assertNotNull(result);
        assertTrue(result.iterator().hasNext());
        verify(contractRepository).findAllByBeginDate(beginDate);
    }

    @Test
    void testFindAllByStatus() {
        when(contractRepository.findAllByStatus(Status.PROPOSED)).thenReturn(Arrays.asList(contract));

        Iterable<Contract> result = contractService.findAllByStatus(Status.PROPOSED);

        assertNotNull(result);
        assertTrue(result.iterator().hasNext());
        verify(contractRepository).findAllByStatus(Status.PROPOSED);
    }

    @Test
    void testFindAllByCustomer() {
        when(contractRepository.findAllByCustomer(customer)).thenReturn(Arrays.asList(contract));

        Iterable<Contract> result = contractService.findAllByCustomer(customer);

        assertNotNull(result);
        assertTrue(result.iterator().hasNext());
        verify(contractRepository).findAllByCustomer(customer);
    }

    @Test
    void testFindAllByUser() {
        when(contractRepository.findAllByUser(user)).thenReturn(Arrays.asList(contract));

        Iterable<Contract> result = contractService.findAllByUser(user);

        assertNotNull(result);
        assertTrue(result.iterator().hasNext());
        verify(contractRepository).findAllByUser(user);
    }

    @Test
    void testFindAllByCustomerAndUser() {
        when(contractRepository.findAllByCustomerAndUser(customer, user)).thenReturn(Arrays.asList(contract));

        Iterable<Contract> result = contractService.findAllByCustomerAndUser(customer, user);

        assertNotNull(result);
        assertTrue(result.iterator().hasNext());
        verify(contractRepository).findAllByCustomerAndUser(customer, user);
    }

    @Test
    void testFindAllByBeginDateBefore() {
        LocalDate date = LocalDate.of(2024, 6, 1);
        when(contractRepository.findAllByBeginDateBefore(date)).thenReturn(Arrays.asList(contract));

        Iterable<Contract> result = contractService.findAllByBeginDateBefore(date);

        assertNotNull(result);
        verify(contractRepository).findAllByBeginDateBefore(date);
    }

    @Test
    void testFindAllByBeginDateAfter() {
        LocalDate date = LocalDate.of(2023, 12, 1);
        when(contractRepository.findAllByBeginDateAfter(date)).thenReturn(Arrays.asList(contract));

        Iterable<Contract> result = contractService.findAllByBeginDateAfter(date);

        assertNotNull(result);
        verify(contractRepository).findAllByBeginDateAfter(date);
    }

    @Test
    void testFindAllByEndDate() {
        LocalDate endDate = LocalDate.of(2024, 12, 31);
        when(contractRepository.findAllByEndDate(endDate)).thenReturn(Arrays.asList(contract));

        Iterable<Contract> result = contractService.findAllByEndDate(endDate);

        assertNotNull(result);
        verify(contractRepository).findAllByEndDate(endDate);
    }
}
