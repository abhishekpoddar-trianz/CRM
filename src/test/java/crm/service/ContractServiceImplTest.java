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
import java.util.Arrays;
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
        Contract contract = new Contract();
        contract.setName("Test Contract");

        when(contractRepository.findByName("Test Contract")).thenReturn(contract);

        Contract result = contractService.findByName("Test Contract");

        assertNotNull(result);
        assertEquals("Test Contract", result.getName());
    }

    @Test
    void testListAllContracts() {
        when(contractRepository.findAll()).thenReturn(Arrays.asList());

        Iterable<Contract> result = contractService.listAllContracts();

        assertNotNull(result);
        verify(contractRepository).findAll();
    }

    @Test
    void testShowContract() {
        Contract contract = new Contract();
        contract.setId(1L);

        when(contractRepository.findById(1L)).thenReturn(Optional.of(contract));

        Contract result = contractService.showContract(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testFindAllByValueLessThanEqual() {
        BigDecimal value = new BigDecimal("1000");
        when(contractRepository.findAllByValueLessThanEqual(value)).thenReturn(Arrays.asList());

        Iterable<Contract> result = contractService.findAllByValueLessThanEqual(value);

        assertNotNull(result);
    }

    @Test
    void testFindAllByBeginDate() {
        LocalDate date = LocalDate.now();
        when(contractRepository.findAllByBeginDate(date)).thenReturn(Arrays.asList());

        Iterable<Contract> result = contractService.findAllByBeginDate(date);

        assertNotNull(result);
    }

    @Test
    void testFindAllByStatus() {
        when(contractRepository.findAllByStatus(Status.PROPOSED)).thenReturn(Arrays.asList());

        Iterable<Contract> result = contractService.findAllByStatus(Status.PROPOSED);

        assertNotNull(result);
    }

    @Test
    void testFindAllByCustomer() {
        Customer customer = new Customer();
        when(contractRepository.findAllByCustomer(customer)).thenReturn(Arrays.asList());

        Iterable<Contract> result = contractService.findAllByCustomer(customer);

        assertNotNull(result);
    }

    @Test
    void testFindAllByUser() {
        User user = new User();
        when(contractRepository.findAllByUser(user)).thenReturn(Arrays.asList());

        Iterable<Contract> result = contractService.findAllByUser(user);

        assertNotNull(result);
    }

    @Test
    void testSaveContract() {
        Contract contract = new Contract();

        when(customerRepository.findAll()).thenReturn(Arrays.asList());
        when(userRepository.findAll()).thenReturn(Arrays.asList());

        contractService.saveContract(contract);

        verify(contractRepository).save(contract);
    }
}
