package crm.viewResolver;

import crm.view.ExcelView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.View;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class ExcelViewResolverTest {

    private ExcelViewResolver resolver;

    @BeforeEach
    void setUp() {
        resolver = new ExcelViewResolver();
    }

    @Test
    void testResolveViewName() throws Exception {
        View view = resolver.resolveViewName("test", Locale.getDefault());

        assertNotNull(view);
        assertTrue(view instanceof ExcelView);
    }

    @Test
    void testResolveViewNameWithDifferentLocale() throws Exception {
        View viewUS = resolver.resolveViewName("test", Locale.US);
        View viewUK = resolver.resolveViewName("test", Locale.UK);

        assertNotNull(viewUS);
        assertNotNull(viewUK);
        assertTrue(viewUS instanceof ExcelView);
        assertTrue(viewUK instanceof ExcelView);
    }

    @Test
    void testResolveViewNameWithNullViewName() throws Exception {
        View view = resolver.resolveViewName(null, Locale.getDefault());

        assertNotNull(view);
        assertTrue(view instanceof ExcelView);
    }

    @Test
    void testResolveViewNameWithEmptyViewName() throws Exception {
        View view = resolver.resolveViewName("", Locale.getDefault());

        assertNotNull(view);
        assertTrue(view instanceof ExcelView);
    }

    @Test
    void testConstructor() {
        assertNotNull(resolver);
    }
}
