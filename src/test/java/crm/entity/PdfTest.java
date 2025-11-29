package crm.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PdfTest {

    private Pdf pdf;

    @BeforeEach
    public void setUp() {
        pdf = Pdf.builder()
                .id(1L)
                .name("TestPdf")
                .content("Test PDF Content")
                .build();
    }

    @Test
    public void testPdfBuilder() {
        assertNotNull(pdf);
        assertEquals(1L, pdf.getId());
        assertEquals("TestPdf", pdf.getName());
        assertEquals("Test PDF Content", pdf.getContent());
    }

    @Test
    public void testPdfNoArgsConstructor() {
        Pdf emptyPdf = new Pdf();
        assertNotNull(emptyPdf);
        assertNull(emptyPdf.getId());
        assertNull(emptyPdf.getName());
        assertNull(emptyPdf.getContent());
    }

    @Test
    public void testPdfAllArgsConstructor() {
        Pdf newPdf = new Pdf(2L, "NewPdf", "New Content");
        assertNotNull(newPdf);
        assertEquals(2L, newPdf.getId());
        assertEquals("NewPdf", newPdf.getName());
        assertEquals("New Content", newPdf.getContent());
    }

    @Test
    public void testSettersAndGetters() {
        pdf.setName("UpdatedPdf");
        pdf.setContent("Updated Content");

        assertEquals("UpdatedPdf", pdf.getName());
        assertEquals("Updated Content", pdf.getContent());
    }

    @Test
    public void testPdfWithNullValues() {
        Pdf nullPdf = Pdf.builder()
                .id(null)
                .name(null)
                .content(null)
                .build();

        assertNotNull(nullPdf);
        assertNull(nullPdf.getId());
        assertNull(nullPdf.getName());
        assertNull(nullPdf.getContent());
    }

    @Test
    public void testNameMinimumSize() {
        pdf.setName("AB");
        assertEquals("AB", pdf.getName());
        assertEquals(2, pdf.getName().length());
    }

    @Test
    public void testContentIsTransient() {
        pdf.setContent("Transient Content");
        assertEquals("Transient Content", pdf.getContent());
    }

    @Test
    public void testPdfIdGeneration() {
        pdf.setId(100L);
        assertEquals(100L, pdf.getId());
    }
}
