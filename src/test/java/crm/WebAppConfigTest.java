package crm;

import crm.viewResolver.CsvViewResolver;
import crm.viewResolver.ExcelViewResolver;
import crm.viewResolver.PdfViewResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class WebAppConfigTest {

    private WebAppConfig webAppConfig;

    @Mock
    private ViewControllerRegistry viewControllerRegistry;

    @Mock
    private ContentNegotiationConfigurer contentNegotiationConfigurer;

    @Mock
    private ContentNegotiationManager contentNegotiationManager;

    @Mock
    private ITemplateResolver templateResolver;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        webAppConfig = new WebAppConfig();
    }

    @Test
    void addViewControllers_shouldRegisterAllViewControllers() {
        // Arrange
        when(viewControllerRegistry.addViewController(anyString())).thenReturn(mock(org.springframework.web.servlet.config.annotation.ViewControllerRegistration.class));

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
    void configureContentNegotiation_shouldConfigureMediaTypes() {
        // Arrange
        when(contentNegotiationConfigurer.ignoreAcceptHeader(anyBoolean())).thenReturn(contentNegotiationConfigurer);
        when(contentNegotiationConfigurer.defaultContentType(any(MediaType.class))).thenReturn(contentNegotiationConfigurer);
        when(contentNegotiationConfigurer.mediaTypes(any())).thenReturn(contentNegotiationConfigurer);

        // Act
        webAppConfig.configureContentNegotiation(contentNegotiationConfigurer);

        // Assert
        verify(contentNegotiationConfigurer).ignoreAcceptHeader(false);
        verify(contentNegotiationConfigurer).defaultContentType(MediaType.APPLICATION_JSON);
        verify(contentNegotiationConfigurer).mediaTypes(any());
    }

    @Test
    void contentNegotiatingViewResolver_shouldReturnConfiguredResolver() {
        // Act
        ViewResolver resolver = webAppConfig.contentNegotiatingViewResolver(contentNegotiationManager);

        // Assert
        assertNotNull(resolver, "ViewResolver should not be null");
        assertTrue(resolver instanceof ContentNegotiatingViewResolver, "Should be ContentNegotiatingViewResolver");

        ContentNegotiatingViewResolver cnvr = (ContentNegotiatingViewResolver) resolver;
        assertNotNull(cnvr.getContentNegotiationManager(), "ContentNegotiationManager should be set");
    }

    @Test
    void templateResolver_shouldReturnConfiguredTemplateResolver() {
        // Act
        ClassLoaderTemplateResolver resolver = webAppConfig.templateResolver();

        // Assert
        assertNotNull(resolver, "TemplateResolver should not be null");
        assertEquals("templates/", resolver.getPrefix());
        assertEquals(".html", resolver.getSuffix());
        assertEquals("HTML", resolver.getTemplateMode().toString());
        assertEquals("UTF-8", resolver.getCharacterEncoding());
        assertFalse(resolver.isCacheable());
    }

    @Test
    void templateEngine_shouldReturnConfiguredEngine() {
        // Act
        SpringTemplateEngine engine = webAppConfig.templateEngine();

        // Assert
        assertNotNull(engine, "TemplateEngine should not be null");
        assertNotNull(engine.getTemplateResolvers(), "Template resolvers should be set");
    }

    @Test
    void templateEngineWithResolver_shouldReturnConfiguredEngine() {
        // Act
        TemplateEngine engine = webAppConfig.templateEngine(templateResolver);

        // Assert
        assertNotNull(engine, "TemplateEngine should not be null");
        assertTrue(engine instanceof SpringTemplateEngine, "Should be SpringTemplateEngine");
    }

    @Test
    void viewResolver_shouldReturnConfiguredViewResolver() {
        // Act
        ViewResolver resolver = webAppConfig.viewResolver();

        // Assert
        assertNotNull(resolver, "ViewResolver should not be null");
        assertTrue(resolver instanceof ThymeleafViewResolver, "Should be ThymeleafViewResolver");

        ThymeleafViewResolver tvr = (ThymeleafViewResolver) resolver;
        assertEquals("UTF-8", tvr.getCharacterEncoding());
    }

    @Test
    void excelViewResolver_shouldReturnExcelViewResolver() {
        // Act
        ViewResolver resolver = webAppConfig.excelViewResolver();

        // Assert
        assertNotNull(resolver, "ViewResolver should not be null");
        assertTrue(resolver instanceof ExcelViewResolver, "Should be ExcelViewResolver");
    }

    @Test
    void csvViewResolver_shouldReturnCsvViewResolver() {
        // Act
        ViewResolver resolver = webAppConfig.csvViewResolver();

        // Assert
        assertNotNull(resolver, "ViewResolver should not be null");
        assertTrue(resolver instanceof CsvViewResolver, "Should be CsvViewResolver");
    }

    @Test
    void pdfViewResolver_shouldReturnPdfViewResolver() {
        // Act
        ViewResolver resolver = webAppConfig.pdfViewResolver();

        // Assert
        assertNotNull(resolver, "ViewResolver should not be null");
        assertTrue(resolver instanceof PdfViewResolver, "Should be PdfViewResolver");
    }

    @Test
    void webAppConfig_shouldBeAnnotatedWithConfiguration() {
        // Assert
        assertTrue(WebAppConfig.class.isAnnotationPresent(org.springframework.context.annotation.Configuration.class));
    }

    @Test
    void webAppConfig_shouldImplementWebMvcConfigurer() {
        // Assert
        assertTrue(org.springframework.web.servlet.config.annotation.WebMvcConfigurer.class.isAssignableFrom(WebAppConfig.class));
    }

    @Test
    void contentNegotiatingViewResolver_shouldIncludeAllViewResolvers() {
        // Act
        ViewResolver resolver = webAppConfig.contentNegotiatingViewResolver(contentNegotiationManager);
        ContentNegotiatingViewResolver cnvr = (ContentNegotiatingViewResolver) resolver;

        // Assert
        assertNotNull(cnvr.getViewResolvers(), "View resolvers should not be null");
        assertEquals(4, cnvr.getViewResolvers().size(), "Should have 4 view resolvers");
    }

    @Test
    void templateEngine_methodsShouldHaveBeanAnnotation() throws NoSuchMethodException {
        // Test that bean methods are properly annotated
        assertTrue(WebAppConfig.class.getMethod("templateEngine").isAnnotationPresent(org.springframework.context.annotation.Bean.class));
        assertTrue(WebAppConfig.class.getMethod("templateResolver").isAnnotationPresent(org.springframework.context.annotation.Bean.class));
        assertTrue(WebAppConfig.class.getMethod("viewResolver").isAnnotationPresent(org.springframework.context.annotation.Bean.class));
        assertTrue(WebAppConfig.class.getMethod("excelViewResolver").isAnnotationPresent(org.springframework.context.annotation.Bean.class));
        assertTrue(WebAppConfig.class.getMethod("csvViewResolver").isAnnotationPresent(org.springframework.context.annotation.Bean.class));
        assertTrue(WebAppConfig.class.getMethod("pdfViewResolver").isAnnotationPresent(org.springframework.context.annotation.Bean.class));
    }
}