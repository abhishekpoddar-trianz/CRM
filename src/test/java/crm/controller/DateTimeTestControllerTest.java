package crm.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DateTimeTestControllerTest {

    private DateTimeTestController dateTimeTestController;

    @Mock
    private Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        dateTimeTestController = new DateTimeTestController();
    }

    @Test
    void testConstructor() {
        DateTimeTestController controller = new DateTimeTestController();
        assertNotNull(controller);
    }

    @Test
    void testDateTimeTest() {
        String viewName = dateTimeTestController.dateTimeTest(model);

        assertEquals("date/test", viewName);
        verify(model).addAttribute(eq("standardDate"), any(Date.class));
        verify(model).addAttribute(eq("localDateTime"), any(LocalDateTime.class));
        verify(model).addAttribute(eq("localDate"), any(LocalDate.class));
        verify(model).addAttribute(eq("timestamp"), any(Instant.class));
    }

    @Test
    void testDateTimeTestAddsStandardDate() {
        dateTimeTestController.dateTimeTest(model);

        verify(model, times(1)).addAttribute(eq("standardDate"), any(Date.class));
    }

    @Test
    void testDateTimeTestAddsLocalDateTime() {
        dateTimeTestController.dateTimeTest(model);

        verify(model, times(1)).addAttribute(eq("localDateTime"), any(LocalDateTime.class));
    }

    @Test
    void testDateTimeTestAddsLocalDate() {
        dateTimeTestController.dateTimeTest(model);

        verify(model, times(1)).addAttribute(eq("localDate"), any(LocalDate.class));
    }

    @Test
    void testDateTimeTestAddsTimestamp() {
        dateTimeTestController.dateTimeTest(model);

        verify(model, times(1)).addAttribute(eq("timestamp"), any(Instant.class));
    }

    @Test
    void testDateTimeTestReturnsCorrectView() {
        String result = dateTimeTestController.dateTimeTest(model);

        assertNotNull(result);
        assertEquals("date/test", result);
    }

    @Test
    void testDateTimeTestAddsAllAttributes() {
        dateTimeTestController.dateTimeTest(model);

        verify(model, times(4)).addAttribute(anyString(), any());
    }
}
