package crm.service;

import crm.entity.Category;
import crm.entity.Customer;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Set;

public class CustomerServiceTest {

    @Test
    public void testServiceInterface() {
        assertTrue(CustomerService.class.isInterface());
    }

    @Test
    public void testListAllCustomersMethodExists() throws NoSuchMethodException {
        java.lang.reflect.Method method = CustomerService.class.getMethod("listAllCustomers");
        assertNotNull(method);
        assertEquals(Iterable.class, method.getReturnType());
    }

    @Test
    public void testShowCustomerMethodExists() throws NoSuchMethodException {
        java.lang.reflect.Method method = CustomerService.class.getMethod("showCustomer", Long.class);
        assertNotNull(method);
        assertEquals(Customer.class, method.getReturnType());
    }

    @Test
    public void testSaveCustomerMethodExists() throws NoSuchMethodException {
        java.lang.reflect.Method method = CustomerService.class.getMethod("saveCustomer", Customer.class);
        assertNotNull(method);
        assertEquals(void.class, method.getReturnType());
    }

    @Test
    public void testFindByNameMethodExists() throws NoSuchMethodException {
        java.lang.reflect.Method method = CustomerService.class.getMethod("findOneByName", String.class);
        assertNotNull(method);
        assertEquals(Customer.class, method.getReturnType());
    }

    @Test
    public void testServicePackage() {
        assertEquals("crm.service", CustomerService.class.getPackage().getName());
    }

    @Test
    public void testServiceName() {
        assertEquals("CustomerService", CustomerService.class.getSimpleName());
    }

    @Test
    public void testServiceIsPublic() {
        assertTrue(java.lang.reflect.Modifier.isPublic(CustomerService.class.getModifiers()));
    }

    @Test
    public void testServiceIsInterface() {
        assertTrue(CustomerService.class.isInterface());
        assertFalse(CustomerService.class.isEnum());
        assertFalse(CustomerService.class.isAnnotation());
    }
}