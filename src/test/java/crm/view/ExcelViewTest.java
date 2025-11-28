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
import java.util.*;

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
    void testBuildExcelDocumentWithValidUsers() throws Exception {
        Role role = new Role();
        role.setId(1);
        role.setName("ADMIN");

        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setUsername("johndoe");
        user.setEmail("john@example.com");
        user.setPassword("password123");
        user.setEnabled(1);
        user.setRole(role);

        List<User> users = Arrays.asList(user);
        Map<String, Object> model = new HashMap<>();
        model.put("users", users);

        Workbook workbook = new HSSFWorkbook();

        excelView.buildExcelDocument(model, workbook, request, response);

        verify(response).setHeader("Content-Disposition", "attachment; filename=\"my-xls-file.xls\"");
        assertEquals(1, workbook.getNumberOfSheets());
        assertEquals("User Detail", workbook.getSheetAt(0).getSheetName());
    }

    @Test
    void testBuildExcelDocumentWithMultipleUsers() throws Exception {
        Role role1 = new Role();
        role1.setId(1);
        role1.setName("ADMIN");

        Role role2 = new Role();
        role2.setId(2);
        role2.setName("USER");

        User user1 = new User();
        user1.setFirstName("John");
        user1.setLastName("Doe");
        user1.setUsername("johndoe");
        user1.setEmail("john@example.com");
        user1.setPassword("password123");
        user1.setEnabled(1);
        user1.setRole(role1);

        User user2 = new User();
        user2.setFirstName("Jane");
        user2.setLastName("Smith");
        user2.setUsername("janesmith");
        user2.setEmail("jane@example.com");
        user2.setPassword("password456");
        user2.setEnabled(0);
        user2.setRole(role2);

        List<User> users = Arrays.asList(user1, user2);
        Map<String, Object> model = new HashMap<>();
        model.put("users", users);

        Workbook workbook = new HSSFWorkbook();

        excelView.buildExcelDocument(model, workbook, request, response);

        verify(response).setHeader("Content-Disposition", "attachment; filename=\"my-xls-file.xls\"");
        assertEquals(1, workbook.getNumberOfSheets());
        assertEquals(3, workbook.getSheetAt(0).getPhysicalNumberOfRows());
    }

    @Test
    void testBuildExcelDocumentSetsContentDisposition() throws Exception {
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

        List<User> users = Collections.singletonList(user);
        Map<String, Object> model = new HashMap<>();
        model.put("users", users);

        Workbook workbook = new HSSFWorkbook();

        excelView.buildExcelDocument(model, workbook, request, response);

        verify(response, times(1)).setHeader("Content-Disposition", "attachment; filename=\"my-xls-file.xls\"");
    }

    @Test
    void testBuildExcelDocumentCreatesHeaderRow() throws Exception {
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

        List<User> users = Collections.singletonList(user);
        Map<String, Object> model = new HashMap<>();
        model.put("users", users);

        Workbook workbook = new HSSFWorkbook();

        excelView.buildExcelDocument(model, workbook, request, response);

        assertNotNull(workbook.getSheetAt(0).getRow(0));
        assertEquals("FirstName", workbook.getSheetAt(0).getRow(0).getCell(0).getStringCellValue());
    }
}
