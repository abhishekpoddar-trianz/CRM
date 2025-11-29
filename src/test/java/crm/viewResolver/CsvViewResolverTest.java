package crm.viewResolver;

import crm.view.CsvView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.View;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

public class CsvViewResolverTest {

    private CsvViewResolver viewResolver;

    @BeforeEach
    public void setUp() {
        viewResolver = new CsvViewResolver();
    }

    @Test
    public void testResolveViewName() throws Exception {
        View view = viewResolver.resolveViewName("testView", Locale.US);

        assertNotNull(view);
        assertTrue(view instanceof CsvView);
    }

    @Test
    public void testResolveViewNameWithNullViewName() throws Exception {
        View view = viewResolver.resolveViewName(null, Locale.US);

        assertNotNull(view);
        assertTrue(view instanceof CsvView);
    }

    @Test
    public void testResolveViewNameWithDifferentLocale() throws Exception {
        View view = viewResolver.resolveViewName("testView", Locale.GERMAN);

        assertNotNull(view);
        assertTrue(view instanceof CsvView);
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
