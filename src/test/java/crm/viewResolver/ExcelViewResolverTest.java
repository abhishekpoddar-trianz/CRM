package crm.viewResolver;

import crm.view.ExcelView;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.web.servlet.View;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Locale;

public class ExcelViewResolverTest {

    private ExcelViewResolver resolver;

    @BeforeEach
    public void setUp() {
        resolver = new ExcelViewResolver();
    }

    @Test
    public void testResolveViewNameReturnsExcelView() throws Exception {
        View view = resolver.resolveViewName("test", Locale.ENGLISH);
        assertNotNull(view);
        assertTrue(view instanceof ExcelView);
    }

    @Test
    public void testResolveViewNameWithNullName() throws Exception {
        View view = resolver.resolveViewName(null, Locale.ENGLISH);
        assertNotNull(view);
        assertTrue(view instanceof ExcelView);
    }

    @Test
    public void testResolveViewNameWithEmptyName() throws Exception {
        View view = resolver.resolveViewName("", Locale.ENGLISH);
        assertNotNull(view);
        assertTrue(view instanceof ExcelView);
    }

    @Test
    public void testResolveViewNameWithDifferentLocales() throws Exception {
        View viewEnglish = resolver.resolveViewName("test", Locale.ENGLISH);
        View viewFrench = resolver.resolveViewName("test", Locale.FRENCH);
        assertNotNull(viewEnglish);
        assertNotNull(viewFrench);
        assertTrue(viewEnglish instanceof ExcelView);
        assertTrue(viewFrench instanceof ExcelView);
    }

    @Test
    public void testResolveViewNameWithNullLocale() throws Exception {
        View view = resolver.resolveViewName("test", null);
        assertNotNull(view);
        assertTrue(view instanceof ExcelView);
    }
}
