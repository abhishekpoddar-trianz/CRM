package crm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

public class SecurityConfigTest {

    private SecurityConfig securityConfig;

    @BeforeEach
    public void setUp() {
        securityConfig = new SecurityConfig();
    }

    @Test
    public void testSecurityConfigConstructor() {
        assertNotNull(securityConfig);
    }

    @Test
    public void testPasswordEncoder() {
        BCryptPasswordEncoder encoder = securityConfig.passwordEncoder();
        assertNotNull(encoder);
    }

    @Test
    public void testPasswordEncoderEncryption() {
        BCryptPasswordEncoder encoder = securityConfig.passwordEncoder();
        String password = "testPassword123";
        String encoded = encoder.encode(password);
        assertNotNull(encoded);
        assertNotEquals(password, encoded);
        assertTrue(encoder.matches(password, encoded));
    }

    @Test
    public void testPasswordEncoderDifferentResults() {
        BCryptPasswordEncoder encoder = securityConfig.passwordEncoder();
        String password = "test";
        String encoded1 = encoder.encode(password);
        String encoded2 = encoder.encode(password);
        assertNotEquals(encoded1, encoded2);
    }

    @Test
    public void testCustomUserDetailsService() {
        assertNotNull(securityConfig.customUserDetailsService());
    }
}
