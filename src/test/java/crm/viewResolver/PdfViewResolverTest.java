package crm.viewResolver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.View;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class PdfViewResolverTest {

    private PdfViewResolver pdfViewResolver;

    @BeforeEach
    void setUp() {
        pdfViewResolver = new PdfViewResolver();
    }

    @Test
    void testConstructor() {
        PdfViewResolver resolver = new PdfViewResolver();
        assertNotNull(resolver);
    }

    @Test
    void testResolveViewName() throws Exception {
        View view = pdfViewResolver.resolveViewName("testView", Locale.US);

        assertNotNull(view);
    }

    @Test
    void testResolveViewNameWithDifferentLocale() throws Exception {
        View view1 = pdfViewResolver.resolveViewName("view1", Locale.US);
        View view2 = pdfViewResolver.resolveViewName("view2", Locale.UK);

        assertNotNull(view1);
        assertNotNull(view2);
    }

    @Test
    void testResolveViewNameWithNullViewName() throws Exception {
        View view = pdfViewResolver.resolveViewName(null, Locale.US);

        assertNotNull(view);
    }

    @Test
    void testResolveViewNameWithEmptyViewName() throws Exception {
        View view = pdfViewResolver.resolveViewName("", Locale.US);

        assertNotNull(view);
    }

    @Test
    void testResolveViewNameReturnsPdfView() throws Exception {
        View view = pdfViewResolver.resolveViewName("testView", Locale.getDefault());

        assertNotNull(view);
        assertEquals("crm.view.PdfView", view.getClass().getName());
    }
}
