package crm.service;

import crm.entity.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserService userService;

    @Test
    void testUserServiceInterface() {
        assertTrue(UserService.class.isInterface());
    }

    @Test
    void testFindByUsernameMethodExists() {
        assertDoesNotThrow(() -> {
            UserService.class.getDeclaredMethod("findByUsername", String.class);
        });
    }

    @Test
    void testListAllUsersMethodExists() {
        assertDoesNotThrow(() -> {
            UserService.class.getDeclaredMethod("listAllUsers");
        });
    }

    @Test
    void testShowUserMethodExists() {
        assertDoesNotThrow(() -> {
            UserService.class.getDeclaredMethod("showUser", Long.class);
        });
    }

    @Test
    void testSaveUserMethodExists() {
        assertDoesNotThrow(() -> {
            UserService.class.getDeclaredMethod("saveUser", User.class);
        });
    }

    @Test
    void testEditUserMethodExists() {
        assertDoesNotThrow(() -> {
            UserService.class.getDeclaredMethod("editUser", User.class);
        });
    }

    @Test
    void testDeleteUserMethodExists() {
        assertDoesNotThrow(() -> {
            UserService.class.getDeclaredMethod("deleteUser", User.class);
        });
    }

    @Test
    void testFindByUsernameReturnsUser() throws Exception {
        var method = UserService.class.getDeclaredMethod("findByUsername", String.class);
        assertEquals(User.class, method.getReturnType());
    }

    @Test
    void testListAllUsersReturnsIterable() throws Exception {
        var method = UserService.class.getDeclaredMethod("listAllUsers");
        assertEquals(Iterable.class, method.getReturnType());
    }

    @Test
    void testShowUserReturnsUser() throws Exception {
        var method = UserService.class.getDeclaredMethod("showUser", Long.class);
        assertEquals(User.class, method.getReturnType());
    }

    @Test
    void testSaveUserReturnsVoid() throws Exception {
        var method = UserService.class.getDeclaredMethod("saveUser", User.class);
        assertEquals(void.class, method.getReturnType());
    }

    @Test
    void testEditUserReturnsVoid() throws Exception {
        var method = UserService.class.getDeclaredMethod("editUser", User.class);
        assertEquals(void.class, method.getReturnType());
    }

    @Test
    void testDeleteUserReturnsVoid() throws Exception {
        var method = UserService.class.getDeclaredMethod("deleteUser", User.class);
        assertEquals(void.class, method.getReturnType());
    }

    @Test
    void testInterfaceHasCorrectNumberOfMethods() {
        assertEquals(6, UserService.class.getDeclaredMethods().length);
    }

    @Test
    void testInterfaceIsPublic() {
        assertTrue(java.lang.reflect.Modifier.isPublic(UserService.class.getModifiers()));
    }

    @Test
    void testInterfaceIsAbstract() {
        assertTrue(java.lang.reflect.Modifier.isAbstract(UserService.class.getModifiers()));
    }

    @Test
    void testAllMethodsArePublicAbstract() throws Exception {
        var methods = UserService.class.getDeclaredMethods();
        for (var method : methods) {
            assertTrue(java.lang.reflect.Modifier.isPublic(method.getModifiers()));
            assertTrue(java.lang.reflect.Modifier.isAbstract(method.getModifiers()));
        }
    }
}