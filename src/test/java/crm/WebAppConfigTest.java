package crm;

import crm.viewResolver.CsvViewResolver;
import crm.viewResolver.ExcelViewResolver;
import crm.viewResolver.PdfViewResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistration;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.extras.springsecurity6.dialect.SpringSecurityDialect;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import java.nio.charset.Charset;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class WebAppConfigTest {

    @InjectMocks
    private WebAppConfig webAppConfig;

    @Mock
    private ViewControllerRegistry viewControllerRegistry;

    @Mock
    private ViewControllerRegistration viewControllerRegistration;

    @Mock
    private ContentNegotiationConfigurer contentNegotiationConfigurer;

    @Mock
    private ContentNegotiationManager contentNegotiationManager;

    @Mock
    private ITemplateResolver mockTemplateResolver;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(viewControllerRegistry.addViewController(anyString())).thenReturn(viewControllerRegistration);
        when(viewControllerRegistration.setViewName(anyString())).thenReturn(viewControllerRegistration);
    }

    @Test
    void testAddViewControllers() {
        // Act
        webAppConfig.addViewControllers(viewControllerRegistry);

        // Assert
        verify(viewControllerRegistry).addViewController("/login");
        verify(viewControllerRegistry).addViewController("/");
        verify(viewControllerRegistry).addViewController("/user/menu");
        verify(viewControllerRegistry).addViewController("/customer/menu");
        verify(viewControllerRegistry).addViewController("/contract/menu");
        verify(viewControllerRegistry).addViewController("/contract/search");
        verify(viewControllerRegistry).addViewController("/admin");
        verify(viewControllerRegistry).addViewController("/search");
        verify(viewControllerRegistry).addViewController("/403");
        verify(viewControllerRegistry).addViewController("/logout");
        verify(viewControllerRegistry).setOrder(any(Integer.class));
    }

    @Test
    void testConfigureContentNegotiation() {
        // Act
        webAppConfig.configureContentNegotiation(contentNegotiationConfigurer);

        // Assert
        verify(contentNegotiationConfigurer).ignoreAcceptHeader(false);
        verify(contentNegotiationConfigurer).defaultContentType(MediaType.APPLICATION_JSON);
        verify(contentNegotiationConfigurer).mediaTypes(any());
    }

    @Test
    void testContentNegotiatingViewResolver() {
        // Act
        ViewResolver resolver = webAppConfig.contentNegotiatingViewResolver(contentNegotiationManager);

        // Assert
        assertNotNull(resolver);
        assertTrue(resolver instanceof ContentNegotiatingViewResolver);
    }

    @Test
    void testTemplateResolver() {
        // Act
        ClassLoaderTemplateResolver resolver = webAppConfig.templateResolver();

        // Assert
        assertNotNull(resolver);
        assertEquals("templates/", resolver.getPrefix());
        assertEquals(".html", resolver.getSuffix());
        assertEquals("HTML", resolver.getTemplateMode());
        assertEquals("UTF-8", resolver.getCharacterEncoding());
        assertFalse(resolver.isCacheable());
    }

    @Test
    void testTemplateEngine() {
        // Act
        SpringTemplateEngine engine = webAppConfig.templateEngine();

        // Assert
        assertNotNull(engine);
        assertTrue(engine.getDialects().stream().anyMatch(d -> d instanceof SpringSecurityDialect));
    }

    @Test
    void testTemplateEngineWithResolver() {
        // Act
        TemplateEngine engine = webAppConfig.templateEngine(mockTemplateResolver);

        // Assert
        assertNotNull(engine);
        assertTrue(engine instanceof SpringTemplateEngine);
    }

    @Test
    void testViewResolver() {
        // Act
        ViewResolver resolver = webAppConfig.viewResolver();

        // Assert
        assertNotNull(resolver);
        assertTrue(resolver instanceof ThymeleafViewResolver);
    }

    @Test
    void testExcelViewResolver() {
        // Act
        ViewResolver resolver = webAppConfig.excelViewResolver();

        // Assert
        assertNotNull(resolver);
        assertTrue(resolver instanceof ExcelViewResolver);
    }

    @Test
    void testCsvViewResolver() {
        // Act
        ViewResolver resolver = webAppConfig.csvViewResolver();

        // Assert
        assertNotNull(resolver);
        assertTrue(resolver instanceof CsvViewResolver);
    }

    @Test
    void testPdfViewResolver() {
        // Act
        ViewResolver resolver = webAppConfig.pdfViewResolver();

        // Assert
        assertNotNull(resolver);
        assertTrue(resolver instanceof PdfViewResolver);
    }

    @Test
    void testSpringDataDialect() {
        // Act
        var dialect = webAppConfig.springDataDialect();

        // Assert
        assertNotNull(dialect);
    }

    @Test
    void testViewControllerPaths() {
        // Arrange
        String[] expectedPaths = {
            "/login", "/", "/user/menu", "/customer/menu", "/contract/menu",
            "/contract/search", "/admin", "/search", "/403", "/logout"
        };

        // Act
        webAppConfig.addViewControllers(viewControllerRegistry);

        // Assert
        for (String path : expectedPaths) {
            verify(viewControllerRegistry).addViewController(path);
        }
    }

    @Test
    void testContentNegotiationMediaTypes() {
        // Arrange
        when(contentNegotiationConfigurer.ignoreAcceptHeader(false)).thenReturn(contentNegotiationConfigurer);
        when(contentNegotiationConfigurer.defaultContentType(any())).thenReturn(contentNegotiationConfigurer);

        // Act
        webAppConfig.configureContentNegotiation(contentNegotiationConfigurer);

        // Assert
        verify(contentNegotiationConfigurer).mediaTypes(argThat(map -> {
            var mediaTypes = (java.util.Map<String, MediaType>) map;
            return mediaTypes.containsKey("html") &&
                   mediaTypes.containsKey("json") &&
                   mediaTypes.containsKey("xls") &&
                   mediaTypes.containsKey("pdf") &&
                   mediaTypes.containsKey("csv");
        }));
    }

    @Test
    void testTemplateEngineConfiguration() {
        // Act
        SpringTemplateEngine engine = webAppConfig.templateEngine();

        // Assert
        assertNotNull(engine.getTemplateResolver());
        assertTrue(engine.getDialects().size() > 0);
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
    void testContentNegotiatingViewResolverConfiguration() {
        // Act
        ViewResolver resolver = webAppConfig.contentNegotiatingViewResolver(contentNegotiationManager);
        ContentNegotiatingViewResolver cnvr = (ContentNegotiatingViewResolver) resolver;

        // Assert
        assertNotNull(cnvr);
        assertEquals(contentNegotiationManager, cnvr.getContentNegotiationManager());
    }
}