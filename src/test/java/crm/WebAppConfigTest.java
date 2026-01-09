package crm;

import crm.viewResolver.CsvViewResolver;
import crm.viewResolver.ExcelViewResolver;
import crm.viewResolver.PdfViewResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WebAppConfigTest {

    private WebAppConfig webAppConfig;

    @Mock
    private ViewControllerRegistry registry;

    @Mock
    private ContentNegotiationManager contentNegotiationManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        webAppConfig = new WebAppConfig();
    }

    @Test
    void testConstructor() {
        assertNotNull(webAppConfig);
    }

    @Test
    void testAddViewControllers() {
        webAppConfig.addViewControllers(registry);

        verify(registry).addViewController("/login");
        verify(registry).addViewController("/");
        verify(registry).addViewController("/user/menu");
        verify(registry).addViewController("/customer/menu");
        verify(registry).addViewController("/contract/menu");
        verify(registry).addViewController("/contract/search");
        verify(registry).addViewController("/admin");
        verify(registry).addViewController("/search");
        verify(registry).addViewController("/403");
        verify(registry).addViewController("/logout");
        verify(registry).setOrder(anyInt());
    }

    @Test
    void testTemplateResolver() {
        ClassLoaderTemplateResolver resolver = webAppConfig.templateResolver();

        assertNotNull(resolver);
        assertEquals("templates/", resolver.getPrefix());
        assertEquals(".html", resolver.getSuffix());
        assertEquals("UTF-8", resolver.getCharacterEncoding());
    }

    @Test
    void testTemplateEngine() {
        SpringTemplateEngine engine = webAppConfig.templateEngine();

        assertNotNull(engine);
    }

    @Test
    void testViewResolver() {
        ViewResolver resolver = webAppConfig.viewResolver();

        assertNotNull(resolver);
        assertTrue(resolver instanceof ThymeleafViewResolver);
    }

    @Test
    void testExcelViewResolver() {
        ViewResolver resolver = webAppConfig.excelViewResolver();

        assertNotNull(resolver);
        assertTrue(resolver instanceof ExcelViewResolver);
    }

    @Test
    void testCsvViewResolver() {
        ViewResolver resolver = webAppConfig.csvViewResolver();

        assertNotNull(resolver);
        assertTrue(resolver instanceof CsvViewResolver);
    }

    @Test
    void testPdfViewResolver() {
        ViewResolver resolver = webAppConfig.pdfViewResolver();

        assertNotNull(resolver);
        assertTrue(resolver instanceof PdfViewResolver);
    }

    @Test
    void testContentNegotiatingViewResolver() {
        ViewResolver resolver = webAppConfig.contentNegotiatingViewResolver(contentNegotiationManager);

        assertNotNull(resolver);
        assertTrue(resolver instanceof ContentNegotiatingViewResolver);
    }

    @Test
    void testTemplateResolverProperties() {
        ClassLoaderTemplateResolver resolver = webAppConfig.templateResolver();

        assertFalse(resolver.isCacheable());
        assertEquals("HTML", resolver.getTemplateMode());
    }
}
