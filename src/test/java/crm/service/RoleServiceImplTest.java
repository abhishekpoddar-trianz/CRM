package crm.service;

import crm.entity.Role;
import crm.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class RoleServiceImplTest {

    @Mock
    private RoleRepository roleRepository;

    private RoleServiceImpl roleService;

    @BeforeEach
    public void setUp() {
        roleService = new RoleServiceImpl(roleRepository);
    }

    @Test
    public void testConstructor() {
        assertNotNull(roleService);
        RoleServiceImpl service = new RoleServiceImpl(roleRepository);
        assertNotNull(service);
    }

    @Test
    public void testConstructorWithNullRepository() {
        assertDoesNotThrow(() -> {
            new RoleServiceImpl(null);
        });
    }

    @Test
    public void testListAllRoles() {
        List<Role> mockRoles = createMockRoles();
        when(roleRepository.findAll()).thenReturn(mockRoles);

        Iterable<Role> result = roleService.listAllRoles();

        assertNotNull(result);
        assertEquals(mockRoles, result);
        verify(roleRepository).findAll();
    }

    @Test
    public void testListAllRolesWithEmptyList() {
        List<Role> emptyRoles = new ArrayList<>();
        when(roleRepository.findAll()).thenReturn(emptyRoles);

        Iterable<Role> result = roleService.listAllRoles();

        assertNotNull(result);
        assertEquals(emptyRoles, result);
        verify(roleRepository).findAll();
    }

    @Test
    public void testListAllRolesCallsRepositoryFindAll() {
        List<Role> mockRoles = createMockRoles();
        when(roleRepository.findAll()).thenReturn(mockRoles);

        roleService.listAllRoles();

        verify(roleRepository, times(1)).findAll();
    }

    @Test
    public void testServiceImplementsInterface() {
        assertTrue(roleService instanceof RoleService);
    }

    @Test
    public void testServiceAnnotation() {
        assertTrue(RoleServiceImpl.class.isAnnotationPresent(org.springframework.stereotype.Service.class));
    }

    @Test
    public void testClassStructure() {
        assertNotNull(RoleServiceImpl.class);
        assertEquals("RoleServiceImpl", RoleServiceImpl.class.getSimpleName());
        assertEquals("crm.service", RoleServiceImpl.class.getPackage().getName());
    }

    @Test
    public void testImplementsRoleServiceInterface() {
        assertTrue(RoleService.class.isAssignableFrom(RoleServiceImpl.class));
    }

    @Test
    public void testListAllRolesReturnType() {
        List<Role> mockRoles = createMockRoles();
        when(roleRepository.findAll()).thenReturn(mockRoles);

        Iterable<Role> result = roleService.listAllRoles();

        assertNotNull(result);
        assertTrue(result instanceof Iterable);
    }

    @Test
    public void testListAllRolesWithNullRepository() {
        RoleServiceImpl serviceWithNullRepo = new RoleServiceImpl(null);

        assertThrows(NullPointerException.class, () -> {
            serviceWithNullRepo.listAllRoles();
        });
    }

    @Test
    public void testRepositoryFieldAccess() {
        // Test that the repository field is properly used
        List<Role> mockRoles = createMockRoles();
        when(roleRepository.findAll()).thenReturn(mockRoles);

        roleService.listAllRoles();

        verify(roleRepository).findAll();
        verifyNoMoreInteractions(roleRepository);
    }

    @Test
    public void testServiceBehaviorConsistency() {
        List<Role> mockRoles = createMockRoles();
        when(roleRepository.findAll()).thenReturn(mockRoles);

        Iterable<Role> result1 = roleService.listAllRoles();
        Iterable<Role> result2 = roleService.listAllRoles();

        assertEquals(result1, result2);
        verify(roleRepository, times(2)).findAll();
    }

    @Test
    public void testOverrideAnnotation() throws NoSuchMethodException {
        java.lang.reflect.Method listAllRolesMethod = RoleServiceImpl.class.getMethod("listAllRoles");
        assertTrue(listAllRolesMethod.isAnnotationPresent(Override.class));
    }

    private List<Role> createMockRoles() {
        List<Role> roles = new ArrayList<>();

        Role role1 = new Role();
        role1.setId(1);
        role1.setName("ADMIN");
        roles.add(role1);

        Role role2 = new Role();
        role2.setId(2);
        role2.setName("USER");
        roles.add(role2);

        return roles;
    }
}