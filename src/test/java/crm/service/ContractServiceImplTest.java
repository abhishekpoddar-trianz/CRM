package crm.service;

import crm.entity.Contract;
import crm.entity.Customer;
import crm.entity.Status;
import crm.entity.User;
import crm.repository.ContractRepository;
import crm.repository.CustomerRepository;
import crm.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ContractServiceImplTest {

    @Mock
    private ContractRepository contractRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private UserRepository userRepository;

    private ContractServiceImpl contractService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        contractService = new ContractServiceImpl(contractRepository, customerRepository, userRepository);
    }

    @Test
    void testConstructor() {
        assertNotNull(contractService);
    }

    @Test
    void testConstructorWithValidParameters() {
        ContractServiceImpl service = new ContractServiceImpl(contractRepository, customerRepository, userRepository);
        assertNotNull(service);
    }

    @Test
    void testFindByName() {
        Contract mockContract = new Contract();
        mockContract.setName("Test Contract");

        when(contractRepository.findByName("Test Contract")).thenReturn(mockContract);

        Contract result = contractService.findByName("Test Contract");

        assertEquals(mockContract, result);
        verify(contractRepository, times(1)).findByName("Test Contract");
    }

    @Test
    void testListAllContracts() {
        List<Contract> mockContracts = new ArrayList<>();
        mockContracts.add(new Contract());

        when(contractRepository.findAll()).thenReturn(mockContracts);

        Iterable<Contract> result = contractService.listAllContracts();

        assertEquals(mockContracts, result);
        verify(contractRepository, times(1)).findAll();
    }

    @Test
    void testShowContract() {
        Contract mockContract = new Contract();
        mockContract.setId(1L);

        when(contractRepository.findOne(1L)).thenReturn(mockContract);

        Contract result = contractService.showContract(1L);

        assertEquals(mockContract, result);
        verify(contractRepository, times(1)).findOne(1L);
    }

    @Test
    void testFindAllByValueLessThanEqual() {
        BigDecimal value = new BigDecimal("1000.00");
        List<Contract> mockContracts = new ArrayList<>();

        when(contractRepository.findAllByValueLessThanEqual(value)).thenReturn(mockContracts);

        Iterable<Contract> result = contractService.findAllByValueLessThanEqual(value);

        assertEquals(mockContracts, result);
        verify(contractRepository, times(1)).findAllByValueLessThanEqual(value);
    }

    @Test
    void testFindAllByValueGreaterThanEqual() {
        BigDecimal value = new BigDecimal("1000.00");
        List<Contract> mockContracts = new ArrayList<>();

        when(contractRepository.findAllByValueGreaterThanEqual(value)).thenReturn(mockContracts);

        Iterable<Contract> result = contractService.findAllByValueGreaterThanEqual(value);

        assertEquals(mockContracts, result);
        verify(contractRepository, times(1)).findAllByValueGreaterThanEqual(value);
    }

    @Test
    void testFindAllByBeginDate() {
        LocalDate beginDate = LocalDate.of(2023, 1, 1);
        List<Contract> mockContracts = new ArrayList<>();

        when(contractRepository.findAllByBeginDate(beginDate)).thenReturn(mockContracts);

        Iterable<Contract> result = contractService.findAllByBeginDate(beginDate);

        assertEquals(mockContracts, result);
        verify(contractRepository, times(1)).findAllByBeginDate(beginDate);
    }

    @Test
    void testFindAllByBeginDateBefore() {
        LocalDate beforeDate = LocalDate.of(2023, 6, 1);
        List<Contract> mockContracts = new ArrayList<>();

        when(contractRepository.findAllByBeginDateBefore(beforeDate)).thenReturn(mockContracts);

        Iterable<Contract> result = contractService.findAllByBeginDateBefore(beforeDate);

        assertEquals(mockContracts, result);
        verify(contractRepository, times(1)).findAllByBeginDateBefore(beforeDate);
    }

    @Test
    void testFindAllByBeginDateAfter() {
        LocalDate afterDate = LocalDate.of(2023, 1, 1);
        List<Contract> mockContracts = new ArrayList<>();

        when(contractRepository.findAllByBeginDateAfter(afterDate)).thenReturn(mockContracts);

        Iterable<Contract> result = contractService.findAllByBeginDateAfter(afterDate);

        assertEquals(mockContracts, result);
        verify(contractRepository, times(1)).findAllByBeginDateAfter(afterDate);
    }

    @Test
    void testFindAllByEndDate() {
        LocalDate endDate = LocalDate.of(2023, 12, 31);
        List<Contract> mockContracts = new ArrayList<>();

        when(contractRepository.findAllByEndDate(endDate)).thenReturn(mockContracts);

        Iterable<Contract> result = contractService.findAllByEndDate(endDate);

        assertEquals(mockContracts, result);
        verify(contractRepository, times(1)).findAllByEndDate(endDate);
    }

    @Test
    void testFindAllByEndDateBefore() {
        LocalDate beforeDate = LocalDate.of(2023, 12, 1);
        List<Contract> mockContracts = new ArrayList<>();

        when(contractRepository.findAllByEndDateBefore(beforeDate)).thenReturn(mockContracts);

        Iterable<Contract> result = contractService.findAllByEndDateBefore(beforeDate);

        assertEquals(mockContracts, result);
        verify(contractRepository, times(1)).findAllByEndDateBefore(beforeDate);
    }

    @Test
    void testFindAllByEndDateAfter() {
        LocalDate afterDate = LocalDate.of(2023, 6, 1);
        List<Contract> mockContracts = new ArrayList<>();

        when(contractRepository.findAllByEndDateAfter(afterDate)).thenReturn(mockContracts);

        Iterable<Contract> result = contractService.findAllByEndDateAfter(afterDate);

        assertEquals(mockContracts, result);
        verify(contractRepository, times(1)).findAllByEndDateAfter(afterDate);
    }

    @Test
    void testFindAllByStatus() {
        Status status = Status.PROPOSED;
        List<Contract> mockContracts = new ArrayList<>();

        when(contractRepository.findAllByStatus(status)).thenReturn(mockContracts);

        Iterable<Contract> result = contractService.findAllByStatus(status);

        assertEquals(mockContracts, result);
        verify(contractRepository, times(1)).findAllByStatus(status);
    }

    @Test
    void testFindAllByCustomer() {
        Customer customer = new Customer();
        customer.setId(1L);
        List<Contract> mockContracts = new ArrayList<>();

        when(contractRepository.findAllByCustomer(customer)).thenReturn(mockContracts);

        Iterable<Contract> result = contractService.findAllByCustomer(customer);

        assertEquals(mockContracts, result);
        verify(contractRepository, times(1)).findAllByCustomer(customer);
    }

    @Test
    void testFindAllByUser() {
        User user = new User();
        user.setId(1L);
        List<Contract> mockContracts = new ArrayList<>();

        when(contractRepository.findAllByUser(user)).thenReturn(mockContracts);

        Iterable<Contract> result = contractService.findAllByUser(user);

        assertEquals(mockContracts, result);
        verify(contractRepository, times(1)).findAllByUser(user);
    }

    @Test
    void testFindAllByCustomerAndUser() {
        Customer customer = new Customer();
        customer.setId(1L);
        User user = new User();
        user.setId(1L);
        List<Contract> mockContracts = new ArrayList<>();

        when(contractRepository.findAllByCustomerAndUser(customer, user)).thenReturn(mockContracts);

        Iterable<Contract> result = contractService.findAllByCustomerAndUser(customer, user);

        assertEquals(mockContracts, result);
        verify(contractRepository, times(1)).findAllByCustomerAndUser(customer, user);
    }

    @Test
    void testSaveContract() {
        Contract contract = new Contract();
        contract.setName("New Contract");

        List<Customer> customers = new ArrayList<>();
        List<User> users = new ArrayList<>();

        when(customerRepository.findAll()).thenReturn(customers);
        when(userRepository.findAll()).thenReturn(users);

        contractService.saveContract(contract);

        verify(customerRepository, times(1)).findAll();
        verify(userRepository, times(1)).findAll();
        verify(customerRepository, times(1)).save(customers);
        verify(userRepository, times(1)).save(users);
        verify(contractRepository, times(1)).save(contract);
    }

    @Test
    void testSaveContractWithNullContract() {
        List<Customer> customers = new ArrayList<>();
        List<User> users = new ArrayList<>();

        when(customerRepository.findAll()).thenReturn(customers);
        when(userRepository.findAll()).thenReturn(users);

        contractService.saveContract(null);

        verify(customerRepository, times(1)).findAll();
        verify(userRepository, times(1)).findAll();
        verify(customerRepository, times(1)).save(customers);
        verify(userRepository, times(1)).save(users);
        verify(contractRepository, times(1)).save(null);
    }
}