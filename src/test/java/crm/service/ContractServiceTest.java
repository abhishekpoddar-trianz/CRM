package crm.service;

import crm.entity.Contract;
import crm.entity.Customer;
import crm.entity.Status;
import crm.entity.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class ContractServiceTest {

    @Test
    public void testServiceInterface() {
        assertTrue(ContractService.class.isInterface());
    }

    @Test
    public void testFindByNameMethodExists() throws NoSuchMethodException {
        java.lang.reflect.Method method = ContractService.class.getMethod("findByName", String.class);
        assertNotNull(method);
        assertEquals("findByName", method.getName());
        assertEquals(Contract.class, method.getReturnType());
    }

    @Test
    public void testListAllContractsMethodExists() throws NoSuchMethodException {
        java.lang.reflect.Method method = ContractService.class.getMethod("listAllContracts");
        assertNotNull(method);
        assertEquals("listAllContracts", method.getName());
        assertEquals(Iterable.class, method.getReturnType());
    }

    @Test
    public void testShowContractMethodExists() throws NoSuchMethodException {
        java.lang.reflect.Method method = ContractService.class.getMethod("showContract", Long.class);
        assertNotNull(method);
        assertEquals("showContract", method.getName());
        assertEquals(Contract.class, method.getReturnType());
    }

    @Test
    public void testFindAllByValueLessThanEqualMethodExists() throws NoSuchMethodException {
        java.lang.reflect.Method method = ContractService.class.getMethod("findAllByValueLessThanEqual", BigDecimal.class);
        assertNotNull(method);
        assertEquals("findAllByValueLessThanEqual", method.getName());
        assertEquals(Iterable.class, method.getReturnType());
    }

    @Test
    public void testFindAllByValueGreaterThanEqualMethodExists() throws NoSuchMethodException {
        java.lang.reflect.Method method = ContractService.class.getMethod("findAllByValueGreaterThanEqual", BigDecimal.class);
        assertNotNull(method);
        assertEquals("findAllByValueGreaterThanEqual", method.getName());
        assertEquals(Iterable.class, method.getReturnType());
    }

    @Test
    public void testFindAllByBeginDateMethodExists() throws NoSuchMethodException {
        java.lang.reflect.Method method = ContractService.class.getMethod("findAllByBeginDate", LocalDate.class);
        assertNotNull(method);
        assertEquals("findAllByBeginDate", method.getName());
        assertEquals(Iterable.class, method.getReturnType());
    }

    @Test
    public void testFindAllByBeginDateBeforeMethodExists() throws NoSuchMethodException {
        java.lang.reflect.Method method = ContractService.class.getMethod("findAllByBeginDateBefore", LocalDate.class);
        assertNotNull(method);
        assertEquals("findAllByBeginDateBefore", method.getName());
        assertEquals(Iterable.class, method.getReturnType());
    }

    @Test
    public void testFindAllByBeginDateAfterMethodExists() throws NoSuchMethodException {
        java.lang.reflect.Method method = ContractService.class.getMethod("findAllByBeginDateAfter", LocalDate.class);
        assertNotNull(method);
        assertEquals("findAllByBeginDateAfter", method.getName());
        assertEquals(Iterable.class, method.getReturnType());
    }

    @Test
    public void testFindAllByEndDateMethodExists() throws NoSuchMethodException {
        java.lang.reflect.Method method = ContractService.class.getMethod("findAllByEndDate", LocalDate.class);
        assertNotNull(method);
        assertEquals("findAllByEndDate", method.getName());
        assertEquals(Iterable.class, method.getReturnType());
    }

    @Test
    public void testFindAllByEndDateBeforeMethodExists() throws NoSuchMethodException {
        java.lang.reflect.Method method = ContractService.class.getMethod("findAllByEndDateBefore", LocalDate.class);
        assertNotNull(method);
        assertEquals("findAllByEndDateBefore", method.getName());
        assertEquals(Iterable.class, method.getReturnType());
    }

    @Test
    public void testFindAllByEndDateAfterMethodExists() throws NoSuchMethodException {
        java.lang.reflect.Method method = ContractService.class.getMethod("findAllByEndDateAfter", LocalDate.class);
        assertNotNull(method);
        assertEquals("findAllByEndDateAfter", method.getName());
        assertEquals(Iterable.class, method.getReturnType());
    }

    @Test
    public void testFindAllByStatusMethodExists() throws NoSuchMethodException {
        java.lang.reflect.Method method = ContractService.class.getMethod("findAllByStatus", Status.class);
        assertNotNull(method);
        assertEquals("findAllByStatus", method.getName());
        assertEquals(Iterable.class, method.getReturnType());
    }

    @Test
    public void testFindAllByCustomerMethodExists() throws NoSuchMethodException {
        java.lang.reflect.Method method = ContractService.class.getMethod("findAllByCustomer", Customer.class);
        assertNotNull(method);
        assertEquals("findAllByCustomer", method.getName());
        assertEquals(Iterable.class, method.getReturnType());
    }

    @Test
    public void testFindAllByCustomerAndUserMethodExists() throws NoSuchMethodException {
        java.lang.reflect.Method method = ContractService.class.getMethod("findAllByCustomerAndUser", Customer.class, User.class);
        assertNotNull(method);
        assertEquals("findAllByCustomerAndUser", method.getName());
        assertEquals(Iterable.class, method.getReturnType());
    }

    @Test
    public void testFindAllByUserMethodExists() throws NoSuchMethodException {
        java.lang.reflect.Method method = ContractService.class.getMethod("findAllByUser", User.class);
        assertNotNull(method);
        assertEquals("findAllByUser", method.getName());
        assertEquals(Iterable.class, method.getReturnType());
    }

    @Test
    public void testSaveContractMethodExists() throws NoSuchMethodException {
        java.lang.reflect.Method method = ContractService.class.getMethod("saveContract", Contract.class);
        assertNotNull(method);
        assertEquals("saveContract", method.getName());
        assertEquals(void.class, method.getReturnType());
    }

    @Test
    public void testServicePackage() {
        assertEquals("crm.service", ContractService.class.getPackage().getName());
    }

    @Test
    public void testServiceName() {
        assertEquals("ContractService", ContractService.class.getSimpleName());
    }

    @Test
    public void testServiceIsPublic() {
        assertTrue(java.lang.reflect.Modifier.isPublic(ContractService.class.getModifiers()));
    }

    @Test
    public void testServiceMethodCount() {
        java.lang.reflect.Method[] declaredMethods = ContractService.class.getDeclaredMethods();
        assertEquals(16, declaredMethods.length); // All the methods defined in the interface
    }

    @Test
    public void testServiceIsInterface() {
        assertTrue(ContractService.class.isInterface());
        assertFalse(ContractService.class.isEnum());
        assertFalse(ContractService.class.isAnnotation());
    }

    @Test
    public void testMethodParameterTypes() throws NoSuchMethodException {
        // Test BigDecimal parameter methods
        java.lang.reflect.Method bigDecimalMethod = ContractService.class.getMethod("findAllByValueLessThanEqual", BigDecimal.class);
        assertEquals(BigDecimal.class, bigDecimalMethod.getParameterTypes()[0]);

        // Test LocalDate parameter methods
        java.lang.reflect.Method localDateMethod = ContractService.class.getMethod("findAllByBeginDate", LocalDate.class);
        assertEquals(LocalDate.class, localDateMethod.getParameterTypes()[0]);

        // Test Status parameter method
        java.lang.reflect.Method statusMethod = ContractService.class.getMethod("findAllByStatus", Status.class);
        assertEquals(Status.class, statusMethod.getParameterTypes()[0]);

        // Test Customer parameter method
        java.lang.reflect.Method customerMethod = ContractService.class.getMethod("findAllByCustomer", Customer.class);
        assertEquals(Customer.class, customerMethod.getParameterTypes()[0]);

        // Test User parameter method
        java.lang.reflect.Method userMethod = ContractService.class.getMethod("findAllByUser", User.class);
        assertEquals(User.class, userMethod.getParameterTypes()[0]);

        // Test multi-parameter method
        java.lang.reflect.Method multiParamMethod = ContractService.class.getMethod("findAllByCustomerAndUser", Customer.class, User.class);
        assertEquals(2, multiParamMethod.getParameterCount());
        assertEquals(Customer.class, multiParamMethod.getParameterTypes()[0]);
        assertEquals(User.class, multiParamMethod.getParameterTypes()[1]);
    }

    @Test
    public void testMethodReturnTypes() throws NoSuchMethodException {
        // Test Contract return type methods
        java.lang.reflect.Method contractReturnMethod = ContractService.class.getMethod("findByName", String.class);
        assertEquals(Contract.class, contractReturnMethod.getReturnType());

        java.lang.reflect.Method showContractMethod = ContractService.class.getMethod("showContract", Long.class);
        assertEquals(Contract.class, showContractMethod.getReturnType());

        // Test Iterable return type methods
        java.lang.reflect.Method iterableReturnMethod = ContractService.class.getMethod("listAllContracts");
        assertEquals(Iterable.class, iterableReturnMethod.getReturnType());

        java.lang.reflect.Method findAllByValueMethod = ContractService.class.getMethod("findAllByValueLessThanEqual", BigDecimal.class);
        assertEquals(Iterable.class, findAllByValueMethod.getReturnType());

        // Test void return type method
        java.lang.reflect.Method voidReturnMethod = ContractService.class.getMethod("saveContract", Contract.class);
        assertEquals(void.class, voidReturnMethod.getReturnType());
    }

    @Test
    public void testAllMethodsPresent() {
        java.lang.reflect.Method[] declaredMethods = ContractService.class.getDeclaredMethods();
        assertEquals(16, declaredMethods.length);

        // Check for specific key methods
        boolean hasFindByName = false;
        boolean hasListAllContracts = false;
        boolean hasSaveContract = false;
        boolean hasFindAllByStatus = false;

        for (java.lang.reflect.Method method : declaredMethods) {
            switch (method.getName()) {
                case "findByName": hasFindByName = true; break;
                case "listAllContracts": hasListAllContracts = true; break;
                case "saveContract": hasSaveContract = true; break;
                case "findAllByStatus": hasFindAllByStatus = true; break;
            }
        }

        assertTrue(hasFindByName);
        assertTrue(hasListAllContracts);
        assertTrue(hasSaveContract);
        assertTrue(hasFindAllByStatus);
    }
}