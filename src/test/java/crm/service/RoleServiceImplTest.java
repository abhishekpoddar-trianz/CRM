package crm.service;

import crm.entity.Role;
import crm.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RoleServiceImplTest {

    private RoleServiceImpl roleService;

    @Mock
    private RoleRepository roleRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        roleService = new RoleServiceImpl(roleRepository);
    }

    @Test
    void testConstructor() {
        RoleServiceImpl service = new RoleServiceImpl(roleRepository);
        assertNotNull(service);
    }

    @Test
    void testListAllRoles() {
        Role role1 = new Role();
        role1.setId(1);
        role1.setName("ADMIN");

        Role role2 = new Role();
        role2.setId(2);
        role2.setName("USER");

        when(roleRepository.findAll()).thenReturn(Arrays.asList(role1, role2));

        Iterable<Role> result = roleService.listAllRoles();

        assertNotNull(result);
        verify(roleRepository, times(1)).findAll();
    }

    @Test
    void testListAllRolesWithEmptyList() {
        when(roleRepository.findAll()).thenReturn(Arrays.asList());

        Iterable<Role> result = roleService.listAllRoles();

        assertNotNull(result);
        verify(roleRepository, times(1)).findAll();
    }
}
