package crm;

import crm.service.SpringDataUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

class SecurityConfigTest {

    private SecurityConfig securityConfig;

    @BeforeEach
    void setUp() {
        securityConfig = new SecurityConfig();
    }

    @Test
    void testConstructor() {
        SecurityConfig config = new SecurityConfig();
        assertNotNull(config);
    }

    @Test
    void testPasswordEncoderBean() {
        BCryptPasswordEncoder encoder = securityConfig.passwordEncoder();

        assertNotNull(encoder);
        assertTrue(encoder instanceof BCryptPasswordEncoder);
    }

    @Test
    void testCustomUserDetailsServiceBean() {
        SpringDataUserDetailsService service = securityConfig.customUserDetailsService();

        assertNotNull(service);
        assertTrue(service instanceof SpringDataUserDetailsService);
    }

    @Test
    void testPasswordEncoderReturnsNewInstance() {
        BCryptPasswordEncoder encoder1 = securityConfig.passwordEncoder();
        BCryptPasswordEncoder encoder2 = securityConfig.passwordEncoder();

        assertNotNull(encoder1);
        assertNotNull(encoder2);
    }

    @Test
    void testPasswordEncoderEncodes() {
        BCryptPasswordEncoder encoder = securityConfig.passwordEncoder();
        String password = "testPassword";

        String encoded = encoder.encode(password);

        assertNotNull(encoded);
        assertNotEquals(password, encoded);
        assertTrue(encoder.matches(password, encoded));
    }

    @Test
    void testPasswordEncoderMatchesCorrectPassword() {
        BCryptPasswordEncoder encoder = securityConfig.passwordEncoder();
        String password = "myPassword123";
        String encoded = encoder.encode(password);

        assertTrue(encoder.matches(password, encoded));
    }

    @Test
    void testPasswordEncoderDoesNotMatchIncorrectPassword() {
        BCryptPasswordEncoder encoder = securityConfig.passwordEncoder();
        String password = "correctPassword";
        String wrongPassword = "wrongPassword";
        String encoded = encoder.encode(password);

        assertFalse(encoder.matches(wrongPassword, encoded));
    }
}
