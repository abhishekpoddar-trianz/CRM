package crm.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class CategoryTestSimple {

    private Category category;

    @BeforeEach
    public void setUp() {
        category = new Category();
    }

    @Test
    public void testDefaultConstructor() {
        Category newCategory = new Category();
        assertNotNull(newCategory);
    }

    @Test
    public void testSetAndGetId() {
        Long expectedId = 1L;
        category.setId(expectedId);
        assertEquals(expectedId, category.getId());
    }

    @Test
    public void testSetAndGetName() {
        String expectedName = "Electronics";
        category.setName(expectedName);
        assertEquals(expectedName, category.getName());
    }

    @Test
    public void testSetAndGetNameWithNull() {
        category.setName(null);
        assertNull(category.getName());
    }

    @Test
    public void testToString() {
        category.setId(1L);
        category.setName("Electronics");
        String result = category.toString();
        assertNotNull(result);
        assertTrue(result.contains("1"));
        assertTrue(result.contains("Electronics"));
    }
}