package crm.service;

import crm.entity.Category;
import crm.entity.Customer;
import crm.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

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
        assertNotNull(customerService);
    }

    @Test
    void testGetMaxId() {
        Long maxId = 100L;
        when(customerRepository.getMaxId()).thenReturn(maxId);

        Long result = customerService.getMaxId();

        assertEquals(maxId, result);
        verify(customerRepository).getMaxId();
    }

    @Test
    void testListAllCustomers() {
        List<Customer> customers = Arrays.asList(
                Customer.builder().id(1L).name("Customer1").build(),
                Customer.builder().id(2L).name("Customer2").build()
        );

        when(customerRepository.findAll()).thenReturn(customers);

        Iterable<Customer> result = customerService.listAllCustomers();

        assertNotNull(result);
        assertEquals(customers, result);
        verify(customerRepository).findAll();
    }

    @Test
    void testShowCustomer() {
        Long customerId = 1L;
        Customer customer = Customer.builder().id(customerId).name("TestCustomer").build();

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        Customer result = customerService.showCustomer(customerId);

        assertEquals(customer, result);
        verify(customerRepository).findById(customerId);
    }

    @Test
    void testShowCustomerNotFound() {
        Long customerId = 999L;
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        Customer result = customerService.showCustomer(customerId);

        assertNull(result);
        verify(customerRepository).findById(customerId);
    }

    @Test
    void testFindAllByEnabledTrue() {
        List<Customer> customers = Arrays.asList(
                Customer.builder().id(1L).name("Customer1").enabled(1).build()
        );

        when(customerRepository.findAllByEnabled(1)).thenReturn(customers);

        Iterable<Customer> result = customerService.findAllByEnabledTrue();

        assertNotNull(result);
        verify(customerRepository).findAllByEnabled(1);
    }

    @Test
    void testFindAllByEnabledFalse() {
        List<Customer> customers = Arrays.asList(
                Customer.builder().id(1L).name("Customer1").enabled(0).build()
        );

        when(customerRepository.findAllByEnabled(0)).thenReturn(customers);

        Iterable<Customer> result = customerService.findAllByEnabledFalse();

        assertNotNull(result);
        verify(customerRepository).findAllByEnabled(0);
    }

    @Test
    void testFindOneByEnabledTrueAndName() {
        String name = "TestCustomer";
        Customer customer = Customer.builder().id(1L).name(name).enabled(1).build();

        when(customerRepository.findOneByEnabledAndName(1, name)).thenReturn(customer);

        Customer result = customerService.findOneByEnabledTrueAndName(name);

        assertEquals(customer, result);
        verify(customerRepository).findOneByEnabledAndName(1, name);
    }

    @Test
    void testFindByEnabledTrueAndEmail() {
        String email = "test@example.com";
        List<Customer> customers = Arrays.asList(
                Customer.builder().id(1L).email(email).enabled(1).build()
        );

        when(customerRepository.findByEnabledAndEmail(1, email)).thenReturn(customers);

        Iterable<Customer> result = customerService.findByEnabledTrueAndEmail(email);

        assertNotNull(result);
        verify(customerRepository).findByEnabledAndEmail(1, email);
    }

    @Test
    void testFindByEnabledTrueAndPhone() {
        int phone = 123456;
        List<Customer> customers = Arrays.asList(
                Customer.builder().id(1L).phone(phone).enabled(1).build()
        );

        when(customerRepository.findByEnabledAndPhone(1, phone)).thenReturn(customers);

        Iterable<Customer> result = customerService.findByEnabledTrueAndPhone(phone);

        assertNotNull(result);
        verify(customerRepository).findByEnabledAndPhone(1, phone);
    }

    @Test
    void testFindByEnabledTrueAndFirstName() {
        String firstName = "John";
        List<Customer> customers = Arrays.asList(
                Customer.builder().id(1L).firstName(firstName).enabled(1).build()
        );

        when(customerRepository.findByEnabledAndFirstName(1, firstName)).thenReturn(customers);

        Iterable<Customer> result = customerService.findByEnabledTrueAndFirstName(firstName);

        assertNotNull(result);
        verify(customerRepository).findByEnabledAndFirstName(1, firstName);
    }

    @Test
    void testFindByEnabledTrueAndLastName() {
        String lastName = "Doe";
        List<Customer> customers = Arrays.asList(
                Customer.builder().id(1L).lastName(lastName).enabled(1).build()
        );

        when(customerRepository.findByEnabledAndLastName(1, lastName)).thenReturn(customers);

        Iterable<Customer> result = customerService.findByEnabledTrueAndLastName(lastName);

        assertNotNull(result);
        verify(customerRepository).findByEnabledAndLastName(1, lastName);
    }

    @Test
    void testFindByEnabledTrueAndCity() {
        String city = "New York";
        List<Customer> customers = Arrays.asList(
                Customer.builder().id(1L).city(city).enabled(1).build()
        );

        when(customerRepository.findByEnabledAndCity(1, city)).thenReturn(customers);

        Iterable<Customer> result = customerService.findByEnabledTrueAndCity(city);

        assertNotNull(result);
        verify(customerRepository).findByEnabledAndCity(1, city);
    }

    @Test
    void testSaveCustomer() {
        Customer customer = Customer.builder()
                .id(1L)
                .name("NewCustomer")
                .email("new@example.com")
                .enabled(0)
                .build();

        customerService.saveCustomer(customer);

        assertEquals(1, customer.getEnabled());
        verify(customerRepository).save(customer);
    }

    @Test
    void testFindByEnabledTrueAndCategories() {
        Category category = new Category();
        category.setId(1L);
        category.setName("VIP");
        Set<Category> categories = new HashSet<>(Arrays.asList(category));

        List<Customer> customers = Arrays.asList(
                Customer.builder().id(1L).categories(categories).enabled(1).build()
        );

        when(customerRepository.findByEnabledAndCategories(1, categories)).thenReturn(customers);

        Iterable<Customer> result = customerService.findByEnabledTrueAndCategories(categories);

        assertNotNull(result);
        verify(customerRepository).findByEnabledAndCategories(1, categories);
    }

    @Test
    void testFindByEnabledTrueAndFirstNameAndLastName() {
        String firstName = "John";
        String lastName = "Doe";
        List<Customer> customers = Arrays.asList(
                Customer.builder().id(1L).firstName(firstName).lastName(lastName).enabled(1).build()
        );

        when(customerRepository.findByEnabledAndFirstNameAndLastName(1, firstName, lastName)).thenReturn(customers);

        Iterable<Customer> result = customerService.findByEnabledTrueAndFirstNameAndLastName(firstName, lastName);

        assertNotNull(result);
        verify(customerRepository).findByEnabledAndFirstNameAndLastName(1, firstName, lastName);
    }

    @Test
    void testFindByEnabledTrueAndCityAndAddress() {
        String city = "Boston";
        String address = "123 Main St";
        List<Customer> customers = Arrays.asList(
                Customer.builder().id(1L).city(city).address(address).enabled(1).build()
        );

        when(customerRepository.findByEnabledAndCityAndAddress(1, city, address)).thenReturn(customers);

        Iterable<Customer> result = customerService.findByEnabledTrueAndCityAndAddress(city, address);

        assertNotNull(result);
        verify(customerRepository).findByEnabledAndCityAndAddress(1, city, address);
    }
}
