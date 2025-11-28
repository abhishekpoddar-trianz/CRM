package crm.repository;

import crm.entity.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class RoleRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    void testFindByName() {
        Role role = new Role();
        role.setName("ADMIN");
        entityManager.persist(role);
        entityManager.flush();

        Role found = roleRepository.findByName("ADMIN");
        assertNotNull(found);
        assertEquals("ADMIN", found.getName());
    }

    @Test
    void testFindByNameNotFound() {
        Role found = roleRepository.findByName("NONEXISTENT");
        assertNull(found);
    }

    @Test
    void testSaveRole() {
        Role role = new Role();
        role.setName("USER");
        Role saved = roleRepository.save(role);
        assertNotNull(saved);
        assertTrue(saved.getId() > 0);
        assertEquals("USER", saved.getName());
    }

    @Test
    void testFindById() {
        Role role = new Role();
        role.setName("MODERATOR");
        entityManager.persist(role);
        entityManager.flush();

        Role found = roleRepository.findById(role.getId()).orElse(null);
        assertNotNull(found);
        assertEquals("MODERATOR", found.getName());
    }

    @Test
    void testDeleteRole() {
        Role role = new Role();
        role.setName("TEMP");
        entityManager.persist(role);
        entityManager.flush();

        roleRepository.delete(role);
        Role found = roleRepository.findByName("TEMP");
        assertNull(found);
    }

    @Test
    void testFindAll() {
        Role role1 = new Role();
        role1.setName("ADMIN");
        entityManager.persist(role1);

        Role role2 = new Role();
        role2.setName("USER");
        entityManager.persist(role2);
        entityManager.flush();

        Iterable<Role> roles = roleRepository.findAll();
        assertNotNull(roles);
        assertTrue(roles.iterator().hasNext());
    }
}
