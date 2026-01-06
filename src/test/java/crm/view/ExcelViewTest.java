package crm.view;

import crm.entity.Role;
import crm.entity.User;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ExcelViewTest {

    private ExcelView excelView;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        excelView = new ExcelView();
    }

    @Test
    void testConstructor() {
        assertNotNull(excelView);
    }

    @Test
    void testBuildExcelDocumentWithUsers() throws Exception {
        Role role = new Role();
        role.setId(1);
        role.setName("ROLE_USER");

        User user1 = User.builder()
                .id(1L)
                .username("user1")
                .email("user1@test.com")
                .firstName("John")
                .lastName("Doe")
                .password("password")
                .enabled(1)
                .role(role)
                .build();

        User user2 = User.builder()
                .id(2L)
                .username("user2")
                .email("user2@test.com")
                .firstName("Jane")
                .lastName("Smith")
                .password("password123")
                .enabled(1)
                .role(role)
                .build();

        List<User> users = Arrays.asList(user1, user2);
        Map<String, Object> model = new HashMap<>();
        model.put("users", users);

        Workbook workbook = new HSSFWorkbook();

        excelView.buildExcelDocument(model, workbook, request, response);

        verify(response).setHeader("Content-Disposition", "attachment; filename=\"my-xls-file.xls\"");
        assertNotNull(workbook.getSheet("User Detail"));
        assertEquals(1, workbook.getNumberOfSheets());
    }

    @Test
    void testBuildExcelDocumentCreatesSheet() throws Exception {
        Role role = new Role();
        role.setId(2);
        role.setName("ROLE_ADMIN");

        User user = User.builder()
                .id(1L)
                .username("admin")
                .email("admin@test.com")
                .firstName("Admin")
                .lastName("User")
                .password("adminpass")
                .enabled(1)
                .role(role)
                .build();

        List<User> users = Arrays.asList(user);
        Map<String, Object> model = new HashMap<>();
        model.put("users", users);

        Workbook workbook = new HSSFWorkbook();

        assertDoesNotThrow(() -> excelView.buildExcelDocument(model, workbook, request, response));
        assertNotNull(workbook.getSheet("User Detail"));
    }
}
