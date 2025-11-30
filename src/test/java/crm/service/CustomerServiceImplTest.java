package crm.service;

import crm.entity.Customer;
import crm.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerServiceImplTest {

    private CustomerServiceImpl customerService;

    @Mock
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        customerService = new CustomerServiceImpl(customerRepository);
    }

    @Test
    void testConstructor() {
        CustomerServiceImpl service = new CustomerServiceImpl(customerRepository);
        assertNotNull(service);
    }

    @Test
    void testGetMaxId() {
        when(customerRepository.getMaxId()).thenReturn(10L);

        Long result = customerService.getMaxId();

        assertEquals(10L, result);
        verify(customerRepository, times(1)).getMaxId();
    }

    @Test
    void testListAllCustomers() {
        when(customerRepository.findAll()).thenReturn(Arrays.asList(new Customer(), new Customer()));

        Iterable<Customer> result = customerService.listAllCustomers();

        assertNotNull(result);
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    void testShowCustomer() {
        Customer customer = new Customer();
        customer.setId(1L);

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        Customer result = customerService.showCustomer(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testFindAllByEnabledTrue() {
        when(customerRepository.findAllByEnabled(1)).thenReturn(Arrays.asList());

        Iterable<Customer> result = customerService.findAllByEnabledTrue();

        assertNotNull(result);
        verify(customerRepository).findAllByEnabled(1);
    }

    @Test
    void testSaveCustomer() {
        Customer customer = new Customer();
        customer.setName("Test");

        customerService.saveCustomer(customer);

        assertEquals(1, customer.getEnabled());
        verify(customerRepository).save(customer);
    }

    @Test
    void testFindOneByEnabledTrueAndName() {
        Customer customer = new Customer();
        customer.setName("Test");

        when(customerRepository.findOneByEnabledAndName(1, "Test")).thenReturn(customer);

        Customer result = customerService.findOneByEnabledTrueAndName("Test");

        assertNotNull(result);
        assertEquals("Test", result.getName());
    }

    @Test
    void testFindByEnabledTrueAndEmail() {
        when(customerRepository.findByEnabledAndEmail(1, "test@example.com")).thenReturn(Arrays.asList());

        Iterable<Customer> result = customerService.findByEnabledTrueAndEmail("test@example.com");

        assertNotNull(result);
    }
}
