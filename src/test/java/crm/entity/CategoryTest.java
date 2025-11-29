package crm.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.persistence.*;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {

    private Category category;

    @BeforeEach
    void setUp() {
        category = new Category();
    }

    @Test
    void category_defaultConstructor_shouldCreateInstance() {
        // Act
        Category newCategory = new Category();

        // Assert
        assertNotNull(newCategory, "Category should be created");
        assertNull(newCategory.getId(), "Default id should be null");
        assertNull(newCategory.getName(), "Default name should be null");
    }

    @Test
    void setId_withValidId_shouldSetId() {
        // Arrange
        Long expectedId = 123L;

        // Act
        category.setId(expectedId);

        // Assert
        assertEquals(expectedId, category.getId(), "Id should be set correctly");
    }

    @Test
    void getId_afterSettingId_shouldReturnCorrectId() {
        // Arrange
        Long expectedId = 456L;
        category.setId(expectedId);

        // Act
        Long actualId = category.getId();

        // Assert
        assertEquals(expectedId, actualId, "getId should return the set id");
    }

    @Test
    void setName_withValidName_shouldSetName() {
        // Arrange
        String expectedName = "Electronics";

        // Act
        category.setName(expectedName);

        // Assert
        assertEquals(expectedName, category.getName(), "Name should be set correctly");
    }

    @Test
    void getName_afterSettingName_shouldReturnCorrectName() {
        // Arrange
        String expectedName = "Books";
        category.setName(expectedName);

        // Act
        String actualName = category.getName();

        // Assert
        assertEquals(expectedName, actualName, "getName should return the set name");
    }

    @Test
    void setName_withNullName_shouldSetNullName() {
        // Act
        category.setName(null);

        // Assert
        assertNull(category.getName(), "Name should be null when set to null");
    }

    @Test
    void setName_withEmptyName_shouldSetEmptyName() {
        // Arrange
        String emptyName = "";

        // Act
        category.setName(emptyName);

        // Assert
        assertEquals(emptyName, category.getName(), "Name should be empty string");
    }

    @Test
    void setId_withNullId_shouldSetNullId() {
        // Act
        category.setId(null);

        // Assert
        assertNull(category.getId(), "Id should be null when set to null");
    }

    @Test
    void setId_withZeroId_shouldSetZeroId() {
        // Act
        category.setId(0L);

        // Assert
        assertEquals(0L, category.getId(), "Id should be set to 0");
    }

    @Test
    void setName_withLongName_shouldSetLongName() {
        // Arrange
        String longName = "This is a very long category name that might be used in some systems";

        // Act
        category.setName(longName);

        // Assert
        assertEquals(longName, category.getName(), "Long name should be set correctly");
    }

    @Test
    void equals_withSameValues_shouldBeEqual() {
        // Arrange
        Category category1 = new Category();
        category1.setId(1L);
        category1.setName("Electronics");

        Category category2 = new Category();
        category2.setId(1L);
        category2.setName("Electronics");

        // Act & Assert
        assertEquals(category1, category2, "Categories with same values should be equal");
        assertEquals(category1.hashCode(), category2.hashCode(), "Hash codes should be equal");
    }

    @Test
    void equals_withDifferentValues_shouldNotBeEqual() {
        // Arrange
        Category category1 = new Category();
        category1.setId(1L);
        category1.setName("Electronics");

        Category category2 = new Category();
        category2.setId(2L);
        category2.setName("Books");

        // Act & Assert
        assertNotEquals(category1, category2, "Categories with different values should not be equal");
    }

    @Test
    void equals_withDifferentIds_shouldNotBeEqual() {
        // Arrange
        Category category1 = new Category();
        category1.setId(1L);
        category1.setName("Electronics");

        Category category2 = new Category();
        category2.setId(2L);
        category2.setName("Electronics");

        // Act & Assert
        assertNotEquals(category1, category2, "Categories with different ids should not be equal");
    }

    @Test
    void equals_withDifferentNames_shouldNotBeEqual() {
        // Arrange
        Category category1 = new Category();
        category1.setId(1L);
        category1.setName("Electronics");

        Category category2 = new Category();
        category2.setId(1L);
        category2.setName("Books");

        // Act & Assert
        assertNotEquals(category1, category2, "Categories with different names should not be equal");
    }

    @Test
    void toString_shouldReturnStringRepresentation() {
        // Arrange
        category.setId(1L);
        category.setName("TEST_CATEGORY");

        // Act
        String result = category.toString();

        // Assert
        assertNotNull(result, "toString should not return null");
        assertTrue(result.contains("1"), "toString should contain id");
        assertTrue(result.contains("TEST_CATEGORY"), "toString should contain name");
    }

    @Test
    void toString_withNullValues_shouldHandleGracefully() {
        // Act
        String result = category.toString();

        // Assert
        assertNotNull(result, "toString should not return null even with null values");
    }

    @Test
    void categoryClass_shouldHaveCorrectJPAAnnotations() {
        // Assert
        assertTrue(Category.class.isAnnotationPresent(Entity.class), "Category should have @Entity annotation");
        assertTrue(Category.class.isAnnotationPresent(Table.class), "Category should have @Table annotation");

        Table tableAnnotation = Category.class.getAnnotation(Table.class);
        assertEquals("category", tableAnnotation.name(), "Table name should be 'category'");
    }

    @Test
    void idField_shouldHaveCorrectJPAAnnotations() throws NoSuchFieldException {
        // Arrange
        var idField = Category.class.getDeclaredField("id");

        // Assert
        assertTrue(idField.isAnnotationPresent(Id.class), "id field should have @Id annotation");
        assertTrue(idField.isAnnotationPresent(GeneratedValue.class), "id field should have @GeneratedValue annotation");
        assertTrue(idField.isAnnotationPresent(Column.class), "id field should have @Column annotation");

        GeneratedValue generatedValue = idField.getAnnotation(GeneratedValue.class);
        assertEquals(GenerationType.IDENTITY, generatedValue.strategy(), "GeneratedValue strategy should be IDENTITY");

        Column column = idField.getAnnotation(Column.class);
        assertEquals("category_id", column.name(), "Column name should be 'category_id'");
    }

    @Test
    void nameField_shouldHaveCorrectJPAAnnotations() throws NoSuchFieldException {
        // Arrange
        var nameField = Category.class.getDeclaredField("name");

        // Assert
        assertTrue(nameField.isAnnotationPresent(Column.class), "name field should have @Column annotation");

        Column column = nameField.getAnnotation(Column.class);
        assertEquals("category", column.name(), "Column name should be 'category'");
    }

    @Test
    void categoryClass_shouldHaveLombokDataAnnotation() {
        // Assert
        assertTrue(Category.class.isAnnotationPresent(lombok.Data.class), "Category should have @Data annotation");
    }

    @Test
    void categoryClass_shouldHaveCorrectFieldTypes() throws NoSuchFieldException {
        // Assert
        var idField = Category.class.getDeclaredField("id");
        assertEquals(Long.class, idField.getType(), "id field should be of type Long");

        var nameField = Category.class.getDeclaredField("name");
        assertEquals(String.class, nameField.getType(), "name field should be of type String");
    }
}