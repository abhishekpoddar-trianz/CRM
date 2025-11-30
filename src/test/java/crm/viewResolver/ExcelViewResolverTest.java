package crm.viewResolver;

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
        ExcelViewResolver resolver = new ExcelViewResolver();
        assertNotNull(resolver);
    }

    @Test
    void testResolveViewName() throws Exception {
        View view = excelViewResolver.resolveViewName("testView", Locale.US);

        assertNotNull(view);
    }

    @Test
    void testResolveViewNameWithDifferentLocale() throws Exception {
        View view1 = excelViewResolver.resolveViewName("view1", Locale.US);
        View view2 = excelViewResolver.resolveViewName("view2", Locale.UK);

        assertNotNull(view1);
        assertNotNull(view2);
    }

    @Test
    void testResolveViewNameWithNullViewName() throws Exception {
        View view = excelViewResolver.resolveViewName(null, Locale.US);

        assertNotNull(view);
    }

    @Test
    void testResolveViewNameWithEmptyViewName() throws Exception {
        View view = excelViewResolver.resolveViewName("", Locale.US);

        assertNotNull(view);
    }

    @Test
    void testResolveViewNameReturnsExcelView() throws Exception {
        View view = excelViewResolver.resolveViewName("testView", Locale.getDefault());

        assertNotNull(view);
        assertEquals("crm.view.ExcelView", view.getClass().getName());
    }
}
