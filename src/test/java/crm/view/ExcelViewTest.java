package crm.view;

import crm.entity.Role;
import crm.entity.User;
import org.apache.poi.ss.usermodel.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
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
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private Workbook mockWorkbook;

    @Mock
    private Sheet mockSheet;

    @Mock
    private Row mockHeaderRow;

    @Mock
    private Row mockDataRow;

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

        // Create test users
        users = new ArrayList<>();

        Role role1 = new Role();
        role1.setId(1);
        role1.setName("ADMIN");

        Role role2 = new Role();
        role2.setId(2);
        role2.setName("USER");

        User user1 = User.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .username("johndoe")
                .email("john@example.com")
                .password("password123")
                .enabled(1)
                .role(role1)
                .build();

        User user2 = User.builder()
                .id(2L)
                .firstName("Jane")
                .lastName("Smith")
                .username("janesmith")
                .email("jane@example.com")
                .password("password456")
                .enabled(0)
                .role(role2)
                .build();

        users.add(user1);
        users.add(user2);

        model = new HashMap<>();
        model.put("users", users);

        // Mock workbook behavior
        when(mockWorkbook.createSheet("User Detail")).thenReturn(mockSheet);
        when(mockWorkbook.createCellStyle()).thenReturn(mockCellStyle);
        when(mockWorkbook.createFont()).thenReturn(mockFont);
        when(mockSheet.createRow(0)).thenReturn(mockHeaderRow);
        when(mockSheet.createRow(anyInt())).thenReturn(mockDataRow);
        when(mockHeaderRow.createCell(anyInt())).thenReturn(mockCell);
        when(mockDataRow.createCell(anyInt())).thenReturn(mockCell);
        when(mockHeaderRow.getCell(anyInt())).thenReturn(mockCell);
    }

    @Test
    void testBuildExcelDocument() throws Exception {
        // Act
        excelView.buildExcelDocument(model, mockWorkbook, request, response);

        // Assert
        verify(response).setHeader("Content-Disposition", "attachment; filename=\"my-xls-file.xls\"");
        verify(mockWorkbook).createSheet("User Detail");
        verify(mockSheet).setDefaultColumnWidth(30);
        verify(mockWorkbook).createCellStyle();
        verify(mockWorkbook).createFont();
    }

    @Test
    void testBuildExcelDocumentCreatesHeader() throws Exception {
        // Act
        excelView.buildExcelDocument(model, mockWorkbook, request, response);

        // Assert
        verify(mockHeaderRow, times(8)).createCell(anyInt());
        verify(mockCell, atLeast(8)).setCellValue(anyString());
        verify(mockCell, atLeast(8)).setCellStyle(mockCellStyle);
    }

    @Test
    void testBuildExcelDocumentCreatesDataRows() throws Exception {
        // Act
        excelView.buildExcelDocument(model, mockWorkbook, request, response);

        // Assert
        verify(mockSheet).createRow(1); // First data row
        verify(mockSheet).createRow(2); // Second data row
        verify(mockDataRow, atLeast(16)).createCell(anyInt()); // 8 cells per user * 2 users
    }

    @Test
    void testBuildExcelDocumentWithEmptyUserList() throws Exception {
        // Arrange
        model.put("users", new ArrayList<User>());

        // Act
        excelView.buildExcelDocument(model, mockWorkbook, request, response);

        // Assert
        verify(response).setHeader("Content-Disposition", "attachment; filename=\"my-xls-file.xls\"");
        verify(mockWorkbook).createSheet("User Detail");
        verify(mockHeaderRow, times(8)).createCell(anyInt()); // Header should still be created
        verify(mockSheet, never()).createRow(1); // No data rows should be created
    }

    @Test
    void testBuildExcelDocumentWithNullUserList() throws Exception {
        // Arrange
        model.put("users", null);

        // Act & Assert
        assertThrows(NullPointerException.class, () ->
            excelView.buildExcelDocument(model, mockWorkbook, request, response));
    }

    @Test
    void testBuildExcelDocumentWithNoUsersKey() throws Exception {
        // Arrange
        Map<String, Object> emptyModel = new HashMap<>();

        // Act & Assert
        assertThrows(ClassCastException.class, () ->
            excelView.buildExcelDocument(emptyModel, mockWorkbook, request, response));
    }

    @Test
    void testBuildExcelDocumentSetsContentDisposition() throws Exception {
        // Act
        excelView.buildExcelDocument(model, mockWorkbook, request, response);

        // Assert
        verify(response).setHeader("Content-Disposition", "attachment; filename=\"my-xls-file.xls\"");
    }

    @Test
    void testBuildExcelDocumentConfiguresSheet() throws Exception {
        // Act
        excelView.buildExcelDocument(model, mockWorkbook, request, response);

        // Assert
        verify(mockWorkbook).createSheet("User Detail");
        verify(mockSheet).setDefaultColumnWidth(30);
    }

    @Test
    void testBuildExcelDocumentConfiguresCellStyle() throws Exception {
        // Act
        excelView.buildExcelDocument(model, mockWorkbook, request, response);

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
    void testBuildExcelDocumentWithSingleUser() throws Exception {
        // Arrange
        List<User> singleUserList = new ArrayList<>();
        singleUserList.add(users.get(0));
        model.put("users", singleUserList);

        // Act
        excelView.buildExcelDocument(model, mockWorkbook, request, response);

        // Assert
        verify(mockSheet).createRow(1); // Only one data row
        verify(mockSheet, never()).createRow(2); // No second data row
    }

    @Test
    void testBuildExcelDocumentWithUserHavingNullRole() throws Exception {
        // Arrange
        User userWithNullRole = User.builder()
                .id(3L)
                .firstName("Test")
                .lastName("User")
                .username("testuser")
                .email("test@example.com")
                .password("password")
                .enabled(1)
                .role(null)
                .build();

        List<User> usersWithNull = new ArrayList<>();
        usersWithNull.add(userWithNullRole);
        model.put("users", usersWithNull);

        // Act & Assert
        assertThrows(NullPointerException.class, () ->
            excelView.buildExcelDocument(model, mockWorkbook, request, response));
    }

    @Test
    void testInheritsFromAbstractXlsView() {
        // Act & Assert
        assertTrue(excelView instanceof org.springframework.web.servlet.view.document.AbstractXlsView);
    }

    @Test
    void testBuildExcelDocumentRowCountIncrement() throws Exception {
        // Act
        excelView.buildExcelDocument(model, mockWorkbook, request, response);

        // Assert
        verify(mockSheet).createRow(0); // Header row
        verify(mockSheet).createRow(1); // First user row
        verify(mockSheet).createRow(2); // Second user row
    }

    @Test
    void testBuildExcelDocumentWithLargeUserList() throws Exception {
        // Arrange
        List<User> largeUserList = new ArrayList<>();
        Role role = new Role();
        role.setId(1);
        role.setName("USER");

        for (int i = 0; i < 100; i++) {
            User user = User.builder()
                    .id((long) i)
                    .firstName("FirstName" + i)
                    .lastName("LastName" + i)
                    .username("user" + i)
                    .email("user" + i + "@example.com")
                    .password("password" + i)
                    .enabled(i % 2)
                    .role(role)
                    .build();
            largeUserList.add(user);
        }
        model.put("users", largeUserList);

        // Act & Assert
        assertDoesNotThrow(() -> excelView.buildExcelDocument(model, mockWorkbook, request, response));

        // Verify that rows are created for all users
        for (int i = 1; i <= 100; i++) {
            verify(mockSheet).createRow(i);
        }
    }

    @Test
    void testBuildExcelDocumentHandlesNullUserFields() throws Exception {
        // Arrange
        Role role = new Role();
        role.setId(1);
        role.setName("USER");

        User userWithNulls = User.builder()
                .id(1L)
                .firstName(null)
                .lastName(null)
                .username(null)
                .email(null)
                .password(null)
                .enabled(1)
                .role(role)
                .build();

        List<User> usersWithNulls = new ArrayList<>();
        usersWithNulls.add(userWithNulls);
        model.put("users", usersWithNulls);

        // Act & Assert
        assertDoesNotThrow(() -> excelView.buildExcelDocument(model, mockWorkbook, request, response));
    }

    @Test
    void testBuildExcelDocumentHeaderLabels() throws Exception {
        // Act
        excelView.buildExcelDocument(model, mockWorkbook, request, response);

        // Assert - Verify all header labels are set
        verify(mockCell).setCellValue("FirstName");
        verify(mockCell).setCellValue("LastName");
        verify(mockCell).setCellValue("Username");
        verify(mockCell).setCellValue("Email");
        verify(mockCell).setCellValue("Password");
        verify(mockCell).setCellValue("Enabled");
        verify(mockCell).setCellValue("Role_id");
        verify(mockCell).setCellValue("Role_name");
    }
}