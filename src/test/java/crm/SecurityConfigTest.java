package crm;

import crm.service.SpringDataUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.junit.jupiter.api.Assertions.*;

class SecurityConfigTest {

    private SecurityConfig securityConfig;

    @BeforeEach
    void setUp() {
        securityConfig = new SecurityConfig();
    }

    @Test
    void testPasswordEncoder() {
        BCryptPasswordEncoder encoder = securityConfig.passwordEncoder();

        assertNotNull(encoder);
        assertTrue(encoder instanceof BCryptPasswordEncoder);
    }

    @Test
    void testPasswordEncoderEncodes() {
        BCryptPasswordEncoder encoder = securityConfig.passwordEncoder();
        String rawPassword = "password123";
        String encoded = encoder.encode(rawPassword);

        assertNotNull(encoded);
        assertNotEquals(rawPassword, encoded);
        assertTrue(encoder.matches(rawPassword, encoded));
    }

    @Test
    void testCustomUserDetailsService() {
        SpringDataUserDetailsService service = securityConfig.customUserDetailsService();

        assertNotNull(service);
        assertTrue(service instanceof SpringDataUserDetailsService);
    }

    @Test
    void testPasswordEncoderGeneratesDifferentHashes() {
        BCryptPasswordEncoder encoder = securityConfig.passwordEncoder();
        String password = "testpass";
        String encoded1 = encoder.encode(password);
        String encoded2 = encoder.encode(password);

        assertNotEquals(encoded1, encoded2);
        assertTrue(encoder.matches(password, encoded1));
        assertTrue(encoder.matches(password, encoded2));
    }

    @Test
    void testConstructor() {
        assertNotNull(securityConfig);
    }
}
