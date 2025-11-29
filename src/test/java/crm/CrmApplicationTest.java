package crm;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.SpringApplication;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CrmApplicationTest {

    @Test
    void testMain() {
        // Arrange
        String[] args = {"--server.port=8080"};

        try (MockedStatic<SpringApplication> mockedStatic = Mockito.mockStatic(SpringApplication.class)) {
            // Act
            CrmApplication.main(args);

            // Assert
            mockedStatic.verify(() -> SpringApplication.run(CrmApplication.class, args));
        }
    }

    @Test
    void testMainWithEmptyArgs() {
        // Arrange
        String[] args = {};

        try (MockedStatic<SpringApplication> mockedStatic = Mockito.mockStatic(SpringApplication.class)) {
            // Act
            CrmApplication.main(args);

            // Assert
            mockedStatic.verify(() -> SpringApplication.run(CrmApplication.class, args));
        }
    }

    @Test
    void testMainWithNullArgs() {
        // Arrange
        String[] args = null;

        try (MockedStatic<SpringApplication> mockedStatic = Mockito.mockStatic(SpringApplication.class)) {
            // Act
            CrmApplication.main(args);

            // Assert
            mockedStatic.verify(() -> SpringApplication.run(CrmApplication.class, args));
        }
    }

    @Test
    void testMainWithMultipleArgs() {
        // Arrange
        String[] args = {"--server.port=8080", "--spring.profiles.active=dev", "--debug"};

        try (MockedStatic<SpringApplication> mockedStatic = Mockito.mockStatic(SpringApplication.class)) {
            // Act
            CrmApplication.main(args);

            // Assert
            mockedStatic.verify(() -> SpringApplication.run(CrmApplication.class, args));
        }
    }

    @Test
    void testApplicationClassExists() {
        // Act & Assert
        assertNotNull(CrmApplication.class);
        assertTrue(CrmApplication.class.isAnnotationPresent(org.springframework.boot.autoconfigure.SpringBootApplication.class));
    }

    @Test
    void testMainMethodExists() throws NoSuchMethodException {
        // Act & Assert
        assertNotNull(CrmApplication.class.getDeclaredMethod("main", String[].class));
    }

    @Test
    void testMainMethodIsStatic() throws NoSuchMethodException {
        // Act & Assert
        assertTrue(java.lang.reflect.Modifier.isStatic(
                CrmApplication.class.getDeclaredMethod("main", String[].class).getModifiers()));
    }

    @Test
    void testMainMethodIsPublic() throws NoSuchMethodException {
        // Act & Assert
        assertTrue(java.lang.reflect.Modifier.isPublic(
                CrmApplication.class.getDeclaredMethod("main", String[].class).getModifiers()));
    }

    @Test
    void testMainMethodReturnType() throws NoSuchMethodException {
        // Act & Assert
        assertEquals(void.class,
                CrmApplication.class.getDeclaredMethod("main", String[].class).getReturnType());
    }

    @Test
    void testSpringBootApplicationAnnotation() {
        // Act & Assert
        assertTrue(CrmApplication.class.isAnnotationPresent(
                org.springframework.boot.autoconfigure.SpringBootApplication.class));
    }

    @Test
    void testClassIsPublic() {
        // Act & Assert
        assertTrue(java.lang.reflect.Modifier.isPublic(CrmApplication.class.getModifiers()));
    }
}