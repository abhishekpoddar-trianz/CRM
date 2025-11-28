package crm.service;

import crm.entity.Pdf;
import crm.repository.PdfRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PdfServiceImplTest {

    @Mock
    private PdfRepository pdfRepository;

    @InjectMocks
    private PdfServiceImpl pdfService;

    private Pdf pdf;

    @BeforeEach
    void setUp() {
        pdf = new Pdf();
        pdf.setId(1L);
        pdf.setName("TestPDF");
        pdf.setContent("Test Content");
    }

    @Test
    void testFindByName() {
        when(pdfRepository.findByName("TestPDF")).thenReturn(pdf);

        Pdf result = pdfService.findByName("TestPDF");

        assertNotNull(result);
        assertEquals("TestPDF", result.getName());
        verify(pdfRepository).findByName("TestPDF");
    }

    @Test
    void testFindByNameNotFound() {
        when(pdfRepository.findByName("NonExistent")).thenReturn(null);

        Pdf result = pdfService.findByName("NonExistent");

        assertNull(result);
        verify(pdfRepository).findByName("NonExistent");
    }

    @Test
    void testSavePdf() {
        when(pdfRepository.save(any(Pdf.class))).thenReturn(pdf);

        pdfService.savePdf(pdf);

        verify(pdfRepository).save(pdf);
    }

    @Test
    void testSavePdfWithNullName() {
        Pdf nullNamePdf = new Pdf();
        nullNamePdf.setContent("Content");
        when(pdfRepository.save(any(Pdf.class))).thenReturn(nullNamePdf);

        pdfService.savePdf(nullNamePdf);

        verify(pdfRepository).save(nullNamePdf);
    }
}
