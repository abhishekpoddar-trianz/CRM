package crm.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class PdfTest {

    private Pdf pdf;

    @BeforeEach
    public void setUp() {
        pdf = new Pdf();
    }

    @Test
    public void testNoArgsConstructor() {
        Pdf newPdf = new Pdf();
        assertNotNull(newPdf);
    }

    @Test
    public void testSettersAndGetters() {
        String content = "Test PDF content";

        pdf.setId(1L);
        pdf.setName("test.pdf");
        pdf.setContent(content);

        assertEquals(1L, pdf.getId());
        assertEquals("test.pdf", pdf.getName());
        assertEquals(content, pdf.getContent());
    }

    @Test
    public void testNameField() {
        pdf.setName("document.pdf");
        assertEquals("document.pdf", pdf.getName());

        pdf.setName("");
        assertEquals("", pdf.getName());

        pdf.setName(null);
        assertNull(pdf.getName());
    }

    @Test
    public void testContentField() {
        String emptyContent = "";
        pdf.setContent(emptyContent);
        assertEquals(emptyContent, pdf.getContent());

        pdf.setContent(null);
        assertNull(pdf.getContent());
    }

    @Test
    public void testEntityAnnotation() {
        assertTrue(Pdf.class.isAnnotationPresent(jakarta.persistence.Entity.class));
    }

    @Test
    public void testDataAnnotation() {
        assertTrue(Pdf.class.isAnnotationPresent(lombok.Data.class));
    }

    @Test
    public void testEqualsAndHashCode() {
        Pdf pdf1 = new Pdf();
        pdf1.setId(1L);
        pdf1.setName("test.pdf");

        Pdf pdf2 = new Pdf();
        pdf2.setId(1L);
        pdf2.setName("test.pdf");

        assertEquals(pdf1, pdf2);
        assertEquals(pdf1.hashCode(), pdf2.hashCode());
    }
}