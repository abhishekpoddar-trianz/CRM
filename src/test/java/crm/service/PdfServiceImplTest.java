package crm.service;

import crm.entity.Pdf;
import crm.repository.PdfRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PdfServiceImplTest {

    @Mock
    private PdfRepository pdfRepository;

    private PdfServiceImpl pdfService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        pdfService = new PdfServiceImpl(pdfRepository);
    }

    @Test
    public void testFindByName() {
        Pdf pdf = new Pdf();
        pdf.setId(1L);
        pdf.setName("TestPDF");

        when(pdfRepository.findByName("TestPDF")).thenReturn(pdf);

        Pdf result = pdfService.findByName("TestPDF");

        assertNotNull(result);
        assertEquals("TestPDF", result.getName());
        verify(pdfRepository, times(1)).findByName("TestPDF");
    }

    @Test
    public void testFindByNameNotFound() {
        when(pdfRepository.findByName("NonExistent")).thenReturn(null);

        Pdf result = pdfService.findByName("NonExistent");

        assertNull(result);
        verify(pdfRepository, times(1)).findByName("NonExistent");
    }

    @Test
    public void testSavePdf() {
        Pdf pdf = new Pdf();
        pdf.setName("NewPDF");

        when(pdfRepository.save(any(Pdf.class))).thenReturn(pdf);

        assertDoesNotThrow(() -> pdfService.savePdf(pdf));
        verify(pdfRepository, times(1)).save(pdf);
    }

    @Test
    public void testSavePdfWithContent() {
        Pdf pdf = new Pdf();
        pdf.setName("ContentPDF");
        pdf.setContent("Sample content");

        when(pdfRepository.save(any(Pdf.class))).thenReturn(pdf);

        pdfService.savePdf(pdf);
        verify(pdfRepository, times(1)).save(pdf);
    }

    @Test
    public void testSavePdfNull() {
        assertDoesNotThrow(() -> pdfService.savePdf(null));
        verify(pdfRepository, times(1)).save(null);
    }
}
