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
    void testPdfBuilder() {
        Pdf built = Pdf.builder()
                .id(1L)
                .name("test.pdf")
                .content("PDF Content")
                .build();

        assertNotNull(built);
        assertEquals(1L, built.getId());
        assertEquals("test.pdf", built.getName());
        assertEquals("PDF Content", built.getContent());
    }

    @Test
    void testSettersAndGetters() {
        pdf.setId(2L);
        pdf.setName("document.pdf");
        pdf.setContent("Document content");

        assertEquals(2L, pdf.getId());
        assertEquals("document.pdf", pdf.getName());
        assertEquals("Document content", pdf.getContent());
    }

    @Test
    void testNoArgsConstructor() {
        Pdf newPdf = new Pdf();
        assertNotNull(newPdf);
        assertNull(newPdf.getId());
        assertNull(newPdf.getName());
        assertNull(newPdf.getContent());
    }

    @Test
    void testAllArgsConstructor() {
        Pdf fullPdf = new Pdf(3L, "full.pdf", "Full content");

        assertEquals(3L, fullPdf.getId());
        assertEquals("full.pdf", fullPdf.getName());
        assertEquals("Full content", fullPdf.getContent());
    }

    @Test
    void testNameWithMinimumSize() {
        pdf.setName("ab");
        assertEquals("ab", pdf.getName());
    }

    @Test
    void testContentIsTransient() {
        pdf.setContent("Transient content");
        assertEquals("Transient content", pdf.getContent());
    }

    @Test
    void testNullValues() {
        pdf.setName(null);
        pdf.setContent(null);

        assertNull(pdf.getName());
        assertNull(pdf.getContent());
    }
}
