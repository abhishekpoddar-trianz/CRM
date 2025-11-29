package crm.repository;

import crm.entity.Role;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DataJpaTest
class RoleRepositoryTest {

    @MockBean
    private RoleRepository roleRepository;

    @Test
    void testFindByName() {
        Role mockRole = new Role();
        mockRole.setId(1);
        mockRole.setName("ADMIN");

        when(roleRepository.findByName("ADMIN")).thenReturn(mockRole);

        Role result = roleRepository.findByName("ADMIN");

        assertNotNull(result);
        assertEquals("ADMIN", result.getName());
        assertEquals(1, result.getId());
        verify(roleRepository, times(1)).findByName("ADMIN");
    }

    @Test
    void testFindByNameNotFound() {
        when(roleRepository.findByName("NONEXISTENT")).thenReturn(null);

        Role result = roleRepository.findByName("NONEXISTENT");

        assertNull(result);
        verify(roleRepository, times(1)).findByName("NONEXISTENT");
    }

    @Test
    void testFindByNameWithNullParameter() {
        when(roleRepository.findByName(null)).thenReturn(null);

        Role result = roleRepository.findByName(null);

        assertNull(result);
        verify(roleRepository, times(1)).findByName(null);
    }

    @Test
    void testFindByNameWithEmptyString() {
        when(roleRepository.findByName("")).thenReturn(null);

        Role result = roleRepository.findByName("");

        assertNull(result);
        verify(roleRepository, times(1)).findByName("");
    }

    @Test
    void testFindByNameCaseSensitive() {
        Role adminRole = new Role();
        adminRole.setId(1);
        adminRole.setName("ADMIN");

        when(roleRepository.findByName("ADMIN")).thenReturn(adminRole);
        when(roleRepository.findByName("admin")).thenReturn(null);

        Role upperCaseResult = roleRepository.findByName("ADMIN");
        Role lowerCaseResult = roleRepository.findByName("admin");

        assertNotNull(upperCaseResult);
        assertEquals("ADMIN", upperCaseResult.getName());
        assertNull(lowerCaseResult);

        verify(roleRepository, times(1)).findByName("ADMIN");
        verify(roleRepository, times(1)).findByName("admin");
    }

    @Test
    void testFindByNameMultipleRoles() {
        Role adminRole = new Role();
        adminRole.setId(1);
        adminRole.setName("ADMIN");

        Role userRole = new Role();
        userRole.setId(2);
        userRole.setName("USER");

        when(roleRepository.findByName("ADMIN")).thenReturn(adminRole);
        when(roleRepository.findByName("USER")).thenReturn(userRole);

        Role adminResult = roleRepository.findByName("ADMIN");
        Role userResult = roleRepository.findByName("USER");

        assertNotNull(adminResult);
        assertNotNull(userResult);
        assertEquals("ADMIN", adminResult.getName());
        assertEquals("USER", userResult.getName());
        assertNotEquals(adminResult.getId(), userResult.getId());

        verify(roleRepository, times(1)).findByName("ADMIN");
        verify(roleRepository, times(1)).findByName("USER");
    }

    @Test
    void testRepositoryInterface() {
        assertTrue(RoleRepository.class.isInterface());
    }

    @Test
    void testExtendsJpaRepository() {
        assertTrue(org.springframework.data.jpa.repository.JpaRepository.class.isAssignableFrom(RoleRepository.class));
    }

    @Test
    void testRepositoryAnnotation() {
        assertTrue(RoleRepository.class.isAnnotationPresent(org.springframework.stereotype.Repository.class));
    }
}