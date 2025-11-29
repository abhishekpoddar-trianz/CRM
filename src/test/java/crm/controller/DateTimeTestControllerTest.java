package crm.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DateTimeTestControllerTest {

    private DateTimeTestController controller;
    private Model model;

    @BeforeEach
    public void setUp() {
        controller = new DateTimeTestController();
        model = mock(Model.class);
    }

    @Test
    public void testDateTimeTest() {
        String viewName = controller.dateTimeTest(model);

        assertEquals("date/test", viewName);
        verify(model, times(1)).addAttribute(eq("standardDate"), any());
        verify(model, times(1)).addAttribute(eq("localDateTime"), any());
        verify(model, times(1)).addAttribute(eq("localDate"), any());
        verify(model, times(1)).addAttribute(eq("timestamp"), any());
    }

    @Test
    public void testDateTimeTestReturnsCorrectView() {
        String viewName = controller.dateTimeTest(model);

        assertNotNull(viewName);
        assertEquals("date/test", viewName);
    }

    @Test
    public void testDateTimeTestAddsAttributes() {
        controller.dateTimeTest(model);

        verify(model, atLeast(4)).addAttribute(anyString(), any());
    }
}
