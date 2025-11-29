package crm.controller;

import crm.entity.Customer;
import crm.service.CustomerService;
import crm.utils.WriteCsvToResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CSVControllerTest {

    @Mock
    private CustomerService customerService;

    @Mock
    private HttpServletResponse httpServletResponse;

    @Mock
    private PrintWriter printWriter;

    private CSVController csvController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        csvController = new CSVController(customerService);
    }

    @Test
    void testConstructor() {
        assertNotNull(csvController);
    }

    @Test
    void testFindCustomers() throws IOException {
        // Arrange
        List<Customer> customers = Arrays.asList(new Customer(), new Customer());
        when(customerService.listAllCustomers()).thenReturn(customers);
        when(httpServletResponse.getWriter()).thenReturn(printWriter);

        try (MockedStatic<WriteCsvToResponse> mockedStatic = mockStatic(WriteCsvToResponse.class)) {
            // Act
            csvController.findCustomers(httpServletResponse);

            // Assert
            verify(customerService).listAllCustomers();
            verify(httpServletResponse).getWriter();
            mockedStatic.verify(() -> WriteCsvToResponse.writeCustomers(printWriter, customers));
        }
    }

    @Test
    void testFindCustomers_IOException() throws IOException {
        // Arrange
        List<Customer> customers = Arrays.asList(new Customer(), new Customer());
        when(customerService.listAllCustomers()).thenReturn(customers);
        when(httpServletResponse.getWriter()).thenThrow(new IOException("Test exception"));

        // Act & Assert
        assertThrows(IOException.class, () -> csvController.findCustomers(httpServletResponse));
        verify(customerService).listAllCustomers();
        verify(httpServletResponse).getWriter();
    }

    @Test
    void testFindCustomer() throws IOException {
        // Arrange
        Long customerId = 1L;
        Customer customer = new Customer();
        when(customerService.showCustomer(customerId)).thenReturn(customer);
        when(httpServletResponse.getWriter()).thenReturn(printWriter);

        try (MockedStatic<WriteCsvToResponse> mockedStatic = mockStatic(WriteCsvToResponse.class)) {
            // Act
            csvController.findCustomer(customerId, httpServletResponse);

            // Assert
            verify(customerService).showCustomer(customerId);
            verify(httpServletResponse).getWriter();
            mockedStatic.verify(() -> WriteCsvToResponse.writeCustomer(printWriter, customer));
        }
    }

    @Test
    void testFindCustomer_IOException() throws IOException {
        // Arrange
        Long customerId = 1L;
        Customer customer = new Customer();
        when(customerService.showCustomer(customerId)).thenReturn(customer);
        when(httpServletResponse.getWriter()).thenThrow(new IOException("Test exception"));

        // Act & Assert
        assertThrows(IOException.class, () -> csvController.findCustomer(customerId, httpServletResponse));
        verify(customerService).showCustomer(customerId);
        verify(httpServletResponse).getWriter();
    }

    @Test
    void testFindCustomer_NullId() throws IOException {
        // Arrange
        Long customerId = null;
        when(httpServletResponse.getWriter()).thenReturn(printWriter);

        // Act & Assert
        assertThrows(Exception.class, () -> csvController.findCustomer(customerId, httpServletResponse));
    }

    @Test
    void testFindCustomer_ValidIdZero() throws IOException {
        // Arrange
        Long customerId = 0L;
        Customer customer = new Customer();
        when(customerService.showCustomer(customerId)).thenReturn(customer);
        when(httpServletResponse.getWriter()).thenReturn(printWriter);

        try (MockedStatic<WriteCsvToResponse> mockedStatic = mockStatic(WriteCsvToResponse.class)) {
            // Act
            csvController.findCustomer(customerId, httpServletResponse);

            // Assert
            verify(customerService).showCustomer(customerId);
            verify(httpServletResponse).getWriter();
            mockedStatic.verify(() -> WriteCsvToResponse.writeCustomer(printWriter, customer));
        }
    }

    @Test
    void testFindCustomer_NegativeId() throws IOException {
        // Arrange
        Long customerId = -1L;
        Customer customer = new Customer();
        when(customerService.showCustomer(customerId)).thenReturn(customer);
        when(httpServletResponse.getWriter()).thenReturn(printWriter);

        try (MockedStatic<WriteCsvToResponse> mockedStatic = mockStatic(WriteCsvToResponse.class)) {
            // Act
            csvController.findCustomer(customerId, httpServletResponse);

            // Assert
            verify(customerService).showCustomer(customerId);
            verify(httpServletResponse).getWriter();
            mockedStatic.verify(() -> WriteCsvToResponse.writeCustomer(printWriter, customer));
        }
    }
}