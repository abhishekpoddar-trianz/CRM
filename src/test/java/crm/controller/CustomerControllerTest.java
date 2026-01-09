package crm.controller;

import crm.entity.Customer;
import crm.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerControllerTest {

    private CustomerController customerController;

    @Mock
    private CustomerService customerService;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        customerController = new CustomerController(customerService);
    }

    @Test
    void testConstructor() {
        assertNotNull(customerController);
    }

    @Test
    void testShowAllCustomers() {
        List<Customer> customers = Arrays.asList(
                Customer.builder().id(1L).name("Customer1").build(),
                Customer.builder().id(2L).name("Customer2").build()
        );

        when(customerService.listAllCustomers()).thenReturn(customers);

        String result = customerController.showAllCustomers(model);

        assertEquals("customer/list", result);
        verify(model).addAttribute("customers", customers);
        verify(customerService).listAllCustomers();
    }

    @Test
    void testShowFormAddCustomer() {
        String result = customerController.showFormAddCustomer(model);

        assertEquals("customer/add", result);
        verify(model).addAttribute(eq("customer"), any(Customer.class));
    }

    @Test
    void testProcessRequestAddCustomerWithErrors() {
        Customer customer = Customer.builder().name("Test").email("test@example.com").build();
        when(bindingResult.hasErrors()).thenReturn(true);

        String result = customerController.processRequestAddCustomer(customer, bindingResult);

        assertEquals("redirect:/customer/add", result);
        verify(bindingResult).hasErrors();
        verify(customerService, never()).saveCustomer(any());
    }

    @Test
    void testProcessRequestAddCustomerSuccess() {
        Customer customer = Customer.builder()
                .name("NewCustomer")
                .email("new@example.com")
                .phone(123456)
                .enabled(1)
                .build();
        when(bindingResult.hasErrors()).thenReturn(false);

        String result = customerController.processRequestAddCustomer(customer, bindingResult);

        assertEquals("customer/success", result);
        verify(bindingResult).hasErrors();
        verify(customerService).saveCustomer(customer);
    }

    @Test
    void testShowFormEditCustomer() {
        Long customerId = 1L;
        Customer customer = Customer.builder().id(customerId).name("EditCustomer").build();

        when(customerService.showCustomer(customerId)).thenReturn(customer);

        String result = customerController.showFormEditCustomer(model, customerId);

        assertEquals("customer/edit", result);
        verify(model).addAttribute("customer", customer);
        verify(customerService).showCustomer(customerId);
    }

    @Test
    void testProcessRequestEditCustomerWithErrors() {
        Long customerId = 1L;
        Customer customer = Customer.builder().id(customerId).name("Test").build();
        when(bindingResult.hasErrors()).thenReturn(true);

        String result = customerController.processRequestEditCustomer(customerId, customer, bindingResult);

        assertEquals("redirect:/customer/edit/" + customerId, result);
        verify(bindingResult).hasErrors();
        verify(customerService, never()).saveCustomer(any());
    }

    @Test
    void testProcessRequestEditCustomerSuccess() {
        Long customerId = 1L;
        Customer customer = Customer.builder()
                .id(customerId)
                .name("EditedCustomer")
                .email("edited@example.com")
                .build();
        when(bindingResult.hasErrors()).thenReturn(false);

        String result = customerController.processRequestEditCustomer(customerId, customer, bindingResult);

        assertEquals("redirect:/customer/list", result);
        verify(bindingResult).hasErrors();
        verify(customerService).saveCustomer(customer);
    }

    @Test
    void testShowNameSearchForm() {
        String result = customerController.showNameSearchForm(model);

        assertEquals("customer/name-search", result);
        verify(model).addAttribute(eq("customer"), any(Customer.class));
    }

    @Test
    void testProcessRequestNameSearch() {
        Customer customer = Customer.builder().name("SearchCustomer").build();
        Customer foundCustomer = Customer.builder().id(1L).name("SearchCustomer").build();

        when(customerService.findOneByEnabledTrueAndName("SearchCustomer")).thenReturn(foundCustomer);

        String result = customerController.processRequestNameSearch(customer, model);

        assertEquals("customer/show-one", result);
        verify(model).addAttribute("customer", foundCustomer);
        verify(customerService).findOneByEnabledTrueAndName("SearchCustomer");
    }

    @Test
    void testShowEmailSearchForm() {
        String result = customerController.showEmailSearchForm(model);

        assertEquals("customer/email-search", result);
        verify(model).addAttribute(eq("customer"), any(Customer.class));
    }

    @Test
    void testProcessRequestEmailSearch() {
        String email = "test@example.com";
        Customer customer = Customer.builder().email(email).build();
        List<Customer> customers = Arrays.asList(
                Customer.builder().id(1L).email(email).build()
        );

        when(customerService.findByEnabledTrueAndEmail(email)).thenReturn(customers);

        String result = customerController.processRequestEmailSearch(customer, model);

        assertEquals("customer/show-list", result);
        verify(model).addAttribute("customers", customers);
        verify(customerService).findByEnabledTrueAndEmail(email);
    }

    @Test
    void testShowPhoneSearchForm() {
        String result = customerController.showPhoneSearchForm(model);

        assertEquals("customer/phone-search", result);
        verify(model).addAttribute(eq("customer"), any(Customer.class));
    }

    @Test
    void testProcessRequestPhoneSearch() {
        int phone = 123456;
        Customer customer = Customer.builder().phone(phone).build();
        List<Customer> customers = Arrays.asList(
                Customer.builder().id(1L).phone(phone).build()
        );

        when(customerService.findByEnabledTrueAndPhone(phone)).thenReturn(customers);

        String result = customerController.processRequestPhoneSearch(customer, model);

        assertEquals("customer/show-list", result);
        verify(model).addAttribute("customers", customers);
        verify(customerService).findByEnabledTrueAndPhone(phone);
    }

    @Test
    void testShowCitySearchForm() {
        String result = customerController.showCitySearchForm(model);

        assertEquals("customer/city-search", result);
        verify(model).addAttribute(eq("customer"), any(Customer.class));
    }

    @Test
    void testProcessRequestCitySearch() {
        String city = "New York";
        Customer customer = Customer.builder().city(city).build();
        List<Customer> customers = Arrays.asList(
                Customer.builder().id(1L).city(city).build()
        );

        when(customerService.findByEnabledTrueAndCity(city)).thenReturn(customers);

        String result = customerController.processRequestCitySearch(customer, model);

        assertEquals("customer/show-list", result);
        verify(model).addAttribute("customers", customers);
        verify(customerService).findByEnabledTrueAndCity(city);
    }
}
