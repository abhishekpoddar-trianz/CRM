package crm.repository;

import crm.entity.Role;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RoleRepositoryTest {

    @Test
    public void testRepositoryInterface() {
        assertTrue(RoleRepository.class.isInterface());
    }

    @Test
    public void testRepositoryAnnotation() {
        assertTrue(RoleRepository.class.isAnnotationPresent(org.springframework.stereotype.Repository.class));
    }

    @Test
    public void testExtendsJpaRepository() {
        assertTrue(org.springframework.data.jpa.repository.JpaRepository.class.isAssignableFrom(RoleRepository.class));
    }

    @Test
    public void testGenericTypes() {
        // Verify that the repository works with Role entity and Integer ID
        java.lang.reflect.Type[] genericInterfaces = RoleRepository.class.getGenericInterfaces();
        assertNotNull(genericInterfaces);
        assertTrue(genericInterfaces.length > 0);
    }

    @Test
    public void testFindByNameMethodExists() throws NoSuchMethodException {
        java.lang.reflect.Method findByNameMethod = RoleRepository.class.getMethod("findByName", String.class);
        assertNotNull(findByNameMethod);
        assertEquals("findByName", findByNameMethod.getName());
        assertEquals(Role.class, findByNameMethod.getReturnType());
    }

    @Test
    public void testFindByNameMethodReturnType() throws NoSuchMethodException {
        java.lang.reflect.Method findByNameMethod = RoleRepository.class.getMethod("findByName", String.class);
        assertEquals(Role.class, findByNameMethod.getReturnType());
    }

    @Test
    public void testFindByNameMethodParameterTypes() throws NoSuchMethodException {
        java.lang.reflect.Method findByNameMethod = RoleRepository.class.getMethod("findByName", String.class);
        Class<?>[] parameterTypes = findByNameMethod.getParameterTypes();
        assertEquals(1, parameterTypes.length);
        assertEquals(String.class, parameterTypes[0]);
    }

    @Test
    public void testRepositoryPackage() {
        assertEquals("crm.repository", RoleRepository.class.getPackage().getName());
    }

    @Test
    public void testRepositoryName() {
        assertEquals("RoleRepository", RoleRepository.class.getSimpleName());
    }

    @Test
    public void testRepositoryIsPublic() {
        assertTrue(java.lang.reflect.Modifier.isPublic(RoleRepository.class.getModifiers()));
    }

    @Test
    public void testRepositoryMethodCount() {
        // Should have the custom findByName method plus inherited JpaRepository methods
        java.lang.reflect.Method[] declaredMethods = RoleRepository.class.getDeclaredMethods();
        assertEquals(1, declaredMethods.length); // Only the custom findByName method
    }

    @Test
    public void testRepositoryIsInterface() {
        assertTrue(RoleRepository.class.isInterface());
        assertFalse(RoleRepository.class.isEnum());
        assertFalse(RoleRepository.class.isAnnotation());
    }

    @Test
    public void testInheritsFromJpaRepository() {
        Class<?>[] interfaces = RoleRepository.class.getInterfaces();
        boolean extendsJpaRepository = false;
        for (Class<?> interfaceClass : interfaces) {
            if (interfaceClass.equals(org.springframework.data.jpa.repository.JpaRepository.class)) {
                extendsJpaRepository = true;
                break;
            }
        }
        assertTrue(extendsJpaRepository);
    }
}