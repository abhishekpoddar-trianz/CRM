package crm.viewResolver;

import crm.view.ExcelView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.View;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class ExcelViewResolverTest {

    private ExcelViewResolver excelViewResolver;

    @BeforeEach
    void setUp() {
        excelViewResolver = new ExcelViewResolver();
    }

    @Test
    void testConstructor() {
        assertNotNull(excelViewResolver);
    }

    @Test
    void testResolveViewName() throws Exception {
        View result = excelViewResolver.resolveViewName("testView", Locale.ENGLISH);

        assertNotNull(result);
        assertTrue(result instanceof ExcelView);
    }

    @Test
    void testResolveViewNameWithNullViewName() throws Exception {
        View result = excelViewResolver.resolveViewName(null, Locale.ENGLISH);

        assertNotNull(result);
        assertTrue(result instanceof ExcelView);
    }

    @Test
    void testResolveViewNameWithNullLocale() throws Exception {
        View result = excelViewResolver.resolveViewName("testView", null);

        assertNotNull(result);
        assertTrue(result instanceof ExcelView);
    }

    @Test
    void testImplementsViewResolver() {
        assertTrue(org.springframework.web.servlet.ViewResolver.class.isAssignableFrom(ExcelViewResolver.class));
    }
}