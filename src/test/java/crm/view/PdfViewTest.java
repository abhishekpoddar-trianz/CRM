package crm.view;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import crm.entity.Role;
import crm.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PdfViewTest {

    private PdfView pdfView;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private Document document;

    @Mock
    private PdfWriter writer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        pdfView = new PdfView();
    }

    @Test
    void testConstructor() {
        assertNotNull(pdfView);
        assertEquals("application/pdf", pdfView.getContentType());
    }

    @Test
    void testBuildPdfDocumentWithUsers() throws Exception {
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

        Document realDocument = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter realWriter = PdfWriter.getInstance(realDocument, baos);
        realDocument.open();

        pdfView.buildPdfDocument(model, realDocument, realWriter, request, response);

        verify(response).setHeader("Content-Disposition", "attachment; filename=\"my-pdf-file.pdf\"");
        realDocument.close();
        assertTrue(baos.size() > 0);
    }

    @Test
    void testBuildPdfDocumentWithEmptyUsers() throws Exception {
        List<User> users = new ArrayList<>();
        Map<String, Object> model = new HashMap<>();
        model.put("users", users);

        Document realDocument = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter realWriter = PdfWriter.getInstance(realDocument, baos);
        realDocument.open();

        assertThrows(Exception.class, () -> {
            pdfView.buildPdfDocument(model, realDocument, realWriter, request, response);
        });

        realDocument.close();
    }

    @Test
    void testBuildPdfDocumentWithNullModel() throws Exception {
        Map<String, Object> model = new HashMap<>();
        model.put("users", null);

        Document realDocument = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter realWriter = PdfWriter.getInstance(realDocument, baos);
        realDocument.open();

        assertThrows(Exception.class, () -> {
            pdfView.buildPdfDocument(model, realDocument, realWriter, request, response);
        });

        realDocument.close();
    }

    @Test
    void testBuildPdfDocumentSetsCorrectHeaders() throws Exception {
        Role role = new Role();
        role.setId(1);
        role.setName("USER");

        User user = new User();
        user.setFirstName("Test");
        user.setLastName("User");
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setPassword("pass");
        user.setEnabled(0);
        user.setRole(role);

        List<User> users = Collections.singletonList(user);
        Map<String, Object> model = new HashMap<>();
        model.put("users", users);

        Document realDocument = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter realWriter = PdfWriter.getInstance(realDocument, baos);
        realDocument.open();

        pdfView.buildPdfDocument(model, realDocument, realWriter, request, response);

        verify(response, times(1)).setHeader("Content-Disposition", "attachment; filename=\"my-pdf-file.pdf\"");
        realDocument.close();
    }

    @Test
    void testBuildPdfDocumentWithMultipleUsers() throws Exception {
        Role role1 = new Role();
        role1.setId(1);
        role1.setName("ADMIN");

        Role role2 = new Role();
        role2.setId(2);
        role2.setName("USER");

        User user1 = new User();
        user1.setFirstName("Alice");
        user1.setLastName("Wonder");
        user1.setUsername("alice");
        user1.setEmail("alice@example.com");
        user1.setPassword("pass1");
        user1.setEnabled(1);
        user1.setRole(role1);

        User user2 = new User();
        user2.setFirstName("Bob");
        user2.setLastName("Builder");
        user2.setUsername("bob");
        user2.setEmail("bob@example.com");
        user2.setPassword("pass2");
        user2.setEnabled(1);
        user2.setRole(role2);

        User user3 = new User();
        user3.setFirstName("Charlie");
        user3.setLastName("Brown");
        user3.setUsername("charlie");
        user3.setEmail("charlie@example.com");
        user3.setPassword("pass3");
        user3.setEnabled(0);
        user3.setRole(role1);

        List<User> users = Arrays.asList(user1, user2, user3);
        Map<String, Object> model = new HashMap<>();
        model.put("users", users);

        Document realDocument = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter realWriter = PdfWriter.getInstance(realDocument, baos);
        realDocument.open();

        pdfView.buildPdfDocument(model, realDocument, realWriter, request, response);

        realDocument.close();
        assertTrue(baos.size() > 0);
    }
}
