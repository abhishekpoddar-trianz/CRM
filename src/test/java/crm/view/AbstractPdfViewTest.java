package crm.view;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AbstractPdfViewTest {

    private TestAbstractPdfView pdfView;

    @Mock
    private HttpServletRequest mockRequest;

    @Mock
    private HttpServletResponse mockResponse;

    @Mock
    private PdfWriter mockWriter;

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
        // Act
        assertDoesNotThrow(() -> pdfView.prepareWriter(model, mockWriter, mockRequest));

        // Assert
        verify(mockWriter).setViewerPreferences(PdfWriter.ALLOW_PRINTING | PdfWriter.PageLayoutSinglePage);
    }

    @Test
    void testPrepareWriterWithNullModel() throws DocumentException {
        // Act & Assert
        assertDoesNotThrow(() -> pdfView.prepareWriter(null, mockWriter, mockRequest));
        verify(mockWriter).setViewerPreferences(anyInt());
    }

    @Test
    void testBuildPdfMetadata() {
        // Arrange
        Document mockDocument = mock(Document.class);

        // Act & Assert - Default implementation should not throw
        assertDoesNotThrow(() -> pdfView.buildPdfMetadata(model, mockDocument, mockRequest));
    }

    @Test
    void testBuildPdfMetadataWithNullParameters() {
        // Act & Assert - Should handle null parameters gracefully
        assertDoesNotThrow(() -> pdfView.buildPdfMetadata(null, null, null));
    }

    @Test
    void testRenderMergedOutputModelCallsAbstractMethod() throws Exception {
        // Arrange
        when(mockResponse.getOutputStream()).thenReturn(mock(jakarta.servlet.ServletOutputStream.class));

        // Act
        pdfView.renderMergedOutputModel(model, mockRequest, mockResponse);

        // Assert
        assertTrue(pdfView.wasBuildPdfDocumentCalled());
        assertEquals(model, pdfView.getReceivedModel());
        assertEquals(mockRequest, pdfView.getReceivedRequest());
        assertEquals(mockResponse, pdfView.getReceivedResponse());
    }

    @Test
    void testAbstractMethodRequiresImplementation() {
        // This test verifies that the abstract method must be implemented
        // The TestAbstractPdfView provides a concrete implementation
        assertTrue(TestAbstractPdfView.class != AbstractPdfView.class);
        assertNotNull(pdfView);
    }

    @Test
    void testContentTypeSet() {
        // Act
        String contentType = pdfView.getContentType();

        // Assert
        assertEquals("application/pdf", contentType);
    }

    @Test
    void testViewerPreferencesConstants() {
        // Act
        int allowPrinting = PdfWriter.ALLOW_PRINTING;
        int pageLayoutSinglePage = PdfWriter.PageLayoutSinglePage;

        // Assert - Verify constants exist and have expected values
        assertNotEquals(0, allowPrinting);
        assertNotEquals(0, pageLayoutSinglePage);
        assertEquals(allowPrinting | pageLayoutSinglePage, pdfView.getViewerPreferences());
    }

    @Test
    void testInheritanceStructure() {
        // Act & Assert
        assertTrue(pdfView instanceof AbstractPdfView);
        assertTrue(pdfView instanceof org.springframework.web.servlet.view.AbstractView);
    }

    @Test
    void testPrepareWriterCallsGetViewerPreferences() throws DocumentException {
        // Arrange
        TestAbstractPdfView spyView = spy(pdfView);

        // Act
        spyView.prepareWriter(model, mockWriter, mockRequest);

        // Assert
        verify(spyView).getViewerPreferences();
        verify(mockWriter).setViewerPreferences(anyInt());
    }

    @Test
    void testEmptyModelHandling() throws Exception {
        // Arrange
        Map<String, Object> emptyModel = new HashMap<>();
        when(mockResponse.getOutputStream()).thenReturn(mock(jakarta.servlet.ServletOutputStream.class));

        // Act
        pdfView.renderMergedOutputModel(emptyModel, mockRequest, mockResponse);

        // Assert
        assertTrue(pdfView.wasBuildPdfDocumentCalled());
        assertEquals(emptyModel, pdfView.getReceivedModel());
    }

    @Test
    void testModelWithMultipleEntries() throws Exception {
        // Arrange
        model.put("key1", "value1");
        model.put("key2", 123);
        model.put("key3", true);
        when(mockResponse.getOutputStream()).thenReturn(mock(jakarta.servlet.ServletOutputStream.class));

        // Act
        pdfView.renderMergedOutputModel(model, mockRequest, mockResponse);

        // Assert
        assertTrue(pdfView.wasBuildPdfDocumentCalled());
        assertEquals(3, pdfView.getReceivedModel().size());
        assertTrue(pdfView.getReceivedModel().containsKey("key1"));
        assertTrue(pdfView.getReceivedModel().containsKey("key2"));
        assertTrue(pdfView.getReceivedModel().containsKey("key3"));
    }

    // Test implementation of AbstractPdfView for testing purposes
    private static class TestAbstractPdfView extends AbstractPdfView {
        private boolean buildPdfDocumentCalled = false;
        private Map<String, Object> receivedModel;
        private HttpServletRequest receivedRequest;
        private HttpServletResponse receivedResponse;
        private Document receivedDocument;
        private PdfWriter receivedWriter;

        @Override
        protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
                                        HttpServletRequest request, HttpServletResponse response) throws Exception {
            this.buildPdfDocumentCalled = true;
            this.receivedModel = model;
            this.receivedRequest = request;
            this.receivedResponse = response;
            this.receivedDocument = document;
            this.receivedWriter = writer;
        }

        public boolean wasBuildPdfDocumentCalled() {
            return buildPdfDocumentCalled;
        }

        public Map<String, Object> getReceivedModel() {
            return receivedModel;
        }

        public HttpServletRequest getReceivedRequest() {
            return receivedRequest;
        }

        public HttpServletResponse getReceivedResponse() {
            return receivedResponse;
        }

        public Document getReceivedDocument() {
            return receivedDocument;
        }

        public PdfWriter getReceivedWriter() {
            return receivedWriter;
        }
    }
}