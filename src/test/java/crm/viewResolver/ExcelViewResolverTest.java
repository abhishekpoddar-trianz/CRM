package crm.viewResolver;

import crm.view.ExcelView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.View;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

public class ExcelViewResolverTest {

    private ExcelViewResolver resolver;

    @BeforeEach
    public void setUp() {
        resolver = new ExcelViewResolver();
    }

    @Test
    public void testResolverConstructor() {
        assertNotNull(resolver);
    }

    @Test
    public void testResolveViewName() throws Exception {
        View view = resolver.resolveViewName("testView", Locale.US);
        assertNotNull(view);
        assertTrue(view instanceof ExcelView);
    }

    @Test
    public void testResolveViewNameWithNullViewName() throws Exception {
        View view = resolver.resolveViewName(null, Locale.US);
        assertNotNull(view);
        assertTrue(view instanceof ExcelView);
    }

    @Test
    public void testResolveViewNameWithNullLocale() throws Exception {
        View view = resolver.resolveViewName("testView", null);
        assertNotNull(view);
        assertTrue(view instanceof ExcelView);
    }

    @Test
    public void testResolveViewNameWithEmptyString() throws Exception {
        View view = resolver.resolveViewName("", Locale.ENGLISH);
        assertNotNull(view);
        assertTrue(view instanceof ExcelView);
    }

    @Test
    public void testResolveViewNameWithDifferentLocales() throws Exception {
        View view1 = resolver.resolveViewName("test", Locale.US);
        View view2 = resolver.resolveViewName("test", Locale.FRANCE);
        assertNotNull(view1);
        assertNotNull(view2);
        assertTrue(view1 instanceof ExcelView);
        assertTrue(view2 instanceof ExcelView);
    }

    @Test
    public void testMultipleResolves() throws Exception {
        View view1 = resolver.resolveViewName("view1", Locale.US);
        View view2 = resolver.resolveViewName("view2", Locale.US);
        assertNotNull(view1);
        assertNotNull(view2);
    }
}
