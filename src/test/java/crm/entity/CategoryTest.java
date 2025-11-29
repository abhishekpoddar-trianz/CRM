package crm.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        Long expectedId = 1L;

        // Act
        category.setId(expectedId);

        // Assert
        assertEquals(expectedId, category.getId());
    }

    @Test
    void testSetAndGetName() {
        // Arrange
        String expectedName = "Electronics";

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
    void testSetNameWithNull() {
        // Act
        category.setName(null);

        // Assert
        assertNull(category.getName());
    }

    @Test
    void testSetIdWithDifferentValues() {
        // Test with various ID values
        Long[] testIds = {0L, 1L, 100L, 999L, Long.MAX_VALUE};

        for (Long testId : testIds) {
            // Act
            category.setId(testId);

            // Assert
            assertEquals(testId, category.getId());
        }
    }

    @Test
    void testSetNameWithDifferentValues() {
        // Arrange
        String[] testNames = {
            "Electronics", "Books", "Clothing", "Home & Garden",
            "Sports", "Automotive", "Health & Beauty", "Toys"
        };

        for (String testName : testNames) {
            // Act
            category.setName(testName);

            // Assert
            assertEquals(testName, category.getName());
        }
    }

    @Test
    void testSetNameWithEmptyString() {
        // Arrange
        String emptyName = "";

        // Act
        category.setName(emptyName);

        // Assert
        assertEquals(emptyName, category.getName());
        assertTrue(category.getName().isEmpty());
    }

    @Test
    void testSetNameWithWhitespace() {
        // Arrange
        String whitespaceName = "   ";

        // Act
        category.setName(whitespaceName);

        // Assert
        assertEquals(whitespaceName, category.getName());
    }

    @Test
    void testCategoryEntityProperties() {
        // Arrange
        Long id = 5L;
        String name = "Technology";

        // Act
        category.setId(id);
        category.setName(name);

        // Assert
        assertEquals(id, category.getId());
        assertEquals(name, category.getName());
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        Category category1 = new Category();
        Category category2 = new Category();
        category1.setId(1L);
        category1.setName("Test Category");
        category2.setId(1L);
        category2.setName("Test Category");

        // Act & Assert
        assertEquals(category1, category2);
        assertEquals(category1.hashCode(), category2.hashCode());
    }

    @Test
    void testNotEquals() {
        // Arrange
        Category category1 = new Category();
        Category category2 = new Category();
        category1.setId(1L);
        category1.setName("Category One");
        category2.setId(2L);
        category2.setName("Category Two");

        // Act & Assert
        assertNotEquals(category1, category2);
    }

    @Test
    void testToString() {
        // Arrange
        category.setId(1L);
        category.setName("Test Category");

        // Act
        String toString = category.toString();

        // Assert
        assertNotNull(toString);
        assertTrue(toString.contains("id"));
        assertTrue(toString.contains("name"));
    }

    @Test
    void testLombokGeneratedMethods() {
        // Test that Lombok @Data generates equals, hashCode, and toString
        Category category1 = new Category();
        Category category2 = new Category();

        category1.setId(10L);
        category1.setName("TEST_CATEGORY");

        category2.setId(10L);
        category2.setName("TEST_CATEGORY");

        // Assert equals and hashCode work
        assertEquals(category1, category2);
        assertEquals(category1.hashCode(), category2.hashCode());

        // Assert toString works
        String str = category1.toString();
        assertNotNull(str);
        assertTrue(str.length() > 0);
    }

    @Test
    void testCategoryNamesWithSpecialCharacters() {
        // Arrange
        String[] specialNames = {
            "Home & Garden", "Books/Magazines", "Audio-Visual",
            "Health.Care", "Toys & Games", "Art/Crafts"
        };

        for (String specialName : specialNames) {
            // Act
            category.setName(specialName);

            // Assert
            assertEquals(specialName, category.getName());
        }
    }

    @Test
    void testIdBoundaryValues() {
        // Test with boundary values
        Long[] boundaryIds = {Long.MIN_VALUE, -1L, 0L, 1L, Long.MAX_VALUE};

        for (Long boundaryId : boundaryIds) {
            // Act
            category.setId(boundaryId);

            // Assert
            assertEquals(boundaryId, category.getId());
        }
    }

    @Test
    void testNameWithLongString() {
        // Arrange
        String longName = "A".repeat(1000);

        // Act
        category.setName(longName);

        // Assert
        assertEquals(longName, category.getName());
        assertEquals(1000, category.getName().length());
    }

    @Test
    void testCategoryNameCaseSensitivity() {
        // Test different case variations
        String[] caseVariations = {"electronics", "ELECTRONICS", "Electronics", "eLeCTrOnICs"};

        for (String variation : caseVariations) {
            // Act
            category.setName(variation);

            // Assert
            assertEquals(variation, category.getName());
        }
    }

    @Test
    void testMultiplePropertiesUpdate() {
        // Test updating multiple properties in sequence
        Long[] ids = {1L, 2L, 3L};
        String[] names = {"Category1", "Category2", "Category3"};

        for (int i = 0; i < ids.length; i++) {
            // Act
            category.setId(ids[i]);
            category.setName(names[i]);

            // Assert
            assertEquals(ids[i], category.getId());
            assertEquals(names[i], category.getName());
        }
    }

    @Test
    void testResetToNull() {
        // Arrange - First set values
        category.setId(100L);
        category.setName("Initial Category");

        // Act - Reset to null
        category.setId(null);
        category.setName(null);

        // Assert
        assertNull(category.getId());
        assertNull(category.getName());
    }
}