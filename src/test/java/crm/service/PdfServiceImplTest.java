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

    private PdfServiceImpl pdfService;

    @Mock
    private PdfRepository pdfRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        pdfService = new PdfServiceImpl(pdfRepository);
    }

    @Test
    void testConstructor() {
        PdfServiceImpl service = new PdfServiceImpl(pdfRepository);
        assertNotNull(service);
    }

    @Test
    void testFindByName() {
        Pdf pdf = new Pdf();
        pdf.setName("test.pdf");

        when(pdfRepository.findByName("test.pdf")).thenReturn(pdf);

        Pdf result = pdfService.findByName("test.pdf");

        assertNotNull(result);
        assertEquals("test.pdf", result.getName());
        verify(pdfRepository, times(1)).findByName("test.pdf");
    }

    @Test
    void testSavePdf() {
        Pdf pdf = new Pdf();
        pdf.setName("document.pdf");

        pdfService.savePdf(pdf);

        verify(pdfRepository, times(1)).save(pdf);
    }

    @Test
    void testFindByNameNotFound() {
        when(pdfRepository.findByName("nonexistent.pdf")).thenReturn(null);

        Pdf result = pdfService.findByName("nonexistent.pdf");

        assertNull(result);
    }
}
