package crm.controller;

import crm.entity.User;
import crm.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    private UserController userController;

    @Mock
    private UserService mockUserService;

    @Mock
    private Model mockModel;

    @Mock
    private UserDetails mockUserDetails;

    @Mock
    private BindingResult mockBindingResult;

    private User testUser;
    private List<User> testUsers;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userController = new UserController(mockUserService);

        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testuser");
        testUser.setEmail("test@example.com");
        testUser.setFirstName("Test");
        testUser.setLastName("User");

        testUsers = Arrays.asList(testUser);
    }

    @Test
    void testConstructor() {
        // Act
        UserController controller = new UserController(mockUserService);

        // Assert
        assertNotNull(controller);
    }

    @Test
    void testConstructorWithNullService() {
        // Act & Assert
        assertDoesNotThrow(() -> new UserController(null));
    }

    @Test
    void testShowAllUsers() {
        // Arrange
        when(mockUserDetails.getUsername()).thenReturn("testuser");
        when(mockUserService.findByUsername("testuser")).thenReturn(testUser);
        when(mockUserService.listAllUsers()).thenReturn(testUsers);

        // Act
        String result = userController.showAllUsers(mockModel, mockUserDetails);

        // Assert
        assertEquals("user/list", result);
        verify(mockModel).addAttribute("currentUser", testUser);
        verify(mockModel).addAttribute("users", testUsers);
        verify(mockUserService).findByUsername("testuser");
        verify(mockUserService).listAllUsers();
    }

    @Test
    void testShowAllUsersWithNullCurrentUser() {
        // Arrange
        when(mockUserDetails.getUsername()).thenReturn("nonexistent");
        when(mockUserService.findByUsername("nonexistent")).thenReturn(null);
        when(mockUserService.listAllUsers()).thenReturn(testUsers);

        // Act
        String result = userController.showAllUsers(mockModel, mockUserDetails);

        // Assert
        assertEquals("user/list", result);
        verify(mockModel).addAttribute("currentUser", null);
        verify(mockModel).addAttribute("users", testUsers);
    }

    @Test
    void testShowFormEditUser() {
        // Arrange
        Long userId = 1L;
        when(mockUserService.showUser(userId)).thenReturn(testUser);

        // Act
        String result = userController.showFormEditUser(mockModel, userId);

        // Assert
        assertEquals("user/edit", result);
        verify(mockModel).addAttribute("user", testUser);
        verify(mockUserService).showUser(userId);
    }

    @Test
    void testShowFormEditUserWithNullUser() {
        // Arrange
        Long userId = 999L;
        when(mockUserService.showUser(userId)).thenReturn(null);

        // Act
        String result = userController.showFormEditUser(mockModel, userId);

        // Assert
        assertEquals("user/edit", result);
        verify(mockModel).addAttribute("user", null);
        verify(mockUserService).showUser(userId);
    }

    @Test
    void testProcessRequestEditUserSuccess() {
        // Arrange
        Long userId = 1L;
        when(mockBindingResult.hasErrors()).thenReturn(false);

        // Act
        String result = userController.processRequestEditUser(userId, testUser, mockBindingResult);

        // Assert
        assertEquals("redirect:/user/list", result);
        verify(mockUserService).editUser(testUser);
        verify(mockBindingResult).hasErrors();
    }

    @Test
    void testProcessRequestEditUserWithErrors() {
        // Arrange
        Long userId = 1L;
        when(mockBindingResult.hasErrors()).thenReturn(true);

        // Act
        String result = userController.processRequestEditUser(userId, testUser, mockBindingResult);

        // Assert
        assertEquals("redirect:/user/edit/" + userId, result);
        verify(mockBindingResult).hasErrors();
        verify(mockUserService, never()).editUser(any(User.class));
    }

    @Test
    void testProcessRequestEditUserWithDifferentIds() {
        // Test with different user IDs
        Long[] userIds = {1L, 2L, 100L, 999L};

        for (Long userId : userIds) {
            // Arrange
            when(mockBindingResult.hasErrors()).thenReturn(true);

            // Act
            String result = userController.processRequestEditUser(userId, testUser, mockBindingResult);

            // Assert
            assertEquals("redirect:/user/edit/" + userId, result);
        }

        verify(mockUserService, never()).editUser(any(User.class));
    }

    @Test
    void testDeleteUser() {
        // Arrange
        Long userId = 1L;
        when(mockUserService.showUser(userId)).thenReturn(testUser);

        // Act
        String result = userController.deleteUser(userId);

        // Assert
        assertEquals("redirect:/user/list", result);
        verify(mockUserService).showUser(userId);
        verify(mockUserService).deleteUser(testUser);
    }

    @Test
    void testDeleteUserWithNullUser() {
        // Arrange
        Long userId = 999L;
        when(mockUserService.showUser(userId)).thenReturn(null);

        // Act
        String result = userController.deleteUser(userId);

        // Assert
        assertEquals("redirect:/user/list", result);
        verify(mockUserService).showUser(userId);
        verify(mockUserService).deleteUser(null);
    }

    @Test
    void testControllerAnnotations() {
        // Act & Assert
        assertTrue(UserController.class.isAnnotationPresent(org.springframework.stereotype.Controller.class));
        assertTrue(UserController.class.isAnnotationPresent(org.springframework.web.bind.annotation.RequestMapping.class));

        org.springframework.web.bind.annotation.RequestMapping requestMapping =
            UserController.class.getAnnotation(org.springframework.web.bind.annotation.RequestMapping.class);
        assertEquals("/user", requestMapping.value()[0]);
    }

    @Test
    void testShowAllUsersAnnotations() throws NoSuchMethodException {
        // Act
        java.lang.reflect.Method method = UserController.class.getMethod("showAllUsers", Model.class, UserDetails.class);

        // Assert
        assertTrue(method.isAnnotationPresent(org.springframework.web.bind.annotation.GetMapping.class));

        org.springframework.web.bind.annotation.GetMapping getMapping =
            method.getAnnotation(org.springframework.web.bind.annotation.GetMapping.class);
        assertEquals("/list", getMapping.value()[0]);
    }

    @Test
    void testShowFormEditUserAnnotations() throws NoSuchMethodException {
        // Act
        java.lang.reflect.Method method = UserController.class.getMethod("showFormEditUser", Model.class, Long.class);

        // Assert
        assertTrue(method.isAnnotationPresent(org.springframework.web.bind.annotation.GetMapping.class));

        org.springframework.web.bind.annotation.GetMapping getMapping =
            method.getAnnotation(org.springframework.web.bind.annotation.GetMapping.class);
        assertEquals("/edit/{id}", getMapping.value()[0]);
    }

    @Test
    void testProcessRequestEditUserAnnotations() throws NoSuchMethodException {
        // Act
        java.lang.reflect.Method method = UserController.class.getMethod("processRequestEditUser", Long.class, User.class, BindingResult.class);

        // Assert
        assertTrue(method.isAnnotationPresent(org.springframework.web.bind.annotation.PostMapping.class));

        org.springframework.web.bind.annotation.PostMapping postMapping =
            method.getAnnotation(org.springframework.web.bind.annotation.PostMapping.class);
        assertEquals("/edit/{id}", postMapping.value()[0]);
    }

    @Test
    void testDeleteUserAnnotations() throws NoSuchMethodException {
        // Act
        java.lang.reflect.Method method = UserController.class.getMethod("deleteUser", Long.class);

        // Assert
        assertTrue(method.isAnnotationPresent(org.springframework.web.bind.annotation.GetMapping.class));

        org.springframework.web.bind.annotation.GetMapping getMapping =
            method.getAnnotation(org.springframework.web.bind.annotation.GetMapping.class);
        assertEquals("/delete/{id}", getMapping.value()[0]);
    }

    @Test
    void testMethodParameterAnnotations() throws NoSuchMethodException {
        // Test showAllUsers method parameters
        java.lang.reflect.Method showAllUsers = UserController.class.getMethod("showAllUsers", Model.class, UserDetails.class);
        java.lang.annotation.Annotation[][] parameterAnnotations = showAllUsers.getParameterAnnotations();

        // Second parameter should have @AuthenticationPrincipal annotation
        assertTrue(parameterAnnotations[1].length > 0);
        boolean hasAuthenticationPrincipal = false;
        for (java.lang.annotation.Annotation annotation : parameterAnnotations[1]) {
            if (annotation instanceof org.springframework.security.core.annotation.AuthenticationPrincipal) {
                hasAuthenticationPrincipal = true;
                break;
            }
        }
        assertTrue(hasAuthenticationPrincipal);

        // Test processRequestEditUser method parameters
        java.lang.reflect.Method processRequest = UserController.class.getMethod("processRequestEditUser", Long.class, User.class, BindingResult.class);
        java.lang.annotation.Annotation[][] processParameterAnnotations = processRequest.getParameterAnnotations();

        // First parameter should have @PathVariable annotation
        assertTrue(processParameterAnnotations[0].length > 0);
        boolean hasPathVariable = false;
        for (java.lang.annotation.Annotation annotation : processParameterAnnotations[0]) {
            if (annotation instanceof org.springframework.web.bind.annotation.PathVariable) {
                hasPathVariable = true;
                break;
            }
        }
        assertTrue(hasPathVariable);

        // Second parameter should have @Valid annotation
        assertTrue(processParameterAnnotations[1].length > 0);
        boolean hasValid = false;
        for (java.lang.annotation.Annotation annotation : processParameterAnnotations[1]) {
            if (annotation instanceof jakarta.validation.Valid) {
                hasValid = true;
                break;
            }
        }
        assertTrue(hasValid);
    }

    @Test
    void testServiceInjection() {
        // Act & Assert
        assertNotNull(userController);
        // We can't directly access the private field, but we can verify behavior
        // by checking that service calls work in other tests
    }

    @Test
    void testAllMethodsReturnStrings() throws NoSuchMethodException {
        // Act & Assert
        assertEquals(String.class, UserController.class.getMethod("showAllUsers", Model.class, UserDetails.class).getReturnType());
        assertEquals(String.class, UserController.class.getMethod("showFormEditUser", Model.class, Long.class).getReturnType());
        assertEquals(String.class, UserController.class.getMethod("processRequestEditUser", Long.class, User.class, BindingResult.class).getReturnType());
        assertEquals(String.class, UserController.class.getMethod("deleteUser", Long.class).getReturnType());
    }

    @Test
    void testMethodVisibility() throws NoSuchMethodException {
        // All controller methods should be public
        assertTrue(java.lang.reflect.Modifier.isPublic(UserController.class.getMethod("showAllUsers", Model.class, UserDetails.class).getModifiers()));
        assertTrue(java.lang.reflect.Modifier.isPublic(UserController.class.getMethod("showFormEditUser", Model.class, Long.class).getModifiers()));
        assertTrue(java.lang.reflect.Modifier.isPublic(UserController.class.getMethod("processRequestEditUser", Long.class, User.class, BindingResult.class).getModifiers()));
        assertTrue(java.lang.reflect.Modifier.isPublic(UserController.class.getMethod("deleteUser", Long.class).getModifiers()));
    }

    @Test
    void testPackageName() {
        // Act & Assert
        assertEquals("crm.controller", UserController.class.getPackage().getName());
    }

    @Test
    void testMultipleUserDeletion() {
        // Test deleting multiple users sequentially
        Long[] userIds = {1L, 2L, 3L};
        User[] users = new User[3];

        for (int i = 0; i < userIds.length; i++) {
            users[i] = new User();
            users[i].setId(userIds[i]);
            when(mockUserService.showUser(userIds[i])).thenReturn(users[i]);
        }

        for (int i = 0; i < userIds.length; i++) {
            // Act
            String result = userController.deleteUser(userIds[i]);

            // Assert
            assertEquals("redirect:/user/list", result);
            verify(mockUserService).showUser(userIds[i]);
            verify(mockUserService).deleteUser(users[i]);
        }
    }
}