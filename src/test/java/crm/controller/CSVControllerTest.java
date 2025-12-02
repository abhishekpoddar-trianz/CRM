package crm.controller;

import crm.entity.Customer;
import crm.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import jakarta.servlet.http.HttpServletResponse;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class CSVControllerTest {

    @Mock
    private CustomerService customerService;

    @Mock
    private HttpServletResponse response;

    private CSVController csvController;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        csvController = new CSVController(customerService);

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);
    }

    @Test
    public void testFindCustomers() throws Exception {
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer());
        when(customerService.listAllCustomers()).thenReturn(customers);

        assertDoesNotThrow(() -> csvController.findCustomers(response));
        verify(customerService, times(1)).listAllCustomers();
    }

    @Test
    public void testFindCustomer() throws Exception {
        Customer customer = new Customer();
        customer.setId(1L);
        when(customerService.showCustomer(1L)).thenReturn(customer);

        assertDoesNotThrow(() -> csvController.findCustomer(1L, response));
        verify(customerService, times(1)).showCustomer(1L);
    }

    @Test
    public void testFindCustomersEmpty() throws Exception {
        when(customerService.listAllCustomers()).thenReturn(new ArrayList<>());

        assertDoesNotThrow(() -> csvController.findCustomers(response));
        verify(customerService, times(1)).listAllCustomers();
    }

    @Test
    public void testFindCustomerWithValidId() throws Exception {
        Customer customer = new Customer();
        customer.setId(100L);
        when(customerService.showCustomer(100L)).thenReturn(customer);

        csvController.findCustomer(100L, response);
        verify(customerService, times(1)).showCustomer(100L);
    }

    @Test
    public void testControllerNotNull() {
        assertNotNull(csvController);
    }
}
