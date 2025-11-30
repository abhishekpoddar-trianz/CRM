package crm.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ContractTest {

    private Contract contract;

    @BeforeEach
    void setUp() {
        contract = new Contract();
    }

    @Test
    void testContractBuilder() {
        Customer customer = new Customer();
        customer.setId(1L);

        User user = new User();
        user.setId(1L);

        Contract builtContract = Contract.builder()
                .id(1L)
                .name("Test Contract")
                .content("Contract Content")
                .value(new BigDecimal("10000.00"))
                .beginDate(LocalDate.of(2024, 1, 1))
                .endDate(LocalDate.of(2024, 12, 31))
                .status(Status.PROPOSED)
                .customer(customer)
                .user(user)
                .build();

        assertNotNull(builtContract);
        assertEquals(1L, builtContract.getId());
        assertEquals("Test Contract", builtContract.getName());
        assertEquals("Contract Content", builtContract.getContent());
        assertEquals(new BigDecimal("10000.00"), builtContract.getValue());
        assertEquals(LocalDate.of(2024, 1, 1), builtContract.getBeginDate());
        assertEquals(LocalDate.of(2024, 12, 31), builtContract.getEndDate());
        assertEquals(Status.PROPOSED, builtContract.getStatus());
        assertEquals(customer, builtContract.getCustomer());
        assertEquals(user, builtContract.getUser());
    }

    @Test
    void testNoArgsConstructor() {
        Contract contract = new Contract();
        assertNotNull(contract);
    }

    @Test
    void testAllArgsConstructor() {
        Customer customer = new Customer();
        User user = new User();

        Contract contract = new Contract(1L, "Test", "Content", new BigDecimal("5000"),
                LocalDate.now(), LocalDate.now().plusDays(30), Status.NEGOTIATED, customer, user);

        assertNotNull(contract);
        assertEquals(1L, contract.getId());
        assertEquals("Test", contract.getName());
    }

    @Test
    void testSetAndGetId() {
        contract.setId(100L);
        assertEquals(100L, contract.getId());
    }

    @Test
    void testSetAndGetName() {
        contract.setName("Service Agreement");
        assertEquals("Service Agreement", contract.getName());
    }

    @Test
    void testSetAndGetContent() {
        String content = "This is a test contract content";
        contract.setContent(content);
        assertEquals(content, contract.getContent());
    }

    @Test
    void testSetAndGetValue() {
        BigDecimal value = new BigDecimal("50000.50");
        contract.setValue(value);
        assertEquals(value, contract.getValue());
    }

    @Test
    void testSetAndGetBeginDate() {
        LocalDate beginDate = LocalDate.of(2024, 6, 1);
        contract.setBeginDate(beginDate);
        assertEquals(beginDate, contract.getBeginDate());
    }

    @Test
    void testSetAndGetEndDate() {
        LocalDate endDate = LocalDate.of(2025, 6, 1);
        contract.setEndDate(endDate);
        assertEquals(endDate, contract.getEndDate());
    }

    @Test
    void testSetAndGetStatus() {
        contract.setStatus(Status.IMPLEMENTED);
        assertEquals(Status.IMPLEMENTED, contract.getStatus());
    }

    @Test
    void testSetAndGetCustomer() {
        Customer customer = new Customer();
        customer.setId(5L);
        customer.setName("Test Customer");

        contract.setCustomer(customer);
        assertEquals(customer, contract.getCustomer());
        assertEquals(5L, contract.getCustomer().getId());
    }

    @Test
    void testSetAndGetUser() {
        User user = new User();
        user.setId(10L);
        user.setUsername("testuser");

        contract.setUser(user);
        assertEquals(user, contract.getUser());
        assertEquals(10L, contract.getUser().getId());
    }

    @Test
    void testStatusEnumValues() {
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
        assertNull(contract.getName());

        contract.setContent(null);
        assertNull(contract.getContent());

        contract.setValue(null);
        assertNull(contract.getValue());
    }

    @Test
    void testDateRange() {
        LocalDate start = LocalDate.of(2024, 1, 1);
        LocalDate end = LocalDate.of(2024, 12, 31);

        contract.setBeginDate(start);
        contract.setEndDate(end);

        assertTrue(contract.getBeginDate().isBefore(contract.getEndDate()));
    }
}
