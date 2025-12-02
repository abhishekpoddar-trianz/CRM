package crm;

import crm.viewResolver.CsvViewResolver;
import crm.viewResolver.ExcelViewResolver;
import crm.viewResolver.PdfViewResolver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.ViewResolver;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import static org.junit.jupiter.api.Assertions.*;

public class WebAppConfigTest {

    private WebAppConfig webAppConfig;

    @BeforeEach
    public void setUp() {
        webAppConfig = new WebAppConfig();
    }

    @Test
    public void testTemplateResolver() {
        ClassLoaderTemplateResolver resolver = webAppConfig.templateResolver();
        assertNotNull(resolver);
    }

    @Test
    public void testTemplateResolverPrefix() {
        ClassLoaderTemplateResolver resolver = webAppConfig.templateResolver();
        assertEquals("templates/", resolver.getPrefix());
    }

    @Test
    public void testTemplateResolverSuffix() {
        ClassLoaderTemplateResolver resolver = webAppConfig.templateResolver();
        assertEquals(".html", resolver.getSuffix());
    }

    @Test
    public void testTemplateEngine() {
        TemplateEngine engine = webAppConfig.templateEngine(webAppConfig.templateResolver());
        assertNotNull(engine);
    }

    @Test
    public void testViewResolver() {
        ViewResolver resolver = webAppConfig.viewResolver();
        assertNotNull(resolver);
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
    public void testWebAppConfigNotNull() {
        assertNotNull(webAppConfig);
    }

    @Test
    public void testTemplateResolverCharacterEncoding() {
        ClassLoaderTemplateResolver resolver = webAppConfig.templateResolver();
        assertEquals("UTF-8", resolver.getCharacterEncoding());
    }

    @Test
    public void testTemplateResolverTemplateMode() {
        ClassLoaderTemplateResolver resolver = webAppConfig.templateResolver();
        assertEquals("HTML", resolver.getTemplateMode().toString());
    }
}
