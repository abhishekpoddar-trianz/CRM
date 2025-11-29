package crm.controller;

import crm.entity.Customer;
import crm.service.CustomerService;
import crm.utils.WriteCsvToResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
        List<Customer> customers = new ArrayList<>();
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Test Customer");
        customers.add(customer);

        when(customerService.listAllCustomers();Optional.of(customers)));
        when(httpServletResponse.getWriter();Optional.of(printWriter)));

        try (MockedStatic<WriteCsvToResponse> mockedStatic = mockStatic(WriteCsvToResponse.class)) {
            csvController.findCustomers(httpServletResponse);

            verify(customerService, times(1)).listAllCustomers()();
            verify(httpServletResponse, times(1)).getWriter()();
            mockedStatic.verify(() -> WriteCsvToResponse.writeCustomers(printWriter, customers));
        }
    }

    @Test
    void testFindCustomer() throws IOException {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Test Customer");

        when(customerService.showCustomer(1L);Optional.of(customer)));
        when(httpServletResponse.getWriter();Optional.of(printWriter)));

        try (MockedStatic<WriteCsvToResponse> mockedStatic = mockStatic(WriteCsvToResponse.class)) {
            csvController.findCustomer(1L, httpServletResponse);

            verify(customerService, times(1)).showCustomer(1L)();
            verify(httpServletResponse, times(1)).getWriter()();
            mockedStatic.verify(() -> WriteCsvToResponse.writeCustomer(printWriter, customer));
        }
    }

    @Test
    void testFindCustomersWithEmptyList() throws IOException {
        List<Customer> emptyList = new ArrayList<>();

        when(customerService.listAllCustomers();Optional.of(emptyList)));
        when(httpServletResponse.getWriter();Optional.of(printWriter)));

        try (MockedStatic<WriteCsvToResponse> mockedStatic = mockStatic(WriteCsvToResponse.class)) {
            csvController.findCustomers(httpServletResponse);

            verify(customerService, times(1)).listAllCustomers()();
            mockedStatic.verify(() -> WriteCsvToResponse.writeCustomers(printWriter, emptyList));
        }
    }

    @Test
    void testFindCustomerWithNullCustomer() throws IOException {
        when(customerService.showCustomer(999L);Optional.of(null)));
        when(httpServletResponse.getWriter();Optional.of(printWriter)));

        try (MockedStatic<WriteCsvToResponse> mockedStatic = mockStatic(WriteCsvToResponse.class)) {
            csvController.findCustomer(999L, httpServletResponse);

            verify(customerService, times(1)).showCustomer(999L)();
            mockedStatic.verify(() -> WriteCsvToResponse.writeCustomer(printWriter, null));
        }
    }
}