package crm.service;

import crm.entity.*;
import crm.repository.ContractRepository;
import crm.repository.CustomerRepository;
import crm.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ContractServiceImplTest {

    private ContractServiceImpl contractService;

    @Mock
    private ContractRepository contractRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private UserRepository userRepository;

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
    void testFindByName() {
        String contractName = "TestContract";
        Contract contract = Contract.builder().name(contractName).build();

        when(contractRepository.findByName(contractName)).thenReturn(contract);

        Contract result = contractService.findByName(contractName);

        assertEquals(contract, result);
        verify(contractRepository).findByName(contractName);
    }

    @Test
    void testListAllContracts() {
        List<Contract> contracts = Arrays.asList(
                Contract.builder().id(1L).name("Contract1").build(),
                Contract.builder().id(2L).name("Contract2").build()
        );

        when(contractRepository.findAll()).thenReturn(contracts);

        Iterable<Contract> result = contractService.listAllContracts();

        assertNotNull(result);
        assertEquals(contracts, result);
        verify(contractRepository).findAll();
    }

    @Test
    void testShowContract() {
        Long contractId = 1L;
        Contract contract = Contract.builder().id(contractId).name("TestContract").build();

        when(contractRepository.findById(contractId)).thenReturn(Optional.of(contract));

        Contract result = contractService.showContract(contractId);

        assertEquals(contract, result);
        verify(contractRepository).findById(contractId);
    }

    @Test
    void testShowContractNotFound() {
        Long contractId = 999L;
        when(contractRepository.findById(contractId)).thenReturn(Optional.empty());

        Contract result = contractService.showContract(contractId);

        assertNull(result);
        verify(contractRepository).findById(contractId);
    }

    @Test
    void testFindAllByValueLessThanEqual() {
        BigDecimal value = BigDecimal.valueOf(10000);
        List<Contract> contracts = Arrays.asList(
                Contract.builder().id(1L).value(BigDecimal.valueOf(5000)).build()
        );

        when(contractRepository.findAllByValueLessThanEqual(value)).thenReturn(contracts);

        Iterable<Contract> result = contractService.findAllByValueLessThanEqual(value);

        assertNotNull(result);
        verify(contractRepository).findAllByValueLessThanEqual(value);
    }

    @Test
    void testFindAllByValueGreaterThanEqual() {
        BigDecimal value = BigDecimal.valueOf(50000);
        List<Contract> contracts = Arrays.asList(
                Contract.builder().id(1L).value(BigDecimal.valueOf(60000)).build()
        );

        when(contractRepository.findAllByValueGreaterThanEqual(value)).thenReturn(contracts);

        Iterable<Contract> result = contractService.findAllByValueGreaterThanEqual(value);

        assertNotNull(result);
        verify(contractRepository).findAllByValueGreaterThanEqual(value);
    }

    @Test
    void testFindAllByBeginDate() {
        LocalDate beginDate = LocalDate.of(2024, 1, 1);
        List<Contract> contracts = Arrays.asList(
                Contract.builder().id(1L).beginDate(beginDate).build()
        );

        when(contractRepository.findAllByBeginDate(beginDate)).thenReturn(contracts);

        Iterable<Contract> result = contractService.findAllByBeginDate(beginDate);

        assertNotNull(result);
        verify(contractRepository).findAllByBeginDate(beginDate);
    }

    @Test
    void testFindAllByBeginDateBefore() {
        LocalDate beforeDate = LocalDate.of(2024, 6, 1);
        List<Contract> contracts = Arrays.asList(
                Contract.builder().id(1L).beginDate(LocalDate.of(2024, 3, 1)).build()
        );

        when(contractRepository.findAllByBeginDateBefore(beforeDate)).thenReturn(contracts);

        Iterable<Contract> result = contractService.findAllByBeginDateBefore(beforeDate);

        assertNotNull(result);
        verify(contractRepository).findAllByBeginDateBefore(beforeDate);
    }

    @Test
    void testFindAllByBeginDateAfter() {
        LocalDate afterDate = LocalDate.of(2024, 1, 1);
        List<Contract> contracts = Arrays.asList(
                Contract.builder().id(1L).beginDate(LocalDate.of(2024, 6, 1)).build()
        );

        when(contractRepository.findAllByBeginDateAfter(afterDate)).thenReturn(contracts);

        Iterable<Contract> result = contractService.findAllByBeginDateAfter(afterDate);

        assertNotNull(result);
        verify(contractRepository).findAllByBeginDateAfter(afterDate);
    }

    @Test
    void testFindAllByStatus() {
        Status status = Status.DONE;
        List<Contract> contracts = Arrays.asList(
                Contract.builder().id(1L).status(status).build()
        );

        when(contractRepository.findAllByStatus(status)).thenReturn(contracts);

        Iterable<Contract> result = contractService.findAllByStatus(status);

        assertNotNull(result);
        verify(contractRepository).findAllByStatus(status);
    }

    @Test
    void testFindAllByCustomer() {
        Customer customer = Customer.builder().id(1L).name("TestCustomer").build();
        List<Contract> contracts = Arrays.asList(
                Contract.builder().id(1L).customer(customer).build()
        );

        when(contractRepository.findAllByCustomer(customer)).thenReturn(contracts);

        Iterable<Contract> result = contractService.findAllByCustomer(customer);

        assertNotNull(result);
        verify(contractRepository).findAllByCustomer(customer);
    }

    @Test
    void testFindAllByUser() {
        User user = User.builder().id(1L).username("testuser").build();
        List<Contract> contracts = Arrays.asList(
                Contract.builder().id(1L).user(user).build()
        );

        when(contractRepository.findAllByUser(user)).thenReturn(contracts);

        Iterable<Contract> result = contractService.findAllByUser(user);

        assertNotNull(result);
        verify(contractRepository).findAllByUser(user);
    }

    @Test
    void testFindAllByCustomerAndUser() {
        Customer customer = Customer.builder().id(1L).name("TestCustomer").build();
        User user = User.builder().id(1L).username("testuser").build();
        List<Contract> contracts = Arrays.asList(
                Contract.builder().id(1L).customer(customer).user(user).build()
        );

        when(contractRepository.findAllByCustomerAndUser(customer, user)).thenReturn(contracts);

        Iterable<Contract> result = contractService.findAllByCustomerAndUser(customer, user);

        assertNotNull(result);
        verify(contractRepository).findAllByCustomerAndUser(customer, user);
    }

    @Test
    void testSaveContract() {
        Contract contract = Contract.builder()
                .id(1L)
                .name("NewContract")
                .value(BigDecimal.valueOf(10000))
                .build();

        when(customerRepository.findAll()).thenReturn(Arrays.asList());
        when(userRepository.findAll()).thenReturn(Arrays.asList());

        contractService.saveContract(contract);

        verify(customerRepository).saveAll(any());
        verify(userRepository).saveAll(any());
        verify(contractRepository).save(contract);
    }
}
