package crm.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class CategoryTest {

    private Category category;

    @BeforeEach
    public void setUp() {
        category = new Category();
    }

    @Test
    public void testSetAndGetId() {
        category.setId(1L);
        assertEquals(1L, category.getId());
    }

    @Test
    public void testSetAndGetName() {
        category.setName("VIP");
        assertEquals("VIP", category.getName());
    }

    @Test
    public void testSetAndGetNameNull() {
        category.setName(null);
        assertNull(category.getName());
    }

    @Test
    public void testSetAndGetIdNull() {
        category.setId(null);
        assertNull(category.getId());
    }

    @Test
    public void testCategoryEquality() {
        Category cat1 = new Category();
        cat1.setId(1L);
        cat1.setName("Premium");

        Category cat2 = new Category();
        cat2.setId(1L);
        cat2.setName("Premium");

        assertEquals(cat1, cat2);
    }

    @Test
    public void testCategoryHashCode() {
        Category cat1 = new Category();
        cat1.setId(1L);
        cat1.setName("Premium");

        Category cat2 = new Category();
        cat2.setId(1L);
        cat2.setName("Premium");

        assertEquals(cat1.hashCode(), cat2.hashCode());
    }
}
