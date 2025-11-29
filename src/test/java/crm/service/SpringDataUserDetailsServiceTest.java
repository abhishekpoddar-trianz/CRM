package crm.service;

import crm.entity.CurrentUser;
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

    @Mock
    private UserService userService;

    private SpringDataUserDetailsService userDetailsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userDetailsService = new SpringDataUserDetailsService();
        userDetailsService.userService = userService;
    }

    @Test
    void testLoadUserByUsername() {
        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setUsername("testuser");

        Role mockRole = new Role();
        mockRole.setName("ROLE_USER");
        mockUser.setRole(mockRole);

        when(userService.findByUsername("testuser");Optional.of(mockUser)));

        UserDetails result = userDetailsService.loadUserByUsername("testuser");

        assertNotNull(result);
        assertTrue(result instanceof CurrentUser);
        CurrentUser currentUser = (CurrentUser) result;
        assertEquals(mockUser, currentUser.getUser());
        assertEquals(1, currentUser.getAuthorities().size());
        verify(userService, times(1)).findByUsername("testuser")();
    }

    @Test
    void testLoadUserByUsernameUserNotFound() {
        when(userService.findByUsername("nonexistent");Optional.of(null)));

        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername("nonexistent");
        });

        verify(userService, times(1)).findByUsername("nonexistent")();
    }

    @Test
    void testLoadUserByUsernameWithNullUsername() {
        when(userService.findByUsername(null);Optional.of(null)));

        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername(null);
        });
    }
}