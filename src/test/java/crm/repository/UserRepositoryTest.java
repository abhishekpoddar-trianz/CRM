package crm.repository;

import crm.entity.Role;
import crm.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    void testFindByUsername() {
        Role role = new Role();
        role.setName("ADMIN");
        entityManager.persist(role);

        User user = new User();
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setEnabled(1);
        user.setRole(role);
        entityManager.persist(user);
        entityManager.flush();

        User found = userRepository.findByUsername("testuser");
        assertNotNull(found);
        assertEquals("testuser", found.getUsername());
    }

    @Test
    void testFindByUsernameNotFound() {
        User found = userRepository.findByUsername("nonexistent");
        assertNull(found);
    }

    @Test
    void testFindAllByEnabled() {
        Role role = new Role();
        role.setName("USER");
        entityManager.persist(role);

        User user1 = new User();
        user1.setUsername("user1");
        user1.setEmail("user1@example.com");
        user1.setPassword("password1");
        user1.setEnabled(1);
        user1.setRole(role);
        entityManager.persist(user1);

        User user2 = new User();
        user2.setUsername("user2");
        user2.setEmail("user2@example.com");
        user2.setPassword("password2");
        user2.setEnabled(0);
        user2.setRole(role);
        entityManager.persist(user2);
        entityManager.flush();

        Iterable<User> enabledUsers = userRepository.findAllByEnabled(1);
        assertNotNull(enabledUsers);
        assertTrue(enabledUsers.iterator().hasNext());
    }

    @Test
    void testFindAllByEnabledZero() {
        Role role = new Role();
        role.setName("USER");
        entityManager.persist(role);

        User user = new User();
        user.setUsername("disableduser");
        user.setEmail("disabled@example.com");
        user.setPassword("password");
        user.setEnabled(0);
        user.setRole(role);
        entityManager.persist(user);
        entityManager.flush();

        Iterable<User> disabledUsers = userRepository.findAllByEnabled(0);
        assertNotNull(disabledUsers);
        assertTrue(disabledUsers.iterator().hasNext());
    }
}
