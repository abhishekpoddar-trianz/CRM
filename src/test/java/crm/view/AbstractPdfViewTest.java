package crm.view;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AbstractPdfViewTest {

    private TestAbstractPdfView pdfView;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    private Map<String, Object> model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        pdfView = new TestAbstractPdfView();
        model = new HashMap<>();
        model.put("testKey", "testValue");
    }

    @Test
    void testConstructor() {
        // Act
        TestAbstractPdfView view = new TestAbstractPdfView();

        // Assert
        assertEquals("application/pdf", view.getContentType());
    }

    @Test
    void testGeneratesDownloadContent() {
        // Act
        boolean result = pdfView.generatesDownloadContent();

        // Assert
        assertTrue(result);
    }

    @Test
    void testGetViewerPreferences() {
        // Act
        int preferences = pdfView.getViewerPreferences();

        // Assert
        assertEquals(PdfWriter.ALLOW_PRINTING | PdfWriter.PageLayoutSinglePage, preferences);
    }

    @Test
    void testPrepareWriter() throws DocumentException {
        // Arrange
        PdfWriter mockWriter = mock(PdfWriter.class);

        // Act
        pdfView.prepareWriter(model, mockWriter, request);

        // Assert
        verify(mockWriter).setViewerPreferences(PdfWriter.ALLOW_PRINTING | PdfWriter.PageLayoutSinglePage);
    }

    @Test
    void testBuildPdfMetadata() {
        // Arrange
        Document mockDocument = mock(Document.class);

        // Act & Assert - Should not throw exception
        assertDoesNotThrow(() -> pdfView.buildPdfMetadata(model, mockDocument, request));
    }

    @Test
    void testRenderMergedOutputModelCallsAbstractMethod() throws Exception {
        // Arrange
        when(response.getOutputStream()).thenReturn(mock(jakarta.servlet.ServletOutputStream.class));

        // Act
        pdfView.renderMergedOutputModel(model, request, response);

        // Assert
        assertTrue(pdfView.buildPdfDocumentCalled);
        assertEquals(model, pdfView.capturedModel);
        assertNotNull(pdfView.capturedDocument);
        assertNotNull(pdfView.capturedWriter);
        assertEquals(request, pdfView.capturedRequest);
        assertEquals(response, pdfView.capturedResponse);
    }

    @Test
    void testRenderMergedOutputModelHandlesException() throws Exception {
        // Arrange
        TestAbstractPdfView failingView = new TestAbstractPdfView() {
            @Override
            protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
                                          HttpServletRequest request, HttpServletResponse response) throws Exception {
                throw new RuntimeException("Test exception");
            }
        };

        // Act & Assert
        assertThrows(RuntimeException.class, () ->
            failingView.renderMergedOutputModel(model, request, response));
    }

    @Test
    void testRenderMergedOutputModelWithNullModel() throws Exception {
        // Arrange
        when(response.getOutputStream()).thenReturn(mock(jakarta.servlet.ServletOutputStream.class));

        // Act
        pdfView.renderMergedOutputModel(null, request, response);

        // Assert
        assertTrue(pdfView.buildPdfDocumentCalled);
        assertNull(pdfView.capturedModel);
    }

    @Test
    void testRenderMergedOutputModelWithEmptyModel() throws Exception {
        // Arrange
        Map<String, Object> emptyModel = new HashMap<>();
        when(response.getOutputStream()).thenReturn(mock(jakarta.servlet.ServletOutputStream.class));

        // Act
        pdfView.renderMergedOutputModel(emptyModel, request, response);

        // Assert
        assertTrue(pdfView.buildPdfDocumentCalled);
        assertEquals(emptyModel, pdfView.capturedModel);
        assertTrue(pdfView.capturedModel.isEmpty());
    }

    @Test
    void testPrepareWriterWithNullModel() throws DocumentException {
        // Arrange
        PdfWriter mockWriter = mock(PdfWriter.class);

        // Act
        pdfView.prepareWriter(null, mockWriter, request);

        // Assert
        verify(mockWriter).setViewerPreferences(anyInt());
    }

    @Test
    void testBuildPdfMetadataWithNullModel() {
        // Arrange
        Document mockDocument = mock(Document.class);

        // Act & Assert
        assertDoesNotThrow(() -> pdfView.buildPdfMetadata(null, mockDocument, request));
    }

    @Test
    void testGetViewerPreferencesIsConstant() {
        // Act
        int preferences1 = pdfView.getViewerPreferences();
        int preferences2 = pdfView.getViewerPreferences();

        // Assert
        assertEquals(preferences1, preferences2);
    }

    @Test
    void testInheritsFromAbstractView() {
        // Act & Assert
        assertTrue(pdfView instanceof org.springframework.web.servlet.view.AbstractView);
    }

    @Test
    void testContentTypeSetInConstructor() {
        // Act
        String contentType = pdfView.getContentType();

        // Assert
        assertEquals("application/pdf", contentType);
    }

    // Test implementation of AbstractPdfView for testing purposes
    private static class TestAbstractPdfView extends AbstractPdfView {

        boolean buildPdfDocumentCalled = false;
        Map<String, Object> capturedModel;
        Document capturedDocument;
        PdfWriter capturedWriter;
        HttpServletRequest capturedRequest;
        HttpServletResponse capturedResponse;

        @Override
        protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
                                      HttpServletRequest request, HttpServletResponse response) throws Exception {
            buildPdfDocumentCalled = true;
            capturedModel = model;
            capturedDocument = document;
            capturedWriter = writer;
            capturedRequest = request;
            capturedResponse = response;

            // Add some basic content to avoid empty document
            document.add(new com.itextpdf.text.Paragraph("Test PDF Content"));
        }
    }

    @Test
    void testCustomViewerPreferences() {
        // Arrange
        AbstractPdfView customView = new TestAbstractPdfView() {
            @Override
            protected int getViewerPreferences() {
                return PdfWriter.ALLOW_COPY | PdfWriter.ALLOW_PRINTING;
            }
        };

        // Act
        int preferences = customView.getViewerPreferences();

        // Assert
        assertEquals(PdfWriter.ALLOW_COPY | PdfWriter.ALLOW_PRINTING, preferences);
    }

    @Test
    void testCustomBuildPdfMetadata() {
        // Arrange
        Document mockDocument = mock(Document.class);
        AbstractPdfView customView = new TestAbstractPdfView() {
            @Override
            protected void buildPdfMetadata(Map<String, Object> model, Document document, HttpServletRequest request) {
                document.addTitle("Test Title");
                document.addAuthor("Test Author");
            }
        };

        // Act & Assert
        assertDoesNotThrow(() -> customView.buildPdfMetadata(model, mockDocument, request));
        verify(mockDocument).addTitle("Test Title");
        verify(mockDocument).addAuthor("Test Author");
    }

    @Test
    void testPrepareWriterWithDocumentException() throws DocumentException {
        // Arrange
        PdfWriter mockWriter = mock(PdfWriter.class);
        doThrow(new DocumentException("Test exception")).when(mockWriter).setViewerPreferences(anyInt());

        // Act & Assert
        assertThrows(DocumentException.class, () -> pdfView.prepareWriter(model, mockWriter, request));
    }
}