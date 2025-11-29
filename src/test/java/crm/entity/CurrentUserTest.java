package crm.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CurrentUserTest {

    private CurrentUser currentUser;
    private User testUser;
    private Set<GrantedAuthority> testAuthorities;

    @BeforeEach
    void setUp() {
        currentUser = new CurrentUser();

        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testuser");
        testUser.setPassword("password123");
        testUser.setEmail("test@example.com");
        testUser.setEnabled(true);

        testAuthorities = new HashSet<>();
        testAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        testAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
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
        currentUser.setUser(testUser);

        // Assert
        assertEquals(testUser, currentUser.getUser());
        assertEquals("testuser", currentUser.getUser().getUsername());
        assertEquals("password123", currentUser.getUser().getPassword());
    }

    @Test
    void testSetAndGetAuthorities() {
        // Act
        currentUser.setAuthorities(testAuthorities);

        // Assert
        assertEquals(testAuthorities, currentUser.getAuthorities());
        assertEquals(2, currentUser.getAuthorities().size());
        assertTrue(currentUser.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER")));
        assertTrue(currentUser.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")));
    }

    @Test
    void testGetAuthoritiesFromInterface() {
        // Arrange
        currentUser.setAuthorities(testAuthorities);

        // Act
        Collection<? extends GrantedAuthority> authorities = currentUser.getAuthorities();

        // Assert
        assertNotNull(authorities);
        assertEquals(2, authorities.size());
        assertTrue(authorities.contains(new SimpleGrantedAuthority("ROLE_USER")));
        assertTrue(authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN")));
    }

    @Test
    void testGetPassword() {
        // Arrange
        currentUser.setUser(testUser);

        // Act
        String password = currentUser.getPassword();

        // Assert
        assertEquals("password123", password);
        assertEquals(testUser.getPassword(), password);
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
        currentUser.setUser(testUser);

        // Act
        String username = currentUser.getUsername();

        // Assert
        assertEquals("testuser", username);
        assertEquals(testUser.getUsername(), username);
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
        currentUser.setUser(testUser);
        currentUser.setAuthorities(testAuthorities);

        // Act & Assert - Test all UserDetails interface methods
        assertEquals("testuser", currentUser.getUsername());
        assertEquals("password123", currentUser.getPassword());
        assertEquals(testAuthorities, currentUser.getAuthorities());
        assertTrue(currentUser.isAccountNonExpired());
        assertTrue(currentUser.isAccountNonLocked());
        assertTrue(currentUser.isCredentialsNonExpired());
        assertTrue(currentUser.isEnabled());
    }

    @Test
    void testWithEmptyAuthorities() {
        // Arrange
        Set<GrantedAuthority> emptyAuthorities = new HashSet<>();
        currentUser.setAuthorities(emptyAuthorities);

        // Act
        Collection<? extends GrantedAuthority> authorities = currentUser.getAuthorities();

        // Assert
        assertNotNull(authorities);
        assertTrue(authorities.isEmpty());
        assertEquals(0, authorities.size());
    }

    @Test
    void testWithSingleAuthority() {
        // Arrange
        Set<GrantedAuthority> singleAuthority = new HashSet<>();
        singleAuthority.add(new SimpleGrantedAuthority("ROLE_USER"));
        currentUser.setAuthorities(singleAuthority);

        // Act
        Collection<? extends GrantedAuthority> authorities = currentUser.getAuthorities();

        // Assert
        assertNotNull(authorities);
        assertEquals(1, authorities.size());
        assertTrue(authorities.contains(new SimpleGrantedAuthority("ROLE_USER")));
    }

    @Test
    void testWithNullAuthorities() {
        // Arrange
        currentUser.setAuthorities(null);

        // Act
        Collection<? extends GrantedAuthority> authorities = currentUser.getAuthorities();

        // Assert
        assertNull(authorities);
    }

    @Test
    void testUserWithDifferentProperties() {
        // Arrange
        User differentUser = new User();
        differentUser.setUsername("adminuser");
        differentUser.setPassword("adminpass");
        differentUser.setEmail("admin@example.com");

        // Act
        currentUser.setUser(differentUser);

        // Assert
        assertEquals("adminuser", currentUser.getUsername());
        assertEquals("adminpass", currentUser.getPassword());
        assertEquals(differentUser, currentUser.getUser());
    }

    @Test
    void testAuthorityTypes() {
        // Arrange - Test different types of authorities
        Set<GrantedAuthority> diverseAuthorities = new HashSet<>();
        diverseAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        diverseAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        diverseAuthorities.add(new SimpleGrantedAuthority("PERMISSION_READ"));
        diverseAuthorities.add(new SimpleGrantedAuthority("PERMISSION_WRITE"));

        currentUser.setAuthorities(diverseAuthorities);

        // Act
        Collection<? extends GrantedAuthority> authorities = currentUser.getAuthorities();

        // Assert
        assertEquals(4, authorities.size());
        assertTrue(authorities.contains(new SimpleGrantedAuthority("ROLE_USER")));
        assertTrue(authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN")));
        assertTrue(authorities.contains(new SimpleGrantedAuthority("PERMISSION_READ")));
        assertTrue(authorities.contains(new SimpleGrantedAuthority("PERMISSION_WRITE")));
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        CurrentUser currentUser1 = new CurrentUser();
        CurrentUser currentUser2 = new CurrentUser();

        currentUser1.setUser(testUser);
        currentUser1.setAuthorities(testAuthorities);

        currentUser2.setUser(testUser);
        currentUser2.setAuthorities(testAuthorities);

        // Act & Assert
        assertEquals(currentUser1, currentUser2);
        assertEquals(currentUser1.hashCode(), currentUser2.hashCode());
    }

    @Test
    void testToString() {
        // Arrange
        currentUser.setUser(testUser);
        currentUser.setAuthorities(testAuthorities);

        // Act
        String toString = currentUser.toString();

        // Assert
        assertNotNull(toString);
        assertTrue(toString.contains("user"));
        assertTrue(toString.contains("authorities"));
    }

    @Test
    void testCompleteUserDetailsSetup() {
        // Arrange - Set up a complete CurrentUser
        currentUser.setUser(testUser);
        currentUser.setAuthorities(testAuthorities);

        // Act & Assert - Verify all UserDetails contract methods
        assertNotNull(currentUser.getUsername());
        assertNotNull(currentUser.getPassword());
        assertNotNull(currentUser.getAuthorities());
        assertFalse(currentUser.getAuthorities().isEmpty());
        assertTrue(currentUser.isAccountNonExpired());
        assertTrue(currentUser.isAccountNonLocked());
        assertTrue(currentUser.isCredentialsNonExpired());
        assertTrue(currentUser.isEnabled());
    }

    @Test
    void testUserDetailsImplementation() {
        // Arrange
        currentUser.setUser(testUser);
        currentUser.setAuthorities(testAuthorities);

        // Act - Test that CurrentUser is a proper UserDetails implementation
        org.springframework.security.core.userdetails.UserDetails userDetails = currentUser;

        // Assert
        assertNotNull(userDetails);
        assertEquals("testuser", userDetails.getUsername());
        assertEquals("password123", userDetails.getPassword());
        assertEquals(2, userDetails.getAuthorities().size());
        assertTrue(userDetails.isAccountNonExpired());
        assertTrue(userDetails.isAccountNonLocked());
        assertTrue(userDetails.isCredentialsNonExpired());
        assertTrue(userDetails.isEnabled());
    }

    @Test
    void testAuthorityModification() {
        // Arrange
        currentUser.setAuthorities(testAuthorities);

        // Act - Add another authority
        testAuthorities.add(new SimpleGrantedAuthority("ROLE_MODERATOR"));

        // Assert - The authorities should reflect the change since it's the same reference
        assertEquals(3, currentUser.getAuthorities().size());
        assertTrue(currentUser.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_MODERATOR")));
    }
}