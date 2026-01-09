package crm.repository;

import crm.entity.Role;
import crm.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

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
        role.setName("USER");
        entityManager.persist(role);

        User user = User.builder()
                .username("testuser")
                .email("test@example.com")
                .firstName("Test")
                .lastName("User")
                .password("password")
                .enabled(1)
                .role(role)
                .build();
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

        User enabledUser = User.builder()
                .username("enabled")
                .email("enabled@example.com")
                .firstName("Enabled")
                .lastName("User")
                .password("password")
                .enabled(1)
                .role(role)
                .build();
        entityManager.persist(enabledUser);

        User disabledUser = User.builder()
                .username("disabled")
                .email("disabled@example.com")
                .firstName("Disabled")
                .lastName("User")
                .password("password")
                .enabled(0)
                .role(role)
                .build();
        entityManager.persist(disabledUser);
        entityManager.flush();

        Iterable<User> enabledUsers = userRepository.findAllByEnabled(1);
        List<User> enabledList = (List<User>) enabledUsers;
        assertTrue(enabledList.size() > 0);
        assertTrue(enabledList.stream().allMatch(u -> u.getEnabled() == 1));
    }

    @Test
    void testFindAllByEnabledZero() {
        Role role = new Role();
        role.setName("USER");
        entityManager.persist(role);

        User disabledUser = User.builder()
                .username("disabled")
                .email("disabled@example.com")
                .firstName("Disabled")
                .lastName("User")
                .password("password")
                .enabled(0)
                .role(role)
                .build();
        entityManager.persist(disabledUser);
        entityManager.flush();

        Iterable<User> disabledUsers = userRepository.findAllByEnabled(0);
        List<User> disabledList = (List<User>) disabledUsers;
        assertTrue(disabledList.size() > 0);
        assertTrue(disabledList.stream().allMatch(u -> u.getEnabled() == 0));
    }

    @Test
    void testSaveUser() {
        Role role = new Role();
        role.setName("ADMIN");
        entityManager.persist(role);

        User user = User.builder()
                .username("newuser")
                .email("new@example.com")
                .firstName("New")
                .lastName("User")
                .password("password123")
                .enabled(1)
                .role(role)
                .build();

        User savedUser = userRepository.save(user);
        assertNotNull(savedUser.getId());
        assertEquals("newuser", savedUser.getUsername());
    }

    @Test
    void testDeleteUser() {
        Role role = new Role();
        role.setName("USER");
        entityManager.persist(role);

        User user = User.builder()
                .username("deleteuser")
                .email("delete@example.com")
                .firstName("Delete")
                .lastName("User")
                .password("password")
                .enabled(1)
                .role(role)
                .build();
        entityManager.persist(user);
        entityManager.flush();

        Long userId = user.getId();
        userRepository.deleteById(userId);
        assertFalse(userRepository.findById(userId).isPresent());
    }
}
