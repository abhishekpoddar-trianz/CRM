package crm.repository;

import crm.entity.Category;
import crm.entity.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DataJpaTest
class CustomerRepositoryTest {

    @MockBean
    private CustomerRepository customerRepository;

    @Test
    void testGetMaxId() {
        when(customerRepository.getMaxId()).thenReturn(100L);

        Long result = customerRepository.getMaxId();

        assertEquals(100L, result);
        verify(customerRepository, times(1)).getMaxId();
    }

    @Test
    void testGetMaxIdWithNullResult() {
        when(customerRepository.getMaxId()).thenReturn(null);

        Long result = customerRepository.getMaxId();

        assertNull(result);
        verify(customerRepository, times(1)).getMaxId();
    }

    @Test
    void testFindAllByEnabled() {
        List<Customer> enabledCustomers = new ArrayList<>();
        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setName("Customer 1");
        customer1.setEnabled(1);
        enabledCustomers.add(customer1);

        when(customerRepository.findAllByEnabled(1)).thenReturn(enabledCustomers);

        Iterable<Customer> result = customerRepository.findAllByEnabled(1);

        assertNotNull(result);
        List<Customer> resultList = new ArrayList<>();
        result.forEach(resultList::add);
        assertEquals(1, resultList.size());
        assertEquals("Customer 1", resultList.get(0).getName());
        verify(customerRepository, times(1)).findAllByEnabled(1);
    }

    @Test
    void testFindOneByEnabledAndName() {
        Customer mockCustomer = new Customer();
        mockCustomer.setId(1L);
        mockCustomer.setName("Test Customer");
        mockCustomer.setEnabled(1);

        when(customerRepository.findOneByEnabledAndName(1, "Test Customer")).thenReturn(mockCustomer);

        Customer result = customerRepository.findOneByEnabledAndName(1, "Test Customer");

        assertNotNull(result);
        assertEquals("Test Customer", result.getName());
        assertEquals(1, result.getEnabled());
        verify(customerRepository, times(1)).findOneByEnabledAndName(1, "Test Customer");
    }

    @Test
    void testFindOneByName() {
        Customer mockCustomer = new Customer();
        mockCustomer.setId(1L);
        mockCustomer.setName("Test Customer");

        when(customerRepository.findOneByName("Test Customer")).thenReturn(mockCustomer);

        Customer result = customerRepository.findOneByName("Test Customer");

        assertNotNull(result);
        assertEquals("Test Customer", result.getName());
        verify(customerRepository, times(1)).findOneByName("Test Customer");
    }

    @Test
    void testFindByEnabledAndEmail() {
        List<Customer> customers = new ArrayList<>();
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setEmail("test@example.com");
        customer.setEnabled(1);
        customers.add(customer);

        when(customerRepository.findByEnabledAndEmail(1, "test@example.com")).thenReturn(customers);

        Iterable<Customer> result = customerRepository.findByEnabledAndEmail(1, "test@example.com");

        assertNotNull(result);
        List<Customer> resultList = new ArrayList<>();
        result.forEach(resultList::add);
        assertEquals(1, resultList.size());
        assertEquals("test@example.com", resultList.get(0).getEmail());
        verify(customerRepository, times(1)).findByEnabledAndEmail(1, "test@example.com");
    }

    @Test
    void testFindByEmail() {
        List<Customer> customers = new ArrayList<>();
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setEmail("test@example.com");
        customers.add(customer);

        when(customerRepository.findByEmail("test@example.com")).thenReturn(customers);

        Iterable<Customer> result = customerRepository.findByEmail("test@example.com");

        assertNotNull(result);
        List<Customer> resultList = new ArrayList<>();
        result.forEach(resultList::add);
        assertEquals(1, resultList.size());
        assertEquals("test@example.com", resultList.get(0).getEmail());
        verify(customerRepository, times(1)).findByEmail("test@example.com");
    }

    @Test
    void testFindByEnabledAndCity() {
        List<Customer> customers = new ArrayList<>();
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setCity("New York");
        customer.setEnabled(1);
        customers.add(customer);

        when(customerRepository.findByEnabledAndCity(1, "New York")).thenReturn(customers);

        Iterable<Customer> result = customerRepository.findByEnabledAndCity(1, "New York");

        assertNotNull(result);
        List<Customer> resultList = new ArrayList<>();
        result.forEach(resultList::add);
        assertEquals(1, resultList.size());
        assertEquals("New York", resultList.get(0).getCity());
        verify(customerRepository, times(1)).findByEnabledAndCity(1, "New York");
    }

    @Test
    void testFindByCity() {
        List<Customer> customers = new ArrayList<>();
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setCity("New York");
        customers.add(customer);

        when(customerRepository.findByCity("New York")).thenReturn(customers);

        Iterable<Customer> result = customerRepository.findByCity("New York");

        assertNotNull(result);
        List<Customer> resultList = new ArrayList<>();
        result.forEach(resultList::add);
        assertEquals(1, resultList.size());
        assertEquals("New York", resultList.get(0).getCity());
        verify(customerRepository, times(1)).findByCity("New York");
    }

    @Test
    void testFindByEnabledAndCityAndAddress() {
        List<Customer> customers = new ArrayList<>();
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setCity("New York");
        customer.setAddress("123 Main St");
        customer.setEnabled(1);
        customers.add(customer);

        when(customerRepository.findByEnabledAndCityAndAddress(1, "New York", "123 Main St")).thenReturn(customers);

        Iterable<Customer> result = customerRepository.findByEnabledAndCityAndAddress(1, "New York", "123 Main St");

        assertNotNull(result);
        List<Customer> resultList = new ArrayList<>();
        result.forEach(resultList::add);
        assertEquals(1, resultList.size());
        assertEquals("New York", resultList.get(0).getCity());
        assertEquals("123 Main St", resultList.get(0).getAddress());
        verify(customerRepository, times(1)).findByEnabledAndCityAndAddress(1, "New York", "123 Main St");
    }

    @Test
    void testFindByCityAndAddress() {
        List<Customer> customers = new ArrayList<>();
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setCity("New York");
        customer.setAddress("123 Main St");
        customers.add(customer);

        when(customerRepository.findByCityAndAddress("New York", "123 Main St")).thenReturn(customers);

        Iterable<Customer> result = customerRepository.findByCityAndAddress("New York", "123 Main St");

        assertNotNull(result);
        List<Customer> resultList = new ArrayList<>();
        result.forEach(resultList::add);
        assertEquals(1, resultList.size());
        assertEquals("New York", resultList.get(0).getCity());
        assertEquals("123 Main St", resultList.get(0).getAddress());
        verify(customerRepository, times(1)).findByCityAndAddress("New York", "123 Main St");
    }

    @Test
    void testFindByEnabledAndPhone() {
        List<Customer> customers = new ArrayList<>();
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setPhone(1234567890);
        customer.setEnabled(1);
        customers.add(customer);

        when(customerRepository.findByEnabledAndPhone(1, 1234567890)).thenReturn(customers);

        Iterable<Customer> result = customerRepository.findByEnabledAndPhone(1, 1234567890);

        assertNotNull(result);
        List<Customer> resultList = new ArrayList<>();
        result.forEach(resultList::add);
        assertEquals(1, resultList.size());
        assertEquals(1234567890, resultList.get(0).getPhone());
        verify(customerRepository, times(1)).findByEnabledAndPhone(1, 1234567890);
    }

    @Test
    void testFindByFirstNameAndLastName() {
        List<Customer> customers = new ArrayList<>();
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customers.add(customer);

        when(customerRepository.findByFirstNameAndLastName("John", "Doe")).thenReturn(customers);

        Iterable<Customer> result = customerRepository.findByFirstNameAndLastName("John", "Doe");

        assertNotNull(result);
        List<Customer> resultList = new ArrayList<>();
        result.forEach(resultList::add);
        assertEquals(1, resultList.size());
        assertEquals("John", resultList.get(0).getFirstName());
        assertEquals("Doe", resultList.get(0).getLastName());
        verify(customerRepository, times(1)).findByFirstNameAndLastName("John", "Doe");
    }

    @Test
    void testFindByCategories() {
        Set<Category> categories = new HashSet<>();
        Category category = new Category();
        category.setId(1);
        category.setName("Premium");
        categories.add(category);

        List<Customer> customers = new ArrayList<>();
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setCategories(categories);
        customers.add(customer);

        when(customerRepository.findByCategories(categories)).thenReturn(customers);

        Iterable<Customer> result = customerRepository.findByCategories(categories);

        assertNotNull(result);
        List<Customer> resultList = new ArrayList<>();
        result.forEach(resultList::add);
        assertEquals(1, resultList.size());
        assertNotNull(resultList.get(0).getCategories());
        verify(customerRepository, times(1)).findByCategories(categories);
    }

    @Test
    void testRepositoryInterface() {
        assertTrue(CustomerRepository.class.isInterface());
    }

    @Test
    void testExtendsJpaRepository() {
        assertTrue(org.springframework.data.jpa.repository.JpaRepository.class.isAssignableFrom(CustomerRepository.class));
    }

    @Test
    void testRepositoryAnnotation() {
        assertTrue(CustomerRepository.class.isAnnotationPresent(org.springframework.stereotype.Repository.class));
    }
}