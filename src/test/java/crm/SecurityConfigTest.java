package crm;

import crm.service.SpringDataUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SecurityConfigTest {

    private SecurityConfig securityConfig;

    @Mock
    private AuthenticationConfiguration mockAuthConfig;

    @Mock
    private HttpSecurity mockHttpSecurity;

    @Mock
    private AuthenticationManager mockAuthenticationManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        securityConfig = new SecurityConfig();
    }

    @Test
    void testPasswordEncoder() {
        // Act
        BCryptPasswordEncoder passwordEncoder = securityConfig.passwordEncoder();

        // Assert
        assertNotNull(passwordEncoder);
        assertTrue(passwordEncoder instanceof BCryptPasswordEncoder);
    }

    @Test
    void testPasswordEncoderCreatesNewInstance() {
        // Act
        BCryptPasswordEncoder encoder1 = securityConfig.passwordEncoder();
        BCryptPasswordEncoder encoder2 = securityConfig.passwordEncoder();

        // Assert
        assertNotNull(encoder1);
        assertNotNull(encoder2);
        assertNotSame(encoder1, encoder2);
    }

    @Test
    void testPasswordEncoderFunctionality() {
        // Arrange
        BCryptPasswordEncoder encoder = securityConfig.passwordEncoder();
        String plainPassword = "testPassword123";

        // Act
        String encodedPassword = encoder.encode(plainPassword);

        // Assert
        assertNotNull(encodedPassword);
        assertNotEquals(plainPassword, encodedPassword);
        assertTrue(encoder.matches(plainPassword, encodedPassword));
    }

    @Test
    void testCustomUserDetailsService() {
        // Act
        SpringDataUserDetailsService userDetailsService = securityConfig.customUserDetailsService();

        // Assert
        assertNotNull(userDetailsService);
        assertTrue(userDetailsService instanceof SpringDataUserDetailsService);
    }

    @Test
    void testCustomUserDetailsServiceCreatesNewInstance() {
        // Act
        SpringDataUserDetailsService service1 = securityConfig.customUserDetailsService();
        SpringDataUserDetailsService service2 = securityConfig.customUserDetailsService();

        // Assert
        assertNotNull(service1);
        assertNotNull(service2);
        assertNotSame(service1, service2);
    }

    @Test
    void testAuthenticationManager() throws Exception {
        // Arrange
        when(mockAuthConfig.getAuthenticationManager()).thenReturn(mockAuthenticationManager);

        // Act
        AuthenticationManager result = securityConfig.authenticationManager(mockAuthConfig);

        // Assert
        assertNotNull(result);
        assertEquals(mockAuthenticationManager, result);
        verify(mockAuthConfig).getAuthenticationManager();
    }

    @Test
    void testAuthenticationManagerWithException() throws Exception {
        // Arrange
        when(mockAuthConfig.getAuthenticationManager()).thenThrow(new RuntimeException("Test exception"));

        // Act & Assert
        assertThrows(RuntimeException.class, () ->
            securityConfig.authenticationManager(mockAuthConfig));
    }

    @Test
    void testFilterChainNotNull() throws Exception {
        // Arrange
        HttpSecurity httpSecurity = mock(HttpSecurity.class, RETURNS_DEEP_STUBS);
        SecurityFilterChain mockFilterChain = mock(SecurityFilterChain.class);
        when(httpSecurity.build()).thenReturn(mockFilterChain);

        // Act
        SecurityFilterChain result = securityConfig.filterChain(httpSecurity);

        // Assert
        assertNotNull(result);
    }

    @Test
    void testSecurityConfigAnnotations() {
        // Act & Assert - Test that the class has correct annotations
        assertTrue(SecurityConfig.class.isAnnotationPresent(org.springframework.context.annotation.Configuration.class));
        assertTrue(SecurityConfig.class.isAnnotationPresent(org.springframework.security.config.annotation.web.configuration.EnableWebSecurity.class));
        assertTrue(SecurityConfig.class.isAnnotationPresent(org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity.class));
    }

    @Test
    void testEnableMethodSecurityAnnotation() {
        // Act
        org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity annotation =
            SecurityConfig.class.getAnnotation(org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity.class);

        // Assert
        assertNotNull(annotation);
        assertTrue(annotation.securedEnabled());
    }

    @Test
    void testBeanMethods() throws NoSuchMethodException {
        // Act & Assert - Verify bean methods exist and are annotated
        assertTrue(SecurityConfig.class.getMethod("passwordEncoder").isAnnotationPresent(org.springframework.context.annotation.Bean.class));
        assertTrue(SecurityConfig.class.getMethod("customUserDetailsService").isAnnotationPresent(org.springframework.context.annotation.Bean.class));
        assertTrue(SecurityConfig.class.getMethod("authenticationManager", AuthenticationConfiguration.class).isAnnotationPresent(org.springframework.context.annotation.Bean.class));
        assertTrue(SecurityConfig.class.getMethod("filterChain", HttpSecurity.class).isAnnotationPresent(org.springframework.context.annotation.Bean.class));
    }

    @Test
    void testPasswordEncoderBeanAnnotation() throws NoSuchMethodException {
        // Act
        org.springframework.context.annotation.Bean beanAnnotation =
            SecurityConfig.class.getMethod("passwordEncoder").getAnnotation(org.springframework.context.annotation.Bean.class);

        // Assert
        assertNotNull(beanAnnotation);
    }

    @Test
    void testCustomUserDetailsServiceBeanAnnotation() throws NoSuchMethodException {
        // Act
        org.springframework.context.annotation.Bean beanAnnotation =
            SecurityConfig.class.getMethod("customUserDetailsService").getAnnotation(org.springframework.context.annotation.Bean.class);

        // Assert
        assertNotNull(beanAnnotation);
    }

    @Test
    void testAuthenticationManagerBeanAnnotation() throws NoSuchMethodException {
        // Act
        org.springframework.context.annotation.Bean beanAnnotation =
            SecurityConfig.class.getMethod("authenticationManager", AuthenticationConfiguration.class).getAnnotation(org.springframework.context.annotation.Bean.class);

        // Assert
        assertNotNull(beanAnnotation);
    }

    @Test
    void testFilterChainBeanAnnotation() throws NoSuchMethodException {
        // Act
        org.springframework.context.annotation.Bean beanAnnotation =
            SecurityConfig.class.getMethod("filterChain", HttpSecurity.class).getAnnotation(org.springframework.context.annotation.Bean.class);

        // Assert
        assertNotNull(beanAnnotation);
    }

    @Test
    void testClassIsPublic() {
        // Act & Assert
        assertTrue(java.lang.reflect.Modifier.isPublic(SecurityConfig.class.getModifiers()));
    }

    @Test
    void testMethodAccessibility() throws NoSuchMethodException {
        // Act & Assert - All bean methods should be public
        assertTrue(java.lang.reflect.Modifier.isPublic(SecurityConfig.class.getMethod("passwordEncoder").getModifiers()));
        assertTrue(java.lang.reflect.Modifier.isPublic(SecurityConfig.class.getMethod("customUserDetailsService").getModifiers()));
        assertTrue(java.lang.reflect.Modifier.isPublic(SecurityConfig.class.getMethod("authenticationManager", AuthenticationConfiguration.class).getModifiers()));
        assertTrue(java.lang.reflect.Modifier.isPublic(SecurityConfig.class.getMethod("filterChain", HttpSecurity.class).getModifiers()));
    }

    @Test
    void testPasswordEncoderStrength() {
        // Arrange
        BCryptPasswordEncoder encoder = securityConfig.passwordEncoder();
        String weakPassword = "123";
        String strongPassword = "StrongP@ssw0rd123!";

        // Act
        String encodedWeak = encoder.encode(weakPassword);
        String encodedStrong = encoder.encode(strongPassword);

        // Assert
        assertNotNull(encodedWeak);
        assertNotNull(encodedStrong);
        assertTrue(encoder.matches(weakPassword, encodedWeak));
        assertTrue(encoder.matches(strongPassword, encodedStrong));
        assertNotEquals(encodedWeak, encodedStrong);
    }

    @Test
    void testPasswordEncoderConsistency() {
        // Arrange
        BCryptPasswordEncoder encoder = securityConfig.passwordEncoder();
        String password = "testPassword";

        // Act
        String encoded1 = encoder.encode(password);
        String encoded2 = encoder.encode(password);

        // Assert - BCrypt should generate different hashes for same password
        assertNotNull(encoded1);
        assertNotNull(encoded2);
        assertNotEquals(encoded1, encoded2);
        assertTrue(encoder.matches(password, encoded1));
        assertTrue(encoder.matches(password, encoded2));
    }

    @Test
    void testDefaultConstructor() {
        // Act
        SecurityConfig config = new SecurityConfig();

        // Assert
        assertNotNull(config);
    }

    @Test
    void testBeanMethodReturnTypes() throws NoSuchMethodException {
        // Act & Assert - Verify return types
        assertEquals(BCryptPasswordEncoder.class, SecurityConfig.class.getMethod("passwordEncoder").getReturnType());
        assertEquals(SpringDataUserDetailsService.class, SecurityConfig.class.getMethod("customUserDetailsService").getReturnType());
        assertEquals(AuthenticationManager.class, SecurityConfig.class.getMethod("authenticationManager", AuthenticationConfiguration.class).getReturnType());
        assertEquals(SecurityFilterChain.class, SecurityConfig.class.getMethod("filterChain", HttpSecurity.class).getReturnType());
    }

    @Test
    void testAuthenticationManagerParameterType() throws NoSuchMethodException {
        // Act
        Class<?>[] parameterTypes = SecurityConfig.class.getMethod("authenticationManager", AuthenticationConfiguration.class).getParameterTypes();

        // Assert
        assertEquals(1, parameterTypes.length);
        assertEquals(AuthenticationConfiguration.class, parameterTypes[0]);
    }

    @Test
    void testFilterChainParameterType() throws NoSuchMethodException {
        // Act
        Class<?>[] parameterTypes = SecurityConfig.class.getMethod("filterChain", HttpSecurity.class).getParameterTypes();

        // Assert
        assertEquals(1, parameterTypes.length);
        assertEquals(HttpSecurity.class, parameterTypes[0]);
    }

    @Test
    void testPackageName() {
        // Act & Assert
        assertEquals("crm", SecurityConfig.class.getPackage().getName());
    }
}