package crm.service;

import crm.entity.Category;
import crm.entity.Customer;
import crm.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    private Customer customer;
    private Set<Category> categories;

    @BeforeEach
    void setUp() {
        Category category = new Category();
        category.setId(1L);
        category.setName("VIP");

        categories = new HashSet<>();
        categories.add(category);

        customer = new Customer();
        customer.setId(1L);
        customer.setName("TestCustomer");
        customer.setEmail("test@example.com");
        customer.setPhone(123456789);
        customer.setCategories(categories);
        customer.setEnabled(1);
    }

    @Test
    void testGetMaxId() {
        when(customerRepository.getMaxId()).thenReturn(10L);

        Long maxId = customerService.getMaxId();

        assertEquals(10L, maxId);
        verify(customerRepository).getMaxId();
    }

    @Test
    void testListAllCustomers() {
        when(customerRepository.findAll()).thenReturn(Arrays.asList(customer));

        Iterable<Customer> result = customerService.listAllCustomers();

        assertNotNull(result);
        assertTrue(result.iterator().hasNext());
        verify(customerRepository).findAll();
    }

    @Test
    void testShowCustomer() {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        Customer result = customerService.showCustomer(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(customerRepository).findById(1L);
    }

    @Test
    void testFindAllByEnabledTrue() {
        when(customerRepository.findAllByEnabled(1)).thenReturn(Arrays.asList(customer));

        Iterable<Customer> result = customerService.findAllByEnabledTrue();

        assertNotNull(result);
        assertTrue(result.iterator().hasNext());
        verify(customerRepository).findAllByEnabled(1);
    }

    @Test
    void testFindAllByEnabledFalse() {
        when(customerRepository.findAllByEnabled(0)).thenReturn(Arrays.asList());

        Iterable<Customer> result = customerService.findAllByEnabledFalse();

        assertNotNull(result);
        verify(customerRepository).findAllByEnabled(0);
    }

    @Test
    void testFindOneByEnabledTrueAndName() {
        when(customerRepository.findOneByEnabledAndName(1, "TestCustomer")).thenReturn(customer);

        Customer result = customerService.findOneByEnabledTrueAndName("TestCustomer");

        assertNotNull(result);
        assertEquals("TestCustomer", result.getName());
        verify(customerRepository).findOneByEnabledAndName(1, "TestCustomer");
    }

    @Test
    void testFindByEnabledTrueAndEmail() {
        when(customerRepository.findByEnabledAndEmail(1, "test@example.com")).thenReturn(Arrays.asList(customer));

        Iterable<Customer> result = customerService.findByEnabledTrueAndEmail("test@example.com");

        assertNotNull(result);
        assertTrue(result.iterator().hasNext());
        verify(customerRepository).findByEnabledAndEmail(1, "test@example.com");
    }

    @Test
    void testFindByEnabledTrueAndPhone() {
        when(customerRepository.findByEnabledAndPhone(1, 123456789)).thenReturn(Arrays.asList(customer));

        Iterable<Customer> result = customerService.findByEnabledTrueAndPhone(123456789);

        assertNotNull(result);
        assertTrue(result.iterator().hasNext());
        verify(customerRepository).findByEnabledAndPhone(1, 123456789);
    }

    @Test
    void testSaveCustomer() {
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        customerService.saveCustomer(customer);

        assertEquals(1, customer.getEnabled());
        verify(customerRepository).save(customer);
    }

    @Test
    void testFindByEnabledTrueAndCategories() {
        when(customerRepository.findByEnabledAndCategories(1, categories)).thenReturn(Arrays.asList(customer));

        Iterable<Customer> result = customerService.findByEnabledTrueAndCategories(categories);

        assertNotNull(result);
        assertTrue(result.iterator().hasNext());
        verify(customerRepository).findByEnabledAndCategories(1, categories);
    }

    @Test
    void testFindByEnabledTrueAndFirstName() {
        when(customerRepository.findByEnabledAndFirstName(1, "John")).thenReturn(Arrays.asList(customer));

        Iterable<Customer> result = customerService.findByEnabledTrueAndFirstName("John");

        assertNotNull(result);
        verify(customerRepository).findByEnabledAndFirstName(1, "John");
    }

    @Test
    void testFindByEnabledTrueAndLastName() {
        when(customerRepository.findByEnabledAndLastName(1, "Doe")).thenReturn(Arrays.asList(customer));

        Iterable<Customer> result = customerService.findByEnabledTrueAndLastName("Doe");

        assertNotNull(result);
        verify(customerRepository).findByEnabledAndLastName(1, "Doe");
    }

    @Test
    void testFindByEnabledTrueAndCity() {
        when(customerRepository.findByEnabledAndCity(1, "New York")).thenReturn(Arrays.asList(customer));

        Iterable<Customer> result = customerService.findByEnabledTrueAndCity("New York");

        assertNotNull(result);
        verify(customerRepository).findByEnabledAndCity(1, "New York");
    }
}
