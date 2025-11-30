package crm.view;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AbstractPdfViewTest {

    private TestAbstractPdfView abstractPdfView;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        abstractPdfView = new TestAbstractPdfView();
    }

    @Test
    void testConstructorSetsContentType() {
        TestAbstractPdfView view = new TestAbstractPdfView();
        assertEquals("application/pdf", view.getContentType());
    }

    @Test
    void testGeneratesDownloadContent() {
        assertTrue(abstractPdfView.generatesDownloadContent());
    }

    @Test
    void testGetViewerPreferences() {
        int preferences = abstractPdfView.getViewerPreferences();
        assertEquals(PdfWriter.ALLOW_PRINTING | PdfWriter.PageLayoutSinglePage, preferences);
    }

    @Test
    void testPrepareWriter() throws Exception {
        Map<String, Object> model = new HashMap<>();
        PdfWriter writer = mock(PdfWriter.class);

        abstractPdfView.prepareWriter(model, writer, request);

        verify(writer).setViewerPreferences(PdfWriter.ALLOW_PRINTING | PdfWriter.PageLayoutSinglePage);
    }

    @Test
    void testBuildPdfMetadata() {
        Map<String, Object> model = new HashMap<>();
        Document document = new Document();

        assertDoesNotThrow(() -> {
            abstractPdfView.buildPdfMetadata(model, document, request);
        });
    }

    @Test
    void testRenderMergedOutputModelCreatesDocument() throws Exception {
        Map<String, Object> model = new HashMap<>();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        when(response.getOutputStream()).thenReturn(mock(javax.servlet.ServletOutputStream.class));

        assertDoesNotThrow(() -> {
            abstractPdfView.renderMergedOutputModel(model, request, response);
        });
    }

    private static class TestAbstractPdfView extends AbstractPdfView {
        @Override
        protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
                                        HttpServletRequest request, HttpServletResponse response) throws Exception {
        }
    }
}
