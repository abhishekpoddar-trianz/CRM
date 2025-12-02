package crm.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class ContractTest {

    private Contract contract;
    private Customer customer;
    private User user;

    @BeforeEach
    public void setUp() {
        contract = new Contract();
        customer = new Customer();
        customer.setId(1L);
        user = new User();
        user.setId(1L);
    }

    @Test
    public void testSetAndGetId() {
        contract.setId(1L);
        assertEquals(1L, contract.getId());
    }

    @Test
    public void testSetAndGetName() {
        contract.setName("Test Contract");
        assertEquals("Test Contract", contract.getName());
    }

    @Test
    public void testSetAndGetContent() {
        contract.setContent("Contract content");
        assertEquals("Contract content", contract.getContent());
    }

    @Test
    public void testSetAndGetValue() {
        BigDecimal value = new BigDecimal("10000.50");
        contract.setValue(value);
        assertEquals(value, contract.getValue());
    }

    @Test
    public void testSetAndGetBeginDate() {
        LocalDate beginDate = LocalDate.of(2024, 1, 1);
        contract.setBeginDate(beginDate);
        assertEquals(beginDate, contract.getBeginDate());
    }

    @Test
    public void testSetAndGetEndDate() {
        LocalDate endDate = LocalDate.of(2024, 12, 31);
        contract.setEndDate(endDate);
        assertEquals(endDate, contract.getEndDate());
    }

    @Test
    public void testSetAndGetStatus() {
        contract.setStatus(Status.PROPOSED);
        assertEquals(Status.PROPOSED, contract.getStatus());
    }

    @Test
    public void testSetAndGetCustomer() {
        contract.setCustomer(customer);
        assertEquals(customer, contract.getCustomer());
    }

    @Test
    public void testSetAndGetUser() {
        contract.setUser(user);
        assertEquals(user, contract.getUser());
    }

    @Test
    public void testBuilderPattern() {
        LocalDate begin = LocalDate.of(2024, 1, 1);
        LocalDate end = LocalDate.of(2024, 12, 31);
        BigDecimal value = new BigDecimal("5000.00");

        Contract contractBuilt = Contract.builder()
                .id(1L)
                .name("Builder Contract")
                .content("Content")
                .value(value)
                .beginDate(begin)
                .endDate(end)
                .status(Status.NEGOTIATED)
                .customer(customer)
                .user(user)
                .build();

        assertEquals(1L, contractBuilt.getId());
        assertEquals("Builder Contract", contractBuilt.getName());
        assertEquals(value, contractBuilt.getValue());
        assertEquals(Status.NEGOTIATED, contractBuilt.getStatus());
    }

    @Test
    public void testAllArgsConstructor() {
        LocalDate begin = LocalDate.of(2024, 1, 1);
        LocalDate end = LocalDate.of(2024, 12, 31);
        BigDecimal value = new BigDecimal("3000.00");

        Contract contractWithArgs = new Contract(
            1L, "Contract Name", "Content", value,
            begin, end, Status.IMPLEMENTED, customer, user
        );

        assertEquals(1L, contractWithArgs.getId());
        assertEquals("Contract Name", contractWithArgs.getName());
        assertEquals(Status.IMPLEMENTED, contractWithArgs.getStatus());
    }

    @Test
    public void testNoArgsConstructor() {
        Contract emptyContract = new Contract();
        assertNull(emptyContract.getId());
        assertNull(emptyContract.getName());
        assertNull(emptyContract.getStatus());
    }

    @Test
    public void testValueZero() {
        contract.setValue(BigDecimal.ZERO);
        assertEquals(BigDecimal.ZERO, contract.getValue());
    }

    @Test
    public void testValueNull() {
        contract.setValue(null);
        assertNull(contract.getValue());
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
}
