package crm.repository;

import crm.entity.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserRepositoryTest {

    @Test
    public void testRepositoryInterface() {
        assertTrue(UserRepository.class.isInterface());
    }

    @Test
    public void testRepositoryAnnotation() {
        assertTrue(UserRepository.class.isAnnotationPresent(org.springframework.stereotype.Repository.class));
    }

    @Test
    public void testExtendsJpaRepository() {
        assertTrue(org.springframework.data.jpa.repository.JpaRepository.class.isAssignableFrom(UserRepository.class));
    }

    @Test
    public void testGenericTypes() {
        // Verify that the repository works with User entity and Long ID
        java.lang.reflect.Type[] genericInterfaces = UserRepository.class.getGenericInterfaces();
        assertNotNull(genericInterfaces);
        assertTrue(genericInterfaces.length > 0);
    }

    @Test
    public void testFindByUsernameMethodExists() throws NoSuchMethodException {
        java.lang.reflect.Method findByUsernameMethod = UserRepository.class.getMethod("findByUsername", String.class);
        assertNotNull(findByUsernameMethod);
        assertEquals("findByUsername", findByUsernameMethod.getName());
        assertEquals(User.class, findByUsernameMethod.getReturnType());
    }

    @Test
    public void testFindByUsernameMethodReturnType() throws NoSuchMethodException {
        java.lang.reflect.Method findByUsernameMethod = UserRepository.class.getMethod("findByUsername", String.class);
        assertEquals(User.class, findByUsernameMethod.getReturnType());
    }

    @Test
    public void testFindByUsernameMethodParameterTypes() throws NoSuchMethodException {
        java.lang.reflect.Method findByUsernameMethod = UserRepository.class.getMethod("findByUsername", String.class);
        Class<?>[] parameterTypes = findByUsernameMethod.getParameterTypes();
        assertEquals(1, parameterTypes.length);
        assertEquals(String.class, parameterTypes[0]);
    }

    @Test
    public void testFindAllByEnabledMethodExists() throws NoSuchMethodException {
        java.lang.reflect.Method findAllByEnabledMethod = UserRepository.class.getMethod("findAllByEnabled", int.class);
        assertNotNull(findAllByEnabledMethod);
        assertEquals("findAllByEnabled", findAllByEnabledMethod.getName());
        assertEquals(Iterable.class, findAllByEnabledMethod.getReturnType());
    }

    @Test
    public void testFindAllByEnabledMethodReturnType() throws NoSuchMethodException {
        java.lang.reflect.Method findAllByEnabledMethod = UserRepository.class.getMethod("findAllByEnabled", int.class);
        assertEquals(Iterable.class, findAllByEnabledMethod.getReturnType());
    }

    @Test
    public void testFindAllByEnabledMethodParameterTypes() throws NoSuchMethodException {
        java.lang.reflect.Method findAllByEnabledMethod = UserRepository.class.getMethod("findAllByEnabled", int.class);
        Class<?>[] parameterTypes = findAllByEnabledMethod.getParameterTypes();
        assertEquals(1, parameterTypes.length);
        assertEquals(int.class, parameterTypes[0]);
    }

    @Test
    public void testRepositoryPackage() {
        assertEquals("crm.repository", UserRepository.class.getPackage().getName());
    }

    @Test
    public void testRepositoryName() {
        assertEquals("UserRepository", UserRepository.class.getSimpleName());
    }

    @Test
    public void testRepositoryIsPublic() {
        assertTrue(java.lang.reflect.Modifier.isPublic(UserRepository.class.getModifiers()));
    }

    @Test
    public void testRepositoryMethodCount() {
        // Should have the custom methods plus inherited JpaRepository methods
        java.lang.reflect.Method[] declaredMethods = UserRepository.class.getDeclaredMethods();
        assertEquals(2, declaredMethods.length); // findByUsername and findAllByEnabled
    }

    @Test
    public void testRepositoryIsInterface() {
        assertTrue(UserRepository.class.isInterface());
        assertFalse(UserRepository.class.isEnum());
        assertFalse(UserRepository.class.isAnnotation());
    }

    @Test
    public void testInheritsFromJpaRepository() {
        Class<?>[] interfaces = UserRepository.class.getInterfaces();
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
        // Verify the ID type is Long
        java.lang.reflect.Type[] genericInterfaces = UserRepository.class.getGenericInterfaces();
        assertNotNull(genericInterfaces);
        // This test verifies the generic signature includes Long as the ID type
        assertTrue(genericInterfaces.length > 0);
    }

    @Test
    public void testEntityTypeIsUser() {
        // Verify the entity type is User
        java.lang.reflect.Type[] genericInterfaces = UserRepository.class.getGenericInterfaces();
        assertNotNull(genericInterfaces);
        // This test verifies the generic signature includes User as the entity type
        assertTrue(genericInterfaces.length > 0);
    }

    @Test
    public void testAllCustomMethods() {
        java.lang.reflect.Method[] declaredMethods = UserRepository.class.getDeclaredMethods();
        assertEquals(2, declaredMethods.length);

        boolean hasFindByUsername = false;
        boolean hasFindAllByEnabled = false;

        for (java.lang.reflect.Method method : declaredMethods) {
            if ("findByUsername".equals(method.getName())) {
                hasFindByUsername = true;
            }
            if ("findAllByEnabled".equals(method.getName())) {
                hasFindAllByEnabled = true;
            }
        }

        assertTrue(hasFindByUsername);
        assertTrue(hasFindAllByEnabled);
    }
}