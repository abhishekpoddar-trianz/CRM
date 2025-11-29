package crm.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryTest {

    private Category category;

    @BeforeEach
    public void setUp() {
        category = new Category();
        category.setId(1L);
        category.setName("Test Category");
    }

    @Test
    public void testCategoryConstructor() {
        Category newCategory = new Category();
        assertNotNull(newCategory);
        assertNull(newCategory.getId());
        assertNull(newCategory.getName());
    }

    @Test
    public void testSettersAndGetters() {
        assertEquals(1L, category.getId());
        assertEquals("Test Category", category.getName());
    }

    @Test
    public void testSetId() {
        category.setId(2L);
        assertEquals(2L, category.getId());
    }

    @Test
    public void testSetName() {
        category.setName("Updated Category");
        assertEquals("Updated Category", category.getName());
    }

    @Test
    public void testCategoryWithNullName() {
        category.setName(null);
        assertNull(category.getName());
    }

    @Test
    public void testCategoryWithEmptyName() {
        category.setName("");
        assertEquals("", category.getName());
    }

    @Test
    public void testMultipleCategories() {
        Category category1 = new Category();
        category1.setId(1L);
        category1.setName("Category 1");

        Category category2 = new Category();
        category2.setId(2L);
        category2.setName("Category 2");

        assertNotEquals(category1.getId(), category2.getId());
        assertNotEquals(category1.getName(), category2.getName());
    }
}
