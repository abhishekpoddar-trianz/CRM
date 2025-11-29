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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    private CustomerController customerController;

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
        List<Customer> customers = Arrays.asList(new Customer(), new Customer());
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
    void testProcessRequestAddCustomer_WithErrors() {
        Customer customer = new Customer();
        when(bindingResult.hasErrors()).thenReturn(true);

        String result = customerController.processRequestAddCustomer(customer, bindingResult);

        assertEquals("redirect:/customer/add", result);
        verify(customerService, never()).saveCustomer(customer);
    }

    @Test
    void testProcessRequestAddCustomer_WithoutErrors() {
        Customer customer = new Customer();
        when(bindingResult.hasErrors()).thenReturn(false);

        String result = customerController.processRequestAddCustomer(customer, bindingResult);

        assertEquals("customer/success", result);
        verify(customerService).saveCustomer(customer);
    }

    @Test
    void testShowFormEditCustomer() {
        Long customerId = 1L;
        Customer customer = new Customer();
        when(customerService.showCustomer(customerId)).thenReturn(customer);

        String result = customerController.showFormEditCustomer(model, customerId);

        assertEquals("customer/edit", result);
        verify(model).addAttribute("customer", customer);
        verify(customerService).showCustomer(customerId);
    }

    @Test
    void testProcessRequestEditCustomer_WithErrors() {
        Long customerId = 1L;
        Customer customer = new Customer();
        when(bindingResult.hasErrors()).thenReturn(true);

        String result = customerController.processRequestEditCustomer(customerId, customer, bindingResult);

        assertEquals("redirect:/customer/edit/" + customerId, result);
        verify(customerService, never()).saveCustomer(customer);
    }

    @Test
    void testProcessRequestEditCustomer_WithoutErrors() {
        Long customerId = 1L;
        Customer customer = new Customer();
        when(bindingResult.hasErrors()).thenReturn(false);

        String result = customerController.processRequestEditCustomer(customerId, customer, bindingResult);

        assertEquals("redirect:/customer/list", result);
        verify(customerService).saveCustomer(customer);
    }

    @Test
    void testShowFormCreateCustomerBasedOnAnotherOne() {
        Long customerId = 1L;
        Customer customer = new Customer();
        when(customerService.showCustomer(customerId)).thenReturn(customer);

        String result = customerController.showFormCreateCustomerBasedOnAnotherOne(model, customerId);

        assertEquals("customer/add-customer-based-on-another-one", result);
        verify(model).addAttribute("customer", customer);
        verify(customerService).showCustomer(customerId);
    }

    @Test
    void testCreateCustomerBasedOnAnotherOne_WithErrors() {
        Long customerId = 1L;
        Customer customer = new Customer();
        when(bindingResult.hasErrors()).thenReturn(true);

        String result = customerController.createCustomerBasedOnAnotherOne(customerId, customer, bindingResult);

        assertEquals("redirect:/customer/addCustomerBasedOnAnotherOne/" + customerId, result);
        verify(customerService, never()).saveCustomer(any(Customer.class));
    }

    @Test
    void testCreateCustomerBasedOnAnotherOne_WithoutErrors() {
        Long customerId = 1L;
        Customer customer = new Customer();
        customer.setName("Test Customer");
        customer.setEmail("test@example.com");
        when(bindingResult.hasErrors()).thenReturn(false);
        when(customerService.getMaxId()).thenReturn(5L);

        String result = customerController.createCustomerBasedOnAnotherOne(customerId, customer, bindingResult);

        assertEquals("redirect:/customer/list", result);
        verify(customerService).saveCustomer(any(Customer.class));
        verify(customerService).getMaxId();
    }

    @Test
    void testShowNameSearchForm() {
        String result = customerController.showNameSearchForm(model);

        assertEquals("customer/name-search", result);
        verify(model).addAttribute(eq("customer"), any(Customer.class));
    }

    @Test
    void testProcessRequestNameSearch() {
        Customer searchCustomer = new Customer();
        searchCustomer.setName("TestCustomer");
        Customer foundCustomer = new Customer();
        when(customerService.findOneByEnabledTrueAndName("TestCustomer")).thenReturn(foundCustomer);

        String result = customerController.processRequestNameSearch(searchCustomer, model);

        assertEquals("customer/show-one", result);
        verify(model).addAttribute("customer", foundCustomer);
        verify(customerService).findOneByEnabledTrueAndName("TestCustomer");
    }

    @Test
    void testShowEmailSearchForm() {
        String result = customerController.showEmailSearchForm(model);

        assertEquals("customer/email-search", result);
        verify(model).addAttribute(eq("customer"), any(Customer.class));
    }

    @Test
    void testProcessRequestEmailSearch() {
        Customer searchCustomer = new Customer();
        searchCustomer.setEmail("test@example.com");
        List<Customer> customers = Arrays.asList(new Customer(), new Customer());
        when(customerService.findByEnabledTrueAndEmail("test@example.com")).thenReturn(customers);

        String result = customerController.processRequestEmailSearch(searchCustomer, model);

        assertEquals("customer/show-list", result);
        verify(model).addAttribute("customers", customers);
        verify(customerService).findByEnabledTrueAndEmail("test@example.com");
    }

    @Test
    void testShowPhoneSearchForm() {
        String result = customerController.showPhoneSearchForm(model);

        assertEquals("customer/phone-search", result);
        verify(model).addAttribute(eq("customer"), any(Customer.class));
    }

    @Test
    void testProcessRequestPhoneSearch() {
        Customer searchCustomer = new Customer();
        searchCustomer.setPhone("123456789");
        List<Customer> customers = Arrays.asList(new Customer(), new Customer());
        when(customerService.findByEnabledTrueAndPhone("123456789")).thenReturn(customers);

        String result = customerController.processRequestPhoneSearch(searchCustomer, model);

        assertEquals("customer/show-list", result);
        verify(model).addAttribute("customers", customers);
        verify(customerService).findByEnabledTrueAndPhone("123456789");
    }

    @Test
    void testShowFirstNameSearchForm() {
        String result = customerController.showFirstNameSearchForm(model);

        assertEquals("customer/first-name-search", result);
        verify(model).addAttribute(eq("customer"), any(Customer.class));
    }

    @Test
    void testProcessRequestFirstNameSearch() {
        Customer searchCustomer = new Customer();
        searchCustomer.setFirstName("John");
        List<Customer> customers = Arrays.asList(new Customer(), new Customer());
        when(customerService.findByEnabledTrueAndFirstName("John")).thenReturn(customers);

        String result = customerController.processRequestFirstNameSearch(searchCustomer, model);

        assertEquals("customer/show-list", result);
        verify(model).addAttribute("customers", customers);
        verify(customerService).findByEnabledTrueAndFirstName("John");
    }

    @Test
    void testShowLastNameSearchForm() {
        String result = customerController.showLastNameSearchForm(model);

        assertEquals("customer/last-name-search", result);
        verify(model).addAttribute(eq("customer"), any(Customer.class));
    }

    @Test
    void testProcessRequestLastNameSearch() {
        Customer searchCustomer = new Customer();
        searchCustomer.setLastName("Doe");
        List<Customer> customers = Arrays.asList(new Customer(), new Customer());
        when(customerService.findByEnabledTrueAndLastName("Doe")).thenReturn(customers);

        String result = customerController.processRequestLastNameSearch(searchCustomer, model);

        assertEquals("customer/show-list", result);
        verify(model).addAttribute("customers", customers);
        verify(customerService).findByEnabledTrueAndLastName("Doe");
    }

    @Test
    void testShowFirstNameLastNameSearchForm() {
        String result = customerController.showFirstNameLastNameSearchForm(model);

        assertEquals("customer/first-name-last-name-search", result);
        verify(model).addAttribute(eq("customer"), any(Customer.class));
    }

    @Test
    void testProcessRequestFirstNameLastNameSearch() {
        Customer searchCustomer = new Customer();
        searchCustomer.setFirstName("John");
        searchCustomer.setLastName("Doe");
        List<Customer> customers = Arrays.asList(new Customer(), new Customer());
        when(customerService.findByEnabledTrueAndFirstNameAndLastName("John", "Doe")).thenReturn(customers);

        String result = customerController.processRequestFirstNameLastNameSearch(searchCustomer, model);

        assertEquals("customer/show-list", result);
        verify(model).addAttribute("customers", customers);
        verify(customerService).findByEnabledTrueAndFirstNameAndLastName("John", "Doe");
    }

    @Test
    void testShowCitySearchForm() {
        String result = customerController.showCitySearchForm(model);

        assertEquals("customer/city-search", result);
        verify(model).addAttribute(eq("customer"), any(Customer.class));
    }

    @Test
    void testProcessRequestCitySearch() {
        Customer searchCustomer = new Customer();
        searchCustomer.setCity("New York");
        List<Customer> customers = Arrays.asList(new Customer(), new Customer());
        when(customerService.findByEnabledTrueAndCity("New York")).thenReturn(customers);

        String result = customerController.processRequestCitySearch(searchCustomer, model);

        assertEquals("customer/show-list", result);
        verify(model).addAttribute("customers", customers);
        verify(customerService).findByEnabledTrueAndCity("New York");
    }

    @Test
    void testShowCityAddressSearchForm() {
        String result = customerController.showCityAddressSearchForm(model);

        assertEquals("customer/city-address-search", result);
        verify(model).addAttribute(eq("customer"), any(Customer.class));
    }

    @Test
    void testProcessRequestCityAddressSearch() {
        Customer searchCustomer = new Customer();
        searchCustomer.setCity("New York");
        searchCustomer.setAddress("123 Main St");
        List<Customer> customers = Arrays.asList(new Customer(), new Customer());
        when(customerService.findByEnabledTrueAndCityAndAddress("New York", "123 Main St")).thenReturn(customers);

        String result = customerController.processRequestCityAddressSearch(searchCustomer, model);

        assertEquals("customer/show-list", result);
        verify(model).addAttribute("customers", customers);
        verify(customerService).findByEnabledTrueAndCityAndAddress("New York", "123 Main St");
    }

    // Edge case tests
    @Test
    void testProcessRequestNameSearch_NullName() {
        Customer searchCustomer = new Customer();
        searchCustomer.setName(null);
        Customer foundCustomer = new Customer();
        when(customerService.findOneByEnabledTrueAndName(null)).thenReturn(foundCustomer);

        String result = customerController.processRequestNameSearch(searchCustomer, model);

        assertEquals("customer/show-one", result);
        verify(customerService).findOneByEnabledTrueAndName(null);
    }

    @Test
    void testProcessRequestEmailSearch_EmptyEmail() {
        Customer searchCustomer = new Customer();
        searchCustomer.setEmail("");
        List<Customer> customers = Arrays.asList();
        when(customerService.findByEnabledTrueAndEmail("")).thenReturn(customers);

        String result = customerController.processRequestEmailSearch(searchCustomer, model);

        assertEquals("customer/show-list", result);
        verify(customerService).findByEnabledTrueAndEmail("");
    }
}