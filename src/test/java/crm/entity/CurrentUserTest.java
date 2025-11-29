package crm.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CurrentUserTest {

    @Mock
    private User mockUser;

    private CurrentUser currentUser;
    private Set<GrantedAuthority> authorities;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        currentUser = new CurrentUser();
        authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    @Test
    void testConstructor() {
        assertNotNull(currentUser);
    }

    @Test
    void testSetAndGetUser() {
        currentUser.setUser(mockUser);
        assertEquals(mockUser, currentUser.getUser());
    }

    @Test
    void testSetAndGetAuthorities() {
        currentUser.setAuthorities(authorities);
        assertEquals(authorities, currentUser.getAuthorities());
        assertEquals(2, currentUser.getAuthorities().size());
    }

    @Test
    void testGetAuthorities() {
        currentUser.setAuthorities(authorities);
        var result = currentUser.getAuthorities();
        assertNotNull(result);
        assertEquals(authorities, result);
        assertTrue(result.contains(new SimpleGrantedAuthority("ROLE_USER")));
        assertTrue(result.contains(new SimpleGrantedAuthority("ROLE_ADMIN")));
    }

    @Test
    void testGetPassword() {
        when(mockUser.getPassword()).thenReturn("testPassword");
        currentUser.setUser(mockUser);

        String password = currentUser.getPassword();
        assertEquals("testPassword", password);
        verify(mockUser, times(1)).getPassword();
    }

    @Test
    void testGetUsername() {
        when(mockUser.getUsername()).thenReturn("testUser");
        currentUser.setUser(mockUser);

        String username = currentUser.getUsername();
        assertEquals("testUser", username);
        verify(mockUser, times(1)).getUsername();
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
    void testGetPasswordWithNullUser() {
        currentUser.setUser(null);
        assertThrows(NullPointerException.class, () -> currentUser.getPassword());
    }

    @Test
    void testGetUsernameWithNullUser() {
        currentUser.setUser(null);
        assertThrows(NullPointerException.class, () -> currentUser.getUsername());
    }

    @Test
    void testGetAuthoritiesWithNullAuthorities() {
        currentUser.setAuthorities(null);
        assertNull(currentUser.getAuthorities());
    }

    @Test
    void testGetAuthoritiesWithEmptySet() {
        Set<GrantedAuthority> emptyAuthorities = new HashSet<>();
        currentUser.setAuthorities(emptyAuthorities);

        var result = currentUser.getAuthorities();
        assertNotNull(result);
        assertTrue(result.isEmpty());
        assertEquals(0, result.size());
    }

    @Test
    void testAllAccountStatusMethods() {
        assertTrue(currentUser.isAccountNonExpired());
        assertTrue(currentUser.isAccountNonLocked());
        assertTrue(currentUser.isCredentialsNonExpired());
        assertTrue(currentUser.isEnabled());
    }

    @Test
    void testLombokGeneratedMethods() {
        // Test that Lombok @Data annotation generates proper equals and hashCode
        CurrentUser user1 = new CurrentUser();
        CurrentUser user2 = new CurrentUser();

        user1.setUser(mockUser);
        user1.setAuthorities(authorities);

        user2.setUser(mockUser);
        user2.setAuthorities(authorities);

        assertEquals(user1, user2);
        assertEquals(user1.hashCode(), user2.hashCode());
    }

    @Test
    void testToString() {
        currentUser.setUser(mockUser);
        currentUser.setAuthorities(authorities);

        String toString = currentUser.toString();
        assertNotNull(toString);
        assertTrue(toString.contains("CurrentUser"));
    }
}