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
    public void testSetAndGetId() {
        pdf.setId(1L);
        assertEquals(1L, pdf.getId());
    }

    @Test
    public void testSetAndGetName() {
        pdf.setName("TestPDF");
        assertEquals("TestPDF", pdf.getName());
    }

    @Test
    public void testSetAndGetContent() {
        pdf.setContent("PDF Content");
        assertEquals("PDF Content", pdf.getContent());
    }

    @Test
    public void testBuilderPattern() {
        Pdf pdfBuilt = Pdf.builder()
                .id(1L)
                .name("Document")
                .content("Sample content")
                .build();

        assertEquals(1L, pdfBuilt.getId());
        assertEquals("Document", pdfBuilt.getName());
        assertEquals("Sample content", pdfBuilt.getContent());
    }

    @Test
    public void testAllArgsConstructor() {
        Pdf pdfWithArgs = new Pdf(1L, "Report", "Report content");
        assertEquals(1L, pdfWithArgs.getId());
        assertEquals("Report", pdfWithArgs.getName());
        assertEquals("Report content", pdfWithArgs.getContent());
    }

    @Test
    public void testNoArgsConstructor() {
        Pdf emptyPdf = new Pdf();
        assertNull(emptyPdf.getId());
        assertNull(emptyPdf.getName());
        assertNull(emptyPdf.getContent());
    }

    @Test
    public void testSetAndGetNameNull() {
        pdf.setName(null);
        assertNull(pdf.getName());
    }

    @Test
    public void testSetAndGetContentNull() {
        pdf.setContent(null);
        assertNull(pdf.getContent());
    }
}
