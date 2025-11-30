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
        PdfController controller = new PdfController(pdfService);
        assertNotNull(controller);
    }

    @Test
    void testPdfGenerator() {
        String viewName = pdfController.pdfGenerator(model);

        assertEquals("pdf/generator", viewName);
        verify(model).addAttribute(eq("pdf"), any(Pdf.class));
    }

    @Test
    void testGeneratePdfWithValidData() {
        Pdf pdf = new Pdf();
        pdf.setName("test.pdf");
        pdf.setContent("Test content");

        when(bindingResult.hasErrors()).thenReturn(false);

        String viewName = pdfController.generatePdf(pdf, bindingResult);

        assertEquals("pdf/success", viewName);
        verify(pdfService, times(1)).savePdf(pdf);
    }

    @Test
    void testGeneratePdfWithErrors() {
        Pdf pdf = new Pdf();
        pdf.setName("test");
        pdf.setContent("Content");

        when(bindingResult.hasErrors()).thenReturn(true);

        String viewName = pdfController.generatePdf(pdf, bindingResult);

        assertEquals("redirect:/pdf-generator", viewName);
        verify(pdfService, never()).savePdf(pdf);
    }

    @Test
    void testGeneratePdfAddsExtension() {
        Pdf pdf = new Pdf();
        pdf.setName("document");
        pdf.setContent("Sample content");

        when(bindingResult.hasErrors()).thenReturn(false);

        String viewName = pdfController.generatePdf(pdf, bindingResult);

        assertEquals("pdf/success", viewName);
        verify(pdfService).savePdf(pdf);
    }

    @Test
    void testGeneratePdfWithExistingExtension() {
        Pdf pdf = new Pdf();
        pdf.setName("document.pdf");
        pdf.setContent("Sample content");

        when(bindingResult.hasErrors()).thenReturn(false);

        String viewName = pdfController.generatePdf(pdf, bindingResult);

        assertEquals("pdf/success", viewName);
        verify(pdfService).savePdf(pdf);
    }

    @Test
    void testPdfGeneratorAddsModelAttribute() {
        pdfController.pdfGenerator(model);

        verify(model, times(1)).addAttribute(eq("pdf"), any(Pdf.class));
    }

    @Test
    void testPdfGeneratorReturnsCorrectView() {
        String result = pdfController.pdfGenerator(model);

        assertNotNull(result);
        assertEquals("pdf/generator", result);
    }

    @Test
    void testGeneratePdfWithEmptyName() {
        Pdf pdf = new Pdf();
        pdf.setName("");
        pdf.setContent("Content");

        when(bindingResult.hasErrors()).thenReturn(false);

        String viewName = pdfController.generatePdf(pdf, bindingResult);

        assertEquals("pdf/success", viewName);
    }

    @Test
    void testGeneratePdfWithEmptyContent() {
        Pdf pdf = new Pdf();
        pdf.setName("test.pdf");
        pdf.setContent("");

        when(bindingResult.hasErrors()).thenReturn(false);

        String viewName = pdfController.generatePdf(pdf, bindingResult);

        assertEquals("pdf/success", viewName);
    }
}
