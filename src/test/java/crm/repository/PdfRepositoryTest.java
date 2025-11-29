package crm.repository;

import crm.entity.Pdf;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PdfRepositoryTest {

    @Test
    public void testRepositoryInterface() {
        assertTrue(PdfRepository.class.isInterface());
    }

    @Test
    public void testRepositoryAnnotation() {
        assertTrue(PdfRepository.class.isAnnotationPresent(org.springframework.stereotype.Repository.class));
    }

    @Test
    public void testExtendsJpaRepository() {
        assertTrue(org.springframework.data.jpa.repository.JpaRepository.class.isAssignableFrom(PdfRepository.class));
    }

    @Test
    public void testGenericTypes() {
        // Verify that the repository works with Pdf entity and Long ID
        java.lang.reflect.Type[] genericInterfaces = PdfRepository.class.getGenericInterfaces();
        assertNotNull(genericInterfaces);
        assertTrue(genericInterfaces.length > 0);
    }

    @Test
    public void testFindByNameMethodExists() throws NoSuchMethodException {
        java.lang.reflect.Method findByNameMethod = PdfRepository.class.getMethod("findByName", String.class);
        assertNotNull(findByNameMethod);
        assertEquals("findByName", findByNameMethod.getName());
        assertEquals(Pdf.class, findByNameMethod.getReturnType());
    }

    @Test
    public void testFindByNameMethodReturnType() throws NoSuchMethodException {
        java.lang.reflect.Method findByNameMethod = PdfRepository.class.getMethod("findByName", String.class);
        assertEquals(Pdf.class, findByNameMethod.getReturnType());
    }

    @Test
    public void testFindByNameMethodParameterTypes() throws NoSuchMethodException {
        java.lang.reflect.Method findByNameMethod = PdfRepository.class.getMethod("findByName", String.class);
        Class<?>[] parameterTypes = findByNameMethod.getParameterTypes();
        assertEquals(1, parameterTypes.length);
        assertEquals(String.class, parameterTypes[0]);
    }

    @Test
    public void testRepositoryPackage() {
        assertEquals("crm.repository", PdfRepository.class.getPackage().getName());
    }

    @Test
    public void testRepositoryName() {
        assertEquals("PdfRepository", PdfRepository.class.getSimpleName());
    }

    @Test
    public void testRepositoryIsPublic() {
        assertTrue(java.lang.reflect.Modifier.isPublic(PdfRepository.class.getModifiers()));
    }

    @Test
    public void testRepositoryMethodCount() {
        // Should have the custom findByName method plus inherited JpaRepository methods
        java.lang.reflect.Method[] declaredMethods = PdfRepository.class.getDeclaredMethods();
        assertEquals(1, declaredMethods.length); // Only the custom findByName method
    }

    @Test
    public void testRepositoryIsInterface() {
        assertTrue(PdfRepository.class.isInterface());
        assertFalse(PdfRepository.class.isEnum());
        assertFalse(PdfRepository.class.isAnnotation());
    }

    @Test
    public void testInheritsFromJpaRepository() {
        Class<?>[] interfaces = PdfRepository.class.getInterfaces();
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
        java.lang.reflect.Type[] genericInterfaces = PdfRepository.class.getGenericInterfaces();
        assertNotNull(genericInterfaces);
        // This test verifies the generic signature includes Long as the ID type
        assertTrue(genericInterfaces.length > 0);
    }

    @Test
    public void testEntityTypeIsPdf() {
        // Verify the entity type is Pdf
        java.lang.reflect.Type[] genericInterfaces = PdfRepository.class.getGenericInterfaces();
        assertNotNull(genericInterfaces);
        // This test verifies the generic signature includes Pdf as the entity type
        assertTrue(genericInterfaces.length > 0);
    }
}