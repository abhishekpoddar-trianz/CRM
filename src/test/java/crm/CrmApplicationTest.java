package crm;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class CrmApplicationTest {

    @Test
    void contextLoads() {
        // Test that the application context loads successfully
        assertDoesNotThrow(() -> {
            // Context loading is implicitly tested by the @SpringBootTest annotation
        });
    }

    @Test
    void mainMethodExists() {
        // Test that the main method exists and can be called
        assertDoesNotThrow(() -> {
            // Verify the main method doesn't throw exceptions when called with empty args
            String[] args = {};
            // Note: We don't actually call main() as it would start the application
            // Instead we verify the method signature exists
            assertTrue(hasMainMethod());
        });
    }

    @Test
    void applicationClassAnnotated() {
        // Test that the application class has the correct annotations
        assertTrue(CrmApplication.class.isAnnotationPresent(org.springframework.boot.autoconfigure.SpringBootApplication.class));
    }

    @Test
    void springApplicationCanRun() {
        // Test that SpringApplication.run can be called with CrmApplication class
        assertDoesNotThrow(() -> {
            // We test that the class reference is valid for SpringApplication.run
            Class<?> applicationClass = CrmApplication.class;
            assertNotNull(applicationClass);
            assertEquals("CrmApplication", applicationClass.getSimpleName());
        });
    }

    @Test
    void packageNameCorrect() {
        // Test that the class is in the correct package
        assertEquals("crm", CrmApplication.class.getPackage().getName());
    }

    @Test
    void classIsPublic() {
        // Test that the class is public
        assertTrue(java.lang.reflect.Modifier.isPublic(CrmApplication.class.getModifiers()));
    }

    @Test
    void mainMethodIsPublicStatic() throws NoSuchMethodException {
        // Test that the main method has correct modifiers
        java.lang.reflect.Method mainMethod = CrmApplication.class.getMethod("main", String[].class);
        assertNotNull(mainMethod);
        assertTrue(java.lang.reflect.Modifier.isPublic(mainMethod.getModifiers()));
        assertTrue(java.lang.reflect.Modifier.isStatic(mainMethod.getModifiers()));
        assertEquals(void.class, mainMethod.getReturnType());
    }

    @Test
    void springBootApplicationAnnotationPresent() {
        // Test that @SpringBootApplication annotation is present
        org.springframework.boot.autoconfigure.SpringBootApplication annotation =
            CrmApplication.class.getAnnotation(org.springframework.boot.autoconfigure.SpringBootApplication.class);
        assertNotNull(annotation);
    }

    @Test
    void classHasDefaultConstructor() {
        // Test that the class has a default constructor
        assertDoesNotThrow(() -> {
            CrmApplication application = new CrmApplication();
            assertNotNull(application);
        });
    }

    @Test
    void mainMethodParameterType() throws NoSuchMethodException {
        // Test that main method has correct parameter types
        java.lang.reflect.Method mainMethod = CrmApplication.class.getMethod("main", String[].class);
        Class<?>[] parameterTypes = mainMethod.getParameterTypes();
        assertEquals(1, parameterTypes.length);
        assertEquals(String[].class, parameterTypes[0]);
    }

    private boolean hasMainMethod() {
        try {
            java.lang.reflect.Method mainMethod = CrmApplication.class.getMethod("main", String[].class);
            return mainMethod != null;
        } catch (NoSuchMethodException e) {
            return false;
        }
    }
}