package crm.view;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import crm.entity.Role;
import crm.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
public class PdfViewTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    private PdfView pdfView;

    @BeforeEach
    public void setUp() {
        pdfView = new PdfView();
    }

    @Test
    public void testBuildPdfDocumentWithUsers() throws Exception {
        List<User> users = createTestUsers();
        Map<String, Object> model = new HashMap<>();
        model.put("users", users);

        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance(document, baos);
        document.open();

        pdfView.buildPdfDocument(model, document, writer, request, response);

        verify(response).setHeader("Content-Disposition", "attachment; filename=\"my-pdf-file.pdf\"");

        document.close();
        assertTrue(baos.size() > 0);
    }

    @Test
    public void testBuildPdfDocumentSetsCorrectHeader() throws Exception {
        List<User> users = createTestUsers();
        Map<String, Object> model = new HashMap<>();
        model.put("users", users);

        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance(document, baos);
        document.open();

        pdfView.buildPdfDocument(model, document, writer, request, response);

        verify(response).setHeader("Content-Disposition", "attachment; filename=\"my-pdf-file.pdf\"");
        document.close();
    }

    @Test
    public void testBuildPdfDocumentWithMultipleUsers() throws Exception {
        List<User> users = createTestUsers();
        users.add(createTestUser("Jane", "Smith", "janesmith", "jane@example.com"));
        users.add(createTestUser("Bob", "Johnson", "bobjohnson", "bob@example.com"));

        Map<String, Object> model = new HashMap<>();
        model.put("users", users);

        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance(document, baos);
        document.open();

        assertDoesNotThrow(() -> {
            pdfView.buildPdfDocument(model, document, writer, request, response);
        });

        document.close();
        assertTrue(baos.size() > 0);
    }

    @Test
    public void testBuildPdfDocumentWithEmptyUsers() throws Exception {
        List<User> users = new ArrayList<>();
        Map<String, Object> model = new HashMap<>();
        model.put("users", users);

        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance(document, baos);
        document.open();

        assertThrows(Exception.class, () -> {
            pdfView.buildPdfDocument(model, document, writer, request, response);
        });

        document.close();
    }

    @Test
    public void testBuildPdfDocumentWithNullUsers() throws Exception {
        Map<String, Object> model = new HashMap<>();
        model.put("users", null);

        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance(document, baos);
        document.open();

        assertThrows(Exception.class, () -> {
            pdfView.buildPdfDocument(model, document, writer, request, response);
        });

        document.close();
    }

    @Test
    public void testInheritanceStructure() {
        assertTrue(pdfView instanceof AbstractPdfView);
    }

    @Test
    public void testBuildPdfDocumentWithNullModel() throws Exception {
        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance(document, baos);
        document.open();

        assertThrows(Exception.class, () -> {
            pdfView.buildPdfDocument(null, document, writer, request, response);
        });

        document.close();
    }

    @Test
    public void testBuildPdfDocumentWithEmptyModel() throws Exception {
        Map<String, Object> model = new HashMap<>();

        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance(document, baos);
        document.open();

        assertThrows(Exception.class, () -> {
            pdfView.buildPdfDocument(model, document, writer, request, response);
        });

        document.close();
    }

    @Test
    public void testBuildPdfDocumentWithUsersHavingNullRole() throws Exception {
        List<User> users = new ArrayList<>();
        User user = createTestUser("John", "Doe", "johndoe", "john@example.com");
        user.setRole(null);
        users.add(user);

        Map<String, Object> model = new HashMap<>();
        model.put("users", users);

        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance(document, baos);
        document.open();

        assertThrows(Exception.class, () -> {
            pdfView.buildPdfDocument(model, document, writer, request, response);
        });

        document.close();
    }

    @Test
    public void testBuildPdfDocumentCreatesTable() throws Exception {
        List<User> users = createTestUsers();
        Map<String, Object> model = new HashMap<>();
        model.put("users", users);

        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance(document, baos);
        document.open();

        assertDoesNotThrow(() -> {
            pdfView.buildPdfDocument(model, document, writer, request, response);
        });

        document.close();
        assertTrue(baos.size() > 0, "PDF document should contain content");
    }

    private List<User> createTestUsers() {
        List<User> users = new ArrayList<>();
        users.add(createTestUser("John", "Doe", "johndoe", "john@example.com"));
        return users;
    }

    private User createTestUser(String firstName, String lastName, String username, String email) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword("password123");
        user.setEnabled(1);

        Role role = new Role();
        role.setId(1);
        role.setName("USER");
        user.setRole(role);

        return user;
    }
}