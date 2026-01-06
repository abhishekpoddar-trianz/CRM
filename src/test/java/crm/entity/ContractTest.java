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

        contract = new Contract();
    }

    @Test
    void testContractBuilder() {
        Contract built = Contract.builder()
                .id(1L)
                .name("Test Contract")
                .content("Test Content")
                .value(new BigDecimal("10000.00"))
                .beginDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(30))
                .status(Status.PROPOSED)
                .customer(customer)
                .user(user)
                .build();

        assertNotNull(built);
        assertEquals(1L, built.getId());
        assertEquals("Test Contract", built.getName());
        assertEquals("Test Content", built.getContent());
        assertEquals(new BigDecimal("10000.00"), built.getValue());
        assertEquals(Status.PROPOSED, built.getStatus());
        assertNotNull(built.getBeginDate());
        assertNotNull(built.getEndDate());
    }

    @Test
    void testSettersAndGetters() {
        contract.setId(2L);
        contract.setName("Contract Name");
        contract.setContent("Contract Content");
        contract.setValue(new BigDecimal("5000.50"));
        contract.setBeginDate(LocalDate.of(2024, 1, 1));
        contract.setEndDate(LocalDate.of(2024, 12, 31));
        contract.setStatus(Status.IMPLEMENTED);
        contract.setCustomer(customer);
        contract.setUser(user);

        assertEquals(2L, contract.getId());
        assertEquals("Contract Name", contract.getName());
        assertEquals("Contract Content", contract.getContent());
        assertEquals(new BigDecimal("5000.50"), contract.getValue());
        assertEquals(LocalDate.of(2024, 1, 1), contract.getBeginDate());
        assertEquals(LocalDate.of(2024, 12, 31), contract.getEndDate());
        assertEquals(Status.IMPLEMENTED, contract.getStatus());
        assertEquals(customer, contract.getCustomer());
        assertEquals(user, contract.getUser());
    }

    @Test
    void testNoArgsConstructor() {
        Contract newContract = new Contract();
        assertNotNull(newContract);
        assertNull(newContract.getId());
        assertNull(newContract.getName());
    }

    @Test
    void testAllArgsConstructor() {
        LocalDate begin = LocalDate.now();
        LocalDate end = LocalDate.now().plusDays(60);
        Contract fullContract = new Contract(
                3L,
                "Full Contract",
                "Full Content",
                new BigDecimal("20000.00"),
                begin,
                end,
                Status.DONE,
                customer,
                user
        );

        assertEquals(3L, fullContract.getId());
        assertEquals("Full Contract", fullContract.getName());
        assertEquals("Full Content", fullContract.getContent());
        assertEquals(new BigDecimal("20000.00"), fullContract.getValue());
        assertEquals(begin, fullContract.getBeginDate());
        assertEquals(end, fullContract.getEndDate());
        assertEquals(Status.DONE, fullContract.getStatus());
        assertEquals(customer, fullContract.getCustomer());
        assertEquals(user, fullContract.getUser());
    }

    @Test
    void testStatusEnum() {
        contract.setStatus(Status.PROPOSED);
        assertEquals(Status.PROPOSED, contract.getStatus());

        contract.setStatus(Status.NEGOTIATED);
        assertEquals(Status.NEGOTIATED, contract.getStatus());

        contract.setStatus(Status.IMPLEMENTED);
        assertEquals(Status.IMPLEMENTED, contract.getStatus());

        contract.setStatus(Status.DONE);
        assertEquals(Status.DONE, contract.getStatus());
    }

    @Test
    void testNullValues() {
        contract.setName(null);
        contract.setContent(null);
        contract.setValue(null);

        assertNull(contract.getName());
        assertNull(contract.getContent());
        assertNull(contract.getValue());
    }
}
