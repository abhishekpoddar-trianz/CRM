package crm.service;

import crm.entity.Pdf;
import crm.repository.PdfRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PdfServiceImplTest {

    @Mock
    private PdfRepository pdfRepository;

    private PdfServiceImpl pdfService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        pdfService = new PdfServiceImpl(pdfRepository);
    }

    @Test
    void testConstructor() {
        assertNotNull(pdfService);
    }

    @Test
    void testFindByName() {
        Pdf mockPdf = new Pdf();
        mockPdf.setId(1L);
        mockPdf.setName("test.pdf");

        when(pdfRepository.findByName("test.pdf");Optional.of(mockPdf)));

        Pdf result = pdfService.findByName("test.pdf");

        assertEquals(mockPdf, result);
        assertEquals("test.pdf", result.getName());
        verify(pdfRepository, times(1)).findByName("test.pdf")();
    }

    @Test
    void testFindByNameNotFound() {
        when(pdfRepository.findByName("nonexistent.pdf");Optional.of(null)));

        Pdf result = pdfService.findByName("nonexistent.pdf");

        assertNull(result);
        verify(pdfRepository, times(1)).findByName("nonexistent.pdf")();
    }

    @Test
    void testSavePdf() {
        Pdf pdf = new Pdf();
        pdf.setName("document.pdf");

        pdfService.savePdf(pdf);

        verify(pdfRepository, times(1)).save(pdf)();
    }

    @Test
    void testSavePdfWithNull() {
        pdfService.savePdf(null);

        verify(pdfRepository, times(1)).save(null)();
    }
}