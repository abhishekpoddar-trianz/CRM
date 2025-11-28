package crm.repository;

import crm.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ContractRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ContractRepository contractRepository;

    private Customer customer;
    private User user;

    @BeforeEach
    void setUp() {
        Role role = new Role();
        role.setName("ADMIN");
        entityManager.persist(role);

        user = new User();
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setEnabled(1);
        user.setRole(role);
        entityManager.persist(user);

        customer = new Customer();
        customer.setName("TestCustomer");
        customer.setEmail("customer@example.com");
        customer.setEnabled(1);
        entityManager.persist(customer);

        entityManager.flush();
    }

    @Test
    void testFindByName() {
        Contract contract = new Contract();
        contract.setName("TestContract");
        contract.setValue(new BigDecimal("10000"));
        contract.setBeginDate(LocalDate.of(2024, 1, 1));
        contract.setEndDate(LocalDate.of(2024, 12, 31));
        contract.setStatus(Status.PROPOSED);
        contract.setCustomer(customer);
        contract.setUser(user);
        entityManager.persist(contract);
        entityManager.flush();

        Contract found = contractRepository.findByName("TestContract");
        assertNotNull(found);
        assertEquals("TestContract", found.getName());
    }

    @Test
    void testFindAllByValueLessThanEqual() {
        Contract contract = new Contract();
        contract.setName("Contract1");
        contract.setValue(new BigDecimal("5000"));
        contract.setBeginDate(LocalDate.now());
        contract.setEndDate(LocalDate.now().plusDays(30));
        contract.setStatus(Status.PROPOSED);
        contract.setCustomer(customer);
        contract.setUser(user);
        entityManager.persist(contract);
        entityManager.flush();

        Iterable<Contract> contracts = contractRepository.findAllByValueLessThanEqual(new BigDecimal("10000"));
        assertNotNull(contracts);
        assertTrue(contracts.iterator().hasNext());
    }

    @Test
    void testFindAllByValueGreaterThanEqual() {
        Contract contract = new Contract();
        contract.setName("Contract2");
        contract.setValue(new BigDecimal("20000"));
        contract.setBeginDate(LocalDate.now());
        contract.setEndDate(LocalDate.now().plusDays(30));
        contract.setStatus(Status.PROPOSED);
        contract.setCustomer(customer);
        contract.setUser(user);
        entityManager.persist(contract);
        entityManager.flush();

        Iterable<Contract> contracts = contractRepository.findAllByValueGreaterThanEqual(new BigDecimal("15000"));
        assertNotNull(contracts);
        assertTrue(contracts.iterator().hasNext());
    }

    @Test
    void testFindAllByBeginDate() {
        LocalDate beginDate = LocalDate.of(2024, 1, 1);
        Contract contract = new Contract();
        contract.setName("Contract3");
        contract.setValue(new BigDecimal("10000"));
        contract.setBeginDate(beginDate);
        contract.setEndDate(LocalDate.of(2024, 12, 31));
        contract.setStatus(Status.PROPOSED);
        contract.setCustomer(customer);
        contract.setUser(user);
        entityManager.persist(contract);
        entityManager.flush();

        Iterable<Contract> contracts = contractRepository.findAllByBeginDate(beginDate);
        assertNotNull(contracts);
        assertTrue(contracts.iterator().hasNext());
    }

    @Test
    void testFindAllByStatus() {
        Contract contract = new Contract();
        contract.setName("Contract4");
        contract.setValue(new BigDecimal("10000"));
        contract.setBeginDate(LocalDate.now());
        contract.setEndDate(LocalDate.now().plusDays(30));
        contract.setStatus(Status.IMPLEMENTED);
        contract.setCustomer(customer);
        contract.setUser(user);
        entityManager.persist(contract);
        entityManager.flush();

        Iterable<Contract> contracts = contractRepository.findAllByStatus(Status.IMPLEMENTED);
        assertNotNull(contracts);
        assertTrue(contracts.iterator().hasNext());
    }

    @Test
    void testFindAllByCustomer() {
        Contract contract = new Contract();
        contract.setName("Contract5");
        contract.setValue(new BigDecimal("10000"));
        contract.setBeginDate(LocalDate.now());
        contract.setEndDate(LocalDate.now().plusDays(30));
        contract.setStatus(Status.PROPOSED);
        contract.setCustomer(customer);
        contract.setUser(user);
        entityManager.persist(contract);
        entityManager.flush();

        Iterable<Contract> contracts = contractRepository.findAllByCustomer(customer);
        assertNotNull(contracts);
        assertTrue(contracts.iterator().hasNext());
    }

    @Test
    void testFindAllByUser() {
        Contract contract = new Contract();
        contract.setName("Contract6");
        contract.setValue(new BigDecimal("10000"));
        contract.setBeginDate(LocalDate.now());
        contract.setEndDate(LocalDate.now().plusDays(30));
        contract.setStatus(Status.PROPOSED);
        contract.setCustomer(customer);
        contract.setUser(user);
        entityManager.persist(contract);
        entityManager.flush();

        Iterable<Contract> contracts = contractRepository.findAllByUser(user);
        assertNotNull(contracts);
        assertTrue(contracts.iterator().hasNext());
    }

    @Test
    void testFindAllByCustomerAndUser() {
        Contract contract = new Contract();
        contract.setName("Contract7");
        contract.setValue(new BigDecimal("10000"));
        contract.setBeginDate(LocalDate.now());
        contract.setEndDate(LocalDate.now().plusDays(30));
        contract.setStatus(Status.PROPOSED);
        contract.setCustomer(customer);
        contract.setUser(user);
        entityManager.persist(contract);
        entityManager.flush();

        Iterable<Contract> contracts = contractRepository.findAllByCustomerAndUser(customer, user);
        assertNotNull(contracts);
        assertTrue(contracts.iterator().hasNext());
    }
}
