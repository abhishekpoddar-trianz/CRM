package crm.service;

import crm.entity.Role;
import crm.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SpringDataUserDetailsServiceTest {

    private SpringDataUserDetailsService service;

    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new SpringDataUserDetailsService();
        service.userService = userService;
    }

    @Test
    void testLoadUserByUsernameSuccess() {
        Role role = new Role();
        role.setName("ROLE_USER");

        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password");
        user.setRole(role);

        when(userService.findByUsername("testuser")).thenReturn(user);

        UserDetails userDetails = service.loadUserByUsername("testuser");

        assertNotNull(userDetails);
        assertEquals("testuser", userDetails.getUsername());
        assertEquals("password", userDetails.getPassword());
    }

    @Test
    void testLoadUserByUsernameNotFound() {
        when(userService.findByUsername("nonexistent")).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> {
            service.loadUserByUsername("nonexistent");
        });
    }

    @Test
    void testLoadUserByUsernameWithAdminRole() {
        Role role = new Role();
        role.setName("ROLE_ADMIN");

        User user = new User();
        user.setUsername("admin");
        user.setRole(role);

        when(userService.findByUsername("admin")).thenReturn(user);

        UserDetails userDetails = service.loadUserByUsername("admin");

        assertNotNull(userDetails);
        assertFalse(userDetails.getAuthorities().isEmpty());
    }
}
