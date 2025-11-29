package crm.service;

import crm.entity.Category;
import crm.entity.Customer;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CustomerServiceTest {

    @Test
    void testCustomerServiceInterface() {
        assertTrue(CustomerService.class.isInterface());
    }

    @Test
    void testGetMaxIdMethodExists() {
        assertDoesNotThrow(() -> {
            CustomerService.class.getDeclaredMethod("getMaxId");
        });
    }

    @Test
    void testListAllCustomersMethodExists() {
        assertDoesNotThrow(() -> {
            CustomerService.class.getDeclaredMethod("listAllCustomers");
        });
    }

    @Test
    void testShowCustomerMethodExists() {
        assertDoesNotThrow(() -> {
            CustomerService.class.getDeclaredMethod("showCustomer", Long.class);
        });
    }

    @Test
    void testSaveCustomerMethodExists() {
        assertDoesNotThrow(() -> {
            CustomerService.class.getDeclaredMethod("saveCustomer", Customer.class);
        });
    }

    @Test
    void testFindByEnabledMethodsExist() {
        assertDoesNotThrow(() -> {
            CustomerService.class.getDeclaredMethod("findAllByEnabledTrue");
            CustomerService.class.getDeclaredMethod("findAllByEnabledFalse");
        });
    }

    @Test
    void testFindByNameMethodsExist() {
        assertDoesNotThrow(() -> {
            CustomerService.class.getDeclaredMethod("findOneByEnabledTrueAndName", String.class);
            CustomerService.class.getDeclaredMethod("findOneByEnabledFalseAndName", String.class);
            CustomerService.class.getDeclaredMethod("findOneByName", String.class);
        });
    }

    @Test
    void testFindByEmailMethodsExist() {
        assertDoesNotThrow(() -> {
            CustomerService.class.getDeclaredMethod("findByEnabledTrueAndEmail", String.class);
            CustomerService.class.getDeclaredMethod("findByEnabledFalseAndEmail", String.class);
            CustomerService.class.getDeclaredMethod("findByEmail", String.class);
        });
    }

    @Test
    void testReturnTypes() throws Exception {
        assertEquals(Long.class, CustomerService.class.getDeclaredMethod("getMaxId").getReturnType());
        assertEquals(Iterable.class, CustomerService.class.getDeclaredMethod("listAllCustomers").getReturnType());
        assertEquals(Customer.class, CustomerService.class.getDeclaredMethod("showCustomer", Long.class).getReturnType());
        assertEquals(void.class, CustomerService.class.getDeclaredMethod("saveCustomer", Customer.class).getReturnType());
    }
}