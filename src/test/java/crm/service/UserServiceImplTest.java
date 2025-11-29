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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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

    @Mock
    private UserDetails userDetails;

    private UserServiceImpl userService;

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
    void testSetUserRepository() {
        UserServiceImpl service = new UserServiceImpl();
        service.setUserRepository(userRepository);
        assertDoesNotThrow(() -> service.findByUsername("test"));
    }

    @Test
    void testSetRoleRepository() {
        UserServiceImpl service = new UserServiceImpl();
        service.setRoleRepository(roleRepository);
        assertNotNull(service);
    }

    @Test
    void testSetPasswordEncoder() {
        UserServiceImpl service = new UserServiceImpl();
        service.setPasswordEncoder(passwordEncoder);
        assertNotNull(service);
    }

    @Test
    void testSetAuthenticationManager() {
        UserServiceImpl service = new UserServiceImpl();
        service.setAuthenticationManager(authenticationManager);
        assertNotNull(service);
    }

    @Test
    void testSetSpringDataUserDetailsService() {
        UserServiceImpl service = new UserServiceImpl();
        service.setSpringDataUserDetailsService(springDataUserDetailsService);
        assertNotNull(service);
    }

    @Test
    void testFindByUsername() {
        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setUsername("testUser");

        when(userRepository.findByUsername("testUser")).thenReturn(mockUser);

        User result = userService.findByUsername("testUser");

        assertEquals(mockUser, result);
        assertEquals("testUser", result.getUsername());
        verify(userRepository, times(1)).findByUsername("testUser");
    }

    @Test
    void testFindByUsernameNotFound() {
        when(userRepository.findByUsername("nonexistent")).thenReturn(null);

        User result = userService.findByUsername("nonexistent");

        assertNull(result);
        verify(userRepository, times(1)).findByUsername("nonexistent");
    }

    @Test
    void testListAllUsers() {
        List<User> mockUsers = new ArrayList<>();
        User user1 = new User();
        user1.setId(1L);
        user1.setEnabled(1);
        mockUsers.add(user1);

        when(userRepository.findAllByEnabled(1)).thenReturn(mockUsers);

        Iterable<User> result = userService.listAllUsers();

        assertNotNull(result);
        verify(userRepository, times(1)).findAllByEnabled(1);
    }

    @Test
    void testShowUser() {
        User mockUser = new User();
        mockUser.setId(1L);

        when(userRepository.findOne(1L)).thenReturn(mockUser);

        User result = userService.showUser(1L);

        assertEquals(mockUser, result);
        verify(userRepository, times(1)).findOne(1L);
    }

    @Test
    void testSaveUser() {
        User user = new User();
        user.setId(2L);
        user.setUsername("testUser");
        user.setPassword("plainPassword");

        Role userRole = new Role();
        userRole.setName("ROLE_USER");

        when(roleRepository.findByName("ROLE_USER")).thenReturn(userRole);
        when(passwordEncoder.encode("plainPassword")).thenReturn("encodedPassword");
        when(springDataUserDetailsService.loadUserByUsername("testUser")).thenReturn(userDetails);

        userService.saveUser(user);

        assertEquals(userRole, user.getRole());
        assertEquals(1, user.getEnabled());
        verify(passwordEncoder, times(1)).encode("plainPassword");
        verify(userRepository, times(1)).save(user);
        verify(authenticationManager, times(1)).authenticate(any());
    }

    @Test
    void testSaveUserAsFirstUser() {
        User user = new User();
        user.setId(1L);
        user.setUsername("admin");
        user.setPassword("adminPassword");

        Role userRole = new Role();
        userRole.setName("ROLE_USER");

        Role adminRole = new Role();
        adminRole.setName("ROLE_ADMIN");

        when(roleRepository.findByName("ROLE_USER")).thenReturn(userRole);
        when(roleRepository.findByName("ROLE_ADMIN")).thenReturn(adminRole);
        when(passwordEncoder.encode("adminPassword")).thenReturn("encodedPassword");
        when(springDataUserDetailsService.loadUserByUsername("admin")).thenReturn(userDetails);

        userService.saveUser(user);

        assertEquals(adminRole, user.getRole());
        verify(userRepository, times(2)).save(user);
        verify(roleRepository, times(1)).findByName("ROLE_ADMIN");
    }

    @Test
    void testEditUser() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testUser");
        user.setPassword("newPassword");

        Role existingRole = new Role();
        existingRole.setId(1);
        existingRole.setName("ROLE_ADMIN");
        user.setRole(existingRole);

        Role userRole = new Role();
        userRole.setName("ROLE_USER");

        when(passwordEncoder.encode("newPassword")).thenReturn("encodedNewPassword");
        when(roleRepository.findByName("ROLE_USER")).thenReturn(userRole);
        when(roleRepository.findOne(1)).thenReturn(existingRole);

        userService.editUser(user);

        assertEquals(existingRole, user.getRole());
        assertEquals(1, user.getEnabled());
        verify(passwordEncoder, times(1)).encode("newPassword");
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testEditUserWithNullRole() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testUser");
        user.setPassword("newPassword");
        user.setRole(null);

        Role userRole = new Role();
        userRole.setName("ROLE_USER");

        when(passwordEncoder.encode("newPassword")).thenReturn("encodedNewPassword");
        when(roleRepository.findByName("ROLE_USER")).thenReturn(userRole);

        userService.editUser(user);

        assertEquals(userRole, user.getRole());
        assertEquals(1, user.getEnabled());
        verify(passwordEncoder, times(1)).encode("newPassword");
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testDeleteUser() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testUser");
        user.setEnabled(1);
        user.setPassword("password");

        userService.deleteUser(user);

        assertEquals(0, user.getEnabled());
        assertNull(user.getPassword());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testDeleteUserAlreadyDisabled() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testUser");
        user.setEnabled(0);
        user.setPassword(null);

        userService.deleteUser(user);

        assertEquals(0, user.getEnabled());
        assertNull(user.getPassword());
        verify(userRepository, times(1)).save(user);
    }
}