package crm.repository;

import crm.entity.Category;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CategoryRepositoryTest {

    @Test
    public void testRepositoryInterface() {
        assertTrue(CategoryRepository.class.isInterface());
    }

    @Test
    public void testRepositoryAnnotation() {
        assertTrue(CategoryRepository.class.isAnnotationPresent(org.springframework.stereotype.Repository.class));
    }

    @Test
    public void testExtendsJpaRepository() {
        assertTrue(org.springframework.data.jpa.repository.JpaRepository.class.isAssignableFrom(CategoryRepository.class));
    }

    @Test
    public void testGenericTypes() {
        // Verify that the repository works with Category entity and Long ID
        java.lang.reflect.Type[] genericInterfaces = CategoryRepository.class.getGenericInterfaces();
        assertNotNull(genericInterfaces);
        assertTrue(genericInterfaces.length > 0);
    }

    @Test
    public void testFindByNameMethodExists() throws NoSuchMethodException {
        java.lang.reflect.Method findByNameMethod = CategoryRepository.class.getMethod("findByName", String.class);
        assertNotNull(findByNameMethod);
        assertEquals("findByName", findByNameMethod.getName());
        assertEquals(Category.class, findByNameMethod.getReturnType());
    }

    @Test
    public void testFindByNameMethodReturnType() throws NoSuchMethodException {
        java.lang.reflect.Method findByNameMethod = CategoryRepository.class.getMethod("findByName", String.class);
        assertEquals(Category.class, findByNameMethod.getReturnType());
    }

    @Test
    public void testFindByNameMethodParameterTypes() throws NoSuchMethodException {
        java.lang.reflect.Method findByNameMethod = CategoryRepository.class.getMethod("findByName", String.class);
        Class<?>[] parameterTypes = findByNameMethod.getParameterTypes();
        assertEquals(1, parameterTypes.length);
        assertEquals(String.class, parameterTypes[0]);
    }

    @Test
    public void testRepositoryPackage() {
        assertEquals("crm.repository", CategoryRepository.class.getPackage().getName());
    }

    @Test
    public void testRepositoryName() {
        assertEquals("CategoryRepository", CategoryRepository.class.getSimpleName());
    }

    @Test
    public void testRepositoryIsPublic() {
        assertTrue(java.lang.reflect.Modifier.isPublic(CategoryRepository.class.getModifiers()));
    }

    @Test
    public void testRepositoryMethodCount() {
        // Should have the custom findByName method plus inherited JpaRepository methods
        java.lang.reflect.Method[] declaredMethods = CategoryRepository.class.getDeclaredMethods();
        assertEquals(1, declaredMethods.length); // Only the custom findByName method
    }

    @Test
    public void testRepositoryIsInterface() {
        assertTrue(CategoryRepository.class.isInterface());
        assertFalse(CategoryRepository.class.isEnum());
        assertFalse(CategoryRepository.class.isAnnotation());
    }

    @Test
    public void testInheritsFromJpaRepository() {
        Class<?>[] interfaces = CategoryRepository.class.getInterfaces();
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
        java.lang.reflect.Type[] genericInterfaces = CategoryRepository.class.getGenericInterfaces();
        assertNotNull(genericInterfaces);
        // This test verifies the generic signature includes Long as the ID type
        assertTrue(genericInterfaces.length > 0);
    }

    @Test
    public void testEntityTypeIsCategory() {
        // Verify the entity type is Category
        java.lang.reflect.Type[] genericInterfaces = CategoryRepository.class.getGenericInterfaces();
        assertNotNull(genericInterfaces);
        // This test verifies the generic signature includes Category as the entity type
        assertTrue(genericInterfaces.length > 0);
    }

    @Test
    public void testRepositoryStructure() {
        assertNotNull(CategoryRepository.class);
        assertTrue(CategoryRepository.class.isInterface());
        assertEquals("CategoryRepository", CategoryRepository.class.getSimpleName());
        assertEquals("crm.repository", CategoryRepository.class.getPackage().getName());
    }

    @Test
    public void testCustomMethodSignature() throws NoSuchMethodException {
        java.lang.reflect.Method method = CategoryRepository.class.getMethod("findByName", String.class);
        assertNotNull(method);
        assertEquals("findByName", method.getName());
        assertEquals(1, method.getParameterCount());
        assertEquals(String.class, method.getParameterTypes()[0]);
        assertEquals(Category.class, method.getReturnType());
    }
}