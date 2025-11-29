package crm.service;

import crm.entity.Role;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RoleServiceTest {

    @Test
    public void testServiceInterface() {
        assertTrue(RoleService.class.isInterface());
    }

    @Test
    public void testListAllRolesMethodExists() throws NoSuchMethodException {
        java.lang.reflect.Method method = RoleService.class.getMethod("listAllRoles");
        assertNotNull(method);
        assertEquals("listAllRoles", method.getName());
        assertEquals(Iterable.class, method.getReturnType());
    }

    @Test
    public void testListAllRolesMethodReturnType() throws NoSuchMethodException {
        java.lang.reflect.Method method = RoleService.class.getMethod("listAllRoles");
        assertEquals(Iterable.class, method.getReturnType());
    }

    @Test
    public void testListAllRolesMethodParameterCount() throws NoSuchMethodException {
        java.lang.reflect.Method method = RoleService.class.getMethod("listAllRoles");
        assertEquals(0, method.getParameterCount());
    }

    @Test
    public void testServicePackage() {
        assertEquals("crm.service", RoleService.class.getPackage().getName());
    }

    @Test
    public void testServiceName() {
        assertEquals("RoleService", RoleService.class.getSimpleName());
    }

    @Test
    public void testServiceIsPublic() {
        assertTrue(java.lang.reflect.Modifier.isPublic(RoleService.class.getModifiers()));
    }

    @Test
    public void testServiceMethodCount() {
        java.lang.reflect.Method[] declaredMethods = RoleService.class.getDeclaredMethods();
        assertEquals(1, declaredMethods.length); // Only listAllRoles method
    }

    @Test
    public void testServiceIsInterface() {
        assertTrue(RoleService.class.isInterface());
        assertFalse(RoleService.class.isEnum());
        assertFalse(RoleService.class.isAnnotation());
    }

    @Test
    public void testServiceStructure() {
        assertNotNull(RoleService.class);
        assertTrue(RoleService.class.isInterface());
        assertEquals("RoleService", RoleService.class.getSimpleName());
        assertEquals("crm.service", RoleService.class.getPackage().getName());
    }

    @Test
    public void testMethodSignature() throws NoSuchMethodException {
        java.lang.reflect.Method method = RoleService.class.getMethod("listAllRoles");
        assertEquals("listAllRoles", method.getName());
        assertEquals(0, method.getParameterCount());
        assertEquals(Iterable.class, method.getReturnType());
    }

    @Test
    public void testNoAdditionalMethods() {
        java.lang.reflect.Method[] declaredMethods = RoleService.class.getDeclaredMethods();
        // Should only have exactly 1 method: listAllRoles
        assertEquals(1, declaredMethods.length);
        assertEquals("listAllRoles", declaredMethods[0].getName());
    }

    @Test
    public void testGenericReturnType() throws NoSuchMethodException {
        java.lang.reflect.Method method = RoleService.class.getMethod("listAllRoles");
        java.lang.reflect.Type genericReturnType = method.getGenericReturnType();
        assertNotNull(genericReturnType);
        // The return type should be parameterized with Role
        assertTrue(genericReturnType.toString().contains("Role"));
    }
}