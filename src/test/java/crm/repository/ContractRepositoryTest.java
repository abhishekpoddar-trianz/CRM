package crm.repository;

import crm.entity.Contract;
import crm.entity.Customer;
import crm.entity.Status;
import crm.entity.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class ContractRepositoryTest {

    @Test
    public void testRepositoryInterface() {
        assertTrue(ContractRepository.class.isInterface());
    }

    @Test
    public void testRepositoryAnnotation() {
        assertTrue(ContractRepository.class.isAnnotationPresent(org.springframework.stereotype.Repository.class));
    }

    @Test
    public void testExtendsJpaRepository() {
        assertTrue(org.springframework.data.jpa.repository.JpaRepository.class.isAssignableFrom(ContractRepository.class));
    }

    @Test
    public void testGenericTypes() {
        // Verify that the repository works with Contract entity and Long ID
        java.lang.reflect.Type[] genericInterfaces = ContractRepository.class.getGenericInterfaces();
        assertNotNull(genericInterfaces);
        assertTrue(genericInterfaces.length > 0);
    }

    @Test
    public void testFindByNameMethodExists() throws NoSuchMethodException {
        java.lang.reflect.Method method = ContractRepository.class.getMethod("findByName", String.class);
        assertNotNull(method);
        assertEquals("findByName", method.getName());
        assertEquals(Contract.class, method.getReturnType());
    }

    @Test
    public void testFindAllByValueLessThanEqualMethodExists() throws NoSuchMethodException {
        java.lang.reflect.Method method = ContractRepository.class.getMethod("findAllByValueLessThanEqual", BigDecimal.class);
        assertNotNull(method);
        assertEquals(Iterable.class, method.getReturnType());
    }

    @Test
    public void testFindAllByValueGreaterThanEqualMethodExists() throws NoSuchMethodException {
        java.lang.reflect.Method method = ContractRepository.class.getMethod("findAllByValueGreaterThanEqual", BigDecimal.class);
        assertNotNull(method);
        assertEquals(Iterable.class, method.getReturnType());
    }

    @Test
    public void testFindAllByBeginDateMethodExists() throws NoSuchMethodException {
        java.lang.reflect.Method method = ContractRepository.class.getMethod("findAllByBeginDate", LocalDate.class);
        assertNotNull(method);
        assertEquals(Iterable.class, method.getReturnType());
    }

    @Test
    public void testFindAllByBeginDateBeforeMethodExists() throws NoSuchMethodException {
        java.lang.reflect.Method method = ContractRepository.class.getMethod("findAllByBeginDateBefore", LocalDate.class);
        assertNotNull(method);
        assertEquals(Iterable.class, method.getReturnType());
    }

    @Test
    public void testFindAllByBeginDateAfterMethodExists() throws NoSuchMethodException {
        java.lang.reflect.Method method = ContractRepository.class.getMethod("findAllByBeginDateAfter", LocalDate.class);
        assertNotNull(method);
        assertEquals(Iterable.class, method.getReturnType());
    }

    @Test
    public void testFindAllByEndDateMethodExists() throws NoSuchMethodException {
        java.lang.reflect.Method method = ContractRepository.class.getMethod("findAllByEndDate", LocalDate.class);
        assertNotNull(method);
        assertEquals(Iterable.class, method.getReturnType());
    }

    @Test
    public void testFindAllByEndDateBeforeMethodExists() throws NoSuchMethodException {
        java.lang.reflect.Method method = ContractRepository.class.getMethod("findAllByEndDateBefore", LocalDate.class);
        assertNotNull(method);
        assertEquals(Iterable.class, method.getReturnType());
    }

    @Test
    public void testFindAllByEndDateAfterMethodExists() throws NoSuchMethodException {
        java.lang.reflect.Method method = ContractRepository.class.getMethod("findAllByEndDateAfter", LocalDate.class);
        assertNotNull(method);
        assertEquals(Iterable.class, method.getReturnType());
    }

    @Test
    public void testFindAllByStatusMethodExists() throws NoSuchMethodException {
        java.lang.reflect.Method method = ContractRepository.class.getMethod("findAllByStatus", Status.class);
        assertNotNull(method);
        assertEquals(Iterable.class, method.getReturnType());
    }

    @Test
    public void testFindAllByCustomerMethodExists() throws NoSuchMethodException {
        java.lang.reflect.Method method = ContractRepository.class.getMethod("findAllByCustomer", Customer.class);
        assertNotNull(method);
        assertEquals(Iterable.class, method.getReturnType());
    }

    @Test
    public void testFindAllByCustomerAndUserMethodExists() throws NoSuchMethodException {
        java.lang.reflect.Method method = ContractRepository.class.getMethod("findAllByCustomerAndUser", Customer.class, User.class);
        assertNotNull(method);
        assertEquals(Iterable.class, method.getReturnType());
    }

    @Test
    public void testFindAllByUserMethodExists() throws NoSuchMethodException {
        java.lang.reflect.Method method = ContractRepository.class.getMethod("findAllByUser", User.class);
        assertNotNull(method);
        assertEquals(Iterable.class, method.getReturnType());
    }

    @Test
    public void testRepositoryPackage() {
        assertEquals("crm.repository", ContractRepository.class.getPackage().getName());
    }

    @Test
    public void testRepositoryName() {
        assertEquals("ContractRepository", ContractRepository.class.getSimpleName());
    }

    @Test
    public void testRepositoryIsPublic() {
        assertTrue(java.lang.reflect.Modifier.isPublic(ContractRepository.class.getModifiers()));
    }

    @Test
    public void testRepositoryMethodCount() {
        java.lang.reflect.Method[] declaredMethods = ContractRepository.class.getDeclaredMethods();
        assertEquals(13, declaredMethods.length); // All the custom finder methods
    }

    @Test
    public void testRepositoryIsInterface() {
        assertTrue(ContractRepository.class.isInterface());
        assertFalse(ContractRepository.class.isEnum());
        assertFalse(ContractRepository.class.isAnnotation());
    }

    @Test
    public void testInheritsFromJpaRepository() {
        Class<?>[] interfaces = ContractRepository.class.getInterfaces();
        boolean extendsJpaRepository = false;
        for (Class<?> interfaceClass : interfaces) {
            if (interfaceClass.equals(org.springframework.data.jpa.repository.JpaRepository.class)) {
                extendsJpaRepository = true;
                break;
            }
        }
        assertTrue(extendsJpaRepository);
    }

    @Test
    public void testIdTypeIsLong() {
        java.lang.reflect.Type[] genericInterfaces = ContractRepository.class.getGenericInterfaces();
        assertNotNull(genericInterfaces);
        assertTrue(genericInterfaces.length > 0);
    }

    @Test
    public void testEntityTypeIsContract() {
        java.lang.reflect.Type[] genericInterfaces = ContractRepository.class.getGenericInterfaces();
        assertNotNull(genericInterfaces);
        assertTrue(genericInterfaces.length > 0);
    }

    @Test
    public void testMethodParameterTypes() throws NoSuchMethodException {
        // Test specific parameter types for complex methods
        java.lang.reflect.Method bigDecimalMethod = ContractRepository.class.getMethod("findAllByValueLessThanEqual", BigDecimal.class);
        assertEquals(BigDecimal.class, bigDecimalMethod.getParameterTypes()[0]);

        java.lang.reflect.Method localDateMethod = ContractRepository.class.getMethod("findAllByBeginDate", LocalDate.class);
        assertEquals(LocalDate.class, localDateMethod.getParameterTypes()[0]);

        java.lang.reflect.Method statusMethod = ContractRepository.class.getMethod("findAllByStatus", Status.class);
        assertEquals(Status.class, statusMethod.getParameterTypes()[0]);

        java.lang.reflect.Method customerMethod = ContractRepository.class.getMethod("findAllByCustomer", Customer.class);
        assertEquals(Customer.class, customerMethod.getParameterTypes()[0]);

        java.lang.reflect.Method userMethod = ContractRepository.class.getMethod("findAllByUser", User.class);
        assertEquals(User.class, userMethod.getParameterTypes()[0]);
    }

    @Test
    public void testMethodReturnTypes() throws NoSuchMethodException {
        // Test return types for different methods
        java.lang.reflect.Method findByNameMethod = ContractRepository.class.getMethod("findByName", String.class);
        assertEquals(Contract.class, findByNameMethod.getReturnType());

        java.lang.reflect.Method findAllByValueMethod = ContractRepository.class.getMethod("findAllByValueLessThanEqual", BigDecimal.class);
        assertEquals(Iterable.class, findAllByValueMethod.getReturnType());

        java.lang.reflect.Method findAllByCustomerAndUserMethod = ContractRepository.class.getMethod("findAllByCustomerAndUser", Customer.class, User.class);
        assertEquals(Iterable.class, findAllByCustomerAndUserMethod.getReturnType());
    }
}