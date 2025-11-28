package crm.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ContractTest {

    private Contract contract;
    private Customer customer;
    private User user;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setId(1L);
        customer.setName("Test Customer");

        user = new User();
        user.setId(1L);
        user.setUsername("testuser");

        contract = Contract.builder()
                .id(1L)
                .name("Test Contract")
                .content("Test Content")
                .value(new BigDecimal("10000.00"))
                .beginDate(LocalDate.of(2024, 1, 1))
                .endDate(LocalDate.of(2024, 12, 31))
                .status(Status.PROPOSED)
                .customer(customer)
                .user(user)
                .build();
    }

    @Test
    void testContractBuilder() {
        assertNotNull(contract);
        assertEquals(1L, contract.getId());
        assertEquals("Test Contract", contract.getName());
        assertEquals("Test Content", contract.getContent());
        assertEquals(new BigDecimal("10000.00"), contract.getValue());
        assertEquals(LocalDate.of(2024, 1, 1), contract.getBeginDate());
        assertEquals(LocalDate.of(2024, 12, 31), contract.getEndDate());
        assertEquals(Status.PROPOSED, contract.getStatus());
        assertEquals(customer, contract.getCustomer());
        assertEquals(user, contract.getUser());
    }

    @Test
    void testNoArgsConstructor() {
        Contract emptyContract = new Contract();
        assertNotNull(emptyContract);
        assertNull(emptyContract.getId());
        assertNull(emptyContract.getName());
    }

    @Test
    void testAllArgsConstructor() {
        Contract newContract = new Contract(2L, "Contract2", "Content2",
                new BigDecimal("20000.00"), LocalDate.of(2024, 2, 1),
                LocalDate.of(2024, 11, 30), Status.NEGOTIATED, customer, user);

        assertNotNull(newContract);
        assertEquals(2L, newContract.getId());
        assertEquals("Contract2", newContract.getName());
        assertEquals("Content2", newContract.getContent());
        assertEquals(new BigDecimal("20000.00"), newContract.getValue());
    }

    @Test
    void testSettersAndGetters() {
        contract.setId(10L);
        contract.setName("Updated Contract");
        contract.setContent("Updated Content");
        contract.setValue(new BigDecimal("50000.00"));
        contract.setBeginDate(LocalDate.of(2025, 1, 1));
        contract.setEndDate(LocalDate.of(2025, 12, 31));
        contract.setStatus(Status.DONE);

        assertEquals(10L, contract.getId());
        assertEquals("Updated Contract", contract.getName());
        assertEquals("Updated Content", contract.getContent());
        assertEquals(new BigDecimal("50000.00"), contract.getValue());
        assertEquals(LocalDate.of(2025, 1, 1), contract.getBeginDate());
        assertEquals(LocalDate.of(2025, 12, 31), contract.getEndDate());
        assertEquals(Status.DONE, contract.getStatus());
    }

    @Test
    void testStatusEnum() {
        contract.setStatus(Status.PROPOSED);
        assertEquals(Status.PROPOSED, contract.getStatus());

        contract.setStatus(Status.NEGOTIATED);
        assertEquals(Status.NEGOTIATED, contract.getStatus());

        contract.setStatus(Status.DONE);
        assertEquals(Status.DONE, contract.getStatus());
    }

    @Test
    void testCustomerRelationship() {
        Customer newCustomer = new Customer();
        newCustomer.setId(2L);
        newCustomer.setName("New Customer");

        contract.setCustomer(newCustomer);
        assertEquals(newCustomer, contract.getCustomer());
        assertEquals(2L, contract.getCustomer().getId());
    }

    @Test
    void testUserRelationship() {
        User newUser = new User();
        newUser.setId(2L);
        newUser.setUsername("newuser");

        contract.setUser(newUser);
        assertEquals(newUser, contract.getUser());
        assertEquals(2L, contract.getUser().getId());
    }

    @Test
    void testNullValues() {
        contract.setCustomer(null);
        contract.setUser(null);
        contract.setStatus(null);

        assertNull(contract.getCustomer());
        assertNull(contract.getUser());
        assertNull(contract.getStatus());
    }
}
