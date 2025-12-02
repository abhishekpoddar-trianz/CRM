package crm.service;

import crm.entity.Contract;
import crm.entity.Customer;
import crm.entity.Status;
import crm.entity.User;
import crm.repository.ContractRepository;
import crm.repository.CustomerRepository;
import crm.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ContractServiceImplTest {

    @Mock
    private ContractRepository contractRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private UserRepository userRepository;

    private ContractServiceImpl contractService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        contractService = new ContractServiceImpl(contractRepository, customerRepository, userRepository);
    }

    @Test
    public void testFindByName() {
        Contract contract = new Contract();
        contract.setName("Test Contract");
        when(contractRepository.findByName("Test Contract")).thenReturn(contract);

        Contract result = contractService.findByName("Test Contract");

        assertNotNull(result);
        assertEquals("Test Contract", result.getName());
        verify(contractRepository, times(1)).findByName("Test Contract");
    }

    @Test
    public void testListAllContracts() {
        List<Contract> contracts = new ArrayList<>();
        when(contractRepository.findAll()).thenReturn(contracts);

        Iterable<Contract> result = contractService.listAllContracts();

        assertNotNull(result);
        verify(contractRepository, times(1)).findAll();
    }

    @Test
    public void testShowContract() {
        Contract contract = new Contract();
        contract.setId(1L);
        when(contractRepository.findById(1L)).thenReturn(Optional.of(contract));

        Contract result = contractService.showContract(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(contractRepository, times(1)).findById(1L);
    }

    @Test
    public void testShowContractNotFound() {
        when(contractRepository.findById(1L)).thenReturn(Optional.empty());

        Contract result = contractService.showContract(1L);

        assertNull(result);
        verify(contractRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindAllByValueLessThanEqual() {
        BigDecimal value = new BigDecimal("1000.00");
        List<Contract> contracts = new ArrayList<>();
        when(contractRepository.findAllByValueLessThanEqual(value)).thenReturn(contracts);

        Iterable<Contract> result = contractService.findAllByValueLessThanEqual(value);

        assertNotNull(result);
        verify(contractRepository, times(1)).findAllByValueLessThanEqual(value);
    }

    @Test
    public void testFindAllByValueGreaterThanEqual() {
        BigDecimal value = new BigDecimal("5000.00");
        List<Contract> contracts = new ArrayList<>();
        when(contractRepository.findAllByValueGreaterThanEqual(value)).thenReturn(contracts);

        Iterable<Contract> result = contractService.findAllByValueGreaterThanEqual(value);

        assertNotNull(result);
        verify(contractRepository, times(1)).findAllByValueGreaterThanEqual(value);
    }

    @Test
    public void testFindAllByBeginDate() {
        LocalDate date = LocalDate.of(2024, 1, 1);
        List<Contract> contracts = new ArrayList<>();
        when(contractRepository.findAllByBeginDate(date)).thenReturn(contracts);

        Iterable<Contract> result = contractService.findAllByBeginDate(date);

        assertNotNull(result);
        verify(contractRepository, times(1)).findAllByBeginDate(date);
    }

    @Test
    public void testFindAllByStatus() {
        List<Contract> contracts = new ArrayList<>();
        when(contractRepository.findAllByStatus(Status.PROPOSED)).thenReturn(contracts);

        Iterable<Contract> result = contractService.findAllByStatus(Status.PROPOSED);

        assertNotNull(result);
        verify(contractRepository, times(1)).findAllByStatus(Status.PROPOSED);
    }

    @Test
    public void testFindAllByCustomer() {
        Customer customer = new Customer();
        customer.setId(1L);
        List<Contract> contracts = new ArrayList<>();
        when(contractRepository.findAllByCustomer(customer)).thenReturn(contracts);

        Iterable<Contract> result = contractService.findAllByCustomer(customer);

        assertNotNull(result);
        verify(contractRepository, times(1)).findAllByCustomer(customer);
    }

    @Test
    public void testFindAllByUser() {
        User user = new User();
        user.setId(1L);
        List<Contract> contracts = new ArrayList<>();
        when(contractRepository.findAllByUser(user)).thenReturn(contracts);

        Iterable<Contract> result = contractService.findAllByUser(user);

        assertNotNull(result);
        verify(contractRepository, times(1)).findAllByUser(user);
    }

    @Test
    public void testFindAllByEndDateBefore() {
        LocalDate date = LocalDate.of(2024, 12, 31);
        List<Contract> contracts = new ArrayList<>();
        when(contractRepository.findAllByEndDateBefore(date)).thenReturn(contracts);

        Iterable<Contract> result = contractService.findAllByEndDateBefore(date);

        assertNotNull(result);
        verify(contractRepository, times(1)).findAllByEndDateBefore(date);
    }
}
