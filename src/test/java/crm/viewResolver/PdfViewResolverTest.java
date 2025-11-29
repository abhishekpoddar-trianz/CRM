package crm.viewResolver;

import crm.view.PdfView;
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
        assertNotNull(pdfViewResolver);
    }

    @Test
    void testResolveViewName() throws Exception {
        View result = pdfViewResolver.resolveViewName("testView", Locale.ENGLISH);

        assertNotNull(result);
        assertTrue(result instanceof PdfView);
    }

    @Test
    void testResolveViewNameWithNullViewName() throws Exception {
        View result = pdfViewResolver.resolveViewName(null, Locale.ENGLISH);

        assertNotNull(result);
        assertTrue(result instanceof PdfView);
    }

    @Test
    void testResolveViewNameWithNullLocale() throws Exception {
        View result = pdfViewResolver.resolveViewName("testView", null);

        assertNotNull(result);
        assertTrue(result instanceof PdfView);
    }

    @Test
    void testResolveViewNameWithDifferentViewNames() throws Exception {
        View result1 = pdfViewResolver.resolveViewName("view1", Locale.ENGLISH);
        View result2 = pdfViewResolver.resolveViewName("view2", Locale.ENGLISH);

        assertNotNull(result1);
        assertNotNull(result2);
        assertTrue(result1 instanceof PdfView);
        assertTrue(result2 instanceof PdfView);
    }

    @Test
    void testResolveViewNameWithDifferentLocales() throws Exception {
        View result1 = pdfViewResolver.resolveViewName("testView", Locale.ENGLISH);
        View result2 = pdfViewResolver.resolveViewName("testView", Locale.FRENCH);

        assertNotNull(result1);
        assertNotNull(result2);
        assertTrue(result1 instanceof PdfView);
        assertTrue(result2 instanceof PdfView);
    }

    @Test
    void testImplementsViewResolver() {
        assertTrue(org.springframework.web.servlet.ViewResolver.class.isAssignableFrom(PdfViewResolver.class));
    }
}