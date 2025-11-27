package crm.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryTest {

    private Category category;

    @BeforeEach
    public void setUp() {
        category = new Category();
    }

    @Test
    public void testCategoryConstructor() {
        assertNotNull(category);
    }

    @Test
    public void testSetAndGetId() {
        category.setId(1L);
        assertEquals(1L, category.getId());
    }

    @Test
    public void testSetAndGetName() {
        category.setName("Test Category");
        assertEquals("Test Category", category.getName());
    }

    @Test
    public void testSetNameNull() {
        category.setName(null);
        assertNull(category.getName());
    }

    @Test
    public void testSetIdNull() {
        category.setId(null);
        assertNull(category.getId());
    }

    @Test
    public void testSetIdZero() {
        category.setId(0L);
        assertEquals(0L, category.getId());
    }

    @Test
    public void testSetIdNegative() {
        category.setId(-1L);
        assertEquals(-1L, category.getId());
    }

    @Test
    public void testCategoryEquality() {
        Category cat1 = new Category();
        cat1.setId(1L);
        cat1.setName("Category1");

        Category cat2 = new Category();
        cat2.setId(1L);
        cat2.setName("Category1");

        assertEquals(cat1.getId(), cat2.getId());
        assertEquals(cat1.getName(), cat2.getName());
    }

    @Test
    public void testToString() {
        category.setId(1L);
        category.setName("Test");
        String result = category.toString();
        assertNotNull(result);
    }

    @Test
    public void testSetNameEmptyString() {
        category.setName("");
        assertEquals("", category.getName());
    }
}
