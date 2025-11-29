package crm.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {

    private Category category;

    @BeforeEach
    void setUp() {
        category = new Category();
    }

    @Test
    void testDefaultConstructor() {
        // Act
        Category newCategory = new Category();

        // Assert
        assertNotNull(newCategory);
        assertNull(newCategory.getId());
        assertNull(newCategory.getName());
    }

    @Test
    void testSetAndGetId() {
        // Arrange
        Long expectedId = 123L;

        // Act
        category.setId(expectedId);

        // Assert
        assertEquals(expectedId, category.getId());
    }

    @Test
    void testSetAndGetName() {
        // Arrange
        String expectedName = "Test Category";

        // Act
        category.setName(expectedName);

        // Assert
        assertEquals(expectedName, category.getName());
    }

    @Test
    void testSetIdWithNull() {
        // Act
        category.setId(null);

        // Assert
        assertNull(category.getId());
    }

    @Test
    void testSetIdWithZero() {
        // Act
        category.setId(0L);

        // Assert
        assertEquals(0L, category.getId());
    }

    @Test
    void testSetIdWithNegative() {
        // Act
        category.setId(-1L);

        // Assert
        assertEquals(-1L, category.getId());
    }

    @Test
    void testSetIdWithLargeValue() {
        // Arrange
        Long largeId = Long.MAX_VALUE;

        // Act
        category.setId(largeId);

        // Assert
        assertEquals(largeId, category.getId());
    }

    @Test
    void testSetNameWithNull() {
        // Act
        category.setName(null);

        // Assert
        assertNull(category.getName());
    }

    @Test
    void testSetNameWithEmpty() {
        // Act
        category.setName("");

        // Assert
        assertEquals("", category.getName());
    }

    @Test
    void testSetNameWithWhitespace() {
        // Arrange
        String whitespace = "   ";

        // Act
        category.setName(whitespace);

        // Assert
        assertEquals(whitespace, category.getName());
    }

    @Test
    void testSetNameWithSpecialCharacters() {
        // Arrange
        String specialName = "Category@#$%^&*()";

        // Act
        category.setName(specialName);

        // Assert
        assertEquals(specialName, category.getName());
    }

    @Test
    void testSetNameWithLongString() {
        // Arrange
        String longName = "A".repeat(1000);

        // Act
        category.setName(longName);

        // Assert
        assertEquals(longName, category.getName());
    }

    @Test
    void testSetNameWithUnicodeCharacters() {
        // Arrange
        String unicodeName = "Катégorie";

        // Act
        category.setName(unicodeName);

        // Assert
        assertEquals(unicodeName, category.getName());
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        Category category1 = new Category();
        category1.setId(1L);
        category1.setName("Electronics");

        Category category2 = new Category();
        category2.setId(1L);
        category2.setName("Electronics");

        // Act & Assert
        assertEquals(category1, category2);
        assertEquals(category1.hashCode(), category2.hashCode());
    }

    @Test
    void testNotEquals() {
        // Arrange
        Category category1 = new Category();
        category1.setId(1L);
        category1.setName("Electronics");

        Category category2 = new Category();
        category2.setId(2L);
        category2.setName("Books");

        // Act & Assert
        assertNotEquals(category1, category2);
    }

    @Test
    void testNotEqualsWithSameIdDifferentName() {
        // Arrange
        Category category1 = new Category();
        category1.setId(1L);
        category1.setName("Electronics");

        Category category2 = new Category();
        category2.setId(1L);
        category2.setName("Books");

        // Act & Assert
        assertNotEquals(category1, category2);
    }

    @Test
    void testNotEqualsWithSameNameDifferentId() {
        // Arrange
        Category category1 = new Category();
        category1.setId(1L);
        category1.setName("Electronics");

        Category category2 = new Category();
        category2.setId(2L);
        category2.setName("Electronics");

        // Act & Assert
        assertNotEquals(category1, category2);
    }

    @Test
    void testToString() {
        // Arrange
        category.setId(1L);
        category.setName("Electronics");

        // Act
        String result = category.toString();

        // Assert
        assertNotNull(result);
        assertTrue(result.contains("1"));
        assertTrue(result.contains("Electronics"));
    }

    @Test
    void testToStringWithNullValues() {
        // Act
        String result = category.toString();

        // Assert
        assertNotNull(result);
    }

    @Test
    void testEntityAnnotationPresent() {
        // Act & Assert
        assertTrue(Category.class.isAnnotationPresent(jakarta.persistence.Entity.class));
        assertTrue(Category.class.isAnnotationPresent(jakarta.persistence.Table.class));
    }

    @Test
    void testLombokDataAnnotation() {
        // Act & Assert
        assertTrue(Category.class.isAnnotationPresent(lombok.Data.class));
    }

    @Test
    void testTableAnnotation() {
        // Act
        jakarta.persistence.Table tableAnnotation = Category.class.getAnnotation(jakarta.persistence.Table.class);

        // Assert
        assertNotNull(tableAnnotation);
        assertEquals("category", tableAnnotation.name());
    }

    @Test
    void testIdFieldAnnotations() throws NoSuchFieldException {
        // Act
        java.lang.reflect.Field idField = Category.class.getDeclaredField("id");

        // Assert
        assertTrue(idField.isAnnotationPresent(jakarta.persistence.Id.class));
        assertTrue(idField.isAnnotationPresent(jakarta.persistence.GeneratedValue.class));
        assertTrue(idField.isAnnotationPresent(jakarta.persistence.Column.class));
    }

    @Test
    void testNameFieldAnnotation() throws NoSuchFieldException {
        // Act
        java.lang.reflect.Field nameField = Category.class.getDeclaredField("name");

        // Assert
        assertTrue(nameField.isAnnotationPresent(jakarta.persistence.Column.class));
    }
}