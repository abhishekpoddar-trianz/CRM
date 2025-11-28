package crm.viewResolver;

import crm.view.CsvView;
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
    void testResolveViewName() throws Exception {
        View view = csvViewResolver.resolveViewName("test", Locale.getDefault());
        assertNotNull(view);
        assertTrue(view instanceof CsvView);
    }

    @Test
    void testResolveViewNameWithNullString() throws Exception {
        View view = csvViewResolver.resolveViewName(null, Locale.getDefault());
        assertNotNull(view);
        assertTrue(view instanceof CsvView);
    }

    @Test
    void testResolveViewNameWithEmptyString() throws Exception {
        View view = csvViewResolver.resolveViewName("", Locale.getDefault());
        assertNotNull(view);
        assertTrue(view instanceof CsvView);
    }

    @Test
    void testResolveViewNameWithDifferentLocales() throws Exception {
        View view1 = csvViewResolver.resolveViewName("test", Locale.US);
        View view2 = csvViewResolver.resolveViewName("test", Locale.UK);
        assertNotNull(view1);
        assertNotNull(view2);
    }
}
