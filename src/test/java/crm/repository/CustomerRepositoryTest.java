package crm.repository;

import crm.entity.Category;
import crm.entity.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CustomerRepository customerRepository;

    private Category category;
    private Set<Category> categories;

    @BeforeEach
    void setUp() {
        category = new Category();
        category.setName("VIP");
        entityManager.persist(category);

        categories = new HashSet<>();
        categories.add(category);
        entityManager.flush();
    }

    @Test
    void testGetMaxId() {
        Customer customer = new Customer();
        customer.setName("TestCustomer");
        customer.setEmail("test@example.com");
        customer.setEnabled(1);
        entityManager.persist(customer);
        entityManager.flush();

        Long maxId = customerRepository.getMaxId();
        assertNotNull(maxId);
        assertTrue(maxId > 0);
    }

    @Test
    void testFindAllByEnabled() {
        Customer customer = new Customer();
        customer.setName("EnabledCustomer");
        customer.setEmail("enabled@example.com");
        customer.setEnabled(1);
        entityManager.persist(customer);
        entityManager.flush();

        Iterable<Customer> customers = customerRepository.findAllByEnabled(1);
        assertNotNull(customers);
        assertTrue(customers.iterator().hasNext());
    }

    @Test
    void testFindOneByEnabledAndName() {
        Customer customer = new Customer();
        customer.setName("UniqueCustomer");
        customer.setEmail("unique@example.com");
        customer.setEnabled(1);
        entityManager.persist(customer);
        entityManager.flush();

        Customer found = customerRepository.findOneByEnabledAndName(1, "UniqueCustomer");
        assertNotNull(found);
        assertEquals("UniqueCustomer", found.getName());
    }

    @Test
    void testFindOneByName() {
        Customer customer = new Customer();
        customer.setName("NameCustomer");
        customer.setEmail("name@example.com");
        customer.setEnabled(1);
        entityManager.persist(customer);
        entityManager.flush();

        Customer found = customerRepository.findOneByName("NameCustomer");
        assertNotNull(found);
        assertEquals("NameCustomer", found.getName());
    }

    @Test
    void testFindByEnabledAndEmail() {
        Customer customer = new Customer();
        customer.setName("EmailCustomer");
        customer.setEmail("email@example.com");
        customer.setEnabled(1);
        entityManager.persist(customer);
        entityManager.flush();

        Iterable<Customer> customers = customerRepository.findByEnabledAndEmail(1, "email@example.com");
        assertNotNull(customers);
        assertTrue(customers.iterator().hasNext());
    }

    @Test
    void testFindByEmail() {
        Customer customer = new Customer();
        customer.setName("EmailTest");
        customer.setEmail("emailtest@example.com");
        customer.setEnabled(0);
        entityManager.persist(customer);
        entityManager.flush();

        Iterable<Customer> customers = customerRepository.findByEmail("emailtest@example.com");
        assertNotNull(customers);
        assertTrue(customers.iterator().hasNext());
    }

    @Test
    void testFindByEnabledAndCity() {
        Customer customer = new Customer();
        customer.setName("CityCustomer");
        customer.setEmail("city@example.com");
        customer.setCity("New York");
        customer.setEnabled(1);
        entityManager.persist(customer);
        entityManager.flush();

        Iterable<Customer> customers = customerRepository.findByEnabledAndCity(1, "New York");
        assertNotNull(customers);
        assertTrue(customers.iterator().hasNext());
    }

    @Test
    void testFindByCity() {
        Customer customer = new Customer();
        customer.setName("CityTest");
        customer.setEmail("citytest@example.com");
        customer.setCity("Boston");
        customer.setEnabled(0);
        entityManager.persist(customer);
        entityManager.flush();

        Iterable<Customer> customers = customerRepository.findByCity("Boston");
        assertNotNull(customers);
        assertTrue(customers.iterator().hasNext());
    }

    @Test
    void testFindByEnabledAndPhone() {
        Customer customer = new Customer();
        customer.setName("PhoneCustomer");
        customer.setEmail("phone@example.com");
        customer.setPhone(123456789);
        customer.setEnabled(1);
        entityManager.persist(customer);
        entityManager.flush();

        Iterable<Customer> customers = customerRepository.findByEnabledAndPhone(1, 123456789);
        assertNotNull(customers);
        assertTrue(customers.iterator().hasNext());
    }

    @Test
    void testFindByPhone() {
        Customer customer = new Customer();
        customer.setName("PhoneTest");
        customer.setEmail("phonetest@example.com");
        customer.setPhone(987654321);
        customer.setEnabled(0);
        entityManager.persist(customer);
        entityManager.flush();

        Iterable<Customer> customers = customerRepository.findByPhone(987654321);
        assertNotNull(customers);
        assertTrue(customers.iterator().hasNext());
    }

    @Test
    void testFindByEnabledAndFirstName() {
        Customer customer = new Customer();
        customer.setName("FirstNameCustomer");
        customer.setEmail("firstname@example.com");
        customer.setFirstName("John");
        customer.setEnabled(1);
        entityManager.persist(customer);
        entityManager.flush();

        Iterable<Customer> customers = customerRepository.findByEnabledAndFirstName(1, "John");
        assertNotNull(customers);
        assertTrue(customers.iterator().hasNext());
    }

    @Test
    void testFindByFirstName() {
        Customer customer = new Customer();
        customer.setName("FirstTest");
        customer.setEmail("firsttest@example.com");
        customer.setFirstName("Jane");
        customer.setEnabled(0);
        entityManager.persist(customer);
        entityManager.flush();

        Iterable<Customer> customers = customerRepository.findByFirstName("Jane");
        assertNotNull(customers);
        assertTrue(customers.iterator().hasNext());
    }

    @Test
    void testFindByEnabledAndLastName() {
        Customer customer = new Customer();
        customer.setName("LastNameCustomer");
        customer.setEmail("lastname@example.com");
        customer.setLastName("Doe");
        customer.setEnabled(1);
        entityManager.persist(customer);
        entityManager.flush();

        Iterable<Customer> customers = customerRepository.findByEnabledAndLastName(1, "Doe");
        assertNotNull(customers);
        assertTrue(customers.iterator().hasNext());
    }

    @Test
    void testFindByCategories() {
        Customer customer = new Customer();
        customer.setName("CategoryCustomer");
        customer.setEmail("category@example.com");
        customer.setCategories(categories);
        customer.setEnabled(1);
        entityManager.persist(customer);
        entityManager.flush();

        Iterable<Customer> customers = customerRepository.findByCategories(categories);
        assertNotNull(customers);
        assertTrue(customers.iterator().hasNext());
    }
}
