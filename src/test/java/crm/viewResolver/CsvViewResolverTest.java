package crm.viewResolver;

import crm.view.CsvView;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.web.servlet.View;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Locale;

public class CsvViewResolverTest {

    private CsvViewResolver resolver;

    @BeforeEach
    public void setUp() {
        resolver = new CsvViewResolver();
    }

    @Test
    public void testResolveViewNameReturnsCsvView() throws Exception {
        View view = resolver.resolveViewName("test", Locale.ENGLISH);
        assertNotNull(view);
        assertTrue(view instanceof CsvView);
    }

    @Test
    public void testResolveViewNameWithNullName() throws Exception {
        View view = resolver.resolveViewName(null, Locale.ENGLISH);
        assertNotNull(view);
        assertTrue(view instanceof CsvView);
    }

    @Test
    public void testResolveViewNameWithEmptyName() throws Exception {
        View view = resolver.resolveViewName("", Locale.ENGLISH);
        assertNotNull(view);
        assertTrue(view instanceof CsvView);
    }

    @Test
    public void testResolveViewNameWithDifferentLocales() throws Exception {
        View viewEnglish = resolver.resolveViewName("test", Locale.ENGLISH);
        View viewGerman = resolver.resolveViewName("test", Locale.GERMAN);
        assertNotNull(viewEnglish);
        assertNotNull(viewGerman);
        assertTrue(viewEnglish instanceof CsvView);
        assertTrue(viewGerman instanceof CsvView);
    }

    @Test
    public void testResolveViewNameWithNullLocale() throws Exception {
        View view = resolver.resolveViewName("test", null);
        assertNotNull(view);
        assertTrue(view instanceof CsvView);
    }
}
