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
        assertNotNull(csvController);
    }

    @Test
    void testFindCustomers() throws IOException {
        Customer customer1 = Customer.builder()
                .id(1L)
                .name("Customer1")
                .email("customer1@example.com")
                .phone(123456)
                .enabled(1)
                .build();

        Customer customer2 = Customer.builder()
                .id(2L)
                .name("Customer2")
                .email("customer2@example.com")
                .phone(654321)
                .enabled(1)
                .build();

        List<Customer> customers = Arrays.asList(customer1, customer2);

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        when(customerService.listAllCustomers()).thenReturn(customers);
        when(httpServletResponse.getWriter()).thenReturn(printWriter);

        csvController.findCustomers(httpServletResponse);

        verify(customerService).listAllCustomers();
        verify(httpServletResponse).getWriter();
    }

    @Test
    void testFindCustomer() throws IOException {
        Long customerId = 1L;
        Customer customer = Customer.builder()
                .id(customerId)
                .name("TestCustomer")
                .email("test@example.com")
                .phone(111111)
                .enabled(1)
                .build();

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        when(customerService.showCustomer(customerId)).thenReturn(customer);
        when(httpServletResponse.getWriter()).thenReturn(printWriter);

        csvController.findCustomer(customerId, httpServletResponse);

        verify(customerService).showCustomer(customerId);
        verify(httpServletResponse).getWriter();
    }

    @Test
    void testFindCustomersWithEmptyList() throws IOException {
        List<Customer> customers = Arrays.asList();

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        when(customerService.listAllCustomers()).thenReturn(customers);
        when(httpServletResponse.getWriter()).thenReturn(printWriter);

        csvController.findCustomers(httpServletResponse);

        verify(customerService, times(1)).listAllCustomers();
    }

    @Test
    void testFindCustomerCallsService() throws IOException {
        Long customerId = 5L;
        Customer customer = Customer.builder()
                .id(customerId)
                .name("Customer5")
                .email("customer5@example.com")
                .phone(555555)
                .enabled(1)
                .build();

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        when(customerService.showCustomer(customerId)).thenReturn(customer);
        when(httpServletResponse.getWriter()).thenReturn(printWriter);

        csvController.findCustomer(customerId, httpServletResponse);

        verify(customerService, times(1)).showCustomer(customerId);
    }

    @Test
    void testFindCustomersCallsService() throws IOException {
        when(customerService.listAllCustomers()).thenReturn(Arrays.asList());
        when(httpServletResponse.getWriter()).thenReturn(new PrintWriter(new StringWriter()));

        csvController.findCustomers(httpServletResponse);

        verify(customerService, times(1)).listAllCustomers();
    }
}
