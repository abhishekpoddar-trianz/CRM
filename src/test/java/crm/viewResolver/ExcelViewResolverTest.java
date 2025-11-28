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
    void testResolveViewName() throws Exception {
        View view = excelViewResolver.resolveViewName("test", Locale.getDefault());
        assertNotNull(view);
        assertTrue(view instanceof ExcelView);
    }

    @Test
    void testResolveViewNameWithNullString() throws Exception {
        View view = excelViewResolver.resolveViewName(null, Locale.getDefault());
        assertNotNull(view);
        assertTrue(view instanceof ExcelView);
    }

    @Test
    void testResolveViewNameWithEmptyString() throws Exception {
        View view = excelViewResolver.resolveViewName("", Locale.getDefault());
        assertNotNull(view);
        assertTrue(view instanceof ExcelView);
    }

    @Test
    void testResolveViewNameWithDifferentLocales() throws Exception {
        View view1 = excelViewResolver.resolveViewName("test", Locale.US);
        View view2 = excelViewResolver.resolveViewName("test", Locale.UK);
        assertNotNull(view1);
        assertNotNull(view2);
    }
}
