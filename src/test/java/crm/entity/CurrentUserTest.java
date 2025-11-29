package crm.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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
        testUser.setEnabled(1);

        testAuthorities = new HashSet<>();
        testAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        testAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    @Test
    void currentUser_defaultConstructor_shouldCreateInstance() {
        // Act
        CurrentUser newCurrentUser = new CurrentUser();

        // Assert
        assertNotNull(newCurrentUser, "CurrentUser should be created");
        assertNull(newCurrentUser.getUser(), "Default user should be null");
        assertNull(newCurrentUser.getAuthorities(), "Default authorities should be null");
    }

    @Test
    void currentUser_shouldImplementUserDetails() {
        // Assert
        assertTrue(currentUser instanceof UserDetails, "CurrentUser should implement UserDetails");
    }

    @Test
    void setUser_withValidUser_shouldSetUser() {
        // Act
        currentUser.setUser(testUser);

        // Assert
        assertEquals(testUser, currentUser.getUser(), "User should be set correctly");
    }

    @Test
    void getUser_afterSettingUser_shouldReturnUser() {
        // Arrange
        currentUser.setUser(testUser);

        // Act
        User retrievedUser = currentUser.getUser();

        // Assert
        assertEquals(testUser, retrievedUser, "getUser should return the set user");
    }

    @Test
    void setAuthorities_withValidAuthorities_shouldSetAuthorities() {
        // Act
        currentUser.setAuthorities(testAuthorities);

        // Assert
        assertEquals(testAuthorities, currentUser.getAuthorities(), "Authorities should be set correctly");
    }

    @Test
    void getAuthorities_afterSettingAuthorities_shouldReturnAuthorities() {
        // Arrange
        currentUser.setAuthorities(testAuthorities);

        // Act
        Collection<? extends GrantedAuthority> retrievedAuthorities = currentUser.getAuthorities();

        // Assert
        assertEquals(testAuthorities, retrievedAuthorities, "getAuthorities should return the set authorities");
        assertEquals(2, retrievedAuthorities.size(), "Should return correct number of authorities");
    }

    @Test
    void getPassword_withUserSet_shouldReturnUserPassword() {
        // Arrange
        currentUser.setUser(testUser);

        // Act
        String password = currentUser.getPassword();

        // Assert
        assertEquals(testUser.getPassword(), password, "getPassword should return user's password");
        assertEquals("password123", password, "Password should match expected value");
    }

    @Test
    void getPassword_withNullUser_shouldThrowException() {
        // Arrange
        currentUser.setUser(null);

        // Act & Assert
        assertThrows(NullPointerException.class, () -> currentUser.getPassword(),
                "getPassword should throw NullPointerException when user is null");
    }

    @Test
    void getUsername_withUserSet_shouldReturnUserUsername() {
        // Arrange
        currentUser.setUser(testUser);

        // Act
        String username = currentUser.getUsername();

        // Assert
        assertEquals(testUser.getUsername(), username, "getUsername should return user's username");
        assertEquals("testuser", username, "Username should match expected value");
    }

    @Test
    void getUsername_withNullUser_shouldThrowException() {
        // Arrange
        currentUser.setUser(null);

        // Act & Assert
        assertThrows(NullPointerException.class, () -> currentUser.getUsername(),
                "getUsername should throw NullPointerException when user is null");
    }

    @Test
    void isAccountNonExpired_shouldAlwaysReturnTrue() {
        // Act
        boolean isAccountNonExpired = currentUser.isAccountNonExpired();

        // Assert
        assertTrue(isAccountNonExpired, "isAccountNonExpired should always return true");
    }

    @Test
    void isAccountNonLocked_shouldAlwaysReturnTrue() {
        // Act
        boolean isAccountNonLocked = currentUser.isAccountNonLocked();

        // Assert
        assertTrue(isAccountNonLocked, "isAccountNonLocked should always return true");
    }

    @Test
    void isCredentialsNonExpired_shouldAlwaysReturnTrue() {
        // Act
        boolean isCredentialsNonExpired = currentUser.isCredentialsNonExpired();

        // Assert
        assertTrue(isCredentialsNonExpired, "isCredentialsNonExpired should always return true");
    }

    @Test
    void isEnabled_shouldAlwaysReturnTrue() {
        // Act
        boolean isEnabled = currentUser.isEnabled();

        // Assert
        assertTrue(isEnabled, "isEnabled should always return true");
    }

    @Test
    void setUser_withNullUser_shouldSetNullUser() {
        // Act
        currentUser.setUser(null);

        // Assert
        assertNull(currentUser.getUser(), "User should be null when set to null");
    }

    @Test
    void setAuthorities_withNullAuthorities_shouldSetNullAuthorities() {
        // Act
        currentUser.setAuthorities(null);

        // Assert
        assertNull(currentUser.getAuthorities(), "Authorities should be null when set to null");
    }

    @Test
    void setAuthorities_withEmptySet_shouldSetEmptyAuthorities() {
        // Arrange
        Set<GrantedAuthority> emptyAuthorities = new HashSet<>();

        // Act
        currentUser.setAuthorities(emptyAuthorities);

        // Assert
        assertEquals(emptyAuthorities, currentUser.getAuthorities(), "Authorities should be empty set");
        assertTrue(currentUser.getAuthorities().isEmpty(), "Authorities set should be empty");
    }

    @Test
    void getAuthorities_withNullAuthorities_shouldReturnNull() {
        // Arrange
        currentUser.setAuthorities(null);

        // Act
        Collection<? extends GrantedAuthority> authorities = currentUser.getAuthorities();

        // Assert
        assertNull(authorities, "getAuthorities should return null when authorities is null");
    }

    @Test
    void currentUser_withCompleteSetup_shouldWorkCorrectly() {
        // Arrange
        currentUser.setUser(testUser);
        currentUser.setAuthorities(testAuthorities);

        // Act & Assert
        assertEquals(testUser, currentUser.getUser(), "User should be set");
        assertEquals(testAuthorities, currentUser.getAuthorities(), "Authorities should be set");
        assertEquals("testuser", currentUser.getUsername(), "Username should match");
        assertEquals("password123", currentUser.getPassword(), "Password should match");
        assertTrue(currentUser.isAccountNonExpired(), "Account should not be expired");
        assertTrue(currentUser.isAccountNonLocked(), "Account should not be locked");
        assertTrue(currentUser.isCredentialsNonExpired(), "Credentials should not be expired");
        assertTrue(currentUser.isEnabled(), "Account should be enabled");
    }

    @Test
    void equals_withSameValues_shouldBeEqual() {
        // Arrange
        CurrentUser currentUser1 = new CurrentUser();
        currentUser1.setUser(testUser);
        currentUser1.setAuthorities(testAuthorities);

        CurrentUser currentUser2 = new CurrentUser();
        currentUser2.setUser(testUser);
        currentUser2.setAuthorities(testAuthorities);

        // Act & Assert
        assertEquals(currentUser1, currentUser2, "CurrentUsers with same values should be equal");
        assertEquals(currentUser1.hashCode(), currentUser2.hashCode(), "Hash codes should be equal");
    }

    @Test
    void toString_shouldReturnStringRepresentation() {
        // Arrange
        currentUser.setUser(testUser);
        currentUser.setAuthorities(testAuthorities);

        // Act
        String result = currentUser.toString();

        // Assert
        assertNotNull(result, "toString should not return null");
        assertTrue(result.contains("testuser") || result.contains("User"), "toString should contain user information");
    }

    @Test
    void currentUserClass_shouldHaveLombokDataAnnotation() {
        // Assert
        assertTrue(CurrentUser.class.isAnnotationPresent(lombok.Data.class), "CurrentUser should have @Data annotation");
    }

    @Test
    void currentUser_userDetailsInterface_shouldHaveAllMethods() throws NoSuchMethodException {
        // Assert that all UserDetails methods are implemented
        assertNotNull(CurrentUser.class.getMethod("getAuthorities"), "getAuthorities method should exist");
        assertNotNull(CurrentUser.class.getMethod("getPassword"), "getPassword method should exist");
        assertNotNull(CurrentUser.class.getMethod("getUsername"), "getUsername method should exist");
        assertNotNull(CurrentUser.class.getMethod("isAccountNonExpired"), "isAccountNonExpired method should exist");
        assertNotNull(CurrentUser.class.getMethod("isAccountNonLocked"), "isAccountNonLocked method should exist");
        assertNotNull(CurrentUser.class.getMethod("isCredentialsNonExpired"), "isCredentialsNonExpired method should exist");
        assertNotNull(CurrentUser.class.getMethod("isEnabled"), "isEnabled method should exist");
    }

    @Test
    void currentUser_fieldsAccessibility_shouldBeCorrect() throws NoSuchFieldException {
        // Assert that fields exist and are accessible
        var userField = CurrentUser.class.getDeclaredField("user");
        assertEquals(User.class, userField.getType(), "user field should be of type User");

        var authoritiesField = CurrentUser.class.getDeclaredField("authorities");
        assertEquals(Set.class, authoritiesField.getType(), "authorities field should be of type Set");
    }
}