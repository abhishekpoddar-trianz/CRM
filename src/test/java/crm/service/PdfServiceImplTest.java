package crm.service;

import crm.entity.Pdf;
import crm.repository.PdfRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PdfServiceImplTest {

    @Mock
    private PdfRepository pdfRepository;

    @InjectMocks
    private PdfServiceImpl pdfService;

    private Pdf pdf;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        pdf = Pdf.builder()
                .id(1L)
                .name("test.pdf")
                .content("Test content")
                .build();
    }

    @Test
    void testFindByName() {
        when(pdfRepository.findByName("test.pdf")).thenReturn(pdf);

        Pdf result = pdfService.findByName("test.pdf");

        assertNotNull(result);
        assertEquals("test.pdf", result.getName());
        verify(pdfRepository, times(1)).findByName("test.pdf");
    }

    @Test
    void testFindByNameNotFound() {
        when(pdfRepository.findByName("nonexistent.pdf")).thenReturn(null);

        Pdf result = pdfService.findByName("nonexistent.pdf");

        assertNull(result);
        verify(pdfRepository, times(1)).findByName("nonexistent.pdf");
    }

    @Test
    void testSavePdf() {
        when(pdfRepository.save(pdf)).thenReturn(pdf);

        assertDoesNotThrow(() -> pdfService.savePdf(pdf));
        verify(pdfRepository, times(1)).save(pdf);
    }

    @Test
    void testSavePdfWithNullName() {
        Pdf nullNamePdf = Pdf.builder()
                .id(2L)
                .name(null)
                .content("Content")
                .build();

        assertDoesNotThrow(() -> pdfService.savePdf(nullNamePdf));
        verify(pdfRepository, times(1)).save(nullNamePdf);
    }

    @Test
    void testConstructor() {
        PdfServiceImpl service = new PdfServiceImpl(pdfRepository);
        assertNotNull(service);
    }
}
