package crm;

import crm.viewResolver.CsvViewResolver;
import crm.viewResolver.ExcelViewResolver;
import crm.viewResolver.PdfViewResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ViewResolver;

import static org.junit.jupiter.api.Assertions.*;

public class WebAppConfigTest {

    private WebAppConfig webAppConfig;

    @BeforeEach
    public void setUp() {
        webAppConfig = new WebAppConfig();
    }

    @Test
    public void testExcelViewResolver() {
        ViewResolver resolver = webAppConfig.excelViewResolver();

        assertNotNull(resolver);
        assertTrue(resolver instanceof ExcelViewResolver);
    }

    @Test
    public void testCsvViewResolver() {
        ViewResolver resolver = webAppConfig.csvViewResolver();

        assertNotNull(resolver);
        assertTrue(resolver instanceof CsvViewResolver);
    }

    @Test
    public void testPdfViewResolver() {
        ViewResolver resolver = webAppConfig.pdfViewResolver();

        assertNotNull(resolver);
        assertTrue(resolver instanceof PdfViewResolver);
    }

    @Test
    public void testTemplateResolver() {
        assertNotNull(webAppConfig.templateResolver());
    }

    @Test
    public void testTemplateEngine() {
        assertNotNull(webAppConfig.templateEngine());
    }

    @Test
    public void testViewResolver() {
        assertNotNull(webAppConfig.viewResolver());
    }

    @Test
    public void testSpringDataDialect() {
        assertNotNull(webAppConfig.springDataDialect());
    }
}
