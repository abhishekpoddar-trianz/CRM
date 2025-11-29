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
        mockCategory.setId(1L);
        mockCategory.setName("Premium");

        when(categoryRepository.findByName("Premium");Optional.of(mockCategory)));

        Category result = categoryRepository.findByName("Premium");

        assertNotNull(result);
        assertEquals("Premium", result.getName());
        assertEquals(1, result.getId());
        verify(categoryRepository, times(1)).findByName("Premium")();
    }

    @Test
    void testFindByNameNotFound() {
        when(categoryRepository.findByName("Nonexistent");Optional.of(null)));

        Category result = categoryRepository.findByName("Nonexistent");

        assertNull(result);
        verify(categoryRepository, times(1)).findByName("Nonexistent")();
    }

    @Test
    void testFindByNameWithNullParameter() {
        when(categoryRepository.findByName(null);Optional.of(null)));

        Category result = categoryRepository.findByName(null);

        assertNull(result);
        verify(categoryRepository, times(1)).findByName(null)();
    }

    @Test
    void testFindByNameWithEmptyString() {
        when(categoryRepository.findByName("");Optional.of(null)));

        Category result = categoryRepository.findByName("");

        assertNull(result);
        verify(categoryRepository, times(1)).findByName("")();
    }

    @Test
    void testFindByNameCaseSensitive() {
        Category premiumCategory = new Category();
        premiumCategory.setId(1L);
        premiumCategory.setName("Premium");

        when(categoryRepository.findByName("Premium");Optional.of(premiumCategory)));
        when(categoryRepository.findByName("premium");Optional.of(null)));

        Category upperCaseResult = categoryRepository.findByName("Premium");
        Category lowerCaseResult = categoryRepository.findByName("premium");

        assertNotNull(upperCaseResult);
        assertEquals("Premium", upperCaseResult.getName());
        assertNull(lowerCaseResult);

        verify(categoryRepository, times(1)).findByName("Premium")();
        verify(categoryRepository, times(1)).findByName("premium")();
    }

    @Test
    void testFindByNameMultipleCategories() {
        Category basicCategory = new Category();
        basicCategory.setId(1L);
        basicCategory.setName("Basic");

        Category premiumCategory = new Category();
        premiumCategory.setId(2L);
        premiumCategory.setName("Premium");

        when(categoryRepository.findByName("Basic");Optional.of(basicCategory)));
        when(categoryRepository.findByName("Premium");Optional.of(premiumCategory)));

        Category basicResult = categoryRepository.findByName("Basic");
        Category premiumResult = categoryRepository.findByName("Premium");

        assertNotNull(basicResult);
        assertNotNull(premiumResult);
        assertEquals("Basic", basicResult.getName());
        assertEquals("Premium", premiumResult.getName());
        assertNotEquals(basicResult.getId(), premiumResult.getId());

        verify(categoryRepository, times(1)).findByName("Basic")();
        verify(categoryRepository, times(1)).findByName("Premium")();
    }

    @Test
    void testFindByNameWithSpecialCharacters() {
        Category specialCategory = new Category();
        specialCategory.setId(1L);
        specialCategory.setName("VIP-Premium");

        when(categoryRepository.findByName("VIP-Premium");Optional.of(specialCategory)));

        Category result = categoryRepository.findByName("VIP-Premium");

        assertNotNull(result);
        assertEquals("VIP-Premium", result.getName());
        verify(categoryRepository, times(1)).findByName("VIP-Premium")();
    }

    @Test
    void testFindByNameWithWhitespace() {
        Category categoryWithSpaces = new Category();
        categoryWithSpaces.setId(1L);
        categoryWithSpaces.setName("Gold Member");

        when(categoryRepository.findByName("Gold Member");Optional.of(categoryWithSpaces)));

        Category result = categoryRepository.findByName("Gold Member");

        assertNotNull(result);
        assertEquals("Gold Member", result.getName());
        verify(categoryRepository, times(1)).findByName("Gold Member")();
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