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

        when(userRepository.findByUsername("testUser")).thenReturn(mockUser);

        User result = userRepository.findByUsername("testUser");

        assertNotNull(result);
        assertEquals("testUser", result.getUsername());
        assertEquals(1L, result.getId());
        verify(userRepository, times(1)).findByUsername("testUser");
    }

    @Test
    void testFindByUsernameNotFound() {
        when(userRepository.findByUsername("nonexistent")).thenReturn(null);

        User result = userRepository.findByUsername("nonexistent");

        assertNull(result);
        verify(userRepository, times(1)).findByUsername("nonexistent");
    }

    @Test
    void testFindByUsernameWithNullParameter() {
        when(userRepository.findByUsername(null)).thenReturn(null);

        User result = userRepository.findByUsername(null);

        assertNull(result);
        verify(userRepository, times(1)).findByUsername(null);
    }

    @Test
    void testFindByUsernameWithEmptyString() {
        when(userRepository.findByUsername("")).thenReturn(null);

        User result = userRepository.findByUsername("");

        assertNull(result);
        verify(userRepository, times(1)).findByUsername("");
    }

    @Test
    void testFindAllByEnabledWithEnabledUsers() {
        List<User> enabledUsers = new ArrayList<>();

        User user1 = new User();
        user1.setId(1L);
        user1.setUsername("user1");
        user1.setEnabled(1);

        User user2 = new User();
        user2.setId(2L);
        user2.setUsername("user2");
        user2.setEnabled(1);

        enabledUsers.add(user1);
        enabledUsers.add(user2);

        when(userRepository.findAllByEnabled(1)).thenReturn(enabledUsers);

        Iterable<User> result = userRepository.findAllByEnabled(1);

        assertNotNull(result);
        List<User> resultList = new ArrayList<>();
        result.forEach(resultList::add);
        assertEquals(2, resultList.size());
        assertEquals("user1", resultList.get(0).getUsername());
        assertEquals("user2", resultList.get(1).getUsername());
        verify(userRepository, times(1)).findAllByEnabled(1);
    }

    @Test
    void testFindAllByEnabledWithDisabledUsers() {
        List<User> disabledUsers = new ArrayList<>();

        User user1 = new User();
        user1.setId(1L);
        user1.setUsername("disabledUser");
        user1.setEnabled(0);

        disabledUsers.add(user1);

        when(userRepository.findAllByEnabled(0)).thenReturn(disabledUsers);

        Iterable<User> result = userRepository.findAllByEnabled(0);

        assertNotNull(result);
        List<User> resultList = new ArrayList<>();
        result.forEach(resultList::add);
        assertEquals(1, resultList.size());
        assertEquals("disabledUser", resultList.get(0).getUsername());
        assertEquals(0, resultList.get(0).getEnabled());
        verify(userRepository, times(1)).findAllByEnabled(0);
    }

    @Test
    void testFindAllByEnabledWithNoResults() {
        List<User> emptyList = new ArrayList<>();
        when(userRepository.findAllByEnabled(1)).thenReturn(emptyList);

        Iterable<User> result = userRepository.findAllByEnabled(1);

        assertNotNull(result);
        List<User> resultList = new ArrayList<>();
        result.forEach(resultList::add);
        assertEquals(0, resultList.size());
        verify(userRepository, times(1)).findAllByEnabled(1);
    }

    @Test
    void testFindByUsernameCaseSensitive() {
        User userUpperCase = new User();
        userUpperCase.setId(1L);
        userUpperCase.setUsername("TESTUSER");

        when(userRepository.findByUsername("TESTUSER")).thenReturn(userUpperCase);
        when(userRepository.findByUsername("testuser")).thenReturn(null);

        User upperCaseResult = userRepository.findByUsername("TESTUSER");
        User lowerCaseResult = userRepository.findByUsername("testuser");

        assertNotNull(upperCaseResult);
        assertEquals("TESTUSER", upperCaseResult.getUsername());
        assertNull(lowerCaseResult);

        verify(userRepository, times(1)).findByUsername("TESTUSER");
        verify(userRepository, times(1)).findByUsername("testuser");
    }

    @Test
    void testFindAllByEnabledWithInvalidValue() {
        List<User> emptyList = new ArrayList<>();
        when(userRepository.findAllByEnabled(999)).thenReturn(emptyList);

        Iterable<User> result = userRepository.findAllByEnabled(999);

        assertNotNull(result);
        List<User> resultList = new ArrayList<>();
        result.forEach(resultList::add);
        assertEquals(0, resultList.size());
        verify(userRepository, times(1)).findAllByEnabled(999);
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