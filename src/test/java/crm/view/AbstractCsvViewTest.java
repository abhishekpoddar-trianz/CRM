package crm.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AbstractCsvViewTest {

    private TestAbstractCsvView abstractCsvView;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        abstractCsvView = new TestAbstractCsvView();
    }

    @Test
    void testConstructorSetsContentType() {
        assertEquals("text/csv", abstractCsvView.getContentType());
    }

    @Test
    void testSetUrl() {
        String testUrl = "test/url";
        abstractCsvView.setUrl(testUrl);
        assertNotNull(abstractCsvView);
    }

    @Test
    void testGeneratesDownloadContent() {
        assertTrue(abstractCsvView.generatesDownloadContent());
    }

    @Test
    void testRenderMergedOutputModel() throws Exception {
        Map<String, Object> model = new HashMap<>();
        when(response.getContentType()).thenReturn("text/csv");

        assertDoesNotThrow(() -> abstractCsvView.renderMergedOutputModel(model, request, response));
        verify(response).setContentType("text/csv");
    }

    private static class TestAbstractCsvView extends AbstractCsvView {
        @Override
        protected void buildCsvDocument(Map<String, Object> model, HttpServletRequest request,
                                        HttpServletResponse response) throws Exception {
        }
    }
}
