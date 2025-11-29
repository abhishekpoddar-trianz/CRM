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
    void testConstructor() {
        assertNotNull(csvViewResolver);
    }

    @Test
    void testResolveViewName() throws Exception {
        View result = csvViewResolver.resolveViewName("testView", Locale.ENGLISH);

        assertNotNull(result);
        assertTrue(result instanceof CsvView);
    }

    @Test
    void testResolveViewNameWithNullViewName() throws Exception {
        View result = csvViewResolver.resolveViewName(null, Locale.ENGLISH);

        assertNotNull(result);
        assertTrue(result instanceof CsvView);
    }

    @Test
    void testResolveViewNameWithNullLocale() throws Exception {
        View result = csvViewResolver.resolveViewName("testView", null);

        assertNotNull(result);
        assertTrue(result instanceof CsvView);
    }

    @Test
    void testImplementsViewResolver() {
        assertTrue(org.springframework.web.servlet.ViewResolver.class.isAssignableFrom(CsvViewResolver.class));
    }
}