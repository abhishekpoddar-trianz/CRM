package crm.service;

import crm.entity.Role;
import crm.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SpringDataUserDetailsServiceTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private SpringDataUserDetailsService userDetailsService;

    private User user;
    private Role role;

    @BeforeEach
    void setUp() {
        role = new Role();
        role.setId(1);
        role.setName("ROLE_ADMIN");

        user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setPassword("password");
        user.setEmail("test@example.com");
        user.setEnabled(1);
        user.setRole(role);
    }

    @Test
    void testLoadUserByUsername() {
        when(userService.findByUsername("testuser")).thenReturn(user);

        UserDetails result = userDetailsService.loadUserByUsername("testuser");

        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
        assertEquals("password", result.getPassword());
        assertTrue(result.getAuthorities().size() > 0);
        verify(userService).findByUsername("testuser");
    }

    @Test
    void testLoadUserByUsernameNotFound() {
        when(userService.findByUsername("nonexistent")).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername("nonexistent");
        });

        verify(userService).findByUsername("nonexistent");
    }

    @Test
    void testLoadUserByUsernameWithDifferentRole() {
        Role userRole = new Role();
        userRole.setId(2);
        userRole.setName("ROLE_USER");
        user.setRole(userRole);

        when(userService.findByUsername("testuser")).thenReturn(user);

        UserDetails result = userDetailsService.loadUserByUsername("testuser");

        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
        assertTrue(result.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_USER")));
        verify(userService).findByUsername("testuser");
    }

    @Test
    void testLoadUserByUsernameReturnsCurrentUser() {
        when(userService.findByUsername("testuser")).thenReturn(user);

        UserDetails result = userDetailsService.loadUserByUsername("testuser");

        assertNotNull(result);
        assertTrue(result.isEnabled());
        assertTrue(result.isAccountNonExpired());
        assertTrue(result.isAccountNonLocked());
        assertTrue(result.isCredentialsNonExpired());
    }
}
