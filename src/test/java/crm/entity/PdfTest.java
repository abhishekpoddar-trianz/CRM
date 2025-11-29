package crm.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PdfTest {

    private Pdf pdf;

    @BeforeEach
    void setUp() {
        pdf = new Pdf();
    }

    @Test
    void testConstructor() {
        Pdf newPdf = new Pdf();
        assertNotNull(newPdf);
    }

    @Test
    void testGettersAndSetters() {
        byte[] content = "test content".getBytes();

        pdf.setId(1L);
        pdf.setName("test.pdf");
        pdf.setContent(content);

        assertEquals(1L, pdf.getId());
        assertEquals("test.pdf", pdf.getName());
        assertArrayEquals(content, pdf.getContent());
    }

    @Test
    void testAllArgsConstructor() {
        byte[] content = "test content".getBytes();
        Pdf newPdf = new Pdf(1L, "document.pdf", content);

        assertEquals(1L, newPdf.getId());
        assertEquals("document.pdf", newPdf.getName());
        assertArrayEquals(content, newPdf.getContent());
    }

    @Test
    void testEqualsAndHashCode() {
        byte[] content = "test".getBytes();

        Pdf pdf1 = new Pdf();
        pdf1.setId(1L);
        pdf1.setName("test.pdf");
        pdf1.setContent(content);

        Pdf pdf2 = new Pdf();
        pdf2.setId(1L);
        pdf2.setName("test.pdf");
        pdf2.setContent(content);

        assertEquals(pdf1, pdf2);
        assertEquals(pdf1.hashCode(), pdf2.hashCode());
    }
}