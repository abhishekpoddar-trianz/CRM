package crm.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
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
        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    @Test
    void testDefaultConstructor() {
        // Act
        CurrentUser newCurrentUser = new CurrentUser();

        // Assert
        assertNotNull(newCurrentUser);
        assertNull(newCurrentUser.getUser());
        assertNull(newCurrentUser.getAuthorities());
    }

    @Test
    void testSetAndGetUser() {
        // Act
        currentUser.setUser(user);

        // Assert
        assertEquals(user, currentUser.getUser());
    }

    @Test
    void testSetAndGetAuthorities() {
        // Act
        currentUser.setAuthorities(authorities);

        // Assert
        assertEquals(authorities, currentUser.getAuthorities());
    }

    @Test
    void testGetAuthoritiesFromInterface() {
        // Arrange
        currentUser.setAuthorities(authorities);

        // Act
        Collection<? extends GrantedAuthority> result = currentUser.getAuthorities();

        // Assert
        assertEquals(authorities, result);
        assertEquals(2, result.size());
        assertTrue(result.contains(new SimpleGrantedAuthority("ROLE_USER")));
        assertTrue(result.contains(new SimpleGrantedAuthority("ROLE_ADMIN")));
    }

    @Test
    void testGetPassword() {
        // Arrange
        currentUser.setUser(user);

        // Act
        String password = currentUser.getPassword();

        // Assert
        assertEquals("password123", password);
    }

    @Test
    void testGetPasswordWithNullUser() {
        // Arrange
        currentUser.setUser(null);

        // Act & Assert
        assertThrows(NullPointerException.class, () -> currentUser.getPassword());
    }

    @Test
    void testGetUsername() {
        // Arrange
        currentUser.setUser(user);

        // Act
        String username = currentUser.getUsername();

        // Assert
        assertEquals("testuser", username);
    }

    @Test
    void testGetUsernameWithNullUser() {
        // Arrange
        currentUser.setUser(null);

        // Act & Assert
        assertThrows(NullPointerException.class, () -> currentUser.getUsername());
    }

    @Test
    void testIsAccountNonExpired() {
        // Act
        boolean result = currentUser.isAccountNonExpired();

        // Assert
        assertTrue(result);
    }

    @Test
    void testIsAccountNonLocked() {
        // Act
        boolean result = currentUser.isAccountNonLocked();

        // Assert
        assertTrue(result);
    }

    @Test
    void testIsCredentialsNonExpired() {
        // Act
        boolean result = currentUser.isCredentialsNonExpired();

        // Assert
        assertTrue(result);
    }

    @Test
    void testIsEnabled() {
        // Act
        boolean result = currentUser.isEnabled();

        // Assert
        assertTrue(result);
    }

    @Test
    void testUserDetailsInterfaceMethods() {
        // Arrange
        currentUser.setUser(user);
        currentUser.setAuthorities(authorities);

        // Act & Assert
        assertEquals("testuser", currentUser.getUsername());
        assertEquals("password123", currentUser.getPassword());
        assertEquals(authorities, currentUser.getAuthorities());
        assertTrue(currentUser.isAccountNonExpired());
        assertTrue(currentUser.isAccountNonLocked());
        assertTrue(currentUser.isCredentialsNonExpired());
        assertTrue(currentUser.isEnabled());
    }

    @Test
    void testSetNullUser() {
        // Act
        currentUser.setUser(null);

        // Assert
        assertNull(currentUser.getUser());
    }

    @Test
    void testSetNullAuthorities() {
        // Act
        currentUser.setAuthorities(null);

        // Assert
        assertNull(currentUser.getAuthorities());
    }

    @Test
    void testSetEmptyAuthorities() {
        // Arrange
        Set<GrantedAuthority> emptyAuthorities = new HashSet<>();

        // Act
        currentUser.setAuthorities(emptyAuthorities);

        // Assert
        assertEquals(emptyAuthorities, currentUser.getAuthorities());
        assertTrue(currentUser.getAuthorities().isEmpty());
    }

    @Test
    void testSetUserWithDifferentProperties() {
        // Arrange
        User differentUser = new User();
        differentUser.setUsername("admin");
        differentUser.setPassword("admin123");
        differentUser.setEnabled(0);

        // Act
        currentUser.setUser(differentUser);

        // Assert
        assertEquals(differentUser, currentUser.getUser());
        assertEquals("admin", currentUser.getUsername());
        assertEquals("admin123", currentUser.getPassword());
    }

    @Test
    void testSetSingleAuthority() {
        // Arrange
        Set<GrantedAuthority> singleAuthority = new HashSet<>();
        singleAuthority.add(new SimpleGrantedAuthority("ROLE_USER"));

        // Act
        currentUser.setAuthorities(singleAuthority);

        // Assert
        assertEquals(1, currentUser.getAuthorities().size());
        assertTrue(currentUser.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER")));
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        CurrentUser currentUser1 = new CurrentUser();
        currentUser1.setUser(user);
        currentUser1.setAuthorities(authorities);

        CurrentUser currentUser2 = new CurrentUser();
        currentUser2.setUser(user);
        currentUser2.setAuthorities(authorities);

        // Act & Assert
        assertEquals(currentUser1, currentUser2);
        assertEquals(currentUser1.hashCode(), currentUser2.hashCode());
    }

    @Test
    void testNotEquals() {
        // Arrange
        User differentUser = new User();
        differentUser.setUsername("different");

        CurrentUser currentUser1 = new CurrentUser();
        currentUser1.setUser(user);

        CurrentUser currentUser2 = new CurrentUser();
        currentUser2.setUser(differentUser);

        // Act & Assert
        assertNotEquals(currentUser1, currentUser2);
    }

    @Test
    void testToString() {
        // Arrange
        currentUser.setUser(user);
        currentUser.setAuthorities(authorities);

        // Act
        String result = currentUser.toString();

        // Assert
        assertNotNull(result);
        assertTrue(result.contains("testuser") || result.contains("CurrentUser"));
    }

    @Test
    void testImplementsUserDetails() {
        // Act & Assert
        assertTrue(currentUser instanceof org.springframework.security.core.userdetails.UserDetails);
    }

    @Test
    void testLombokDataAnnotation() {
        // Act & Assert
        assertTrue(CurrentUser.class.isAnnotationPresent(lombok.Data.class));
    }

    @Test
    void testGetPasswordWithNullPassword() {
        // Arrange
        User userWithNullPassword = new User();
        userWithNullPassword.setUsername("testuser");
        userWithNullPassword.setPassword(null);
        currentUser.setUser(userWithNullPassword);

        // Act
        String password = currentUser.getPassword();

        // Assert
        assertNull(password);
    }

    @Test
    void testGetUsernameWithNullUsername() {
        // Arrange
        User userWithNullUsername = new User();
        userWithNullUsername.setUsername(null);
        userWithNullUsername.setPassword("password");
        currentUser.setUser(userWithNullUsername);

        // Act
        String username = currentUser.getUsername();

        // Assert
        assertNull(username);
    }
}