package crm.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.persistence.*;

import static org.junit.jupiter.api.Assertions.*;

class RoleTest {

    private Role role;

    @BeforeEach
    void setUp() {
        role = new Role();
    }

    @Test
    void role_defaultConstructor_shouldCreateInstance() {
        // Act
        Role newRole = new Role();

        // Assert
        assertNotNull(newRole, "Role should be created");
        assertEquals(0, newRole.getId(), "Default id should be 0");
        assertNull(newRole.getName(), "Default name should be null");
    }

    @Test
    void setId_withValidId_shouldSetId() {
        // Arrange
        int expectedId = 123;

        // Act
        role.setId(expectedId);

        // Assert
        assertEquals(expectedId, role.getId(), "Id should be set correctly");
    }

    @Test
    void getId_afterSettingId_shouldReturnCorrectId() {
        // Arrange
        int expectedId = 456;
        role.setId(expectedId);

        // Act
        int actualId = role.getId();

        // Assert
        assertEquals(expectedId, actualId, "getId should return the set id");
    }

    @Test
    void setName_withValidName_shouldSetName() {
        // Arrange
        String expectedName = "ADMIN";

        // Act
        role.setName(expectedName);

        // Assert
        assertEquals(expectedName, role.getName(), "Name should be set correctly");
    }

    @Test
    void getName_afterSettingName_shouldReturnCorrectName() {
        // Arrange
        String expectedName = "USER";
        role.setName(expectedName);

        // Act
        String actualName = role.getName();

        // Assert
        assertEquals(expectedName, actualName, "getName should return the set name");
    }

    @Test
    void setName_withNullName_shouldSetNullName() {
        // Act
        role.setName(null);

        // Assert
        assertNull(role.getName(), "Name should be null when set to null");
    }

    @Test
    void setName_withEmptyName_shouldSetEmptyName() {
        // Arrange
        String emptyName = "";

        // Act
        role.setName(emptyName);

        // Assert
        assertEquals(emptyName, role.getName(), "Name should be empty string");
    }

    @Test
    void setId_withZeroId_shouldSetZeroId() {
        // Act
        role.setId(0);

        // Assert
        assertEquals(0, role.getId(), "Id should be set to 0");
    }

    @Test
    void setId_withNegativeId_shouldSetNegativeId() {
        // Arrange
        int negativeId = -1;

        // Act
        role.setId(negativeId);

        // Assert
        assertEquals(negativeId, role.getId(), "Id should be set to negative value");
    }

    @Test
    void equals_withSameValues_shouldBeEqual() {
        // Arrange
        Role role1 = new Role();
        role1.setId(1);
        role1.setName("ADMIN");

        Role role2 = new Role();
        role2.setId(1);
        role2.setName("ADMIN");

        // Act & Assert
        assertEquals(role1, role2, "Roles with same values should be equal");
        assertEquals(role1.hashCode(), role2.hashCode(), "Hash codes should be equal");
    }

    @Test
    void equals_withDifferentValues_shouldNotBeEqual() {
        // Arrange
        Role role1 = new Role();
        role1.setId(1);
        role1.setName("ADMIN");

        Role role2 = new Role();
        role2.setId(2);
        role2.setName("USER");

        // Act & Assert
        assertNotEquals(role1, role2, "Roles with different values should not be equal");
    }

    @Test
    void toString_shouldReturnStringRepresentation() {
        // Arrange
        role.setId(1);
        role.setName("TEST_ROLE");

        // Act
        String result = role.toString();

        // Assert
        assertNotNull(result, "toString should not return null");
        assertTrue(result.contains("1"), "toString should contain id");
        assertTrue(result.contains("TEST_ROLE"), "toString should contain name");
    }

    @Test
    void roleClass_shouldHaveCorrectJPAAnnotations() {
        // Assert
        assertTrue(Role.class.isAnnotationPresent(Entity.class), "Role should have @Entity annotation");
        assertTrue(Role.class.isAnnotationPresent(Table.class), "Role should have @Table annotation");

        Table tableAnnotation = Role.class.getAnnotation(Table.class);
        assertEquals("role", tableAnnotation.name(), "Table name should be 'role'");
    }

    @Test
    void idField_shouldHaveCorrectJPAAnnotations() throws NoSuchFieldException {
        // Arrange
        var idField = Role.class.getDeclaredField("id");

        // Assert
        assertTrue(idField.isAnnotationPresent(Id.class), "id field should have @Id annotation");
        assertTrue(idField.isAnnotationPresent(GeneratedValue.class), "id field should have @GeneratedValue annotation");
        assertTrue(idField.isAnnotationPresent(Column.class), "id field should have @Column annotation");

        GeneratedValue generatedValue = idField.getAnnotation(GeneratedValue.class);
        assertEquals(GenerationType.IDENTITY, generatedValue.strategy(), "GeneratedValue strategy should be IDENTITY");

        Column column = idField.getAnnotation(Column.class);
        assertEquals("role_id", column.name(), "Column name should be 'role_id'");
    }

    @Test
    void nameField_shouldHaveCorrectJPAAnnotations() throws NoSuchFieldException {
        // Arrange
        var nameField = Role.class.getDeclaredField("name");

        // Assert
        assertTrue(nameField.isAnnotationPresent(Column.class), "name field should have @Column annotation");

        Column column = nameField.getAnnotation(Column.class);
        assertEquals("role", column.name(), "Column name should be 'role'");
        assertTrue(column.unique(), "Column should be unique");
    }

    @Test
    void roleClass_shouldHaveLombokDataAnnotation() {
        // Assert
        assertTrue(Role.class.isAnnotationPresent(lombok.Data.class), "Role should have @Data annotation");
    }
}