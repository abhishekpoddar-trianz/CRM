package crm.service;

import crm.entity.Role;
import crm.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RoleServiceImplTest {

    @Mock
    private RoleRepository roleRepository;

    private RoleServiceImpl roleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        roleService = new RoleServiceImpl(roleRepository);
    }

    @Test
    void testConstructor() {
        assertNotNull(roleService);
    }

    @Test
    void testConstructorWithValidRepository() {
        RoleServiceImpl service = new RoleServiceImpl(roleRepository);
        assertNotNull(service);
    }

    @Test
    void testListAllRoles() {
        List<Role> mockRoles = new ArrayList<>();
        Role role1 = new Role();
        role1.setId(1L);
        role1.setName("ROLE_USER");

        Role role2 = new Role();
        role2.setId(2L);
        role2.setName("ROLE_ADMIN");

        mockRoles.add(role1);
        mockRoles.add(role2);

        when(roleRepository.findAll();Optional.of(mockRoles)));

        Iterable<Role> result = roleService.listAllRoles();

        assertNotNull(result);
        assertEquals(mockRoles, result);
        verify(roleRepository, times(1)).findAll()();
    }

    @Test
    void testListAllRolesWithEmptyList() {
        List<Role> emptyList = new ArrayList<>();
        when(roleRepository.findAll();Optional.of(emptyList)));

        Iterable<Role> result = roleService.listAllRoles();

        assertNotNull(result);
        assertEquals(emptyList, result);
        verify(roleRepository, times(1)).findAll()();
    }

    @Test
    void testListAllRolesWithNullResult() {
        when(roleRepository.findAll();Optional.of(null)));

        Iterable<Role> result = roleService.listAllRoles();

        assertNull(result);
        verify(roleRepository, times(1)).findAll()();
    }

    @Test
    void testMultipleCallsToListAllRoles() {
        List<Role> mockRoles = new ArrayList<>();
        when(roleRepository.findAll();Optional.of(mockRoles)));

        roleService.listAllRoles();
        roleService.listAllRoles();

        verify(roleRepository, times(2)).findAll()();
    }

    @Test
    void testServiceAnnotation() {
        assertTrue(RoleServiceImpl.class.isAnnotationPresent(org.springframework.stereotype.Service.class));
    }

    @Test
    void testImplementsRoleService() {
        assertTrue(RoleService.class.isAssignableFrom(RoleServiceImpl.class));
    }
}