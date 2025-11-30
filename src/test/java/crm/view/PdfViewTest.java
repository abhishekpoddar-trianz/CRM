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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    void testBuildPdfDocumentWithValidUsers() throws Exception {
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

        realDocument.close();
        verify(response).setHeader("Content-Disposition", "attachment; filename=\"my-pdf-file.pdf\"");
        assertTrue(baos.size() > 0);
    }

    @Test
    void testBuildPdfDocumentWithEmptyUsersList() throws Exception {
        Map<String, Object> model = new HashMap<>();
        model.put("users", Arrays.asList());

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
    void testBuildPdfDocumentSetsContentDisposition() throws Exception {
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

        Document realDocument = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter realWriter = PdfWriter.getInstance(realDocument, baos);
        realDocument.open();

        pdfView.buildPdfDocument(model, realDocument, realWriter, request, response);

        realDocument.close();
        verify(response, times(1)).setHeader("Content-Disposition", "attachment; filename=\"my-pdf-file.pdf\"");
    }

    @Test
    void testConstructorSetsContentType() {
        PdfView view = new PdfView();
        assertNotNull(view);
        assertEquals("application/pdf", view.getContentType());
    }
}
