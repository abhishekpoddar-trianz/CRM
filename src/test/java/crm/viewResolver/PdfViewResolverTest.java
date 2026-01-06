package crm.viewResolver;

import crm.view.PdfView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.View;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class PdfViewResolverTest {

    private PdfViewResolver resolver;

    @BeforeEach
    void setUp() {
        resolver = new PdfViewResolver();
    }

    @Test
    void testResolveViewName() throws Exception {
        View view = resolver.resolveViewName("test", Locale.getDefault());

        assertNotNull(view);
        assertTrue(view instanceof PdfView);
    }

    @Test
    void testResolveViewNameWithDifferentLocale() throws Exception {
        View viewUS = resolver.resolveViewName("test", Locale.US);
        View viewJapan = resolver.resolveViewName("test", Locale.JAPAN);

        assertNotNull(viewUS);
        assertNotNull(viewJapan);
        assertTrue(viewUS instanceof PdfView);
        assertTrue(viewJapan instanceof PdfView);
    }

    @Test
    void testResolveViewNameWithNullViewName() throws Exception {
        View view = resolver.resolveViewName(null, Locale.getDefault());

        assertNotNull(view);
        assertTrue(view instanceof PdfView);
    }

    @Test
    void testResolveViewNameWithEmptyViewName() throws Exception {
        View view = resolver.resolveViewName("", Locale.getDefault());

        assertNotNull(view);
        assertTrue(view instanceof PdfView);
    }

    @Test
    void testConstructor() {
        assertNotNull(resolver);
    }
}
