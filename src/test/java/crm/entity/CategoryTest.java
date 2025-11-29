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
    public void testNoArgsConstructor() {
        Category newCategory = new Category();
        assertNotNull(newCategory);
    }

    @Test
    public void testSettersAndGetters() {
        category.setId(1L);
        category.setName("VIP");

        assertEquals(1L, category.getId());
        assertEquals("VIP", category.getName());
    }

    @Test
    public void testIdField() {
        category.setId(Long.MAX_VALUE);
        assertEquals(Long.MAX_VALUE, category.getId());

        category.setId(0L);
        assertEquals(0L, category.getId());

        category.setId(null);
        assertNull(category.getId());
    }

    @Test
    public void testNameField() {
        category.setName("Premium");
        assertEquals("Premium", category.getName());

        category.setName("");
        assertEquals("", category.getName());

        category.setName(null);
        assertNull(category.getName());
    }

    @Test
    public void testEqualsAndHashCode() {
        Category category1 = new Category();
        category1.setId(1L);
        category1.setName("VIP");

        Category category2 = new Category();
        category2.setId(1L);
        category2.setName("VIP");

        assertEquals(category1, category2);
        assertEquals(category1.hashCode(), category2.hashCode());
    }

    @Test
    public void testToString() {
        category.setId(1L);
        category.setName("Premium");

        String categoryString = category.toString();
        assertNotNull(categoryString);
        assertTrue(categoryString.contains("Premium"));
    }

    @Test
    public void testEntityAnnotation() {
        assertTrue(Category.class.isAnnotationPresent(jakarta.persistence.Entity.class));
    }

    @Test
    public void testDataAnnotation() {
        assertTrue(Category.class.isAnnotationPresent(lombok.Data.class));
    }
}