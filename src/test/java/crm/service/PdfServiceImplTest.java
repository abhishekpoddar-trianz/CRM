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

public class PdfServiceImplTest {

    @Mock
    private PdfRepository pdfRepository;

    @InjectMocks
    private PdfServiceImpl pdfService;

    private Pdf pdf;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        pdf = Pdf.builder()
                .id(1L)
                .name("TestPdf")
                .content("Test Content")
                .build();
    }

    @Test
    public void testFindByName() {
        when(pdfRepository.findByName("TestPdf")).thenReturn(pdf);

        Pdf result = pdfService.findByName("TestPdf");

        assertNotNull(result);
        assertEquals("TestPdf", result.getName());
        verify(pdfRepository, times(1)).findByName("TestPdf");
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
        when(pdfRepository.save(pdf)).thenReturn(pdf);

        pdfService.savePdf(pdf);

        verify(pdfRepository, times(1)).save(pdf);
    }

    @Test
    public void testSavePdfWithNullName() {
        Pdf nullPdf = Pdf.builder()
                .id(2L)
                .name(null)
                .content("Content")
                .build();

        pdfService.savePdf(nullPdf);

        verify(pdfRepository, times(1)).save(nullPdf);
    }
}
