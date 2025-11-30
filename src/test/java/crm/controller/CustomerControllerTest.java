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
        CustomerController controller = new CustomerController(customerService);
        assertNotNull(controller);
    }

    @Test
    void testShowAllCustomers() {
        List<Customer> customers = Arrays.asList(new Customer(), new Customer());
        when(customerService.listAllCustomers()).thenReturn(customers);

        String viewName = customerController.showAllCustomers(model);

        assertEquals("customer/list", viewName);
        verify(model).addAttribute("customers", customers);
    }

    @Test
    void testShowFormAddCustomer() {
        String viewName = customerController.showFormAddCustomer(model);

        assertEquals("customer/add", viewName);
        verify(model).addAttribute(eq("customer"), any(Customer.class));
    }

    @Test
    void testProcessRequestAddCustomerWithValidData() {
        Customer customer = new Customer();
        when(bindingResult.hasErrors()).thenReturn(false);

        String viewName = customerController.processRequestAddCustomer(customer, bindingResult);

        assertEquals("customer/success", viewName);
        verify(customerService).saveCustomer(customer);
    }

    @Test
    void testProcessRequestAddCustomerWithErrors() {
        Customer customer = new Customer();
        when(bindingResult.hasErrors()).thenReturn(true);

        String viewName = customerController.processRequestAddCustomer(customer, bindingResult);

        assertEquals("redirect:/customer/add", viewName);
        verify(customerService, never()).saveCustomer(customer);
    }

    @Test
    void testShowFormEditCustomer() {
        Customer customer = new Customer();
        when(customerService.showCustomer(1L)).thenReturn(customer);

        String viewName = customerController.showFormEditCustomer(model, 1L);

        assertEquals("customer/edit", viewName);
        verify(model).addAttribute("customer", customer);
    }

    @Test
    void testProcessRequestEditCustomerWithValidData() {
        Customer customer = new Customer();
        when(bindingResult.hasErrors()).thenReturn(false);

        String viewName = customerController.processRequestEditCustomer(1L, customer, bindingResult);

        assertEquals("redirect:/customer/list", viewName);
        verify(customerService).saveCustomer(customer);
    }

    @Test
    void testProcessRequestEditCustomerWithErrors() {
        Customer customer = new Customer();
        when(bindingResult.hasErrors()).thenReturn(true);

        String viewName = customerController.processRequestEditCustomer(1L, customer, bindingResult);

        assertEquals("redirect:/customer/edit/1", viewName);
        verify(customerService, never()).saveCustomer(customer);
    }

    @Test
    void testShowFormCreateCustomerBasedOnAnotherOne() {
        Customer customer = new Customer();
        when(customerService.showCustomer(1L)).thenReturn(customer);

        String viewName = customerController.showFormCreateCustomerBasedOnAnotherOne(model, 1L);

        assertEquals("customer/add-customer-based-on-another-one", viewName);
        verify(model).addAttribute("customer", customer);
    }

    @Test
    void testCreateCustomerBasedOnAnotherOneWithValidData() {
        Customer customer = new Customer();
        customer.setName("Test");
        when(bindingResult.hasErrors()).thenReturn(false);
        when(customerService.getMaxId()).thenReturn(10L);

        String viewName = customerController.createCustomerBasedOnAnotherOne(1L, customer, bindingResult);

        assertEquals("redirect:/customer/list", viewName);
        verify(customerService).saveCustomer(any(Customer.class));
    }

    @Test
    void testCreateCustomerBasedOnAnotherOneWithErrors() {
        Customer customer = new Customer();
        when(bindingResult.hasErrors()).thenReturn(true);

        String viewName = customerController.createCustomerBasedOnAnotherOne(1L, customer, bindingResult);

        assertEquals("redirect:/customer/addCustomerBasedOnAnotherOne/1", viewName);
    }

    @Test
    void testShowNameSearchForm() {
        String viewName = customerController.showNameSearchForm(model);

        assertEquals("customer/name-search", viewName);
        verify(model).addAttribute(eq("customer"), any(Customer.class));
    }

    @Test
    void testProcessRequestNameSearch() {
        Customer customer = new Customer();
        customer.setName("Test");
        when(customerService.findOneByEnabledTrueAndName("Test")).thenReturn(customer);

        String viewName = customerController.processRequestNameSearch(customer, model);

        assertEquals("customer/show-one", viewName);
        verify(model).addAttribute("customer", customer);
    }

    @Test
    void testShowEmailSearchForm() {
        String viewName = customerController.showEmailSearchForm(model);

        assertEquals("customer/email-search", viewName);
        verify(model).addAttribute(eq("customer"), any(Customer.class));
    }

    @Test
    void testProcessRequestEmailSearch() {
        Customer customer = new Customer();
        customer.setEmail("test@example.com");
        List<Customer> customers = Arrays.asList(customer);
        when(customerService.findByEnabledTrueAndEmail("test@example.com")).thenReturn(customers);

        String viewName = customerController.processRequestEmailSearch(customer, model);

        assertEquals("customer/show-list", viewName);
        verify(model).addAttribute("customers", customers);
    }

    @Test
    void testShowPhoneSearchForm() {
        String viewName = customerController.showPhoneSearchForm(model);

        assertEquals("customer/phone-search", viewName);
    }

    @Test
    void testProcessRequestPhoneSearch() {
        Customer customer = new Customer();
        customer.setPhone(123456789);
        List<Customer> customers = Arrays.asList(customer);
        when(customerService.findByEnabledTrueAndPhone(123456789)).thenReturn(customers);

        String viewName = customerController.processRequestPhoneSearch(customer, model);

        assertEquals("customer/show-list", viewName);
    }

    @Test
    void testShowFirstNameSearchForm() {
        String viewName = customerController.showFirstNameSearchForm(model);

        assertEquals("customer/first-name-search", viewName);
    }

    @Test
    void testProcessRequestFirstNameSearch() {
        Customer customer = new Customer();
        customer.setFirstName("John");
        List<Customer> customers = Arrays.asList(customer);
        when(customerService.findByEnabledTrueAndFirstName("John")).thenReturn(customers);

        String viewName = customerController.processRequestFirstNameSearch(customer, model);

        assertEquals("customer/show-list", viewName);
    }

    @Test
    void testShowLastNameSearchForm() {
        String viewName = customerController.showLastNameSearchForm(model);

        assertEquals("customer/last-name-search", viewName);
    }

    @Test
    void testProcessRequestLastNameSearch() {
        Customer customer = new Customer();
        customer.setLastName("Doe");
        List<Customer> customers = Arrays.asList(customer);
        when(customerService.findByEnabledTrueAndLastName("Doe")).thenReturn(customers);

        String viewName = customerController.processRequestLastNameSearch(customer, model);

        assertEquals("customer/show-list", viewName);
    }

    @Test
    void testShowFirstNameLastNameSearchForm() {
        String viewName = customerController.showFirstNameLastNameSearchForm(model);

        assertEquals("customer/first-name-last-name-search", viewName);
    }

    @Test
    void testProcessRequestFirstNameLastNameSearch() {
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        List<Customer> customers = Arrays.asList(customer);
        when(customerService.findByEnabledTrueAndFirstNameAndLastName("John", "Doe")).thenReturn(customers);

        String viewName = customerController.processRequestFirstNameLastNameSearch(customer, model);

        assertEquals("customer/show-list", viewName);
    }

    @Test
    void testShowCitySearchForm() {
        String viewName = customerController.showCitySearchForm(model);

        assertEquals("customer/city-search", viewName);
    }

    @Test
    void testProcessRequestCitySearch() {
        Customer customer = new Customer();
        customer.setCity("New York");
        List<Customer> customers = Arrays.asList(customer);
        when(customerService.findByEnabledTrueAndCity("New York")).thenReturn(customers);

        String viewName = customerController.processRequestCitySearch(customer, model);

        assertEquals("customer/show-list", viewName);
    }

    @Test
    void testShowCityAddressSearchForm() {
        String viewName = customerController.showCityAddressSearchForm(model);

        assertEquals("customer/city-address-search", viewName);
    }

    @Test
    void testProcessRequestCityAddressSearch() {
        Customer customer = new Customer();
        customer.setCity("New York");
        customer.setAddress("123 Main St");
        List<Customer> customers = Arrays.asList(customer);
        when(customerService.findByEnabledTrueAndCityAndAddress("New York", "123 Main St")).thenReturn(customers);

        String viewName = customerController.processRequestCityAddressSearch(customer, model);

        assertEquals("customer/show-list", viewName);
    }
}
