package crm.controller;

import crm.entity.Customer;
import crm.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CSVControllerTest {

    private CSVController csvController;

    @Mock
    private CustomerService customerService;

    @Mock
    private HttpServletResponse httpServletResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        csvController = new CSVController(customerService);
    }

    @Test
    void testConstructor() {
        CSVController controller = new CSVController(customerService);
        assertNotNull(controller);
    }

    @Test
    void testFindCustomers() throws IOException {
        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setName("Customer1");

        Customer customer2 = new Customer();
        customer2.setId(2L);
        customer2.setName("Customer2");

        List<Customer> customers = Arrays.asList(customer1, customer2);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        when(customerService.listAllCustomers()).thenReturn(customers);
        when(httpServletResponse.getWriter()).thenReturn(writer);

        csvController.findCustomers(httpServletResponse);

        verify(customerService, times(1)).listAllCustomers();
        verify(httpServletResponse, times(1)).getWriter();
    }

    @Test
    void testFindCustomer() throws IOException {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Test Customer");

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        when(customerService.showCustomer(1L)).thenReturn(customer);
        when(httpServletResponse.getWriter()).thenReturn(writer);

        csvController.findCustomer(1L, httpServletResponse);

        verify(customerService, times(1)).showCustomer(1L);
        verify(httpServletResponse, times(1)).getWriter();
    }

    @Test
    void testFindCustomersWithEmptyList() throws IOException {
        List<Customer> emptyList = Arrays.asList();

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        when(customerService.listAllCustomers()).thenReturn(emptyList);
        when(httpServletResponse.getWriter()).thenReturn(writer);

        csvController.findCustomers(httpServletResponse);

        verify(customerService, times(1)).listAllCustomers();
    }

    @Test
    void testFindCustomerWithNullCustomer() throws IOException {
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        when(customerService.showCustomer(999L)).thenReturn(null);
        when(httpServletResponse.getWriter()).thenReturn(writer);

        csvController.findCustomer(999L, httpServletResponse);

        verify(customerService, times(1)).showCustomer(999L);
    }

    @Test
    void testFindCustomersThrowsIOException() throws IOException {
        when(customerService.listAllCustomers()).thenReturn(Arrays.asList());
        when(httpServletResponse.getWriter()).thenThrow(new IOException("Test exception"));

        assertThrows(IOException.class, () -> {
            csvController.findCustomers(httpServletResponse);
        });
    }

    @Test
    void testFindCustomerThrowsIOException() throws IOException {
        when(customerService.showCustomer(1L)).thenReturn(new Customer());
        when(httpServletResponse.getWriter()).thenThrow(new IOException("Test exception"));

        assertThrows(IOException.class, () -> {
            csvController.findCustomer(1L, httpServletResponse);
        });
    }
}
