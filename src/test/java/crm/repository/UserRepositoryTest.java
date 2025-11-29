package crm.repository;

import crm.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.repository.JpaRepository;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {

    @Test
    void testInterfaceExists() {
        // Act & Assert
        assertTrue(UserRepository.class.isInterface());
    }

    @Test
    void testExtendsJpaRepository() {
        // Act & Assert
        assertTrue(JpaRepository.class.isAssignableFrom(UserRepository.class));
    }

    @Test
    void testRepositoryAnnotation() {
        // Act & Assert
        assertTrue(UserRepository.class.isAnnotationPresent(org.springframework.stereotype.Repository.class));
    }

    @Test
    void testFindByUsernameMethodExists() throws NoSuchMethodException {
        // Act & Assert
        assertNotNull(UserRepository.class.getMethod("findByUsername", String.class));
    }

    @Test
    void testFindAllByEnabledMethodExists() throws NoSuchMethodException {
        // Act & Assert
        assertNotNull(UserRepository.class.getMethod("findAllByEnabled", boolean.class));
    }

    @Test
    void testFindByUsernameMethodSignature() throws NoSuchMethodException {
        // Act
        java.lang.reflect.Method method = UserRepository.class.getMethod("findByUsername", String.class);

        // Assert
        assertEquals(User.class, method.getReturnType());
        assertEquals(1, method.getParameterCount());
        assertEquals(String.class, method.getParameterTypes()[0]);
    }

    @Test
    void testFindAllByEnabledMethodSignature() throws NoSuchMethodException {
        // Act
        java.lang.reflect.Method method = UserRepository.class.getMethod("findAllByEnabled", boolean.class);

        // Assert
        assertEquals(Iterable.class, method.getReturnType());
        assertEquals(1, method.getParameterCount());
        assertEquals(boolean.class, method.getParameterTypes()[0]);
    }

    @Test
    void testInterfaceModifiers() {
        // Act & Assert
        assertTrue(java.lang.reflect.Modifier.isPublic(UserRepository.class.getModifiers()));
        assertTrue(java.lang.reflect.Modifier.isInterface(UserRepository.class.getModifiers()));
        assertTrue(java.lang.reflect.Modifier.isAbstract(UserRepository.class.getModifiers()));
    }

    @Test
    void testMethodModifiers() throws NoSuchMethodException {
        // Act
        java.lang.reflect.Method findByUsername = UserRepository.class.getMethod("findByUsername", String.class);
        java.lang.reflect.Method findAllByEnabled = UserRepository.class.getMethod("findAllByEnabled", boolean.class);

        // Assert - Interface methods should be public and abstract
        assertTrue(java.lang.reflect.Modifier.isPublic(findByUsername.getModifiers()));
        assertTrue(java.lang.reflect.Modifier.isAbstract(findByUsername.getModifiers()));
        assertTrue(java.lang.reflect.Modifier.isPublic(findAllByEnabled.getModifiers()));
        assertTrue(java.lang.reflect.Modifier.isAbstract(findAllByEnabled.getModifiers()));
    }

    @Test
    void testPackageName() {
        // Act & Assert
        assertEquals("crm.repository", UserRepository.class.getPackage().getName());
    }

    @Test
    void testGenericTypeParameters() {
        // Act
        java.lang.reflect.Type[] genericInterfaces = UserRepository.class.getGenericInterfaces();

        // Assert
        assertEquals(1, genericInterfaces.length);
        assertTrue(genericInterfaces[0].toString().contains("User"));
        assertTrue(genericInterfaces[0].toString().contains("Long"));
    }

    @Test
    void testDeclaredMethodsCount() {
        // Act
        java.lang.reflect.Method[] declaredMethods = UserRepository.class.getDeclaredMethods();

        // Assert - Should have exactly 2 custom methods declared in this interface
        assertEquals(2, declaredMethods.length);
    }

    @Test
    void testMethodNames() {
        // Act
        java.lang.reflect.Method[] methods = UserRepository.class.getDeclaredMethods();

        // Assert - Verify method names
        boolean hasFindByUsername = false;
        boolean hasFindAllByEnabled = false;

        for (java.lang.reflect.Method method : methods) {
            if ("findByUsername".equals(method.getName())) {
                hasFindByUsername = true;
            } else if ("findAllByEnabled".equals(method.getName())) {
                hasFindAllByEnabled = true;
            }
        }

        assertTrue(hasFindByUsername, "findByUsername method not found");
        assertTrue(hasFindAllByEnabled, "findAllByEnabled method not found");
    }

    @Test
    void testInheritedJpaRepositoryMethods() {
        // Act & Assert - Should inherit standard CRUD methods from JpaRepository
        java.lang.reflect.Method[] allMethods = UserRepository.class.getMethods();

        boolean hasCount = false;
        boolean hasFindAll = false;
        boolean hasDeleteById = false;
        boolean hasSave = false;
        boolean hasFindById = false;

        for (java.lang.reflect.Method method : allMethods) {
            switch (method.getName()) {
                case "count":
                    hasCount = true;
                    break;
                case "findAll":
                    if (method.getParameterCount() == 0) {
                        hasFindAll = true;
                    }
                    break;
                case "deleteById":
                    hasDeleteById = true;
                    break;
                case "save":
                    hasSave = true;
                    break;
                case "findById":
                    hasFindById = true;
                    break;
            }
        }

        assertTrue(hasCount, "count method not inherited");
        assertTrue(hasFindAll, "findAll method not inherited");
        assertTrue(hasDeleteById, "deleteById method not inherited");
        assertTrue(hasSave, "save method not inherited");
        assertTrue(hasFindById, "findById method not inherited");
    }

    @Test
    void testNoFields() {
        // Act
        java.lang.reflect.Field[] fields = UserRepository.class.getDeclaredFields();

        // Assert - Interface should not have any fields
        assertEquals(0, fields.length);
    }

    @Test
    void testNoConstructors() {
        // Act
        java.lang.reflect.Constructor<?>[] constructors = UserRepository.class.getDeclaredConstructors();

        // Assert - Interface should not have constructors
        assertEquals(0, constructors.length);
    }

    @Test
    void testMethodExceptions() throws NoSuchMethodException {
        // Act
        java.lang.reflect.Method findByUsername = UserRepository.class.getMethod("findByUsername", String.class);
        java.lang.reflect.Method findAllByEnabled = UserRepository.class.getMethod("findAllByEnabled", boolean.class);

        // Assert - Methods don't declare exceptions
        assertEquals(0, findByUsername.getExceptionTypes().length);
        assertEquals(0, findAllByEnabled.getExceptionTypes().length);
    }

    @Test
    void testInterfaceName() {
        // Act & Assert
        assertEquals("UserRepository", UserRepository.class.getSimpleName());
        assertEquals("crm.repository.UserRepository", UserRepository.class.getName());
    }

    @Test
    void testRepositoryAnnotationValue() {
        // Act
        org.springframework.stereotype.Repository repositoryAnnotation =
            UserRepository.class.getAnnotation(org.springframework.stereotype.Repository.class);

        // Assert
        assertNotNull(repositoryAnnotation);
        assertEquals("", repositoryAnnotation.value()); // Default value should be empty string
    }

    @Test
    void testCanBeAssignedToVariable() {
        // Act & Assert - Test that interface can be used as a type
        UserRepository userRepository = null;
        assertNull(userRepository);

        // Test that it can be cast
        Object obj = userRepository;
        assertNull(obj);

        // Test assignment compatibility
        JpaRepository<User, Long> jpaRepo = userRepository;
        assertNull(jpaRepo);
    }

    @Test
    void testGenericReturnTypes() throws NoSuchMethodException {
        // Act
        java.lang.reflect.Method findAllByEnabled = UserRepository.class.getMethod("findAllByEnabled", boolean.class);
        java.lang.reflect.Type returnType = findAllByEnabled.getGenericReturnType();

        // Assert
        assertNotNull(returnType);
        assertEquals("java.lang.Iterable<crm.entity.User>", returnType.getTypeName());
    }

    @Test
    void testInterfaceHierarchy() {
        // Act & Assert
        assertFalse(UserRepository.class.isEnum());
        assertFalse(UserRepository.class.isAnnotation());
        assertTrue(UserRepository.class.isInterface());
        assertFalse(UserRepository.class.isPrimitive());
        assertFalse(UserRepository.class.isArray());
    }

    @Test
    void testSpringDataQueryMethods() {
        // Act & Assert - Test that method names follow Spring Data naming conventions
        java.lang.reflect.Method[] methods = UserRepository.class.getDeclaredMethods();

        for (java.lang.reflect.Method method : methods) {
            String methodName = method.getName();

            // Should follow Spring Data naming conventions
            if (methodName.equals("findByUsername")) {
                assertTrue(methodName.startsWith("find"));
                assertTrue(methodName.contains("By"));
                assertTrue(methodName.contains("Username"));
            } else if (methodName.equals("findAllByEnabled")) {
                assertTrue(methodName.startsWith("find"));
                assertTrue(methodName.contains("All"));
                assertTrue(methodName.contains("By"));
                assertTrue(methodName.contains("Enabled"));
            }
        }
    }

    @Test
    void testSupportsSpringDataFeatures() {
        // Act & Assert - Verify it's a proper Spring Data repository interface
        assertTrue(UserRepository.class.isInterface());
        assertTrue(UserRepository.class.isAnnotationPresent(org.springframework.stereotype.Repository.class));
        assertTrue(JpaRepository.class.isAssignableFrom(UserRepository.class));

        // Should support entity type User and ID type Long
        java.lang.reflect.Type[] genericInterfaces = UserRepository.class.getGenericInterfaces();
        assertTrue(genericInterfaces[0].toString().contains("User"));
        assertTrue(genericInterfaces[0].toString().contains("Long"));
    }
}