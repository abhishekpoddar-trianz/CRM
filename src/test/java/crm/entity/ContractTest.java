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
        customer = Customer.builder()
                .id(1L)
                .name("Test Customer")
                .email("customer@test.com")
                .build();

        user = User.builder()
                .id(1L)
                .username("testuser")
                .email("user@test.com")
                .build();

        contract = Contract.builder()
                .id(1L)
                .name("Test Contract")
                .content("Contract Content")
                .value(new BigDecimal("10000.00"))
                .beginDate(LocalDate.of(2023, 1, 1))
                .endDate(LocalDate.of(2023, 12, 31))
                .status(Status.PROPOSED)
                .customer(customer)
                .user(user)
                .build();
    }

    @Test
    public void testContractBuilder() {
        assertNotNull(contract);
        assertEquals(1L, contract.getId());
        assertEquals("Test Contract", contract.getName());
        assertEquals("Contract Content", contract.getContent());
        assertEquals(new BigDecimal("10000.00"), contract.getValue());
        assertEquals(LocalDate.of(2023, 1, 1), contract.getBeginDate());
        assertEquals(LocalDate.of(2023, 12, 31), contract.getEndDate());
        assertEquals(Status.PROPOSED, contract.getStatus());
        assertEquals(customer, contract.getCustomer());
        assertEquals(user, contract.getUser());
    }

    @Test
    public void testContractNoArgsConstructor() {
        Contract emptyContract = new Contract();
        assertNotNull(emptyContract);
        assertNull(emptyContract.getId());
        assertNull(emptyContract.getName());
    }

    @Test
    public void testContractAllArgsConstructor() {
        Contract newContract = new Contract(2L, "New Contract", "New Content",
                new BigDecimal("20000.00"), LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 12, 31), Status.NEGOTIATED, customer, user);

        assertNotNull(newContract);
        assertEquals(2L, newContract.getId());
        assertEquals("New Contract", newContract.getName());
        assertEquals("New Content", newContract.getContent());
        assertEquals(Status.NEGOTIATED, newContract.getStatus());
    }

    @Test
    public void testSettersAndGetters() {
        contract.setName("Updated Contract");
        contract.setValue(new BigDecimal("15000.00"));
        contract.setStatus(Status.IMPLEMENTED);

        assertEquals("Updated Contract", contract.getName());
        assertEquals(new BigDecimal("15000.00"), contract.getValue());
        assertEquals(Status.IMPLEMENTED, contract.getStatus());
    }

    @Test
    public void testContractWithNullValues() {
        Contract nullContract = Contract.builder()
                .id(null)
                .name(null)
                .content(null)
                .value(null)
                .beginDate(null)
                .endDate(null)
                .status(null)
                .customer(null)
                .user(null)
                .build();

        assertNotNull(nullContract);
        assertNull(nullContract.getId());
        assertNull(nullContract.getName());
        assertNull(nullContract.getStatus());
    }

    @Test
    public void testDateRange() {
        contract.setBeginDate(LocalDate.of(2023, 6, 1));
        contract.setEndDate(LocalDate.of(2023, 6, 30));

        assertTrue(contract.getBeginDate().isBefore(contract.getEndDate()));
    }

    @Test
    public void testStatusValues() {
        contract.setStatus(Status.PROPOSED);
        assertEquals(Status.PROPOSED, contract.getStatus());

        contract.setStatus(Status.NEGOTIATED);
        assertEquals(Status.NEGOTIATED, contract.getStatus());

        contract.setStatus(Status.IMPLEMENTED);
        assertEquals(Status.IMPLEMENTED, contract.getStatus());

        contract.setStatus(Status.DONE);
        assertEquals(Status.DONE, contract.getStatus());
    }
}
