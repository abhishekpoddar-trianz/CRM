package crm.viewResolver;

import crm.view.CsvView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.View;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class CsvViewResolverTest {

    private CsvViewResolver resolver;

    @BeforeEach
    void setUp() {
        resolver = new CsvViewResolver();
    }

    @Test
    void testResolveViewName() throws Exception {
        View view = resolver.resolveViewName("test", Locale.getDefault());

        assertNotNull(view);
        assertTrue(view instanceof CsvView);
    }

    @Test
    void testResolveViewNameWithDifferentLocale() throws Exception {
        View viewUS = resolver.resolveViewName("test", Locale.US);
        View viewFrance = resolver.resolveViewName("test", Locale.FRANCE);

        assertNotNull(viewUS);
        assertNotNull(viewFrance);
        assertTrue(viewUS instanceof CsvView);
        assertTrue(viewFrance instanceof CsvView);
    }

    @Test
    void testResolveViewNameWithNullViewName() throws Exception {
        View view = resolver.resolveViewName(null, Locale.getDefault());

        assertNotNull(view);
        assertTrue(view instanceof CsvView);
    }

    @Test
    void testResolveViewNameWithEmptyViewName() throws Exception {
        View view = resolver.resolveViewName("", Locale.getDefault());

        assertNotNull(view);
        assertTrue(view instanceof CsvView);
    }

    @Test
    void testConstructor() {
        assertNotNull(resolver);
    }
}
