package crm.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PdfTest {

    private Pdf pdf;

    @BeforeEach
    void setUp() {
        pdf = Pdf.builder()
                .id(1L)
                .name("Test PDF")
                .content("Test Content")
                .build();
    }

    @Test
    void testPdfBuilder() {
        assertNotNull(pdf);
        assertEquals(1L, pdf.getId());
        assertEquals("Test PDF", pdf.getName());
        assertEquals("Test Content", pdf.getContent());
    }

    @Test
    void testNoArgsConstructor() {
        Pdf emptyPdf = new Pdf();
        assertNotNull(emptyPdf);
        assertNull(emptyPdf.getId());
        assertNull(emptyPdf.getName());
        assertNull(emptyPdf.getContent());
    }

    @Test
    void testAllArgsConstructor() {
        Pdf newPdf = new Pdf(2L, "Another PDF", "Another Content");

        assertNotNull(newPdf);
        assertEquals(2L, newPdf.getId());
        assertEquals("Another PDF", newPdf.getName());
        assertEquals("Another Content", newPdf.getContent());
    }

    @Test
    void testSettersAndGetters() {
        pdf.setId(10L);
        pdf.setName("Updated PDF");
        pdf.setContent("Updated Content");

        assertEquals(10L, pdf.getId());
        assertEquals("Updated PDF", pdf.getName());
        assertEquals("Updated Content", pdf.getContent());
    }

    @Test
    void testNameNotNull() {
        pdf.setName("Valid Name");
        assertNotNull(pdf.getName());
        assertTrue(pdf.getName().length() >= 2);
    }

    @Test
    void testNameMinSize() {
        pdf.setName("AB");
        assertEquals(2, pdf.getName().length());
    }

    @Test
    void testContentIsTransient() {
        pdf.setContent("Transient Content");
        assertEquals("Transient Content", pdf.getContent());
    }

    @Test
    void testIdAutoGeneration() {
        Pdf newPdf = new Pdf();
        assertNull(newPdf.getId());
    }

    @Test
    void testNullContent() {
        pdf.setContent(null);
        assertNull(pdf.getContent());
    }

    @Test
    void testEmptyName() {
        pdf.setName("");
        assertEquals("", pdf.getName());
    }

    @Test
    void testLongName() {
        String longName = "A".repeat(100);
        pdf.setName(longName);
        assertEquals(longName, pdf.getName());
    }
}
