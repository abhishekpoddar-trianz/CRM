package crm;

import crm.viewResolver.CsvViewResolver;
import crm.viewResolver.ExcelViewResolver;
import crm.viewResolver.PdfViewResolver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.nio.charset.Charset;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WebAppConfigTest {

    private WebAppConfig webAppConfig;

    @Mock
    private ViewControllerRegistry mockRegistry;

    @Mock
    private ContentNegotiationConfigurer mockConfigurer;

    @Mock
    private ContentNegotiationManager mockManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        webAppConfig = new WebAppConfig();
    }

    @Test
    void testAddViewControllers() {
        // Act
        webAppConfig.addViewControllers(mockRegistry);

        // Assert
        verify(mockRegistry).addViewController("/login");
        verify(mockRegistry).addViewController("/");
        verify(mockRegistry).addViewController("/user/menu");
        verify(mockRegistry).addViewController("/customer/menu");
        verify(mockRegistry).addViewController("/contract/menu");
        verify(mockRegistry).addViewController("/contract/search");
        verify(mockRegistry).addViewController("/admin");
        verify(mockRegistry).addViewController("/search");
        verify(mockRegistry).addViewController("/403");
        verify(mockRegistry).addViewController("/logout");
        verify(mockRegistry).setOrder(anyInt());
    }

    @Test
    void testConfigureContentNegotiation() {
        // Arrange
        when(mockConfigurer.ignoreAcceptHeader(false)).thenReturn(mockConfigurer);
        when(mockConfigurer.defaultContentType(MediaType.TEXT_HTML)).thenReturn(mockConfigurer);
        when(mockConfigurer.mediaTypes(any(Map.class))).thenReturn(mockConfigurer);

        // Act
        webAppConfig.configureContentNegotiation(mockConfigurer);

        // Assert
        verify(mockConfigurer).ignoreAcceptHeader(false);
        verify(mockConfigurer).defaultContentType(MediaType.TEXT_HTML);
        verify(mockConfigurer).mediaTypes(any(Map.class));
    }

    @Test
    void testContentNegotiatingViewResolver() {
        // Act
        ViewResolver result = webAppConfig.contentNegotiatingViewResolver(mockManager);

        // Assert
        assertNotNull(result);
        assertTrue(result instanceof ContentNegotiatingViewResolver);
    }

    @Test
    void testTemplateResolver() {
        // Act
        ClassLoaderTemplateResolver result = webAppConfig.templateResolver();

        // Assert
        assertNotNull(result);
        assertEquals("templates/", result.getPrefix());
        assertEquals(".html", result.getSuffix());
        assertEquals("HTML", result.getTemplateMode());
        assertEquals("UTF-8", result.getCharacterEncoding());
        assertFalse(result.isCacheable());
    }

    @Test
    void testTemplateEngine() {
        // Act
        SpringTemplateEngine result = webAppConfig.templateEngine();

        // Assert
        assertNotNull(result);
        assertNotNull(result.getTemplateResolver());
    }

    @Test
    void testViewResolver() {
        // Act
        ViewResolver result = webAppConfig.viewResolver();

        // Assert
        assertNotNull(result);
        assertTrue(result instanceof ThymeleafViewResolver);
    }

    @Test
    void testExcelViewResolver() {
        // Act
        ViewResolver result = webAppConfig.excelViewResolver();

        // Assert
        assertNotNull(result);
        assertTrue(result instanceof ExcelViewResolver);
    }

    @Test
    void testCsvViewResolver() {
        // Act
        ViewResolver result = webAppConfig.csvViewResolver();

        // Assert
        assertNotNull(result);
        assertTrue(result instanceof CsvViewResolver);
    }

    @Test
    void testPdfViewResolver() {
        // Act
        ViewResolver result = webAppConfig.pdfViewResolver();

        // Assert
        assertNotNull(result);
        assertTrue(result instanceof PdfViewResolver);
    }

    @Test
    void testTemplateResolverConfiguration() {
        // Act
        ClassLoaderTemplateResolver resolver = webAppConfig.templateResolver();

        // Assert
        assertEquals("templates/", resolver.getPrefix());
        assertEquals(".html", resolver.getSuffix());
        assertEquals("HTML", resolver.getTemplateMode());
        assertEquals("UTF-8", resolver.getCharacterEncoding());
        assertFalse(resolver.isCacheable());
    }

    @Test
    void testTemplateEngineWithDialects() {
        // Act
        SpringTemplateEngine engine = webAppConfig.templateEngine();

        // Assert
        assertNotNull(engine);
        assertFalse(engine.getDialects().isEmpty());
    }

    @Test
    void testViewResolverCharacterEncoding() {
        // Act
        ViewResolver resolver = webAppConfig.viewResolver();

        // Assert
        assertTrue(resolver instanceof ThymeleafViewResolver);
        ThymeleafViewResolver thymeleafResolver = (ThymeleafViewResolver) resolver;
        assertEquals("UTF-8", thymeleafResolver.getCharacterEncoding());
    }

    @Test
    void testContentNegotiatingViewResolverWithAllResolvers() {
        // Act
        ViewResolver resolver = webAppConfig.contentNegotiatingViewResolver(mockManager);

        // Assert
        assertNotNull(resolver);
        assertTrue(resolver instanceof ContentNegotiatingViewResolver);
        ContentNegotiatingViewResolver cnvr = (ContentNegotiatingViewResolver) resolver;
        assertNotNull(cnvr.getContentNegotiationManager());
    }
}