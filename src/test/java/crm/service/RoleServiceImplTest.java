package crm.service;

import crm.entity.Role;
import crm.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.ArrayList;
import java.util.List;

public class RoleServiceImplTest {

    @Mock
    private RoleRepository roleRepository;

    private RoleServiceImpl roleService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        roleService = new RoleServiceImpl(roleRepository);
    }

    @Test
    public void testListAllRoles() {
        List<Role> roles = new ArrayList<>();
        Role role1 = new Role();
        role1.setId(1);
        role1.setName("ROLE_USER");
        Role role2 = new Role();
        role2.setId(2);
        role2.setName("ROLE_ADMIN");
        roles.add(role1);
        roles.add(role2);

        when(roleRepository.findAll()).thenReturn(roles);

        Iterable<Role> result = roleService.listAllRoles();

        assertNotNull(result);
        assertEquals(2, ((List<Role>) result).size());
        verify(roleRepository, times(1)).findAll();
    }

    @Test
    public void testListAllRolesEmpty() {
        List<Role> emptyList = new ArrayList<>();
        when(roleRepository.findAll()).thenReturn(emptyList);

        Iterable<Role> result = roleService.listAllRoles();

        assertNotNull(result);
        assertEquals(0, ((List<Role>) result).size());
        verify(roleRepository, times(1)).findAll();
    }

    @Test
    public void testListAllRolesNotNull() {
        when(roleRepository.findAll()).thenReturn(new ArrayList<>());

        Iterable<Role> result = roleService.listAllRoles();

        assertNotNull(result);
        verify(roleRepository, times(1)).findAll();
    }
}
