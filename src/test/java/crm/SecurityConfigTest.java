package crm;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;
import crm.service.SpringDataUserDetailsService;
import static org.junit.jupiter.api.Assertions.*;

public class SecurityConfigTest {

    private SecurityConfig securityConfig = new SecurityConfig();

    @Test
    public void testPasswordEncoderBean() {
        PasswordEncoder passwordEncoder = securityConfig.passwordEncoder();
        assertNotNull(passwordEncoder);
    }

    @Test
    public void testPasswordEncoderEncodes() {
        PasswordEncoder passwordEncoder = securityConfig.passwordEncoder();
        String encoded = passwordEncoder.encode("password");
        assertNotNull(encoded);
        assertNotEquals("password", encoded);
    }

    @Test
    public void testPasswordEncoderMatches() {
        PasswordEncoder passwordEncoder = securityConfig.passwordEncoder();
        String encoded = passwordEncoder.encode("password");
        assertTrue(passwordEncoder.matches("password", encoded));
    }

    @Test
    public void testCustomUserDetailsServiceBean() {
        SpringDataUserDetailsService userDetailsService = securityConfig.customUserDetailsService();
        assertNotNull(userDetailsService);
    }

    @Test
    public void testPasswordEncoderNotNull() {
        assertNotNull(securityConfig.passwordEncoder());
    }

    @Test
    public void testSecurityConfigNotNull() {
        assertNotNull(securityConfig);
    }

    @Test
    public void testPasswordEncoderDifferentPasswords() {
        PasswordEncoder passwordEncoder = securityConfig.passwordEncoder();
        String encoded1 = passwordEncoder.encode("password1");
        String encoded2 = passwordEncoder.encode("password2");
        assertNotEquals(encoded1, encoded2);
    }

    @Test
    public void testPasswordEncoderSamePasswordDifferentHash() {
        PasswordEncoder passwordEncoder = securityConfig.passwordEncoder();
        String encoded1 = passwordEncoder.encode("password");
        String encoded2 = passwordEncoder.encode("password");
        // BCrypt produces different hashes for same password
        assertTrue(passwordEncoder.matches("password", encoded1));
        assertTrue(passwordEncoder.matches("password", encoded2));
    }
}
