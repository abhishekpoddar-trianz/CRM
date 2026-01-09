package crm.controller;

import crm.entity.Pdf;
import crm.service.PdfService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PdfControllerTest {

    private PdfController pdfController;

    @Mock
    private PdfService pdfService;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        pdfController = new PdfController(pdfService);
    }

    @Test
    void testConstructor() {
        assertNotNull(pdfController);
    }

    @Test
    void testPdfGenerator() {
        String result = pdfController.pdfGenerator(model);

        assertEquals("pdf/generator", result);
        verify(model).addAttribute(eq("pdf"), any(Pdf.class));
    }

    @Test
    void testGeneratePdfWithBindingErrors() {
        Pdf pdf = Pdf.builder().name("test").content("content").build();
        when(bindingResult.hasErrors()).thenReturn(true);

        String result = pdfController.generatePdf(pdf, bindingResult);

        assertEquals("redirect:/pdf-generator", result);
        verify(bindingResult).hasErrors();
        verify(pdfService, never()).savePdf(any());
    }

    @Test
    void testGeneratePdfSuccess() throws Exception {
        Pdf pdf = Pdf.builder().name("test.pdf").content("Test content").build();
        when(bindingResult.hasErrors()).thenReturn(false);

        String result = pdfController.generatePdf(pdf, bindingResult);

        assertEquals("pdf/success", result);
        verify(bindingResult).hasErrors();
        verify(pdfService).savePdf(pdf);
    }

    @Test
    void testGeneratePdfWithoutPdfExtension() throws Exception {
        Pdf pdf = Pdf.builder().name("test").content("Content without extension").build();
        when(bindingResult.hasErrors()).thenReturn(false);

        String result = pdfController.generatePdf(pdf, bindingResult);

        assertEquals("pdf/success", result);
        verify(pdfService).savePdf(pdf);
    }

    @Test
    void testPdfGeneratorAddsNewPdfToModel() {
        pdfController.pdfGenerator(model);
        verify(model, times(1)).addAttribute(eq("pdf"), any(Pdf.class));
    }
}
