package crm.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

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
    public void testCurrentUserConstructor() {
        assertNotNull(currentUser);
    }

    @Test
    public void testSetAndGetUser() {
        currentUser.setUser(user);
        assertNotNull(currentUser.getUser());
        assertEquals("testuser", currentUser.getUser().getUsername());
    }

    @Test
    public void testSetAndGetAuthorities() {
        currentUser.setAuthorities(authorities);
        assertNotNull(currentUser.getAuthorities());
        assertEquals(1, currentUser.getAuthorities().size());
    }

    @Test
    public void testGetAuthorities() {
        currentUser.setAuthorities(authorities);
        assertEquals(authorities, currentUser.getAuthorities());
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
    public void testGetPasswordWithNullUser() {
        currentUser.setUser(null);
        assertThrows(NullPointerException.class, () -> {
            currentUser.getPassword();
        });
    }

    @Test
    public void testGetUsernameWithNullUser() {
        currentUser.setUser(null);
        assertThrows(NullPointerException.class, () -> {
            currentUser.getUsername();
        });
    }

    @Test
    public void testMultipleAuthorities() {
        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        currentUser.setAuthorities(authorities);
        assertEquals(2, currentUser.getAuthorities().size());
    }

    @Test
    public void testEmptyAuthorities() {
        currentUser.setAuthorities(new HashSet<>());
        assertNotNull(currentUser.getAuthorities());
        assertEquals(0, currentUser.getAuthorities().size());
    }
}
