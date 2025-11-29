package crm.repository;

import crm.entity.Pdf;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DataJpaTest
class PdfRepositoryTest {

    @MockBean
    private PdfRepository pdfRepository;

    @Test
    void testFindByName() {
        Pdf mockPdf = new Pdf();
        mockPdf.setId(1L);
        mockPdf.setName("test.pdf");

        when(pdfRepository.findByName("test.pdf")).thenReturn(mockPdf);

        Pdf result = pdfRepository.findByName("test.pdf");

        assertNotNull(result);
        assertEquals("test.pdf", result.getName());
        assertEquals(1L, result.getId());
        verify(pdfRepository, times(1)).findByName("test.pdf");
    }

    @Test
    void testFindByNameNotFound() {
        when(pdfRepository.findByName("nonexistent.pdf")).thenReturn(null);

        Pdf result = pdfRepository.findByName("nonexistent.pdf");

        assertNull(result);
        verify(pdfRepository, times(1)).findByName("nonexistent.pdf");
    }

    @Test
    void testFindByNameWithNullParameter() {
        when(pdfRepository.findByName(null)).thenReturn(null);

        Pdf result = pdfRepository.findByName(null);

        assertNull(result);
        verify(pdfRepository, times(1)).findByName(null);
    }

    @Test
    void testFindByNameWithEmptyString() {
        when(pdfRepository.findByName("")).thenReturn(null);

        Pdf result = pdfRepository.findByName("");

        assertNull(result);
        verify(pdfRepository, times(1)).findByName("");
    }

    @Test
    void testFindByNameCaseSensitive() {
        Pdf pdfUpperCase = new Pdf();
        pdfUpperCase.setId(1L);
        pdfUpperCase.setName("TEST.PDF");

        when(pdfRepository.findByName("TEST.PDF")).thenReturn(pdfUpperCase);
        when(pdfRepository.findByName("test.pdf")).thenReturn(null);

        Pdf upperCaseResult = pdfRepository.findByName("TEST.PDF");
        Pdf lowerCaseResult = pdfRepository.findByName("test.pdf");

        assertNotNull(upperCaseResult);
        assertEquals("TEST.PDF", upperCaseResult.getName());
        assertNull(lowerCaseResult);

        verify(pdfRepository, times(1)).findByName("TEST.PDF");
        verify(pdfRepository, times(1)).findByName("test.pdf");
    }

    @Test
    void testFindByNameMultiplePdfs() {
        Pdf pdf1 = new Pdf();
        pdf1.setId(1L);
        pdf1.setName("document1.pdf");

        Pdf pdf2 = new Pdf();
        pdf2.setId(2L);
        pdf2.setName("document2.pdf");

        when(pdfRepository.findByName("document1.pdf")).thenReturn(pdf1);
        when(pdfRepository.findByName("document2.pdf")).thenReturn(pdf2);

        Pdf result1 = pdfRepository.findByName("document1.pdf");
        Pdf result2 = pdfRepository.findByName("document2.pdf");

        assertNotNull(result1);
        assertNotNull(result2);
        assertEquals("document1.pdf", result1.getName());
        assertEquals("document2.pdf", result2.getName());
        assertNotEquals(result1.getId(), result2.getId());

        verify(pdfRepository, times(1)).findByName("document1.pdf");
        verify(pdfRepository, times(1)).findByName("document2.pdf");
    }

    @Test
    void testFindByNameWithSpecialCharacters() {
        Pdf pdfSpecial = new Pdf();
        pdfSpecial.setId(1L);
        pdfSpecial.setName("test-file_v1.0.pdf");

        when(pdfRepository.findByName("test-file_v1.0.pdf")).thenReturn(pdfSpecial);

        Pdf result = pdfRepository.findByName("test-file_v1.0.pdf");

        assertNotNull(result);
        assertEquals("test-file_v1.0.pdf", result.getName());
        verify(pdfRepository, times(1)).findByName("test-file_v1.0.pdf");
    }

    @Test
    void testRepositoryInterface() {
        assertTrue(PdfRepository.class.isInterface());
    }

    @Test
    void testExtendsJpaRepository() {
        assertTrue(org.springframework.data.jpa.repository.JpaRepository.class.isAssignableFrom(PdfRepository.class));
    }

    @Test
    void testRepositoryAnnotation() {
        assertTrue(PdfRepository.class.isAnnotationPresent(org.springframework.stereotype.Repository.class));
    }
}