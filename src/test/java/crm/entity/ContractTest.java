package crm.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class ContractTest {

    private Contract contract;

    @BeforeEach
    public void setUp() {
        contract = new Contract();
    }

    @Test
    public void testNoArgsConstructor() {
        Contract newContract = new Contract();
        assertNotNull(newContract);
    }

    @Test
    public void testSettersAndGetters() {
        contract.setId(1L);
        contract.setName("Test Contract");
        contract.setValue(BigDecimal.valueOf(1000.00));
        contract.setBeginDate(LocalDate.of(2024, 1, 1));
        contract.setEndDate(LocalDate.of(2024, 12, 31));
        contract.setStatus(Status.PROPOSED);

        assertEquals(1L, contract.getId());
        assertEquals("Test Contract", contract.getName());
        assertEquals(BigDecimal.valueOf(1000.00), contract.getValue());
        assertEquals(LocalDate.of(2024, 1, 1), contract.getBeginDate());
        assertEquals(LocalDate.of(2024, 12, 31), contract.getEndDate());
        assertEquals(Status.PROPOSED, contract.getStatus());
    }

    @Test
    public void testCustomerRelationship() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Test Customer");

        contract.setCustomer(customer);
        assertEquals(customer, contract.getCustomer());
    }

    @Test
    public void testUserRelationship() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");

        contract.setUser(user);
        assertEquals(user, contract.getUser());
    }

    @Test
    public void testEntityAnnotation() {
        assertTrue(Contract.class.isAnnotationPresent(jakarta.persistence.Entity.class));
    }

    @Test
    public void testDataAnnotation() {
        assertTrue(Contract.class.isAnnotationPresent(lombok.Data.class));
    }

    @Test
    public void testEqualsAndHashCode() {
        Contract contract1 = new Contract();
        contract1.setId(1L);
        contract1.setName("Contract1");

        Contract contract2 = new Contract();
        contract2.setId(1L);
        contract2.setName("Contract1");

        assertEquals(contract1, contract2);
        assertEquals(contract1.hashCode(), contract2.hashCode());
    }
}