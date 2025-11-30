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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
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
    void testFindByUsername() {
        User user = new User();
        user.setUsername("testuser");

        when(userRepository.findByUsername("testuser")).thenReturn(user);

        User result = userService.findByUsername("testuser");

        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
    }

    @Test
    void testListAllUsers() {
        when(userRepository.findAllByEnabled(1)).thenReturn(Arrays.asList(new User(), new User()));

        Iterable<User> result = userService.listAllUsers();

        assertNotNull(result);
        verify(userRepository, times(1)).findAllByEnabled(1);
    }

    @Test
    void testShowUser() {
        User user = new User();
        user.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.showUser(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testDeleteUser() {
        User user = new User();
        user.setId(1L);
        user.setEnabled(1);
        user.setPassword("password");

        userService.deleteUser(user);

        assertEquals(0, user.getEnabled());
        assertNull(user.getPassword());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testEditUser() {
        Role role = new Role();
        role.setId(1);
        role.setName("ROLE_USER");

        User user = new User();
        user.setId(1L);
        user.setPassword("newpassword");
        user.setRole(role);

        when(passwordEncoder.encode("newpassword")).thenReturn("encodedpassword");
        when(roleRepository.findById(1)).thenReturn(Optional.of(role));

        userService.editUser(user);

        verify(userRepository, times(1)).save(user);
        assertEquals(1, user.getEnabled());
    }

    @Test
    void testShowUserNotFound() {
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        User result = userService.showUser(999L);

        assertNull(result);
    }

    @Test
    void testFindByUsernameNotFound() {
        when(userRepository.findByUsername("nonexistent")).thenReturn(null);

        User result = userService.findByUsername("nonexistent");

        assertNull(result);
    }
}
