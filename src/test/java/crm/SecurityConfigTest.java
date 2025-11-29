package crm;

import crm.service.SpringDataUserDetailsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SecurityConfigTest {

    @Mock
    private AuthenticationConfiguration authenticationConfiguration;

    @Mock
    private HttpSecurity httpSecurity;

    @Mock
    private AuthenticationManager authenticationManager;

    private SecurityConfig securityConfig;

    @BeforeEach
    public void setUp() {
        securityConfig = new SecurityConfig();
    }

    @Test
    public void testPasswordEncoderBean() {
        BCryptPasswordEncoder encoder = securityConfig.passwordEncoder();
        assertNotNull(encoder);
        assertTrue(encoder instanceof BCryptPasswordEncoder);
    }

    @Test
    public void testCustomUserDetailsServiceBean() {
        SpringDataUserDetailsService service = securityConfig.customUserDetailsService();
        assertNotNull(service);
        assertTrue(service instanceof SpringDataUserDetailsService);
    }

    @Test
    public void testAuthenticationManagerBean() throws Exception {
        when(authenticationConfiguration.getAuthenticationManager()).thenReturn(authenticationManager);

        AuthenticationManager result = securityConfig.authenticationManager(authenticationConfiguration);

        assertEquals(authenticationManager, result);
        verify(authenticationConfiguration).getAuthenticationManager();
    }

    @Test
    public void testPasswordEncoderIsDifferentInstances() {
        BCryptPasswordEncoder encoder1 = securityConfig.passwordEncoder();
        BCryptPasswordEncoder encoder2 = securityConfig.passwordEncoder();

        assertNotNull(encoder1);
        assertNotNull(encoder2);
        assertNotSame(encoder1, encoder2); // Different instances each time
    }

    @Test
    public void testCustomUserDetailsServiceIsDifferentInstances() {
        SpringDataUserDetailsService service1 = securityConfig.customUserDetailsService();
        SpringDataUserDetailsService service2 = securityConfig.customUserDetailsService();

        assertNotNull(service1);
        assertNotNull(service2);
        assertNotSame(service1, service2); // Different instances each time
    }

    @Test
    public void testPasswordEncoderFunctionality() {
        BCryptPasswordEncoder encoder = securityConfig.passwordEncoder();
        String plainPassword = "testPassword123";

        String encodedPassword = encoder.encode(plainPassword);

        assertNotNull(encodedPassword);
        assertNotEquals(plainPassword, encodedPassword);
        assertTrue(encoder.matches(plainPassword, encodedPassword));
    }

    @Test
    public void testPasswordEncoderProducesStrongHashes() {
        BCryptPasswordEncoder encoder = securityConfig.passwordEncoder();
        String plainPassword = "password123";

        String hash1 = encoder.encode(plainPassword);
        String hash2 = encoder.encode(plainPassword);

        assertNotNull(hash1);
        assertNotNull(hash2);
        assertNotEquals(hash1, hash2); // BCrypt should produce different hashes each time
        assertTrue(encoder.matches(plainPassword, hash1));
        assertTrue(encoder.matches(plainPassword, hash2));
    }

    @Test
    public void testConfigurationAnnotations() {
        assertTrue(SecurityConfig.class.isAnnotationPresent(org.springframework.context.annotation.Configuration.class));
        assertTrue(SecurityConfig.class.isAnnotationPresent(org.springframework.security.config.annotation.web.configuration.EnableWebSecurity.class));
        assertTrue(SecurityConfig.class.isAnnotationPresent(org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity.class));
    }

    @Test
    public void testBeanMethodsReturnNonNullValues() {
        assertNotNull(securityConfig.passwordEncoder());
        assertNotNull(securityConfig.customUserDetailsService());
    }

    @Test
    public void testAuthenticationManagerWithNullConfiguration() {
        assertThrows(Exception.class, () -> {
            securityConfig.authenticationManager(null);
        });
    }

    @Test
    public void testClassStructure() {
        assertNotNull(SecurityConfig.class);
        assertEquals("SecurityConfig", SecurityConfig.class.getSimpleName());
        assertEquals("crm", SecurityConfig.class.getPackage().getName());
    }
}