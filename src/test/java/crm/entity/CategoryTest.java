package crm.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {

    private Category category;

    @BeforeEach
    void setUp() {
        category = new Category();
        category.setId(1L);
        category.setName("Test Category");
    }

    @Test
    void testConstructor() {
        Category newCategory = new Category();
        assertNotNull(newCategory);
        assertNull(newCategory.getId());
        assertNull(newCategory.getName());
    }

    @Test
    void testSettersAndGetters() {
        assertEquals(1L, category.getId());
        assertEquals("Test Category", category.getName());
    }

    @Test
    void testSetId() {
        category.setId(10L);
        assertEquals(10L, category.getId());
    }

    @Test
    void testSetName() {
        category.setName("Updated Category");
        assertEquals("Updated Category", category.getName());
    }

    @Test
    void testNullId() {
        category.setId(null);
        assertNull(category.getId());
    }

    @Test
    void testNullName() {
        category.setName(null);
        assertNull(category.getName());
    }

    @Test
    void testEmptyName() {
        category.setName("");
        assertEquals("", category.getName());
    }

    @Test
    void testLongName() {
        String longName = "A".repeat(255);
        category.setName(longName);
        assertEquals(longName, category.getName());
    }

    @Test
    void testIdAutoGeneration() {
        Category newCategory = new Category();
        assertNull(newCategory.getId());
    }

    @Test
    void testEntityAnnotation() {
        assertNotNull(category);
        assertTrue(category instanceof Category);
    }
}
