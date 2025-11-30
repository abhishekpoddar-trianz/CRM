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
        user.setEnabled(1);

        authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Test
    void testConstructor() {
        CurrentUser currentUser = new CurrentUser();
        assertNotNull(currentUser);
    }

    @Test
    void testSetAndGetUser() {
        currentUser.setUser(user);
        assertEquals(user, currentUser.getUser());
    }

    @Test
    void testSetAndGetAuthorities() {
        currentUser.setAuthorities(authorities);
        assertEquals(authorities, currentUser.getAuthorities());
    }

    @Test
    void testGetAuthoritiesReturnsCollection() {
        currentUser.setAuthorities(authorities);
        assertNotNull(currentUser.getAuthorities());
        assertEquals(1, currentUser.getAuthorities().size());
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
    void testWithMultipleAuthorities() {
        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        authorities.add(new SimpleGrantedAuthority("ROLE_MANAGER"));

        currentUser.setAuthorities(authorities);
        assertEquals(3, currentUser.getAuthorities().size());
    }

    @Test
    void testWithNullUser() {
        currentUser.setUser(null);
        assertNull(currentUser.getUser());
    }

    @Test
    void testWithEmptyAuthorities() {
        Set<GrantedAuthority> emptyAuthorities = new HashSet<>();
        currentUser.setAuthorities(emptyAuthorities);
        assertNotNull(currentUser.getAuthorities());
        assertEquals(0, currentUser.getAuthorities().size());
    }

    @Test
    void testGetPasswordWithNullUser() {
        currentUser.setUser(null);
        assertThrows(NullPointerException.class, () -> {
            currentUser.getPassword();
        });
    }

    @Test
    void testGetUsernameWithNullUser() {
        currentUser.setUser(null);
        assertThrows(NullPointerException.class, () -> {
            currentUser.getUsername();
        });
    }

    @Test
    void testUserDetailsInterfaceImplementation() {
        currentUser.setUser(user);
        currentUser.setAuthorities(authorities);

        assertNotNull(currentUser.getUsername());
        assertNotNull(currentUser.getPassword());
        assertNotNull(currentUser.getAuthorities());
        assertTrue(currentUser.isEnabled());
        assertTrue(currentUser.isAccountNonExpired());
        assertTrue(currentUser.isAccountNonLocked());
        assertTrue(currentUser.isCredentialsNonExpired());
    }

    @Test
    void testWithDifferentRoles() {
        Set<GrantedAuthority> adminAuthorities = new HashSet<>();
        adminAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

        currentUser.setAuthorities(adminAuthorities);
        assertTrue(currentUser.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")));
    }
}
