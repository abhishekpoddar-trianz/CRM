package crm.service;

import crm.entity.Role;
import crm.entity.User;
import crm.repository.RoleRepository;
import crm.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

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

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private Role role;

    @BeforeEach
    void setUp() {
        role = new Role();
        role.setId(1);
        role.setName("ROLE_USER");

        user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setPassword("password");
        user.setEmail("test@example.com");
        user.setEnabled(1);
        user.setRole(role);
    }

    @Test
    void testFindByUsername() {
        when(userRepository.findByUsername("testuser")).thenReturn(user);

        User result = userService.findByUsername("testuser");

        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
        verify(userRepository).findByUsername("testuser");
    }

    @Test
    void testFindByUsernameNotFound() {
        when(userRepository.findByUsername("nonexistent")).thenReturn(null);

        User result = userService.findByUsername("nonexistent");

        assertNull(result);
        verify(userRepository).findByUsername("nonexistent");
    }

    @Test
    void testListAllUsers() {
        when(userRepository.findAllByEnabled(1)).thenReturn(Arrays.asList(user));

        Iterable<User> result = userService.listAllUsers();

        assertNotNull(result);
        assertTrue(result.iterator().hasNext());
        verify(userRepository).findAllByEnabled(1);
    }

    @Test
    void testShowUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.showUser(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(userRepository).findById(1L);
    }

    @Test
    void testShowUserNotFound() {
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        User result = userService.showUser(999L);

        assertNull(result);
        verify(userRepository).findById(999L);
    }

    @Test
    void testDeleteUser() {
        userService.deleteUser(user);

        assertEquals(0, user.getEnabled());
        assertNull(user.getPassword());
        verify(userRepository).save(user);
    }
}
