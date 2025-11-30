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
        Category category = new Category();
        assertNotNull(category);
    }

    @Test
    void testSetAndGetId() {
        category.setId(1L);
        assertEquals(1L, category.getId());
    }

    @Test
    void testSetAndGetName() {
        category.setName("Technology");
        assertEquals("Technology", category.getName());
    }

    @Test
    void testSetIdWithNullValue() {
        category.setId(null);
        assertNull(category.getId());
    }

    @Test
    void testSetNameWithNullValue() {
        category.setName(null);
        assertNull(category.getName());
    }

    @Test
    void testSetNameWithEmptyString() {
        category.setName("");
        assertEquals("", category.getName());
    }

    @Test
    void testSetIdWithLargeValue() {
        Long largeId = Long.MAX_VALUE;
        category.setId(largeId);
        assertEquals(largeId, category.getId());
    }

    @Test
    void testSetIdWithZero() {
        category.setId(0L);
        assertEquals(0L, category.getId());
    }

    @Test
    void testSetIdWithNegativeValue() {
        category.setId(-1L);
        assertEquals(-1L, category.getId());
    }

    @Test
    void testMultipleCategoriesWithDifferentNames() {
        Category cat1 = new Category();
        cat1.setName("Finance");

        Category cat2 = new Category();
        cat2.setName("Marketing");

        assertNotEquals(cat1.getName(), cat2.getName());
    }

    @Test
    void testCategoryNameWithSpecialCharacters() {
        String specialName = "Tech & Science!";
        category.setName(specialName);
        assertEquals(specialName, category.getName());
    }

    @Test
    void testCategoryNameWithNumbers() {
        String nameWithNumbers = "Category123";
        category.setName(nameWithNumbers);
        assertEquals(nameWithNumbers, category.getName());
    }

    @Test
    void testCategoryNameWithSpaces() {
        String nameWithSpaces = "Business Category";
        category.setName(nameWithSpaces);
        assertEquals(nameWithSpaces, category.getName());
    }

    @Test
    void testLongCategoryName() {
        String longName = "A".repeat(255);
        category.setName(longName);
        assertEquals(longName, category.getName());
        assertEquals(255, category.getName().length());
    }
}
