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
    void testConstructor() {
        Category newCategory = new Category();
        assertNotNull(newCategory);
    }

    @Test
    void testGettersAndSetters() {
        category.setId(1L);
        category.setName("Premium");

        assertEquals(1, category.getId());
        assertEquals("Premium", category.getName());
    }

    @Test
    void testAllArgsConstructor() {
        Category newCategory = new Category(1, "Gold");
        assertEquals(1, newCategory.getId());
        assertEquals("Gold", newCategory.getName());
    }

    @Test
    void testEqualsAndHashCode() {
        Category category1 = new Category();
        category1.setId(1L);
        category1.setName("Premium");

        Category category2 = new Category();
        category2.setId(1L);
        category2.setName("Premium");

        assertEquals(category1, category2);
        assertEquals(category1.hashCode(), category2.hashCode());
    }
}