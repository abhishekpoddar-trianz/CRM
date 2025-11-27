package crm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class WebAppConfigTest {

    private WebAppConfig webAppConfig;

    @BeforeEach
    public void setUp() {
        webAppConfig = new WebAppConfig();
    }

    @Test
    public void testWebAppConfigConstructor() {
        assertNotNull(webAppConfig);
    }

    @Test
    public void testAddViewControllers() {
        ViewControllerRegistry registry = mock(ViewControllerRegistry.class);
        assertDoesNotThrow(() -> {
            webAppConfig.addViewControllers(registry);
        });
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
    public void testExcelViewResolver() {
        assertNotNull(webAppConfig.excelViewResolver());
    }

    @Test
    public void testCsvViewResolver() {
        assertNotNull(webAppConfig.csvViewResolver());
    }

    @Test
    public void testPdfViewResolver() {
        assertNotNull(webAppConfig.pdfViewResolver());
    }
}
