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
        Role role = new Role();
        role.setId(1);
        role.setName("ADMIN");

        user = new User();
        user.setId(1L);
        user.setUsername("johndoe");
        user.setPassword("password123");
        user.setEmail("john@example.com");
        user.setEnabled(1);
        user.setRole(role);

        authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

        currentUser = new CurrentUser();
        currentUser.setUser(user);
        currentUser.setAuthorities(authorities);
    }

    @Test
    void testConstructor() {
        CurrentUser newCurrentUser = new CurrentUser();
        assertNotNull(newCurrentUser);
    }

    @Test
    void testGetUser() {
        assertEquals(user, currentUser.getUser());
    }

    @Test
    void testSetUser() {
        User newUser = new User();
        newUser.setUsername("janesmith");
        currentUser.setUser(newUser);
        assertEquals(newUser, currentUser.getUser());
    }

    @Test
    void testGetAuthorities() {
        assertEquals(authorities, currentUser.getAuthorities());
        assertEquals(1, currentUser.getAuthorities().size());
    }

    @Test
    void testSetAuthorities() {
        Set<GrantedAuthority> newAuthorities = new HashSet<>();
        newAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        currentUser.setAuthorities(newAuthorities);
        assertEquals(1, currentUser.getAuthorities().size());
    }

    @Test
    void testGetPassword() {
        assertEquals("password123", currentUser.getPassword());
    }

    @Test
    void testGetUsername() {
        assertEquals("johndoe", currentUser.getUsername());
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
    void testMultipleAuthorities() {
        Set<GrantedAuthority> multipleAuthorities = new HashSet<>();
        multipleAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        multipleAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        currentUser.setAuthorities(multipleAuthorities);
        assertEquals(2, currentUser.getAuthorities().size());
    }

    @Test
    void testNullUser() {
        currentUser.setUser(null);
        assertNull(currentUser.getUser());
    }

    @Test
    void testNullAuthorities() {
        currentUser.setAuthorities(null);
        assertNull(currentUser.getAuthorities());
    }
}
