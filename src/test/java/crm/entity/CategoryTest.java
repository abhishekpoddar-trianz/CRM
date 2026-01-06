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
    void testSettersAndGetters() {
        category.setId(1L);
        category.setName("Technology");

        assertEquals(1L, category.getId());
        assertEquals("Technology", category.getName());
    }

    @Test
    void testDefaultConstructor() {
        Category newCategory = new Category();
        assertNotNull(newCategory);
        assertNull(newCategory.getId());
        assertNull(newCategory.getName());
    }

    @Test
    void testIdGeneration() {
        category.setId(5L);
        assertEquals(5L, category.getId());
    }

    @Test
    void testCategoryName() {
        category.setName("Healthcare");
        assertEquals("Healthcare", category.getName());

        category.setName("Finance");
        assertEquals("Finance", category.getName());
    }

    @Test
    void testNullValues() {
        category.setId(null);
        category.setName(null);

        assertNull(category.getId());
        assertNull(category.getName());
    }
}
