package crm.service;

import crm.entity.Pdf;
import crm.repository.PdfRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PdfServiceImplTest {

    @Mock
    private PdfRepository pdfRepository;

    private PdfServiceImpl pdfService;

    @BeforeEach
    public void setUp() {
        pdfService = new PdfServiceImpl(pdfRepository);
    }

    @Test
    public void testConstructor() {
        assertNotNull(pdfService);
        PdfServiceImpl service = new PdfServiceImpl(pdfRepository);
        assertNotNull(service);
    }

    @Test
    public void testConstructorWithNullRepository() {
        assertDoesNotThrow(() -> {
            new PdfServiceImpl(null);
        });
    }

    @Test
    public void testFindByName() {
        String pdfName = "test.pdf";
        Pdf mockPdf = createMockPdf(pdfName);
        when(pdfRepository.findByName(pdfName)).thenReturn(mockPdf);

        Pdf result = pdfService.findByName(pdfName);

        assertNotNull(result);
        assertEquals(mockPdf, result);
        assertEquals(pdfName, result.getName());
        verify(pdfRepository).findByName(pdfName);
    }

    @Test
    public void testFindByNameWithNullName() {
        when(pdfRepository.findByName(null)).thenReturn(null);

        Pdf result = pdfService.findByName(null);

        assertNull(result);
        verify(pdfRepository).findByName(null);
    }

    @Test
    public void testFindByNameWithEmptyName() {
        String emptyName = "";
        when(pdfRepository.findByName(emptyName)).thenReturn(null);

        Pdf result = pdfService.findByName(emptyName);

        assertNull(result);
        verify(pdfRepository).findByName(emptyName);
    }

    @Test
    public void testFindByNameNotFound() {
        String pdfName = "nonexistent.pdf";
        when(pdfRepository.findByName(pdfName)).thenReturn(null);

        Pdf result = pdfService.findByName(pdfName);

        assertNull(result);
        verify(pdfRepository).findByName(pdfName);
    }

    @Test
    public void testSavePdf() {
        Pdf mockPdf = createMockPdf("save-test.pdf");
        when(pdfRepository.save(mockPdf)).thenReturn(mockPdf);

        assertDoesNotThrow(() -> {
            pdfService.savePdf(mockPdf);
        });

        verify(pdfRepository).save(mockPdf);
    }

    @Test
    public void testSavePdfWithNull() {
        assertDoesNotThrow(() -> {
            pdfService.savePdf(null);
        });

        verify(pdfRepository).save(null);
    }

    @Test
    public void testSavePdfCallsRepository() {
        Pdf mockPdf = createMockPdf("repository-test.pdf");

        pdfService.savePdf(mockPdf);

        verify(pdfRepository, times(1)).save(mockPdf);
        verifyNoMoreInteractions(pdfRepository);
    }

    @Test
    public void testServiceImplementsInterface() {
        assertTrue(pdfService instanceof PdfService);
    }

    @Test
    public void testServiceAnnotation() {
        assertTrue(PdfServiceImpl.class.isAnnotationPresent(org.springframework.stereotype.Service.class));
    }

    @Test
    public void testClassStructure() {
        assertNotNull(PdfServiceImpl.class);
        assertEquals("PdfServiceImpl", PdfServiceImpl.class.getSimpleName());
        assertEquals("crm.service", PdfServiceImpl.class.getPackage().getName());
    }

    @Test
    public void testImplementsPdfServiceInterface() {
        assertTrue(PdfService.class.isAssignableFrom(PdfServiceImpl.class));
    }

    @Test
    public void testFindByNameWithNullRepository() {
        PdfServiceImpl serviceWithNullRepo = new PdfServiceImpl(null);

        assertThrows(NullPointerException.class, () -> {
            serviceWithNullRepo.findByName("test.pdf");
        });
    }

    @Test
    public void testSavePdfWithNullRepository() {
        PdfServiceImpl serviceWithNullRepo = new PdfServiceImpl(null);
        Pdf mockPdf = createMockPdf("test.pdf");

        assertThrows(NullPointerException.class, () -> {
            serviceWithNullRepo.savePdf(mockPdf);
        });
    }

    @Test
    public void testOverrideAnnotations() throws NoSuchMethodException {
        java.lang.reflect.Method findByNameMethod = PdfServiceImpl.class.getMethod("findByName", String.class);
        assertTrue(findByNameMethod.isAnnotationPresent(Override.class));

        java.lang.reflect.Method savePdfMethod = PdfServiceImpl.class.getMethod("savePdf", Pdf.class);
        assertTrue(savePdfMethod.isAnnotationPresent(Override.class));
    }

    @Test
    public void testRepositoryFieldAccess() {
        Pdf mockPdf = createMockPdf("field-test.pdf");
        when(pdfRepository.findByName("field-test.pdf")).thenReturn(mockPdf);

        pdfService.findByName("field-test.pdf");
        pdfService.savePdf(mockPdf);

        verify(pdfRepository).findByName("field-test.pdf");
        verify(pdfRepository).save(mockPdf);
        verifyNoMoreInteractions(pdfRepository);
    }

    @Test
    public void testServiceBehaviorConsistency() {
        String pdfName = "consistency-test.pdf";
        Pdf mockPdf = createMockPdf(pdfName);
        when(pdfRepository.findByName(pdfName)).thenReturn(mockPdf);

        Pdf result1 = pdfService.findByName(pdfName);
        Pdf result2 = pdfService.findByName(pdfName);

        assertEquals(result1, result2);
        verify(pdfRepository, times(2)).findByName(pdfName);
    }

    private Pdf createMockPdf(String name) {
        Pdf pdf = new Pdf();
        pdf.setName(name);
        pdf.setId(1L);
        pdf.setContent("Mock PDF content");
        return pdf;
    }
}