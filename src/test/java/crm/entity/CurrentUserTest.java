package crm.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import static org.junit.jupiter.api.Assertions.*;
import java.util.HashSet;
import java.util.Set;

public class CurrentUserTest {

    private CurrentUser currentUser;
    private User user;
    private Set<GrantedAuthority> authorities;

    @BeforeEach
    public void setUp() {
        currentUser = new CurrentUser();
        user = new User();
        user.setUsername("testuser");
        user.setPassword("password123");
        user.setEnabled(1);

        authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Test
    public void testSetAndGetUser() {
        currentUser.setUser(user);
        assertEquals(user, currentUser.getUser());
    }

    @Test
    public void testSetAndGetAuthorities() {
        currentUser.setAuthorities(authorities);
        assertEquals(authorities, currentUser.getAuthorities());
    }

    @Test
    public void testGetAuthorities() {
        currentUser.setAuthorities(authorities);
        assertEquals(1, currentUser.getAuthorities().size());
        assertTrue(currentUser.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER")));
    }

    @Test
    public void testGetPassword() {
        currentUser.setUser(user);
        assertEquals("password123", currentUser.getPassword());
    }

    @Test
    public void testGetUsername() {
        currentUser.setUser(user);
        assertEquals("testuser", currentUser.getUsername());
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
    public void testMultipleAuthorities() {
        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        currentUser.setAuthorities(authorities);
        assertEquals(2, currentUser.getAuthorities().size());
    }

    @Test
    public void testEmptyAuthorities() {
        Set<GrantedAuthority> emptyAuth = new HashSet<>();
        currentUser.setAuthorities(emptyAuth);
        assertTrue(currentUser.getAuthorities().isEmpty());
    }

    @Test
    public void testUserWithNullPassword() {
        User userNull = new User();
        userNull.setUsername("testuser");
        userNull.setPassword(null);
        currentUser.setUser(userNull);
        assertNull(currentUser.getPassword());
    }
}
