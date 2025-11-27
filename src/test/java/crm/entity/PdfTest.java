package crm.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PdfTest {

    private Pdf pdf;

    @BeforeEach
    public void setUp() {
        pdf = new Pdf();
    }

    @Test
    public void testPdfConstructor() {
        assertNotNull(pdf);
    }

    @Test
    public void testPdfBuilderConstructor() {
        Pdf pdfBuilt = Pdf.builder()
                .id(1L)
                .name("test.pdf")
                .content("Test content")
                .build();
        assertNotNull(pdfBuilt);
        assertEquals(1L, pdfBuilt.getId());
        assertEquals("test.pdf", pdfBuilt.getName());
        assertEquals("Test content", pdfBuilt.getContent());
    }

    @Test
    public void testAllArgsConstructor() {
        Pdf pdfAll = new Pdf(1L, "document.pdf", "Content here");
        assertNotNull(pdfAll);
        assertEquals(1L, pdfAll.getId());
        assertEquals("document.pdf", pdfAll.getName());
        assertEquals("Content here", pdfAll.getContent());
    }

    @Test
    public void testSetAndGetId() {
        pdf.setId(100L);
        assertEquals(100L, pdf.getId());
    }

    @Test
    public void testSetAndGetName() {
        pdf.setName("myfile.pdf");
        assertEquals("myfile.pdf", pdf.getName());
    }

    @Test
    public void testSetAndGetContent() {
        pdf.setContent("This is PDF content");
        assertEquals("This is PDF content", pdf.getContent());
    }

    @Test
    public void testSetNameNull() {
        pdf.setName(null);
        assertNull(pdf.getName());
    }

    @Test
    public void testSetContentNull() {
        pdf.setContent(null);
        assertNull(pdf.getContent());
    }

    @Test
    public void testSetIdNull() {
        pdf.setId(null);
        assertNull(pdf.getId());
    }

    @Test
    public void testNameMinSize() {
        pdf.setName("ab");
        assertEquals("ab", pdf.getName());
    }

    @Test
    public void testNameSingleCharacter() {
        pdf.setName("a");
        assertEquals("a", pdf.getName());
    }

    @Test
    public void testToString() {
        pdf.setId(1L);
        pdf.setName("test.pdf");
        String result = pdf.toString();
        assertNotNull(result);
    }
}
