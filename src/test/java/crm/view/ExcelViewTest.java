package crm.view;

import crm.entity.Role;
import crm.entity.User;
import org.apache.poi.ss.usermodel.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ExcelViewTest {

    private ExcelView excelView;

    @Mock
    private HttpServletRequest mockRequest;

    @Mock
    private HttpServletResponse mockResponse;

    @Mock
    private Workbook mockWorkbook;

    @Mock
    private Sheet mockSheet;

    @Mock
    private Row mockHeaderRow;

    @Mock
    private Row mockUserRow;

    @Mock
    private Cell mockCell;

    @Mock
    private CellStyle mockCellStyle;

    @Mock
    private Font mockFont;

    private Map<String, Object> model;
    private List<User> users;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        excelView = new ExcelView();
        model = new HashMap<>();
        users = new ArrayList<>();

        // Setup mock behavior
        when(mockWorkbook.createSheet("User Detail")).thenReturn(mockSheet);
        when(mockWorkbook.createCellStyle()).thenReturn(mockCellStyle);
        when(mockWorkbook.createFont()).thenReturn(mockFont);
        when(mockSheet.createRow(anyInt())).thenReturn(mockHeaderRow, mockUserRow);
        when(mockHeaderRow.createCell(anyInt())).thenReturn(mockCell);
        when(mockUserRow.createCell(anyInt())).thenReturn(mockCell);
        when(mockHeaderRow.getCell(anyInt())).thenReturn(mockCell);
    }

    @Test
    void testBuildExcelDocument() throws Exception {
        // Arrange
        Role role = new Role();
        role.setId(1L);
        role.setName("USER");

        User user1 = User.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .username("johndoe")
                .email("john@example.com")
                .password("password123")
                .enabled(true)
                .role(role)
                .build();

        User user2 = User.builder()
                .id(2L)
                .firstName("Jane")
                .lastName("Smith")
                .username("janesmith")
                .email("jane@example.com")
                .password("password456")
                .enabled(false)
                .role(role)
                .build();

        users.add(user1);
        users.add(user2);
        model.put("users", users);

        // Act
        excelView.buildExcelDocument(model, mockWorkbook, mockRequest, mockResponse);

        // Assert
        verify(mockResponse).setHeader("Content-Disposition", "attachment; filename=\"my-xls-file.xls\"");
        verify(mockWorkbook).createSheet("User Detail");
        verify(mockSheet).setDefaultColumnWidth(30);
        verify(mockWorkbook).createCellStyle();
        verify(mockWorkbook).createFont();

        // Verify header row creation
        verify(mockSheet).createRow(0);

        // Verify user rows creation (2 users = 2 rows starting from row 1)
        verify(mockSheet).createRow(1);
        verify(mockSheet).createRow(2);

        // Verify header cells creation (8 columns)
        verify(mockHeaderRow, times(8)).createCell(anyInt());
        verify(mockHeaderRow, times(8)).getCell(anyInt());

        // Verify user data cells creation (8 columns per user * 2 users = 16 cells)
        verify(mockUserRow, times(16)).createCell(anyInt());
    }

    @Test
    void testBuildExcelDocumentWithEmptyUserList() throws Exception {
        // Arrange
        List<User> emptyUsers = new ArrayList<>();
        model.put("users", emptyUsers);

        // Act
        excelView.buildExcelDocument(model, mockWorkbook, mockRequest, mockResponse);

        // Assert
        verify(mockResponse).setHeader("Content-Disposition", "attachment; filename=\"my-xls-file.xls\"");
        verify(mockWorkbook).createSheet("User Detail");
        verify(mockSheet).setDefaultColumnWidth(30);

        // Verify header row creation even with empty user list
        verify(mockSheet).createRow(0);

        // Verify no user rows created
        verify(mockSheet, never()).createRow(1);
    }

    @Test
    void testBuildExcelDocumentWithSingleUser() throws Exception {
        // Arrange
        Role role = new Role();
        role.setId(2L);
        role.setName("ADMIN");

        User singleUser = User.builder()
                .id(1L)
                .firstName("Admin")
                .lastName("User")
                .username("admin")
                .email("admin@example.com")
                .password("adminpass")
                .enabled(true)
                .role(role)
                .build();

        users.add(singleUser);
        model.put("users", users);

        // Act
        excelView.buildExcelDocument(model, mockWorkbook, mockRequest, mockResponse);

        // Assert
        verify(mockResponse).setHeader("Content-Disposition", "attachment; filename=\"my-xls-file.xls\"");
        verify(mockWorkbook).createSheet("User Detail");

        // Verify single user row creation
        verify(mockSheet).createRow(1);
        verify(mockSheet, never()).createRow(2);

        // Verify user data cells creation (8 columns for 1 user)
        verify(mockUserRow, times(8)).createCell(anyInt());
    }

    @Test
    void testBuildExcelDocumentSetsContentDisposition() throws Exception {
        // Arrange
        model.put("users", new ArrayList<>());

        // Act
        excelView.buildExcelDocument(model, mockWorkbook, mockRequest, mockResponse);

        // Assert
        verify(mockResponse).setHeader("Content-Disposition", "attachment; filename=\"my-xls-file.xls\"");
    }

    @Test
    void testBuildExcelDocumentCreatesSheetWithCorrectName() throws Exception {
        // Arrange
        model.put("users", new ArrayList<>());

        // Act
        excelView.buildExcelDocument(model, mockWorkbook, mockRequest, mockResponse);

        // Assert
        verify(mockWorkbook).createSheet("User Detail");
    }

    @Test
    void testBuildExcelDocumentSetsDefaultColumnWidth() throws Exception {
        // Arrange
        model.put("users", new ArrayList<>());

        // Act
        excelView.buildExcelDocument(model, mockWorkbook, mockRequest, mockResponse);

        // Assert
        verify(mockSheet).setDefaultColumnWidth(30);
    }

    @Test
    void testBuildExcelDocumentCreatesHeaderStyle() throws Exception {
        // Arrange
        model.put("users", new ArrayList<>());

        // Act
        excelView.buildExcelDocument(model, mockWorkbook, mockRequest, mockResponse);

        // Assert
        verify(mockWorkbook).createCellStyle();
        verify(mockWorkbook).createFont();
        verify(mockFont).setFontName("Arial");
        verify(mockCellStyle).setFillForegroundColor(IndexedColors.BLUE.getIndex());
        verify(mockCellStyle).setFillPattern(FillPatternType.SOLID_FOREGROUND);
        verify(mockFont).setBold(true);
        verify(mockFont).setColor(IndexedColors.WHITE.getIndex());
        verify(mockCellStyle).setFont(mockFont);
    }

    @Test
    void testBuildExcelDocumentCreatesHeaderCells() throws Exception {
        // Arrange
        model.put("users", new ArrayList<>());

        // Act
        excelView.buildExcelDocument(model, mockWorkbook, mockRequest, mockResponse);

        // Assert
        // Verify all 8 header columns are created
        for (int i = 0; i < 8; i++) {
            verify(mockHeaderRow).createCell(i);
            verify(mockHeaderRow).getCell(i);
        }

        // Verify header cell style is applied
        verify(mockCell, times(8)).setCellStyle(mockCellStyle);
    }

    @Test
    void testBuildExcelDocumentWithNullUsers() throws Exception {
        // Arrange
        model.put("users", null);

        // Act & Assert
        assertThrows(NullPointerException.class, () ->
            excelView.buildExcelDocument(model, mockWorkbook, mockRequest, mockResponse));
    }

    @Test
    void testBuildExcelDocumentWithMultipleUsers() throws Exception {
        // Arrange
        Role userRole = new Role();
        userRole.setId(1L);
        userRole.setName("USER");

        Role adminRole = new Role();
        adminRole.setId(2L);
        adminRole.setName("ADMIN");

        for (int i = 1; i <= 5; i++) {
            User user = User.builder()
                    .id((long) i)
                    .firstName("FirstName" + i)
                    .lastName("LastName" + i)
                    .username("user" + i)
                    .email("user" + i + "@example.com")
                    .password("password" + i)
                    .enabled(i % 2 == 0)
                    .role(i == 1 ? adminRole : userRole)
                    .build();
            users.add(user);
        }

        model.put("users", users);

        // Act
        excelView.buildExcelDocument(model, mockWorkbook, mockRequest, mockResponse);

        // Assert
        verify(mockResponse).setHeader("Content-Disposition", "attachment; filename=\"my-xls-file.xls\"");

        // Verify 5 user rows are created (rows 1-5)
        for (int i = 1; i <= 5; i++) {
            verify(mockSheet).createRow(i);
        }

        // Verify user data cells creation (8 columns * 5 users = 40 cells)
        verify(mockUserRow, times(40)).createCell(anyInt());
    }

    @Test
    void testExcelViewInheritance() {
        // Act & Assert
        assertTrue(excelView instanceof org.springframework.web.servlet.view.document.AbstractXlsView);
    }

    @Test
    void testBuildExcelDocumentRowCounter() throws Exception {
        // Arrange
        Role role = new Role();
        role.setId(1L);
        role.setName("TEST_ROLE");

        // Add 3 users to verify row counter increments correctly
        for (int i = 1; i <= 3; i++) {
            User user = User.builder()
                    .firstName("User" + i)
                    .lastName("Test" + i)
                    .username("user" + i)
                    .email("user" + i + "@test.com")
                    .enabled(true)
                    .role(role)
                    .build();
            users.add(user);
        }

        model.put("users", users);

        // Act
        excelView.buildExcelDocument(model, mockWorkbook, mockRequest, mockResponse);

        // Assert
        // Verify rows 1, 2, 3 are created (starting from 1 after header row 0)
        verify(mockSheet).createRow(1);
        verify(mockSheet).createRow(2);
        verify(mockSheet).createRow(3);
        verify(mockSheet, never()).createRow(4);
    }

    @Test
    void testModelWithoutUsersKey() throws Exception {
        // Arrange
        Map<String, Object> modelWithoutUsers = new HashMap<>();
        modelWithoutUsers.put("someOtherKey", "someValue");

        // Act & Assert
        assertThrows(ClassCastException.class, () ->
            excelView.buildExcelDocument(modelWithoutUsers, mockWorkbook, mockRequest, mockResponse));
    }

    @Test
    void testBuildExcelDocumentWithUserWithNullRole() throws Exception {
        // Arrange
        User userWithNullRole = User.builder()
                .id(1L)
                .firstName("Test")
                .lastName("User")
                .username("testuser")
                .email("test@example.com")
                .password("password")
                .enabled(true)
                .role(null)
                .build();

        users.add(userWithNullRole);
        model.put("users", users);

        // Act & Assert
        assertThrows(NullPointerException.class, () ->
            excelView.buildExcelDocument(model, mockWorkbook, mockRequest, mockResponse));
    }

    @Test
    void testBuildExcelDocumentCellValueSettings() throws Exception {
        // Arrange
        Role role = new Role();
        role.setId(100L);
        role.setName("SPECIAL_ROLE");

        User user = User.builder()
                .firstName("TestFirst")
                .lastName("TestLast")
                .username("testuser")
                .email("test@email.com")
                .password("testpass")
                .enabled(false)
                .role(role)
                .build();

        users.add(user);
        model.put("users", users);

        // Act
        excelView.buildExcelDocument(model, mockWorkbook, mockRequest, mockResponse);

        // Assert
        // Verify setCellValue is called with correct user data
        verify(mockCell, atLeastOnce()).setCellValue("TestFirst");
        verify(mockCell, atLeastOnce()).setCellValue("TestLast");
        verify(mockCell, atLeastOnce()).setCellValue("testuser");
        verify(mockCell, atLeastOnce()).setCellValue("test@email.com");
        verify(mockCell, atLeastOnce()).setCellValue("testpass");
        verify(mockCell, atLeastOnce()).setCellValue(false);
        verify(mockCell, atLeastOnce()).setCellValue(100L);
        verify(mockCell, atLeastOnce()).setCellValue("SPECIAL_ROLE");
    }
}