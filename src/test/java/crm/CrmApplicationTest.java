package crm;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CrmApplicationTest {

    @Test
    public void testApplicationAnnotations() {
        assertTrue(CrmApplication.class.isAnnotationPresent(SpringBootApplication.class));
        assertTrue(CrmApplication.class.isAnnotationPresent(EntityScan.class));
    }

    @Test
    public void testEntityScanConfiguration() {
        EntityScan entityScanAnnotation = CrmApplication.class.getAnnotation(EntityScan.class);
        assertNotNull(entityScanAnnotation);

        Class<?>[] basePackageClasses = entityScanAnnotation.basePackageClasses();
        assertEquals(1, basePackageClasses.length);
        assertEquals(CrmApplication.class, basePackageClasses[0]);
    }

    @Test
    public void testSpringBootApplicationAnnotation() {
        SpringBootApplication springBootApp = CrmApplication.class.getAnnotation(SpringBootApplication.class);
        assertNotNull(springBootApp);
    }

    @Test
    public void testMainMethodExists() throws NoSuchMethodException {
        java.lang.reflect.Method mainMethod = CrmApplication.class.getMethod("main", String[].class);
        assertNotNull(mainMethod);
        assertEquals("main", mainMethod.getName());
        assertTrue(java.lang.reflect.Modifier.isStatic(mainMethod.getModifiers()));
        assertTrue(java.lang.reflect.Modifier.isPublic(mainMethod.getModifiers()));
        assertEquals(void.class, mainMethod.getReturnType());
    }

    @Test
    public void testMainMethodCallsSpringApplication() {
        try (MockedStatic<SpringApplication> mockedSpringApplication = mockStatic(SpringApplication.class)) {
            String[] args = {"test-arg"};

            CrmApplication.main(args);

            mockedSpringApplication.verify(() -> SpringApplication.run(CrmApplication.class, args));
        }
    }

    @Test
    public void testMainMethodWithEmptyArgs() {
        try (MockedStatic<SpringApplication> mockedSpringApplication = mockStatic(SpringApplication.class)) {
            String[] emptyArgs = {};

            CrmApplication.main(emptyArgs);

            mockedSpringApplication.verify(() -> SpringApplication.run(CrmApplication.class, emptyArgs));
        }
    }

    @Test
    public void testMainMethodWithNullArgs() {
        try (MockedStatic<SpringApplication> mockedSpringApplication = mockStatic(SpringApplication.class)) {
            String[] nullArgs = null;

            CrmApplication.main(nullArgs);

            mockedSpringApplication.verify(() -> SpringApplication.run(CrmApplication.class, nullArgs));
        }
    }

    @Test
    public void testClassStructure() {
        assertNotNull(CrmApplication.class);
        assertEquals("CrmApplication", CrmApplication.class.getSimpleName());
        assertEquals("crm", CrmApplication.class.getPackage().getName());
        assertTrue(java.lang.reflect.Modifier.isPublic(CrmApplication.class.getModifiers()));
    }

    @Test
    public void testClassIsNotAbstract() {
        assertFalse(java.lang.reflect.Modifier.isAbstract(CrmApplication.class.getModifiers()));
    }

    @Test
    public void testClassIsNotInterface() {
        assertFalse(CrmApplication.class.isInterface());
    }

    @Test
    public void testConstructorExists() {
        assertDoesNotThrow(() -> {
            CrmApplication.class.getDeclaredConstructor();
        });
    }

    @Test
    public void testApplicationCanBeInstantiated() {
        assertDoesNotThrow(() -> {
            new CrmApplication();
        });
    }

    @Test
    public void testMainMethodParameterTypes() throws NoSuchMethodException {
        java.lang.reflect.Method mainMethod = CrmApplication.class.getMethod("main", String[].class);
        Class<?>[] parameterTypes = mainMethod.getParameterTypes();
        assertEquals(1, parameterTypes.length);
        assertEquals(String[].class, parameterTypes[0]);
    }

    @Test
    public void testPackageStructure() {
        assertEquals("crm", CrmApplication.class.getPackage().getName());
    }

    @Test
    public void testMethodCount() {
        java.lang.reflect.Method[] declaredMethods = CrmApplication.class.getDeclaredMethods();
        assertEquals(1, declaredMethods.length); // Only main method
        assertEquals("main", declaredMethods[0].getName());
    }
}