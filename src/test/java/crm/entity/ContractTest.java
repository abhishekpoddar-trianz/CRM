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
    void testConstructor() {
        Contract newContract = new Contract();
        assertNotNull(newContract);
    }

    @Test
    void testBuilderConstructor() {
        Customer customer = new Customer();
        customer.setId(1L);
        User user = new User();
        user.setId(1L);

        Contract builtContract = Contract.builder()
                .id(1L)
                .name("Test Contract")
                .content("Contract content")
                .value(new BigDecimal("1000.00"))
                .beginDate(LocalDate.of(2023, 1, 1))
                .endDate(LocalDate.of(2023, 12, 31))
                .status(Status.PROPOSED)
                .customer(customer)
                .user(user)
                .build();

        assertNotNull(builtContract);
        assertEquals(1L, builtContract.getId());
        assertEquals("Test Contract", builtContract.getName());
    }

    @Test
    void testAllArgsConstructor() {
        Customer customer = new Customer();
        User user = new User();
        Contract newContract = new Contract(1L, "Test Contract", "Content",
                                          new BigDecimal("1000.00"),
                                          LocalDate.of(2023, 1, 1),
                                          LocalDate.of(2023, 12, 31),
                                          Status.PROPOSED, customer, user);
        assertNotNull(newContract);
        assertEquals(1L, newContract.getId());
        assertEquals("Test Contract", newContract.getName());
    }

    @Test
    void testGettersAndSetters() {
        Customer customer = new Customer();
        customer.setId(1L);
        User user = new User();
        user.setId(1L);

        contract.setId(1L);
        contract.setName("Test Contract");
        contract.setContent("Contract content");
        contract.setValue(new BigDecimal("1000.00"));
        contract.setBeginDate(LocalDate.of(2023, 1, 1));
        contract.setEndDate(LocalDate.of(2023, 12, 31));
        contract.setStatus(Status.PROPOSED);
        contract.setCustomer(customer);
        contract.setUser(user);

        assertEquals(1L, contract.getId());
        assertEquals("Test Contract", contract.getName());
        assertEquals("Contract content", contract.getContent());
        assertEquals(new BigDecimal("1000.00"), contract.getValue());
        assertEquals(LocalDate.of(2023, 1, 1), contract.getBeginDate());
        assertEquals(LocalDate.of(2023, 12, 31), contract.getEndDate());
        assertEquals(Status.PROPOSED, contract.getStatus());
        assertEquals(customer, contract.getCustomer());
        assertEquals(user, contract.getUser());
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
    void testDateHandling() {
        LocalDate beginDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 12, 31);

        contract.setBeginDate(beginDate);
        contract.setEndDate(endDate);

        assertEquals(beginDate, contract.getBeginDate());
        assertEquals(endDate, contract.getEndDate());
        assertTrue(contract.getEndDate().isAfter(contract.getBeginDate()));
    }

    @Test
    void testEqualsAndHashCode() {
        Contract contract1 = new Contract();
        contract1.setId(1L);
        contract1.setName("Test Contract");

        Contract contract2 = new Contract();
        contract2.setId(1L);
        contract2.setName("Test Contract");

        assertEquals(contract1, contract2);
        assertEquals(contract1.hashCode(), contract2.hashCode());
    }
}