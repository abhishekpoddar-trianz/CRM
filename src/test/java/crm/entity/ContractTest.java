package crm.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ContractTest {

    private Contract contract;
    private Customer customer;
    private User user;

    @BeforeEach
    public void setUp() {
        contract = new Contract();
        customer = new Customer();
        customer.setId(1L);
        customer.setName("Test Customer");

        user = new User();
        user.setId(1L);
        user.setUsername("testuser");
    }

    @Test
    public void testContractConstructor() {
        assertNotNull(contract);
    }

    @Test
    public void testContractBuilderConstructor() {
        Contract builtContract = Contract.builder()
                .id(1L)
                .name("Contract 1")
                .content("Contract content")
                .value(new BigDecimal("10000.00"))
                .beginDate(LocalDate.of(2024, 1, 1))
                .endDate(LocalDate.of(2024, 12, 31))
                .status(Status.PROPOSED)
                .customer(customer)
                .user(user)
                .build();
        assertNotNull(builtContract);
        assertEquals(1L, builtContract.getId());
        assertEquals("Contract 1", builtContract.getName());
    }

    @Test
    public void testAllArgsConstructor() {
        Contract contractAll = new Contract(1L, "Test", "Content", new BigDecimal("5000"),
                LocalDate.now(), LocalDate.now().plusDays(30), Status.DONE, customer, user);
        assertNotNull(contractAll);
        assertEquals(1L, contractAll.getId());
    }

    @Test
    public void testSetAndGetId() {
        contract.setId(50L);
        assertEquals(50L, contract.getId());
    }

    @Test
    public void testSetAndGetName() {
        contract.setName("My Contract");
        assertEquals("My Contract", contract.getName());
    }

    @Test
    public void testSetAndGetContent() {
        contract.setContent("This is contract content");
        assertEquals("This is contract content", contract.getContent());
    }

    @Test
    public void testSetAndGetValue() {
        BigDecimal value = new BigDecimal("15000.50");
        contract.setValue(value);
        assertEquals(value, contract.getValue());
    }

    @Test
    public void testSetAndGetBeginDate() {
        LocalDate beginDate = LocalDate.of(2024, 6, 1);
        contract.setBeginDate(beginDate);
        assertEquals(beginDate, contract.getBeginDate());
    }

    @Test
    public void testSetAndGetEndDate() {
        LocalDate endDate = LocalDate.of(2025, 6, 1);
        contract.setEndDate(endDate);
        assertEquals(endDate, contract.getEndDate());
    }

    @Test
    public void testSetAndGetStatus() {
        contract.setStatus(Status.NEGOTIATED);
        assertEquals(Status.NEGOTIATED, contract.getStatus());
    }

    @Test
    public void testSetAndGetCustomer() {
        contract.setCustomer(customer);
        assertNotNull(contract.getCustomer());
        assertEquals(1L, contract.getCustomer().getId());
    }

    @Test
    public void testSetAndGetUser() {
        contract.setUser(user);
        assertNotNull(contract.getUser());
        assertEquals(1L, contract.getUser().getId());
    }

    @Test
    public void testSetNameNull() {
        contract.setName(null);
        assertNull(contract.getName());
    }

    @Test
    public void testSetValueNull() {
        contract.setValue(null);
        assertNull(contract.getValue());
    }

    @Test
    public void testSetStatusNull() {
        contract.setStatus(null);
        assertNull(contract.getStatus());
    }

    @Test
    public void testAllStatusValues() {
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
    public void testToString() {
        contract.setName("Test");
        String result = contract.toString();
        assertNotNull(result);
    }
}
