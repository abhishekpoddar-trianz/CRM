package crm.service;

import crm.entity.Role;
import crm.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RoleServiceImplTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleServiceImpl roleService;

    private Role role1;
    private Role role2;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        role1 = new Role();
        role1.setId(1);
        role1.setName("ROLE_USER");

        role2 = new Role();
        role2.setId(2);
        role2.setName("ROLE_ADMIN");
    }

    @Test
    public void testListAllRoles() {
        List<Role> roles = Arrays.asList(role1, role2);
        when(roleRepository.findAll()).thenReturn(roles);

        Iterable<Role> result = roleService.listAllRoles();

        assertNotNull(result);
        assertEquals(2, ((List<Role>) result).size());
        verify(roleRepository, times(1)).findAll();
    }

    @Test
    public void testListAllRolesEmpty() {
        when(roleRepository.findAll()).thenReturn(Arrays.asList());

        Iterable<Role> result = roleService.listAllRoles();

        assertNotNull(result);
        assertEquals(0, ((List<Role>) result).size());
        verify(roleRepository, times(1)).findAll();
    }
}
