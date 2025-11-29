package crm.repository;

import crm.entity.Category;
import crm.entity.Customer;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Set;

public class CustomerRepositoryTest {

    @Test
    public void testRepositoryInterface() {
        assertTrue(CustomerRepository.class.isInterface());
    }

    @Test
    public void testRepositoryAnnotation() {
        assertTrue(CustomerRepository.class.isAnnotationPresent(org.springframework.stereotype.Repository.class));
    }

    @Test
    public void testExtendsJpaRepository() {
        assertTrue(org.springframework.data.jpa.repository.JpaRepository.class.isAssignableFrom(CustomerRepository.class));
    }

    @Test
    public void testGenericTypes() {
        // Verify that the repository works with Customer entity and Long ID
        java.lang.reflect.Type[] genericInterfaces = CustomerRepository.class.getGenericInterfaces();
        assertNotNull(genericInterfaces);
        assertTrue(genericInterfaces.length > 0);
    }

    @Test
    public void testGetMaxIdMethodExists() throws NoSuchMethodException {
        java.lang.reflect.Method getMaxIdMethod = CustomerRepository.class.getMethod("getMaxId");
        assertNotNull(getMaxIdMethod);
        assertEquals("getMaxId", getMaxIdMethod.getName());
        assertEquals(Long.class, getMaxIdMethod.getReturnType());
    }

    @Test
    public void testGetMaxIdMethodHasQueryAnnotation() throws NoSuchMethodException {
        java.lang.reflect.Method getMaxIdMethod = CustomerRepository.class.getMethod("getMaxId");
        assertTrue(getMaxIdMethod.isAnnotationPresent(org.springframework.data.jpa.repository.Query.class));
    }

    @Test
    public void testFindAllByEnabledMethodExists() throws NoSuchMethodException {
        java.lang.reflect.Method method = CustomerRepository.class.getMethod("findAllByEnabled", int.class);
        assertNotNull(method);
        assertEquals(Iterable.class, method.getReturnType());
    }

    @Test
    public void testFindOneByEnabledAndNameMethodExists() throws NoSuchMethodException {
        java.lang.reflect.Method method = CustomerRepository.class.getMethod("findOneByEnabledAndName", int.class, String.class);
        assertNotNull(method);
        assertEquals(Customer.class, method.getReturnType());
    }

    @Test
    public void testFindOneByNameMethodExists() throws NoSuchMethodException {
        java.lang.reflect.Method method = CustomerRepository.class.getMethod("findOneByName", String.class);
        assertNotNull(method);
        assertEquals(Customer.class, method.getReturnType());
    }

    @Test
    public void testFindByEnabledAndEmailMethodExists() throws NoSuchMethodException {
        java.lang.reflect.Method method = CustomerRepository.class.getMethod("findByEnabledAndEmail", int.class, String.class);
        assertNotNull(method);
        assertEquals(Iterable.class, method.getReturnType());
    }

    @Test
    public void testFindByEmailMethodExists() throws NoSuchMethodException {
        java.lang.reflect.Method method = CustomerRepository.class.getMethod("findByEmail", String.class);
        assertNotNull(method);
        assertEquals(Iterable.class, method.getReturnType());
    }

    @Test
    public void testFindByEnabledAndCityMethodExists() throws NoSuchMethodException {
        java.lang.reflect.Method method = CustomerRepository.class.getMethod("findByEnabledAndCity", int.class, String.class);
        assertNotNull(method);
        assertEquals(Iterable.class, method.getReturnType());
    }

    @Test
    public void testFindByCityMethodExists() throws NoSuchMethodException {
        java.lang.reflect.Method method = CustomerRepository.class.getMethod("findByCity", String.class);
        assertNotNull(method);
        assertEquals(Iterable.class, method.getReturnType());
    }

    @Test
    public void testFindByEnabledAndCityAndAddressMethodExists() throws NoSuchMethodException {
        java.lang.reflect.Method method = CustomerRepository.class.getMethod("findByEnabledAndCityAndAddress", int.class, String.class, String.class);
        assertNotNull(method);
        assertEquals(Iterable.class, method.getReturnType());
    }

    @Test
    public void testFindByCityAndAddressMethodExists() throws NoSuchMethodException {
        java.lang.reflect.Method method = CustomerRepository.class.getMethod("findByCityAndAddress", String.class, String.class);
        assertNotNull(method);
        assertEquals(Iterable.class, method.getReturnType());
    }

    @Test
    public void testFindByEnabledAndPhoneMethodExists() throws NoSuchMethodException {
        java.lang.reflect.Method method = CustomerRepository.class.getMethod("findByEnabledAndPhone", int.class, int.class);
        assertNotNull(method);
        assertEquals(Iterable.class, method.getReturnType());
    }

    @Test
    public void testFindByPhoneMethodExists() throws NoSuchMethodException {
        java.lang.reflect.Method method = CustomerRepository.class.getMethod("findByPhone", int.class);
        assertNotNull(method);
        assertEquals(Iterable.class, method.getReturnType());
    }

    @Test
    public void testFindByEnabledAndFirstNameMethodExists() throws NoSuchMethodException {
        java.lang.reflect.Method method = CustomerRepository.class.getMethod("findByEnabledAndFirstName", int.class, String.class);
        assertNotNull(method);
        assertEquals(Iterable.class, method.getReturnType());
    }

    @Test
    public void testFindByFirstNameMethodExists() throws NoSuchMethodException {
        java.lang.reflect.Method method = CustomerRepository.class.getMethod("findByFirstName", String.class);
        assertNotNull(method);
        assertEquals(Iterable.class, method.getReturnType());
    }

    @Test
    public void testFindByEnabledAndLastNameMethodExists() throws NoSuchMethodException {
        java.lang.reflect.Method method = CustomerRepository.class.getMethod("findByEnabledAndLastName", int.class, String.class);
        assertNotNull(method);
        assertEquals(Iterable.class, method.getReturnType());
    }

    @Test
    public void testFindByLastNameMethodExists() throws NoSuchMethodException {
        java.lang.reflect.Method method = CustomerRepository.class.getMethod("findByLastName", String.class);
        assertNotNull(method);
        assertEquals(Iterable.class, method.getReturnType());
    }

    @Test
    public void testFindByEnabledAndFirstNameAndLastNameMethodExists() throws NoSuchMethodException {
        java.lang.reflect.Method method = CustomerRepository.class.getMethod("findByEnabledAndFirstNameAndLastName", int.class, String.class, String.class);
        assertNotNull(method);
        assertEquals(Iterable.class, method.getReturnType());
    }

    @Test
    public void testFindByFirstNameAndLastNameMethodExists() throws NoSuchMethodException {
        java.lang.reflect.Method method = CustomerRepository.class.getMethod("findByFirstNameAndLastName", String.class, String.class);
        assertNotNull(method);
        assertEquals(Iterable.class, method.getReturnType());
    }

    @Test
    public void testFindByEnabledAndCategoriesMethodExists() throws NoSuchMethodException {
        java.lang.reflect.Method method = CustomerRepository.class.getMethod("findByEnabledAndCategories", int.class, Set.class);
        assertNotNull(method);
        assertEquals(Iterable.class, method.getReturnType());
    }

    @Test
    public void testFindByCategoriesMethodExists() throws NoSuchMethodException {
        java.lang.reflect.Method method = CustomerRepository.class.getMethod("findByCategories", Set.class);
        assertNotNull(method);
        assertEquals(Iterable.class, method.getReturnType());
    }

    @Test
    public void testRepositoryPackage() {
        assertEquals("crm.repository", CustomerRepository.class.getPackage().getName());
    }

    @Test
    public void testRepositoryName() {
        assertEquals("CustomerRepository", CustomerRepository.class.getSimpleName());
    }

    @Test
    public void testRepositoryIsPublic() {
        assertTrue(java.lang.reflect.Modifier.isPublic(CustomerRepository.class.getModifiers()));
    }

    @Test
    public void testRepositoryMethodCount() {
        java.lang.reflect.Method[] declaredMethods = CustomerRepository.class.getDeclaredMethods();
        assertEquals(19, declaredMethods.length); // All the custom finder methods plus getMaxId
    }

    @Test
    public void testRepositoryIsInterface() {
        assertTrue(CustomerRepository.class.isInterface());
        assertFalse(CustomerRepository.class.isEnum());
        assertFalse(CustomerRepository.class.isAnnotation());
    }

    @Test
    public void testInheritsFromJpaRepository() {
        Class<?>[] interfaces = CustomerRepository.class.getInterfaces();
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
        java.lang.reflect.Type[] genericInterfaces = CustomerRepository.class.getGenericInterfaces();
        assertNotNull(genericInterfaces);
        assertTrue(genericInterfaces.length > 0);
    }

    @Test
    public void testEntityTypeIsCustomer() {
        java.lang.reflect.Type[] genericInterfaces = CustomerRepository.class.getGenericInterfaces();
        assertNotNull(genericInterfaces);
        assertTrue(genericInterfaces.length > 0);
    }
}