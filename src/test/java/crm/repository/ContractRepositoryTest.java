package crm.repository;

import crm.entity.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ContractRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ContractRepository contractRepository;

    @Test
    void testFindByName() {
        Customer customer = Customer.builder()
                .name("TestCustomer")
                .email("customer@test.com")
                .phone(123456)
                .enabled(1)
                .build();
        entityManager.persist(customer);

        Role role = new Role();
        role.setName("USER");
        entityManager.persist(role);

        User user = User.builder()
                .username("testuser")
                .email("user@test.com")
                .firstName("Test")
                .lastName("User")
                .password("pass")
                .enabled(1)
                .role(role)
                .build();
        entityManager.persist(user);

        Contract contract = Contract.builder()
                .name("TestContract")
                .content("Test content")
                .value(BigDecimal.valueOf(10000))
                .beginDate(LocalDate.now())
                .endDate(LocalDate.now().plusMonths(6))
                .status(Status.PROPOSED)
                .customer(customer)
                .user(user)
                .build();
        entityManager.persist(contract);
        entityManager.flush();

        Contract found = contractRepository.findByName("TestContract");
        assertNotNull(found);
        assertEquals("TestContract", found.getName());
    }

    @Test
    void testFindAllByValueLessThanEqual() {
        Customer customer = Customer.builder()
                .name("Customer1")
                .email("customer1@test.com")
                .phone(111111)
                .enabled(1)
                .build();
        entityManager.persist(customer);

        Role role = new Role();
        role.setName("USER");
        entityManager.persist(role);

        User user = User.builder()
                .username("user1")
                .email("user1@test.com")
                .firstName("User")
                .lastName("One")
                .password("pass")
                .enabled(1)
                .role(role)
                .build();
        entityManager.persist(user);

        Contract contract = Contract.builder()
                .name("SmallContract")
                .content("Content")
                .value(BigDecimal.valueOf(5000))
                .beginDate(LocalDate.now())
                .endDate(LocalDate.now().plusMonths(3))
                .status(Status.PROPOSED)
                .customer(customer)
                .user(user)
                .build();
        entityManager.persist(contract);
        entityManager.flush();

        Iterable<Contract> contracts = contractRepository.findAllByValueLessThanEqual(BigDecimal.valueOf(10000));
        List<Contract> contractList = (List<Contract>) contracts;
        assertTrue(contractList.size() > 0);
    }

    @Test
    void testFindAllByValueGreaterThanEqual() {
        Customer customer = Customer.builder()
                .name("Customer2")
                .email("customer2@test.com")
                .phone(222222)
                .enabled(1)
                .build();
        entityManager.persist(customer);

        Role role = new Role();
        role.setName("USER");
        entityManager.persist(role);

        User user = User.builder()
                .username("user2")
                .email("user2@test.com")
                .firstName("User")
                .lastName("Two")
                .password("pass")
                .enabled(1)
                .role(role)
                .build();
        entityManager.persist(user);

        Contract contract = Contract.builder()
                .name("LargeContract")
                .content("Large content")
                .value(BigDecimal.valueOf(50000))
                .beginDate(LocalDate.now())
                .endDate(LocalDate.now().plusYears(1))
                .status(Status.NEGOTIATED)
                .customer(customer)
                .user(user)
                .build();
        entityManager.persist(contract);
        entityManager.flush();

        Iterable<Contract> contracts = contractRepository.findAllByValueGreaterThanEqual(BigDecimal.valueOf(40000));
        List<Contract> contractList = (List<Contract>) contracts;
        assertTrue(contractList.size() > 0);
    }

    @Test
    void testFindAllByBeginDate() {
        Customer customer = Customer.builder()
                .name("Customer3")
                .email("customer3@test.com")
                .phone(333333)
                .enabled(1)
                .build();
        entityManager.persist(customer);

        Role role = new Role();
        role.setName("USER");
        entityManager.persist(role);

        User user = User.builder()
                .username("user3")
                .email("user3@test.com")
                .firstName("User")
                .lastName("Three")
                .password("pass")
                .enabled(1)
                .role(role)
                .build();
        entityManager.persist(user);

        LocalDate testDate = LocalDate.of(2024, 1, 1);
        Contract contract = Contract.builder()
                .name("DateContract")
                .content("Date content")
                .value(BigDecimal.valueOf(20000))
                .beginDate(testDate)
                .endDate(testDate.plusMonths(6))
                .status(Status.IMPLEMENTED)
                .customer(customer)
                .user(user)
                .build();
        entityManager.persist(contract);
        entityManager.flush();

        Iterable<Contract> contracts = contractRepository.findAllByBeginDate(testDate);
        List<Contract> contractList = (List<Contract>) contracts;
        assertTrue(contractList.size() > 0);
    }

    @Test
    void testFindAllByStatus() {
        Customer customer = Customer.builder()
                .name("Customer4")
                .email("customer4@test.com")
                .phone(444444)
                .enabled(1)
                .build();
        entityManager.persist(customer);

        Role role = new Role();
        role.setName("USER");
        entityManager.persist(role);

        User user = User.builder()
                .username("user4")
                .email("user4@test.com")
                .firstName("User")
                .lastName("Four")
                .password("pass")
                .enabled(1)
                .role(role)
                .build();
        entityManager.persist(user);

        Contract contract = Contract.builder()
                .name("DoneContract")
                .content("Done content")
                .value(BigDecimal.valueOf(15000))
                .beginDate(LocalDate.now())
                .endDate(LocalDate.now().plusMonths(3))
                .status(Status.DONE)
                .customer(customer)
                .user(user)
                .build();
        entityManager.persist(contract);
        entityManager.flush();

        Iterable<Contract> contracts = contractRepository.findAllByStatus(Status.DONE);
        List<Contract> contractList = (List<Contract>) contracts;
        assertTrue(contractList.size() > 0);
        assertTrue(contractList.stream().allMatch(c -> c.getStatus() == Status.DONE));
    }

    @Test
    void testFindAllByCustomer() {
        Customer customer = Customer.builder()
                .name("Customer5")
                .email("customer5@test.com")
                .phone(555555)
                .enabled(1)
                .build();
        entityManager.persist(customer);

        Role role = new Role();
        role.setName("USER");
        entityManager.persist(role);

        User user = User.builder()
                .username("user5")
                .email("user5@test.com")
                .firstName("User")
                .lastName("Five")
                .password("pass")
                .enabled(1)
                .role(role)
                .build();
        entityManager.persist(user);

        Contract contract = Contract.builder()
                .name("CustomerContract")
                .content("Customer content")
                .value(BigDecimal.valueOf(25000))
                .beginDate(LocalDate.now())
                .endDate(LocalDate.now().plusMonths(9))
                .status(Status.PROPOSED)
                .customer(customer)
                .user(user)
                .build();
        entityManager.persist(contract);
        entityManager.flush();

        Iterable<Contract> contracts = contractRepository.findAllByCustomer(customer);
        List<Contract> contractList = (List<Contract>) contracts;
        assertTrue(contractList.size() > 0);
    }

    @Test
    void testFindAllByUser() {
        Customer customer = Customer.builder()
                .name("Customer6")
                .email("customer6@test.com")
                .phone(666666)
                .enabled(1)
                .build();
        entityManager.persist(customer);

        Role role = new Role();
        role.setName("USER");
        entityManager.persist(role);

        User user = User.builder()
                .username("user6")
                .email("user6@test.com")
                .firstName("User")
                .lastName("Six")
                .password("pass")
                .enabled(1)
                .role(role)
                .build();
        entityManager.persist(user);

        Contract contract = Contract.builder()
                .name("UserContract")
                .content("User content")
                .value(BigDecimal.valueOf(30000))
                .beginDate(LocalDate.now())
                .endDate(LocalDate.now().plusMonths(12))
                .status(Status.NEGOTIATED)
                .customer(customer)
                .user(user)
                .build();
        entityManager.persist(contract);
        entityManager.flush();

        Iterable<Contract> contracts = contractRepository.findAllByUser(user);
        List<Contract> contractList = (List<Contract>) contracts;
        assertTrue(contractList.size() > 0);
    }
}
