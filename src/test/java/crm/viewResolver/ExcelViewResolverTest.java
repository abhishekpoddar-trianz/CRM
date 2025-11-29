package crm.viewResolver;

import crm.view.ExcelView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.View;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

public class ExcelViewResolverTest {

    private ExcelViewResolver viewResolver;

    @BeforeEach
    public void setUp() {
        viewResolver = new ExcelViewResolver();
    }

    @Test
    public void testResolveViewName() throws Exception {
        View view = viewResolver.resolveViewName("testView", Locale.US);

        assertNotNull(view);
        assertTrue(view instanceof ExcelView);
    }

    @Test
    public void testResolveViewNameWithNullViewName() throws Exception {
        View view = viewResolver.resolveViewName(null, Locale.US);

        assertNotNull(view);
        assertTrue(view instanceof ExcelView);
    }

    @Test
    public void testResolveViewNameWithDifferentLocale() throws Exception {
        View view = viewResolver.resolveViewName("testView", Locale.FRENCH);

        assertNotNull(view);
        assertTrue(view instanceof ExcelView);
    }

    @Test
    public void testResolveViewNameReturnsNewInstance() throws Exception {
        View view1 = viewResolver.resolveViewName("view1", Locale.US);
        View view2 = viewResolver.resolveViewName("view2", Locale.US);

        assertNotNull(view1);
        assertNotNull(view2);
        assertNotSame(view1, view2);
    }
}
