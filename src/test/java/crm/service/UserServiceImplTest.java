package crm.service;

import crm.entity.Role;
import crm.entity.User;
import crm.repository.RoleRepository;
import crm.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private SpringDataUserDetailsService springDataUserDetailsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserServiceImpl();
        userService.setUserRepository(userRepository);
        userService.setRoleRepository(roleRepository);
        userService.setPasswordEncoder(passwordEncoder);
        userService.setAuthenticationManager(authenticationManager);
        userService.setSpringDataUserDetailsService(springDataUserDetailsService);
    }

    @Test
    void testConstructor() {
        assertNotNull(userService);
    }

    @Test
    void testFindByUsername() {
        String username = "testuser";
        User user = User.builder().username(username).build();

        when(userRepository.findByUsername(username)).thenReturn(user);

        User result = userService.findByUsername(username);

        assertEquals(user, result);
        verify(userRepository).findByUsername(username);
    }

    @Test
    void testListAllUsers() {
        List<User> users = Arrays.asList(
                User.builder().id(1L).username("user1").enabled(1).build(),
                User.builder().id(2L).username("user2").enabled(1).build()
        );

        when(userRepository.findAllByEnabled(1)).thenReturn(users);

        Iterable<User> result = userService.listAllUsers();

        assertNotNull(result);
        verify(userRepository).findAllByEnabled(1);
    }

    @Test
    void testShowUser() {
        Long userId = 1L;
        User user = User.builder().id(userId).username("testuser").build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        User result = userService.showUser(userId);

        assertEquals(user, result);
        verify(userRepository).findById(userId);
    }

    @Test
    void testShowUserNotFound() {
        Long userId = 999L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        User result = userService.showUser(userId);

        assertNull(result);
        verify(userRepository).findById(userId);
    }

    @Test
    void testDeleteUser() {
        User user = User.builder()
                .id(1L)
                .username("deleteuser")
                .password("password")
                .enabled(1)
                .build();

        userService.deleteUser(user);

        assertEquals(0, user.getEnabled());
        assertNull(user.getPassword());
        verify(userRepository).save(user);
    }

    @Test
    void testDeleteUserSetsEnabledToZero() {
        User user = User.builder()
                .id(2L)
                .username("user2")
                .password("pass")
                .enabled(1)
                .build();

        userService.deleteUser(user);

        assertEquals(0, user.getEnabled());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testDeleteUserSetsPasswordToNull() {
        User user = User.builder()
                .id(3L)
                .username("user3")
                .password("somepass")
                .enabled(1)
                .build();

        userService.deleteUser(user);

        assertNull(user.getPassword());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testFindByUsernameReturnsNull() {
        String username = "nonexistent";
        when(userRepository.findByUsername(username)).thenReturn(null);

        User result = userService.findByUsername(username);

        assertNull(result);
        verify(userRepository).findByUsername(username);
    }

    @Test
    void testListAllUsersReturnsEmptyList() {
        when(userRepository.findAllByEnabled(1)).thenReturn(Arrays.asList());

        Iterable<User> result = userService.listAllUsers();

        assertNotNull(result);
        verify(userRepository).findAllByEnabled(1);
    }
}
