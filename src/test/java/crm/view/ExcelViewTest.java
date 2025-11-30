package crm.view;

import crm.entity.Role;
import crm.entity.User;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
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
    void testBuildExcelDocumentWithValidUsers() throws Exception {
        Role role = new Role();
        role.setId(1);
        role.setName("ADMIN");

        User user1 = new User();
        user1.setFirstName("John");
        user1.setLastName("Doe");
        user1.setUsername("johndoe");
        user1.setEmail("john@example.com");
        user1.setPassword("password123");
        user1.setEnabled(1);
        user1.setRole(role);

        User user2 = new User();
        user2.setFirstName("Jane");
        user2.setLastName("Smith");
        user2.setUsername("janesmith");
        user2.setEmail("jane@example.com");
        user2.setPassword("password456");
        user2.setEnabled(1);
        user2.setRole(role);

        List<User> users = Arrays.asList(user1, user2);
        Map<String, Object> model = new HashMap<>();
        model.put("users", users);

        Workbook workbook = new HSSFWorkbook();

        excelView.buildExcelDocument(model, workbook, request, response);

        verify(response).setHeader("Content-Disposition", "attachment; filename=\"my-xls-file.xls\"");
        Sheet sheet = workbook.getSheet("User Detail");
        assertNotNull(sheet);
        assertEquals(3, sheet.getPhysicalNumberOfRows());
    }

    @Test
    void testBuildExcelDocumentSetsContentDisposition() throws Exception {
        Role role = new Role();
        role.setId(1);
        role.setName("USER");

        User user = new User();
        user.setFirstName("Test");
        user.setLastName("User");
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setPassword("testpass");
        user.setEnabled(1);
        user.setRole(role);

        List<User> users = Arrays.asList(user);
        Map<String, Object> model = new HashMap<>();
        model.put("users", users);

        Workbook workbook = new HSSFWorkbook();

        excelView.buildExcelDocument(model, workbook, request, response);

        verify(response, times(1)).setHeader("Content-Disposition", "attachment; filename=\"my-xls-file.xls\"");
    }

    @Test
    void testBuildExcelDocumentCreatesSheet() throws Exception {
        Role role = new Role();
        role.setId(1);
        role.setName("ADMIN");

        User user = new User();
        user.setFirstName("Test");
        user.setLastName("User");
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setPassword("testpass");
        user.setEnabled(1);
        user.setRole(role);

        List<User> users = Arrays.asList(user);
        Map<String, Object> model = new HashMap<>();
        model.put("users", users);

        Workbook workbook = new HSSFWorkbook();

        excelView.buildExcelDocument(model, workbook, request, response);

        Sheet sheet = workbook.getSheet("User Detail");
        assertNotNull(sheet);
        assertEquals("User Detail", sheet.getSheetName());
        assertEquals(30, sheet.getDefaultColumnWidth());
    }

    @Test
    void testBuildExcelDocumentWithEmptyUsersList() throws Exception {
        Map<String, Object> model = new HashMap<>();
        model.put("users", Arrays.asList());

        Workbook workbook = new HSSFWorkbook();

        excelView.buildExcelDocument(model, workbook, request, response);

        Sheet sheet = workbook.getSheet("User Detail");
        assertNotNull(sheet);
        assertEquals(1, sheet.getPhysicalNumberOfRows());
    }

    @Test
    void testConstructor() {
        ExcelView view = new ExcelView();
        assertNotNull(view);
    }
}
