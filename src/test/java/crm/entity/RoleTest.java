package crm.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class RoleTest {

    private Role role;

    @BeforeEach
    public void setUp() {
        role = new Role();
    }

    @Test
    public void testNoArgsConstructor() {
        Role newRole = new Role();
        assertNotNull(newRole);
    }

    @Test
    public void testSettersAndGetters() {
        role.setId(1);
        role.setName("ADMIN");

        assertEquals(1, role.getId());
        assertEquals("ADMIN", role.getName());
    }

    @Test
    public void testIdField() {
        role.setId(5);
        assertEquals(5, role.getId());

        role.setId(0);
        assertEquals(0, role.getId());

        role.setId(-1);
        assertEquals(-1, role.getId());
    }

    @Test
    public void testNameField() {
        role.setName("USER");
        assertEquals("USER", role.getName());

        role.setName("MANAGER");
        assertEquals("MANAGER", role.getName());

        role.setName("");
        assertEquals("", role.getName());

        role.setName(null);
        assertNull(role.getName());
    }

    @Test
    public void testEntityAnnotation() {
        assertTrue(Role.class.isAnnotationPresent(jakarta.persistence.Entity.class));
    }

    @Test
    public void testTableAnnotation() {
        assertTrue(Role.class.isAnnotationPresent(jakarta.persistence.Table.class));
        jakarta.persistence.Table tableAnnotation = Role.class.getAnnotation(jakarta.persistence.Table.class);
        assertEquals("role", tableAnnotation.name());
    }

    @Test
    public void testDataAnnotation() {
        assertTrue(Role.class.isAnnotationPresent(lombok.Data.class));
    }

    @Test
    public void testIdAnnotations() throws NoSuchFieldException {
        java.lang.reflect.Field idField = Role.class.getDeclaredField("id");
        assertTrue(idField.isAnnotationPresent(jakarta.persistence.Id.class));
        assertTrue(idField.isAnnotationPresent(jakarta.persistence.GeneratedValue.class));
        assertTrue(idField.isAnnotationPresent(jakarta.persistence.Column.class));

        jakarta.persistence.GeneratedValue generatedValue = idField.getAnnotation(jakarta.persistence.GeneratedValue.class);
        assertEquals(jakarta.persistence.GenerationType.IDENTITY, generatedValue.strategy());

        jakarta.persistence.Column columnAnnotation = idField.getAnnotation(jakarta.persistence.Column.class);
        assertEquals("role_id", columnAnnotation.name());
    }

    @Test
    public void testNameAnnotations() throws NoSuchFieldException {
        java.lang.reflect.Field nameField = Role.class.getDeclaredField("name");
        assertTrue(nameField.isAnnotationPresent(jakarta.persistence.Column.class));

        jakarta.persistence.Column columnAnnotation = nameField.getAnnotation(jakarta.persistence.Column.class);
        assertEquals("role", columnAnnotation.name());
        assertTrue(columnAnnotation.unique());
    }

    @Test
    public void testEqualsAndHashCode() {
        Role role1 = new Role();
        role1.setId(1);
        role1.setName("ADMIN");

        Role role2 = new Role();
        role2.setId(1);
        role2.setName("ADMIN");

        assertEquals(role1, role2);
        assertEquals(role1.hashCode(), role2.hashCode());
    }

    @Test
    public void testEqualsDifferentRoles() {
        Role role1 = new Role();
        role1.setId(1);
        role1.setName("ADMIN");

        Role role2 = new Role();
        role2.setId(2);
        role2.setName("USER");

        assertNotEquals(role1, role2);
    }

    @Test
    public void testToString() {
        role.setId(1);
        role.setName("ADMIN");

        String roleString = role.toString();
        assertNotNull(roleString);
        assertTrue(roleString.contains("ADMIN"));
        assertTrue(roleString.contains("1"));
    }

    @Test
    public void testFieldTypes() throws NoSuchFieldException {
        java.lang.reflect.Field idField = Role.class.getDeclaredField("id");
        assertEquals(int.class, idField.getType());

        java.lang.reflect.Field nameField = Role.class.getDeclaredField("name");
        assertEquals(String.class, nameField.getType());
    }

    @Test
    public void testClassStructure() {
        assertNotNull(Role.class);
        assertEquals("Role", Role.class.getSimpleName());
        assertEquals("crm.entity", Role.class.getPackage().getName());
        assertTrue(java.lang.reflect.Modifier.isPublic(Role.class.getModifiers()));
    }

    @Test
    public void testFieldCount() {
        java.lang.reflect.Field[] declaredFields = Role.class.getDeclaredFields();
        assertEquals(2, declaredFields.length); // id and name
    }

    @Test
    public void testIdRange() {
        role.setId(Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, role.getId());

        role.setId(Integer.MIN_VALUE);
        assertEquals(Integer.MIN_VALUE, role.getId());
    }

    @Test
    public void testNameWithSpecialCharacters() {
        role.setName("ROLE_WITH_UNDERSCORE");
        assertEquals("ROLE_WITH_UNDERSCORE", role.getName());

        role.setName("ROLE-WITH-DASH");
        assertEquals("ROLE-WITH-DASH", role.getName());

        role.setName("ROLE123");
        assertEquals("ROLE123", role.getName());
    }

    @Test
    public void testNameMaxLength() {
        String longName = "A".repeat(1000);
        role.setName(longName);
        assertEquals(longName, role.getName());
    }
}