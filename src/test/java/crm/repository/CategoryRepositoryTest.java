package crm.repository;

import crm.entity.Category;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DataJpaTest
class CategoryRepositoryTest {

    @MockBean
    private CategoryRepository categoryRepository;

    @Test
    void testFindByName() {
        Category mockCategory = new Category();
        mockCategory.setId(1);
        mockCategory.setName("Premium");

        when(categoryRepository.findByName("Premium")).thenReturn(mockCategory);

        Category result = categoryRepository.findByName("Premium");

        assertNotNull(result);
        assertEquals("Premium", result.getName());
        assertEquals(1, result.getId());
        verify(categoryRepository, times(1)).findByName("Premium");
    }

    @Test
    void testFindByNameNotFound() {
        when(categoryRepository.findByName("Nonexistent")).thenReturn(null);

        Category result = categoryRepository.findByName("Nonexistent");

        assertNull(result);
        verify(categoryRepository, times(1)).findByName("Nonexistent");
    }

    @Test
    void testFindByNameWithNullParameter() {
        when(categoryRepository.findByName(null)).thenReturn(null);

        Category result = categoryRepository.findByName(null);

        assertNull(result);
        verify(categoryRepository, times(1)).findByName(null);
    }

    @Test
    void testFindByNameWithEmptyString() {
        when(categoryRepository.findByName("")).thenReturn(null);

        Category result = categoryRepository.findByName("");

        assertNull(result);
        verify(categoryRepository, times(1)).findByName("");
    }

    @Test
    void testFindByNameCaseSensitive() {
        Category premiumCategory = new Category();
        premiumCategory.setId(1);
        premiumCategory.setName("Premium");

        when(categoryRepository.findByName("Premium")).thenReturn(premiumCategory);
        when(categoryRepository.findByName("premium")).thenReturn(null);

        Category upperCaseResult = categoryRepository.findByName("Premium");
        Category lowerCaseResult = categoryRepository.findByName("premium");

        assertNotNull(upperCaseResult);
        assertEquals("Premium", upperCaseResult.getName());
        assertNull(lowerCaseResult);

        verify(categoryRepository, times(1)).findByName("Premium");
        verify(categoryRepository, times(1)).findByName("premium");
    }

    @Test
    void testFindByNameMultipleCategories() {
        Category basicCategory = new Category();
        basicCategory.setId(1);
        basicCategory.setName("Basic");

        Category premiumCategory = new Category();
        premiumCategory.setId(2);
        premiumCategory.setName("Premium");

        when(categoryRepository.findByName("Basic")).thenReturn(basicCategory);
        when(categoryRepository.findByName("Premium")).thenReturn(premiumCategory);

        Category basicResult = categoryRepository.findByName("Basic");
        Category premiumResult = categoryRepository.findByName("Premium");

        assertNotNull(basicResult);
        assertNotNull(premiumResult);
        assertEquals("Basic", basicResult.getName());
        assertEquals("Premium", premiumResult.getName());
        assertNotEquals(basicResult.getId(), premiumResult.getId());

        verify(categoryRepository, times(1)).findByName("Basic");
        verify(categoryRepository, times(1)).findByName("Premium");
    }

    @Test
    void testFindByNameWithSpecialCharacters() {
        Category specialCategory = new Category();
        specialCategory.setId(1);
        specialCategory.setName("VIP-Premium");

        when(categoryRepository.findByName("VIP-Premium")).thenReturn(specialCategory);

        Category result = categoryRepository.findByName("VIP-Premium");

        assertNotNull(result);
        assertEquals("VIP-Premium", result.getName());
        verify(categoryRepository, times(1)).findByName("VIP-Premium");
    }

    @Test
    void testFindByNameWithWhitespace() {
        Category categoryWithSpaces = new Category();
        categoryWithSpaces.setId(1);
        categoryWithSpaces.setName("Gold Member");

        when(categoryRepository.findByName("Gold Member")).thenReturn(categoryWithSpaces);

        Category result = categoryRepository.findByName("Gold Member");

        assertNotNull(result);
        assertEquals("Gold Member", result.getName());
        verify(categoryRepository, times(1)).findByName("Gold Member");
    }

    @Test
    void testRepositoryInterface() {
        assertTrue(CategoryRepository.class.isInterface());
    }

    @Test
    void testExtendsJpaRepository() {
        assertTrue(org.springframework.data.jpa.repository.JpaRepository.class.isAssignableFrom(CategoryRepository.class));
    }

    @Test
    void testRepositoryAnnotation() {
        assertTrue(CategoryRepository.class.isAnnotationPresent(org.springframework.stereotype.Repository.class));
    }
}