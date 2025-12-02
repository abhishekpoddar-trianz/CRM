package crm.viewResolver;

import crm.view.PdfView;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.web.servlet.View;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Locale;

public class PdfViewResolverTest {

    private PdfViewResolver resolver;

    @BeforeEach
    public void setUp() {
        resolver = new PdfViewResolver();
    }

    @Test
    public void testResolveViewNameReturnsPdfView() throws Exception {
        View view = resolver.resolveViewName("test", Locale.ENGLISH);
        assertNotNull(view);
        assertTrue(view instanceof PdfView);
    }

    @Test
    public void testResolveViewNameWithNullName() throws Exception {
        View view = resolver.resolveViewName(null, Locale.ENGLISH);
        assertNotNull(view);
        assertTrue(view instanceof PdfView);
    }

    @Test
    public void testResolveViewNameWithEmptyName() throws Exception {
        View view = resolver.resolveViewName("", Locale.ENGLISH);
        assertNotNull(view);
        assertTrue(view instanceof PdfView);
    }

    @Test
    public void testResolveViewNameWithDifferentLocales() throws Exception {
        View viewEnglish = resolver.resolveViewName("test", Locale.ENGLISH);
        View viewJapanese = resolver.resolveViewName("test", Locale.JAPANESE);
        assertNotNull(viewEnglish);
        assertNotNull(viewJapanese);
        assertTrue(viewEnglish instanceof PdfView);
        assertTrue(viewJapanese instanceof PdfView);
    }

    @Test
    public void testResolveViewNameWithNullLocale() throws Exception {
        View view = resolver.resolveViewName("test", null);
        assertNotNull(view);
        assertTrue(view instanceof PdfView);
    }
}
