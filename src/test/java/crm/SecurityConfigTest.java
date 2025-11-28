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
    void testPasswordEncoder() {
        BCryptPasswordEncoder passwordEncoder = securityConfig.passwordEncoder();
        assertNotNull(passwordEncoder);
    }

    @Test
    void testPasswordEncoderEncodesPassword() {
        BCryptPasswordEncoder passwordEncoder = securityConfig.passwordEncoder();
        String encoded = passwordEncoder.encode("password");
        assertNotNull(encoded);
        assertTrue(passwordEncoder.matches("password", encoded));
    }

    @Test
    void testCustomUserDetailsService() {
        SpringDataUserDetailsService userDetailsService = securityConfig.customUserDetailsService();
        assertNotNull(userDetailsService);
    }
}
