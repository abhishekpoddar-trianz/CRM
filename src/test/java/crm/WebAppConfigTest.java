package crm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ViewResolver;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import static org.junit.jupiter.api.Assertions.*;

class WebAppConfigTest {

    private WebAppConfig webAppConfig;

    @BeforeEach
    void setUp() {
        webAppConfig = new WebAppConfig();
    }

    @Test
    void testTemplateResolver() {
        ClassLoaderTemplateResolver templateResolver = webAppConfig.templateResolver();
        assertNotNull(templateResolver);
        assertEquals("templates/", templateResolver.getPrefix());
        assertEquals(".html", templateResolver.getSuffix());
        assertEquals("HTML", templateResolver.getTemplateMode());
        assertEquals("UTF-8", templateResolver.getCharacterEncoding());
    }

    @Test
    void testTemplateEngine() {
        SpringTemplateEngine templateEngine = webAppConfig.templateEngine();
        assertNotNull(templateEngine);
    }

    @Test
    void testViewResolver() {
        ViewResolver viewResolver = webAppConfig.viewResolver();
        assertNotNull(viewResolver);
    }

    @Test
    void testExcelViewResolver() {
        ViewResolver excelViewResolver = webAppConfig.excelViewResolver();
        assertNotNull(excelViewResolver);
    }

    @Test
    void testCsvViewResolver() {
        ViewResolver csvViewResolver = webAppConfig.csvViewResolver();
        assertNotNull(csvViewResolver);
    }

    @Test
    void testPdfViewResolver() {
        ViewResolver pdfViewResolver = webAppConfig.pdfViewResolver();
        assertNotNull(pdfViewResolver);
    }
}
