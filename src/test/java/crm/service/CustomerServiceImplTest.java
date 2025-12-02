package crm.service;

import crm.entity.Category;
import crm.entity.Customer;
import crm.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.*;

public class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    private CustomerServiceImpl customerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        customerService = new CustomerServiceImpl(customerRepository);
    }

    @Test
    public void testGetMaxId() {
        when(customerRepository.getMaxId()).thenReturn(100L);

        Long maxId = customerService.getMaxId();

        assertEquals(100L, maxId);
        verify(customerRepository, times(1)).getMaxId();
    }

    @Test
    public void testListAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer());
        when(customerRepository.findAll()).thenReturn(customers);

        Iterable<Customer> result = customerService.listAllCustomers();

        assertNotNull(result);
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    public void testShowCustomer() {
        Customer customer = new Customer();
        customer.setId(1L);
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        Customer result = customerService.showCustomer(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(customerRepository, times(1)).findById(1L);
    }

    @Test
    public void testShowCustomerNotFound() {
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        Customer result = customerService.showCustomer(1L);

        assertNull(result);
        verify(customerRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindAllByEnabledTrue() {
        List<Customer> customers = new ArrayList<>();
        when(customerRepository.findAllByEnabled(1)).thenReturn(customers);

        Iterable<Customer> result = customerService.findAllByEnabledTrue();

        assertNotNull(result);
        verify(customerRepository, times(1)).findAllByEnabled(1);
    }

    @Test
    public void testFindAllByEnabledFalse() {
        List<Customer> customers = new ArrayList<>();
        when(customerRepository.findAllByEnabled(0)).thenReturn(customers);

        Iterable<Customer> result = customerService.findAllByEnabledFalse();

        assertNotNull(result);
        verify(customerRepository, times(1)).findAllByEnabled(0);
    }

    @Test
    public void testFindOneByEnabledTrueAndName() {
        Customer customer = new Customer();
        customer.setName("John Doe");
        when(customerRepository.findOneByEnabledAndName(1, "John Doe")).thenReturn(customer);

        Customer result = customerService.findOneByEnabledTrueAndName("John Doe");

        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        verify(customerRepository, times(1)).findOneByEnabledAndName(1, "John Doe");
    }

    @Test
    public void testFindByEnabledTrueAndEmail() {
        List<Customer> customers = new ArrayList<>();
        when(customerRepository.findByEnabledAndEmail(1, "test@example.com")).thenReturn(customers);

        Iterable<Customer> result = customerService.findByEnabledTrueAndEmail("test@example.com");

        assertNotNull(result);
        verify(customerRepository, times(1)).findByEnabledAndEmail(1, "test@example.com");
    }

    @Test
    public void testFindByEnabledTrueAndPhone() {
        List<Customer> customers = new ArrayList<>();
        when(customerRepository.findByEnabledAndPhone(1, 123456789)).thenReturn(customers);

        Iterable<Customer> result = customerService.findByEnabledTrueAndPhone(123456789);

        assertNotNull(result);
        verify(customerRepository, times(1)).findByEnabledAndPhone(1, 123456789);
    }

    @Test
    public void testSaveCustomer() {
        Customer customer = new Customer();
        customer.setName("Test Customer");
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        customerService.saveCustomer(customer);

        assertEquals(1, customer.getEnabled());
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    public void testFindByEnabledTrueAndCity() {
        List<Customer> customers = new ArrayList<>();
        when(customerRepository.findByEnabledAndCity(1, "New York")).thenReturn(customers);

        Iterable<Customer> result = customerService.findByEnabledTrueAndCity("New York");

        assertNotNull(result);
        verify(customerRepository, times(1)).findByEnabledAndCity(1, "New York");
    }

    @Test
    public void testFindByEnabledTrueAndFirstNameAndLastName() {
        List<Customer> customers = new ArrayList<>();
        when(customerRepository.findByEnabledAndFirstNameAndLastName(1, "John", "Doe"))
            .thenReturn(customers);

        Iterable<Customer> result = customerService.findByEnabledTrueAndFirstNameAndLastName("John", "Doe");

        assertNotNull(result);
        verify(customerRepository, times(1))
            .findByEnabledAndFirstNameAndLastName(1, "John", "Doe");
    }
}
