package crm.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Set;
import java.util.HashSet;

public class CurrentUserTest {

    private CurrentUser currentUser;
    private User user;
    private Set<GrantedAuthority> authorities;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setUsername("testuser");
        user.setPassword("testpassword");

        authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        currentUser = new CurrentUser();
        currentUser.setUser(user);
        currentUser.setAuthorities(authorities);
    }

    @Test
    public void testGetUser() {
        assertEquals(user, currentUser.getUser());
        assertNotNull(currentUser.getUser());
    }

    @Test
    public void testSetUser() {
        User newUser = new User();
        newUser.setUsername("newuser");
        currentUser.setUser(newUser);
        assertEquals(newUser, currentUser.getUser());
        assertEquals("newuser", currentUser.getUsername());
    }

    @Test
    public void testGetAuthorities() {
        assertEquals(authorities, currentUser.getAuthorities());
        assertNotNull(currentUser.getAuthorities());
        assertTrue(currentUser.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER")));
    }

    @Test
    public void testSetAuthorities() {
        Set<GrantedAuthority> newAuthorities = new HashSet<>();
        newAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        currentUser.setAuthorities(newAuthorities);
        assertEquals(newAuthorities, currentUser.getAuthorities());
        assertTrue(currentUser.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")));
    }

    @Test
    public void testGetPassword() {
        assertEquals("testpassword", currentUser.getPassword());
        assertEquals(user.getPassword(), currentUser.getPassword());
    }

    @Test
    public void testGetUsername() {
        assertEquals("testuser", currentUser.getUsername());
        assertEquals(user.getUsername(), currentUser.getUsername());
    }

    @Test
    public void testIsAccountNonExpired() {
        assertTrue(currentUser.isAccountNonExpired());
    }

    @Test
    public void testIsAccountNonLocked() {
        assertTrue(currentUser.isAccountNonLocked());
    }

    @Test
    public void testIsCredentialsNonExpired() {
        assertTrue(currentUser.isCredentialsNonExpired());
    }

    @Test
    public void testIsEnabled() {
        assertTrue(currentUser.isEnabled());
    }

    @Test
    public void testUserDetailsContract() {
        assertNotNull(currentUser.getUsername());
        assertNotNull(currentUser.getPassword());
        assertNotNull(currentUser.getAuthorities());
        assertTrue(currentUser.isAccountNonExpired());
        assertTrue(currentUser.isAccountNonLocked());
        assertTrue(currentUser.isCredentialsNonExpired());
        assertTrue(currentUser.isEnabled());
    }

    @Test
    public void testWithNullUser() {
        CurrentUser nullUserCurrentUser = new CurrentUser();
        nullUserCurrentUser.setUser(null);
        assertNull(nullUserCurrentUser.getUser());
        assertThrows(NullPointerException.class, () -> {
            nullUserCurrentUser.getUsername();
        });
        assertThrows(NullPointerException.class, () -> {
            nullUserCurrentUser.getPassword();
        });
    }

    @Test
    public void testWithEmptyAuthorities() {
        Set<GrantedAuthority> emptyAuthorities = new HashSet<>();
        currentUser.setAuthorities(emptyAuthorities);
        assertNotNull(currentUser.getAuthorities());
        assertTrue(currentUser.getAuthorities().isEmpty());
    }

    @Test
    public void testWithNullAuthorities() {
        currentUser.setAuthorities(null);
        assertNull(currentUser.getAuthorities());
    }
}