package crm.view;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import static org.junit.jupiter.api.Assertions.*;
import java.util.HashMap;
import java.util.Map;

public class AbstractCsvViewTest {

    private TestAbstractCsvView csvView;

    private static class TestAbstractCsvView extends AbstractCsvView {
        @Override
        protected void buildCsvDocument(Map<String, Object> model,
                                        jakarta.servlet.http.HttpServletRequest request,
                                        jakarta.servlet.http.HttpServletResponse response) throws Exception {
            response.getWriter().write("test,csv");
        }
    }

    @BeforeEach
    public void setUp() {
        csvView = new TestAbstractCsvView();
    }

    @Test
    public void testContentTypeIsCsv() {
        assertEquals("text/csv", csvView.getContentType());
    }

    @Test
    public void testSetAndGetUrl() {
        csvView.setUrl("test.csv");
        assertDoesNotThrow(() -> csvView.setUrl("test.csv"));
    }

    @Test
    public void testGeneratesDownloadContent() {
        assertTrue(csvView.generatesDownloadContent());
    }

    @Test
    public void testRenderMergedOutputModel() throws Exception {
        Map<String, Object> model = new HashMap<>();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        assertDoesNotThrow(() -> {
            csvView.renderMergedOutputModel(model, request, response);
        });
    }

    @Test
    public void testSetUrlNull() {
        assertDoesNotThrow(() -> csvView.setUrl(null));
    }

    @Test
    public void testSetUrlEmpty() {
        assertDoesNotThrow(() -> csvView.setUrl(""));
    }
}
