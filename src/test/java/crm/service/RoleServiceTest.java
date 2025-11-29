package crm.service;

import crm.entity.Role;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoleServiceTest {

    @Test
    void testRoleServiceInterface() {
        assertTrue(RoleService.class.isInterface());
    }

    @Test
    void testListAllRolesMethodExists() {
        assertDoesNotThrow(() -> {
            RoleService.class.getDeclaredMethod("listAllRoles");
        });
    }

    @Test
    void testListAllRolesReturnsIterable() throws Exception {
        var method = RoleService.class.getDeclaredMethod("listAllRoles");
        assertEquals(Iterable.class, method.getReturnType());
    }

    @Test
    void testInterfaceHasCorrectNumberOfMethods() {
        assertEquals(1, RoleService.class.getDeclaredMethods().length);
    }

    @Test
    void testInterfaceIsPublic() {
        assertTrue(java.lang.reflect.Modifier.isPublic(RoleService.class.getModifiers()));
    }

    @Test
    void testInterfaceIsAbstract() {
        assertTrue(java.lang.reflect.Modifier.isAbstract(RoleService.class.getModifiers()));
    }
}