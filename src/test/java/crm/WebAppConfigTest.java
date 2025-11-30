package crm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WebAppConfigTest {

    private WebAppConfig webAppConfig;

    @BeforeEach
    void setUp() {
        webAppConfig = new WebAppConfig();
    }

    @Test
    void testConstructor() {
        WebAppConfig config = new WebAppConfig();
        assertNotNull(config);
    }

    @Test
    void testAddViewControllers() {
        ViewControllerRegistry registry = mock(ViewControllerRegistry.class);

        webAppConfig.addViewControllers(registry);

        verify(registry, atLeastOnce()).addViewController(anyString());
    }

    @Test
    void testConfigureContentNegotiation() {
        ContentNegotiationConfigurer configurer = mock(ContentNegotiationConfigurer.class);
        when(configurer.ignoreAcceptHeader(anyBoolean())).thenReturn(configurer);
        when(configurer.defaultContentType(any())).thenReturn(configurer);
        when(configurer.mediaTypes(any())).thenReturn(configurer);

        webAppConfig.configureContentNegotiation(configurer);

        verify(configurer).ignoreAcceptHeader(false);
    }

    @Test
    void testTemplateResolver() {
        ClassLoaderTemplateResolver templateResolver = webAppConfig.templateResolver();

        assertNotNull(templateResolver);
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
