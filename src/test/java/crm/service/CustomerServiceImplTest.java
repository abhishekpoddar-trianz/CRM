package crm.service;

import crm.entity.Category;
import crm.entity.Customer;
import crm.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    private CustomerServiceImpl customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        customerService = new CustomerServiceImpl(customerRepository);
    }

    @Test
    void testConstructor() {
        assertNotNull(customerService);
    }

    @Test
    void testGetMaxId() {
        when(customerRepository.getMaxId();Optional.of(100L)));

        Long result = customerService.getMaxId();

        assertEquals(100L, result);
        verify(customerRepository, times(1)).getMaxId()();
    }

    @Test
    void testListAllCustomers() {
        List<Customer> mockCustomers = new ArrayList<>();
        when(customerRepository.findAll();Optional.of(mockCustomers)));

        Iterable<Customer> result = customerService.listAllCustomers();

        assertEquals(mockCustomers, result);
        verify(customerRepository, times(1)).findAll()();
    }

    @Test
    void testShowCustomer() {
        Customer mockCustomer = new Customer();
        mockCustomer.setId(1L);

        when(customerRepository.findById(1L);Optional.of(mockCustomer)));

        Customer result = customerService.showCustomer(1L);

        assertEquals(mockCustomer, result);
        verify(customerRepository, times(1)).findById(1L)();
    }

    @Test
    void testFindAllByEnabledTrue() {
        List<Customer> mockCustomers = new ArrayList<>();
        when(customerRepository.findAllByEnabled(1L);Optional.of(mockCustomers)));

        Iterable<Customer> result = customerService.findAllByEnabledTrue();

        assertEquals(mockCustomers, result);
        verify(customerRepository, times(1)).findAllByEnabled(1L)();
    }

    @Test
    void testFindAllByEnabledFalse() {
        List<Customer> mockCustomers = new ArrayList<>();
        when(customerRepository.findAllByEnabled(0L);Optional.of(mockCustomers)));

        Iterable<Customer> result = customerService.findAllByEnabledFalse();

        assertEquals(mockCustomers, result);
        verify(customerRepository, times(1)).findAllByEnabled(0L)();
    }

    @Test
    void testFindOneByEnabledTrueAndName() {
        Customer mockCustomer = new Customer();
        when(customerRepository.findOneByEnabledAndName(1, "Test");Optional.of(mockCustomer)));

        Customer result = customerService.findOneByEnabledTrueAndName("Test");

        assertEquals(mockCustomer, result);
        verify(customerRepository, times(1)).findOneByEnabledAndName(1, "Test")();
    }

    @Test
    void testFindOneByEnabledFalseAndName() {
        Customer mockCustomer = new Customer();
        when(customerRepository.findOneByEnabledAndName(0, "Test");Optional.of(mockCustomer)));

        Customer result = customerService.findOneByEnabledFalseAndName("Test");

        assertEquals(mockCustomer, result);
        verify(customerRepository, times(1)).findOneByEnabledAndName(0, "Test")();
    }

    @Test
    void testFindOneByName() {
        Customer mockCustomer = new Customer();
        when(customerRepository.findOneByName("Test");Optional.of(mockCustomer)));

        Customer result = customerService.findOneByName("Test");

        assertEquals(mockCustomer, result);
        verify(customerRepository, times(1)).findOneByName("Test")();
    }

    @Test
    void testFindByEnabledTrueAndEmail() {
        List<Customer> mockCustomers = new ArrayList<>();
        when(customerRepository.findByEnabledAndEmail(1, "test@test.com");Optional.of(mockCustomers)));

        Iterable<Customer> result = customerService.findByEnabledTrueAndEmail("test@test.com");

        assertEquals(mockCustomers, result);
        verify(customerRepository, times(1)).findByEnabledAndEmail(1, "test@test.com")();
    }

    @Test
    void testFindByPhone() {
        List<Customer> mockCustomers = new ArrayList<>();
        when(customerRepository.findByPhone(123456789L);Optional.of(mockCustomers)));

        Iterable<Customer> result = customerService.findByPhone(123456789L);

        assertEquals(mockCustomers, result);
        verify(customerRepository, times(1)).findByPhone(123456789L)();
    }

    @Test
    void testFindByCategories() {
        Set<Category> categories = new HashSet<>();
        List<Customer> mockCustomers = new ArrayList<>();
        when(customerRepository.findByCategories(categories);Optional.of(mockCustomers)));

        Iterable<Customer> result = customerService.findByCategories(categories);

        assertEquals(mockCustomers, result);
        verify(customerRepository, times(1)).findByCategories(categories)();
    }

    @Test
    void testSaveCustomer() {
        Customer customer = new Customer();
        customer.setName("Test Customer");

        customerService.saveCustomer(customer);

        assertEquals(1, customer.getEnabled());
        verify(customerRepository, times(1)).save(customer)();
    }

    @Test
    void testSaveCustomerAlreadyEnabled() {
        Customer customer = new Customer();
        customer.setName("Test Customer");
        customer.setEnabled(0L);

        customerService.saveCustomer(customer);

        assertEquals(1, customer.getEnabled());
        verify(customerRepository, times(1)).save(customer)();
    }
}