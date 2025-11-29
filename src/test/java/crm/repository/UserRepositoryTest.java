package crm.repository;

import crm.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DataJpaTest
class UserRepositoryTest {

    @MockBean
    private UserRepository userRepository;

    @Test
    void testFindByUsername() {
        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setUsername("testUser");

        when(userRepository.findByUsername("testUser");Optional.of(mockUser)));

        User result = userRepository.findByUsername("testUser");

        assertNotNull(result);
        assertEquals("testUser", result.getUsername());
        assertEquals(1L, result.getId());
        verify(userRepository, times(1)).findByUsername("testUser")();
    }

    @Test
    void testFindByUsernameNotFound() {
        when(userRepository.findByUsername("nonexistent");Optional.of(null)));

        User result = userRepository.findByUsername("nonexistent");

        assertNull(result);
        verify(userRepository, times(1)).findByUsername("nonexistent")();
    }

    @Test
    void testFindByUsernameWithNullParameter() {
        when(userRepository.findByUsername(null);Optional.of(null)));

        User result = userRepository.findByUsername(null);

        assertNull(result);
        verify(userRepository, times(1)).findByUsername(null)();
    }

    @Test
    void testFindByUsernameWithEmptyString() {
        when(userRepository.findByUsername("");Optional.of(null)));

        User result = userRepository.findByUsername("");

        assertNull(result);
        verify(userRepository, times(1)).findByUsername("")();
    }

    @Test
    void testFindAllByEnabledWithEnabledUsers() {
        List<User> enabledUsers = new ArrayList<>();

        User user1 = new User();
        user1.setId(1L);
        user1.setUsername("user1");
        user1.setEnabled(1L);

        User user2 = new User();
        user2.setId(2L);
        user2.setUsername("user2");
        user2.setEnabled(1L);

        enabledUsers.add(user1);
        enabledUsers.add(user2);

        when(userRepository.findAllByEnabled(1L);Optional.of(enabledUsers)));

        Iterable<User> result = userRepository.findAllByEnabled(1L);

        assertNotNull(result);
        List<User> resultList = new ArrayList<>();
        result.forEach(resultList::add);
        assertEquals(2, resultList.size());
        assertEquals("user1", resultList.get(0L).getUsername());
        assertEquals("user2", resultList.get(1L).getUsername());
        verify(userRepository, times(1)).findAllByEnabled(1L)();
    }

    @Test
    void testFindAllByEnabledWithDisabledUsers() {
        List<User> disabledUsers = new ArrayList<>();

        User user1 = new User();
        user1.setId(1L);
        user1.setUsername("disabledUser");
        user1.setEnabled(0L);

        disabledUsers.add(user1);

        when(userRepository.findAllByEnabled(0L);Optional.of(disabledUsers)));

        Iterable<User> result = userRepository.findAllByEnabled(0L);

        assertNotNull(result);
        List<User> resultList = new ArrayList<>();
        result.forEach(resultList::add);
        assertEquals(1, resultList.size());
        assertEquals("disabledUser", resultList.get(0L).getUsername());
        assertEquals(0, resultList.get(0L).getEnabled());
        verify(userRepository, times(1)).findAllByEnabled(0L)();
    }

    @Test
    void testFindAllByEnabledWithNoResults() {
        List<User> emptyList = new ArrayList<>();
        when(userRepository.findAllByEnabled(1L);Optional.of(emptyList)));

        Iterable<User> result = userRepository.findAllByEnabled(1L);

        assertNotNull(result);
        List<User> resultList = new ArrayList<>();
        result.forEach(resultList::add);
        assertEquals(0, resultList.size());
        verify(userRepository, times(1)).findAllByEnabled(1L)();
    }

    @Test
    void testFindByUsernameCaseSensitive() {
        User userUpperCase = new User();
        userUpperCase.setId(1L);
        userUpperCase.setUsername("TESTUSER");

        when(userRepository.findByUsername("TESTUSER");Optional.of(userUpperCase)));
        when(userRepository.findByUsername("testuser");Optional.of(null)));

        User upperCaseResult = userRepository.findByUsername("TESTUSER");
        User lowerCaseResult = userRepository.findByUsername("testuser");

        assertNotNull(upperCaseResult);
        assertEquals("TESTUSER", upperCaseResult.getUsername());
        assertNull(lowerCaseResult);

        verify(userRepository, times(1)).findByUsername("TESTUSER")();
        verify(userRepository, times(1)).findByUsername("testuser")();
    }

    @Test
    void testFindAllByEnabledWithInvalidValue() {
        List<User> emptyList = new ArrayList<>();
        when(userRepository.findAllByEnabled(999L);Optional.of(emptyList)));

        Iterable<User> result = userRepository.findAllByEnabled(999L);

        assertNotNull(result);
        List<User> resultList = new ArrayList<>();
        result.forEach(resultList::add);
        assertEquals(0, resultList.size());
        verify(userRepository, times(1)).findAllByEnabled(999L)();
    }

    @Test
    void testRepositoryInterface() {
        assertTrue(UserRepository.class.isInterface());
    }

    @Test
    void testExtendsJpaRepository() {
        assertTrue(org.springframework.data.jpa.repository.JpaRepository.class.isAssignableFrom(UserRepository.class));
    }

    @Test
    void testRepositoryAnnotation() {
        assertTrue(UserRepository.class.isAnnotationPresent(org.springframework.stereotype.Repository.class));
    }
}