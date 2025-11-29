package crm.controller;

import com.itextpdf.text.DocumentException;
import crm.entity.Pdf;
import crm.service.PdfService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class PdfControllerTest {

    @Mock
    private PdfService pdfService;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    private PdfController pdfController;

    @TempDir
    Path tempDir;

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
    void testGeneratePdf_WithErrors() {
        Pdf pdf = new Pdf();
        when(bindingResult.hasErrors()).thenReturn(true);

        String result = pdfController.generatePdf(pdf, bindingResult);

        assertEquals("redirect:/pdf-generator", result);
        verify(pdfService, never()).savePdf(any(Pdf.class));
    }

    @Test
    void testGeneratePdf_WithoutErrors_Success() {
        Pdf pdf = new Pdf();
        pdf.setName("test");
        pdf.setContent("Test content");
        when(bindingResult.hasErrors()).thenReturn(false);

        String result = pdfController.generatePdf(pdf, bindingResult);

        assertEquals("pdf/success", result);
        verify(pdfService).savePdf(pdf);
    }

    @Test
    void testGeneratePdf_WithFileExtension() {
        Pdf pdf = new Pdf();
        pdf.setName("test.pdf");
        pdf.setContent("Test content");
        when(bindingResult.hasErrors()).thenReturn(false);

        String result = pdfController.generatePdf(pdf, bindingResult);

        assertEquals("pdf/success", result);
        verify(pdfService).savePdf(pdf);
    }

    @Test
    void testGeneratePdf_WithoutFileExtension() {
        Pdf pdf = new Pdf();
        pdf.setName("testfile");
        pdf.setContent("Test content");
        when(bindingResult.hasErrors()).thenReturn(false);

        String result = pdfController.generatePdf(pdf, bindingResult);

        assertEquals("pdf/success", result);
        verify(pdfService).savePdf(pdf);
    }

    @Test
    void testGeneratePdf_NullName() {
        Pdf pdf = new Pdf();
        pdf.setName(null);
        pdf.setContent("Test content");
        when(bindingResult.hasErrors()).thenReturn(false);

        String result = pdfController.generatePdf(pdf, bindingResult);

        assertEquals("pdf/success", result);
        verify(pdfService).savePdf(pdf);
    }

    @Test
    void testGeneratePdf_EmptyName() {
        Pdf pdf = new Pdf();
        pdf.setName("");
        pdf.setContent("Test content");
        when(bindingResult.hasErrors()).thenReturn(false);

        String result = pdfController.generatePdf(pdf, bindingResult);

        assertEquals("pdf/success", result);
        verify(pdfService).savePdf(pdf);
    }

    @Test
    void testGeneratePdf_NullContent() {
        Pdf pdf = new Pdf();
        pdf.setName("test");
        pdf.setContent(null);
        when(bindingResult.hasErrors()).thenReturn(false);

        String result = pdfController.generatePdf(pdf, bindingResult);

        assertEquals("pdf/success", result);
        verify(pdfService).savePdf(pdf);
    }

    @Test
    void testGeneratePdf_EmptyContent() {
        Pdf pdf = new Pdf();
        pdf.setName("test");
        pdf.setContent("");
        when(bindingResult.hasErrors()).thenReturn(false);

        String result = pdfController.generatePdf(pdf, bindingResult);

        assertEquals("pdf/success", result);
        verify(pdfService).savePdf(pdf);
    }

    @Test
    void testPdfGenerator_ModelInteraction() {
        pdfController.pdfGenerator(model);

        verify(model, times(1)).addAttribute(anyString(), any());
        verify(model).addAttribute(eq("pdf"), any(Pdf.class));
    }

    @Test
    void testGeneratePdf_BindingResultChecked() {
        Pdf pdf = new Pdf();
        pdfController.generatePdf(pdf, bindingResult);

        verify(bindingResult, times(1)).hasErrors();
    }

    @Test
    void testPdfController_NoExceptions() {
        assertDoesNotThrow(() -> {
            pdfController.pdfGenerator(model);
        });

        Pdf pdf = new Pdf();
        pdf.setName("test");
        pdf.setContent("content");
        when(bindingResult.hasErrors()).thenReturn(false);

        assertDoesNotThrow(() -> {
            pdfController.generatePdf(pdf, bindingResult);
        });
    }
}