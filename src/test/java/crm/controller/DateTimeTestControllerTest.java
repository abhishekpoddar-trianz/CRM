package crm.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DateTimeTestControllerTest {

    @Mock
    private Model model;

    private DateTimeTestController controller;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        controller = new DateTimeTestController();
    }

    @Test
    public void testDateTimeTest() {
        String viewName = controller.dateTimeTest(model);

        assertEquals("date/test", viewName);
        verify(model, times(4)).addAttribute(anyString(), any());
    }

    @Test
    public void testDateTimeTestAddsStandardDate() {
        controller.dateTimeTest(model);
        verify(model, times(1)).addAttribute(eq("standardDate"), any());
    }

    @Test
    public void testDateTimeTestAddsLocalDateTime() {
        controller.dateTimeTest(model);
        verify(model, times(1)).addAttribute(eq("localDateTime"), any());
    }

    @Test
    public void testDateTimeTestAddsLocalDate() {
        controller.dateTimeTest(model);
        verify(model, times(1)).addAttribute(eq("localDate"), any());
    }

    @Test
    public void testDateTimeTestAddsTimestamp() {
        controller.dateTimeTest(model);
        verify(model, times(1)).addAttribute(eq("timestamp"), any());
    }

    @Test
    public void testControllerNotNull() {
        assertNotNull(controller);
    }
}
