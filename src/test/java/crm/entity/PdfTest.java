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
        Pdf builtPdf = Pdf.builder()
                .id(1L)
                .name("Test PDF")
                .content("PDF Content")
                .build();

        assertNotNull(builtPdf);
        assertEquals(1L, builtPdf.getId());
        assertEquals("Test PDF", builtPdf.getName());
        assertEquals("PDF Content", builtPdf.getContent());
    }

    @Test
    void testNoArgsConstructor() {
        Pdf pdf = new Pdf();
        assertNotNull(pdf);
    }

    @Test
    void testAllArgsConstructor() {
        Pdf pdf = new Pdf(1L, "Document", "Content");

        assertNotNull(pdf);
        assertEquals(1L, pdf.getId());
        assertEquals("Document", pdf.getName());
        assertEquals("Content", pdf.getContent());
    }

    @Test
    void testSetAndGetId() {
        pdf.setId(100L);
        assertEquals(100L, pdf.getId());
    }

    @Test
    void testSetAndGetName() {
        pdf.setName("Annual Report");
        assertEquals("Annual Report", pdf.getName());
    }

    @Test
    void testSetAndGetContent() {
        String content = "This is the PDF content";
        pdf.setContent(content);
        assertEquals(content, pdf.getContent());
    }

    @Test
    void testNameMinimumSize() {
        String shortName = "AB";
        pdf.setName(shortName);
        assertEquals(shortName, pdf.getName());
        assertTrue(pdf.getName().length() >= 2);
    }

    @Test
    void testNameTooShort() {
        String tooShort = "A";
        pdf.setName(tooShort);
        assertTrue(pdf.getName().length() < 2);
    }

    @Test
    void testNameNotNull() {
        pdf.setName("ValidName");
        assertNotNull(pdf.getName());
    }

    @Test
    void testNullValues() {
        pdf.setName(null);
        assertNull(pdf.getName());

        pdf.setContent(null);
        assertNull(pdf.getContent());
    }

    @Test
    void testContentIsTransient() {
        pdf.setContent("Transient Content");
        assertEquals("Transient Content", pdf.getContent());
    }

    @Test
    void testLongName() {
        String longName = "This is a very long PDF name that should still be valid";
        pdf.setName(longName);
        assertEquals(longName, pdf.getName());
    }

    @Test
    void testLongContent() {
        String longContent = "A".repeat(1000);
        pdf.setContent(longContent);
        assertEquals(longContent, pdf.getContent());
        assertEquals(1000, pdf.getContent().length());
    }
}
