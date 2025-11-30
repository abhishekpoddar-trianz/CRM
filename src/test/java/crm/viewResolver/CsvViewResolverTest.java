package crm.viewResolver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.View;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class CsvViewResolverTest {

    private CsvViewResolver csvViewResolver;

    @BeforeEach
    void setUp() {
        csvViewResolver = new CsvViewResolver();
    }

    @Test
    void testConstructor() {
        CsvViewResolver resolver = new CsvViewResolver();
        assertNotNull(resolver);
    }

    @Test
    void testResolveViewName() throws Exception {
        View view = csvViewResolver.resolveViewName("testView", Locale.US);

        assertNotNull(view);
    }

    @Test
    void testResolveViewNameWithDifferentLocale() throws Exception {
        View view1 = csvViewResolver.resolveViewName("view1", Locale.US);
        View view2 = csvViewResolver.resolveViewName("view2", Locale.UK);

        assertNotNull(view1);
        assertNotNull(view2);
    }

    @Test
    void testResolveViewNameWithNullViewName() throws Exception {
        View view = csvViewResolver.resolveViewName(null, Locale.US);

        assertNotNull(view);
    }

    @Test
    void testResolveViewNameWithEmptyViewName() throws Exception {
        View view = csvViewResolver.resolveViewName("", Locale.US);

        assertNotNull(view);
    }

    @Test
    void testResolveViewNameReturnsCsvView() throws Exception {
        View view = csvViewResolver.resolveViewName("testView", Locale.getDefault());

        assertNotNull(view);
        assertEquals("crm.view.CsvView", view.getClass().getName());
    }
}
