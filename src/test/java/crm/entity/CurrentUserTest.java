package crm.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CurrentUserTest {

    private CurrentUser currentUser;
    private User user;
    private Set<GrantedAuthority> authorities;

    @BeforeEach
    void setUp() {
        currentUser = new CurrentUser();

        user = new User();
        user.setUsername("testuser");
        user.setPassword("password123");

        authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Test
    void testSettersAndGetters() {
        currentUser.setUser(user);
        currentUser.setAuthorities(authorities);

        assertEquals(user, currentUser.getUser());
        assertEquals(authorities, currentUser.getAuthorities());
    }

    @Test
    void testGetAuthorities() {
        currentUser.setAuthorities(authorities);

        assertNotNull(currentUser.getAuthorities());
        assertEquals(1, currentUser.getAuthorities().size());
        assertTrue(currentUser.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER")));
    }

    @Test
    void testGetPassword() {
        currentUser.setUser(user);
        assertEquals("password123", currentUser.getPassword());
    }

    @Test
    void testGetUsername() {
        currentUser.setUser(user);
        assertEquals("testuser", currentUser.getUsername());
    }

    @Test
    void testIsAccountNonExpired() {
        assertTrue(currentUser.isAccountNonExpired());
    }

    @Test
    void testIsAccountNonLocked() {
        assertTrue(currentUser.isAccountNonLocked());
    }

    @Test
    void testIsCredentialsNonExpired() {
        assertTrue(currentUser.isCredentialsNonExpired());
    }

    @Test
    void testIsEnabled() {
        assertTrue(currentUser.isEnabled());
    }

    @Test
    void testDefaultConstructor() {
        CurrentUser newCurrentUser = new CurrentUser();
        assertNotNull(newCurrentUser);
        assertNull(newCurrentUser.getUser());
        assertNull(newCurrentUser.getAuthorities());
    }

    @Test
    void testMultipleAuthorities() {
        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        currentUser.setAuthorities(authorities);

        assertEquals(2, currentUser.getAuthorities().size());
        assertTrue(currentUser.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER")));
        assertTrue(currentUser.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")));
    }

    @Test
    void testNullUser() {
        currentUser.setUser(null);
        assertNull(currentUser.getUser());
    }
}
