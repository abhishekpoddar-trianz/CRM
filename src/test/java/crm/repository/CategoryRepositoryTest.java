package crm.repository;

import crm.entity.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CategoryRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void testFindByName() {
        Category category = new Category();
        category.setName("VIP");
        entityManager.persist(category);
        entityManager.flush();

        Category found = categoryRepository.findByName("VIP");
        assertNotNull(found);
        assertEquals("VIP", found.getName());
    }

    @Test
    void testFindByNameNotFound() {
        Category found = categoryRepository.findByName("NONEXISTENT");
        assertNull(found);
    }

    @Test
    void testSaveCategory() {
        Category category = new Category();
        category.setName("Premium");
        Category saved = categoryRepository.save(category);
        assertNotNull(saved);
        assertNotNull(saved.getId());
        assertEquals("Premium", saved.getName());
    }

    @Test
    void testFindById() {
        Category category = new Category();
        category.setName("Standard");
        entityManager.persist(category);
        entityManager.flush();

        Category found = categoryRepository.findById(category.getId()).orElse(null);
        assertNotNull(found);
        assertEquals("Standard", found.getName());
    }

    @Test
    void testDeleteCategory() {
        Category category = new Category();
        category.setName("Temporary");
        entityManager.persist(category);
        entityManager.flush();

        categoryRepository.delete(category);
        Category found = categoryRepository.findByName("Temporary");
        assertNull(found);
    }

    @Test
    void testFindAll() {
        Category category1 = new Category();
        category1.setName("Category1");
        entityManager.persist(category1);

        Category category2 = new Category();
        category2.setName("Category2");
        entityManager.persist(category2);
        entityManager.flush();

        Iterable<Category> categories = categoryRepository.findAll();
        assertNotNull(categories);
        assertTrue(categories.iterator().hasNext());
    }
}
