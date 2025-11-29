package crm.controller;

import crm.entity.Contract;
import crm.entity.Customer;
import crm.entity.User;
import crm.service.ContractService;
import crm.service.CustomerService;
import crm.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ContractControllerTest {

    @Mock
    private ContractService contractService;

    @Mock
    private CustomerService customerService;

    @Mock
    private UserService userService;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    private ContractController contractController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        contractController = new ContractController(contractService, customerService, userService);
    }

    @Test
    void testConstructor() {
        assertNotNull(contractController);
    }

    @Test
    void testShowAllContracts() {
        List<Contract> contracts = Arrays.asList(new Contract(), new Contract());
        when(contractService.listAllContracts()).thenReturn(contracts);

        String result = contractController.showAllContracts(model);

        assertEquals("contract/list", result);
        verify(model).addAttribute("contracts", contracts);
        verify(contractService).listAllContracts();
    }

    @Test
    void testShowFormAddContract() {
        List<Customer> customers = Arrays.asList(new Customer(), new Customer());
        List<User> users = Arrays.asList(new User(), new User());
        when(customerService.findAllByEnabledTrue()).thenReturn(customers);
        when(userService.listAllUsers()).thenReturn(users);

        String result = contractController.showFormAddContract(model);

        assertEquals("contract/add", result);
        verify(model).addAttribute(eq("contract"), any(Contract.class));
        verify(model).addAttribute("customers", customers);
        verify(model).addAttribute("users", users);
    }

    @Test
    void testProcessRequestAddContract_WithErrors() {
        Contract contract = new Contract();
        when(bindingResult.hasErrors()).thenReturn(true);

        String result = contractController.processRequestAddContract(contract, bindingResult);

        assertEquals("redirect:/contract/add", result);
        verify(contractService, never()).saveContract(contract);
    }

    @Test
    void testProcessRequestAddContract_WithoutErrors() {
        Contract contract = new Contract();
        when(bindingResult.hasErrors()).thenReturn(false);

        String result = contractController.processRequestAddContract(contract, bindingResult);

        assertEquals("contract/success", result);
        verify(contractService).saveContract(contract);
    }

    @Test
    void testShowFormEditContract() {
        Long contractId = 1L;
        Contract contract = new Contract();
        when(contractService.showContract(contractId)).thenReturn(contract);

        String result = contractController.showFormEditContract(model, contractId);

        assertEquals("contract/edit", result);
        verify(model).addAttribute("contract", contract);
        verify(contractService).showContract(contractId);
    }

    @Test
    void testProcessRequestEditContract_WithErrors() {
        Long contractId = 1L;
        Contract contract = new Contract();
        when(bindingResult.hasErrors()).thenReturn(true);

        String result = contractController.processRequestEditContract(contractId, contract, bindingResult);

        assertEquals("redirect:/contract/edit/" + contractId, result);
        verify(contractService, never()).saveContract(contract);
    }

    @Test
    void testProcessRequestEditContract_WithoutErrors() {
        Long contractId = 1L;
        Contract contract = new Contract();
        when(bindingResult.hasErrors()).thenReturn(false);

        String result = contractController.processRequestEditContract(contractId, contract, bindingResult);

        assertEquals("redirect:/contract/list", result);
        verify(contractService).saveContract(contract);
    }

    @Test
    void testShowNameSearchForm() {
        String result = contractController.showNameSearchForm(model);

        assertEquals("contract/name-search", result);
        verify(model).addAttribute(eq("contract"), any(Contract.class));
    }

    @Test
    void testProcessRequestNameSearch() {
        Contract searchContract = new Contract();
        searchContract.setName("TestContract");
        Contract foundContract = new Contract();
        when(contractService.findByName("TestContract")).thenReturn(foundContract);

        String result = contractController.processRequestNameSearch(searchContract, model);

        assertEquals("contract/show-one", result);
        verify(model).addAttribute("contract", foundContract);
        verify(contractService).findByName("TestContract");
    }

    @Test
    void testShowValueLeesThanEqualSearchForm() {
        String result = contractController.showValueLeesThanEqualSearchForm(model);

        assertEquals("contract/value-le-search", result);
        verify(model).addAttribute(eq("contract"), any(Contract.class));
    }

    @Test
    void testProcessRequestValueLessThanEqualSearch() {
        Contract searchContract = new Contract();
        searchContract.setValue(1000.0);
        List<Contract> contracts = Arrays.asList(new Contract(), new Contract());
        when(contractService.findAllByValueLessThanEqual(1000.0)).thenReturn(contracts);

        String result = contractController.processRequestValueLessThanEqualSearch(searchContract, model);

        assertEquals("contract/show-list", result);
        verify(model).addAttribute("contracts", contracts);
        verify(contractService).findAllByValueLessThanEqual(1000.0);
    }

    @Test
    void testShowValueGreaterThanEqualSearchForm() {
        String result = contractController.showValueGreaterThanEqualSearchForm(model);

        assertEquals("contract/value-ge-search", result);
        verify(model).addAttribute(eq("contract"), any(Contract.class));
    }

    @Test
    void testProcessRequestValueGreaterThanEqualSearch() {
        Contract searchContract = new Contract();
        searchContract.setValue(5000.0);
        List<Contract> contracts = Arrays.asList(new Contract(), new Contract());
        when(contractService.findAllByValueGreaterThanEqual(5000.0)).thenReturn(contracts);

        String result = contractController.processRequestValueGreaterThanEqualSearch(searchContract, model);

        assertEquals("contract/show-list", result);
        verify(model).addAttribute("contracts", contracts);
        verify(contractService).findAllByValueGreaterThanEqual(5000.0);
    }

    @Test
    void testShowBeginDateSearchForm() {
        String result = contractController.showBeginDateSearchForm(model);

        assertEquals("contract/begin-date-search", result);
        verify(model).addAttribute(eq("contract"), any(Contract.class));
    }

    @Test
    void testProcessRequestBeginDateSearch() {
        Contract searchContract = new Contract();
        LocalDate beginDate = LocalDate.of(2023, 1, 1);
        searchContract.setBeginDate(beginDate);
        List<Contract> contracts = Arrays.asList(new Contract(), new Contract());
        when(contractService.findAllByBeginDate(beginDate)).thenReturn(contracts);

        String result = contractController.processRequestBeginDateSearch(searchContract, model);

        assertEquals("contract/show-list", result);
        verify(model).addAttribute("contracts", contracts);
        verify(contractService).findAllByBeginDate(beginDate);
    }

    @Test
    void testShowBeginDateBeforeSearchForm() {
        String result = contractController.showBeginDateBeforeSearchForm(model);

        assertEquals("contract/begin-date-before-search", result);
        verify(model).addAttribute(eq("contract"), any(Contract.class));
    }

    @Test
    void testProcessRequestBeginDateBeforeSearch() {
        Contract searchContract = new Contract();
        LocalDate beforeDate = LocalDate.of(2023, 6, 1);
        searchContract.setBeginDate(beforeDate);
        List<Contract> contracts = Arrays.asList(new Contract(), new Contract());
        when(contractService.findAllByBeginDateBefore(beforeDate)).thenReturn(contracts);

        String result = contractController.processRequestBeginDateBeforeSearch(searchContract, model);

        assertEquals("contract/show-list", result);
        verify(model).addAttribute("contracts", contracts);
        verify(contractService).findAllByBeginDateBefore(beforeDate);
    }

    @Test
    void testShowBeginDateAfterSearchForm() {
        String result = contractController.showBeginDateAfterSearchForm(model);

        assertEquals("contract/begin-date-after-search", result);
        verify(model).addAttribute(eq("contract"), any(Contract.class));
    }

    @Test
    void testProcessRequestBeginDateAfterSearch() {
        Contract searchContract = new Contract();
        LocalDate afterDate = LocalDate.of(2023, 12, 1);
        searchContract.setBeginDate(afterDate);
        List<Contract> contracts = Arrays.asList(new Contract(), new Contract());
        when(contractService.findAllByBeginDateAfter(afterDate)).thenReturn(contracts);

        String result = contractController.processRequestBeginDateAfterSearch(searchContract, model);

        assertEquals("contract/show-list", result);
        verify(model).addAttribute("contracts", contracts);
        verify(contractService).findAllByBeginDateAfter(afterDate);
    }

    @Test
    void testShowEndDateSearchForm() {
        String result = contractController.showEndDateSearchForm(model);

        assertEquals("contract/end-date-search", result);
        verify(model).addAttribute(eq("contract"), any(Contract.class));
    }

    @Test
    void testProcessRequestEndDateSearch() {
        Contract searchContract = new Contract();
        LocalDate endDate = LocalDate.of(2024, 1, 1);
        searchContract.setEndDate(endDate);
        List<Contract> contracts = Arrays.asList(new Contract(), new Contract());
        when(contractService.findAllByEndDate(endDate)).thenReturn(contracts);

        String result = contractController.processRequestEndDateSearch(searchContract, model);

        assertEquals("contract/show-list", result);
        verify(model).addAttribute("contracts", contracts);
        verify(contractService).findAllByEndDate(endDate);
    }

    @Test
    void testShowEndDateBeforeSearchForm() {
        String result = contractController.showEndDateBeforeSearchForm(model);

        assertEquals("contract/end-date-before-search", result);
        verify(model).addAttribute(eq("contract"), any(Contract.class));
    }

    @Test
    void testProcessRequestEndDateBeforeSearch() {
        Contract searchContract = new Contract();
        LocalDate beforeDate = LocalDate.of(2024, 6, 1);
        searchContract.setEndDate(beforeDate);
        List<Contract> contracts = Arrays.asList(new Contract(), new Contract());
        when(contractService.findAllByEndDateBefore(beforeDate)).thenReturn(contracts);

        String result = contractController.processRequestEndDateBeforeSearch(searchContract, model);

        assertEquals("contract/show-list", result);
        verify(model).addAttribute("contracts", contracts);
        verify(contractService).findAllByEndDateBefore(beforeDate);
    }

    @Test
    void testShowEndDateAfterSearchForm() {
        String result = contractController.showEndDateAfterSearchForm(model);

        assertEquals("contract/end-date-after-search", result);
        verify(model).addAttribute(eq("contract"), any(Contract.class));
    }

    @Test
    void testProcessRequestEndDateAfterSearch() {
        Contract searchContract = new Contract();
        LocalDate afterDate = LocalDate.of(2024, 12, 1);
        searchContract.setEndDate(afterDate);
        List<Contract> contracts = Arrays.asList(new Contract(), new Contract());
        when(contractService.findAllByEndDateAfter(afterDate)).thenReturn(contracts);

        String result = contractController.processRequestEndDateAfterSearch(searchContract, model);

        assertEquals("contract/show-list", result);
        verify(model).addAttribute("contracts", contracts);
        verify(contractService).findAllByEndDateAfter(afterDate);
    }

    @Test
    void testShowStatusSearchForm() {
        String result = contractController.showStatusSearchForm(model);

        assertEquals("contract/status-search", result);
        verify(model).addAttribute(eq("contract"), any(Contract.class));
    }

    @Test
    void testProcessRequestStatusSearch() {
        Contract searchContract = new Contract();
        searchContract.setStatus("ACTIVE");
        List<Contract> contracts = Arrays.asList(new Contract(), new Contract());
        when(contractService.findAllByStatus("ACTIVE")).thenReturn(contracts);

        String result = contractController.processRequestStatusSearch(searchContract, model);

        assertEquals("contract/show-list", result);
        verify(model).addAttribute("contracts", contracts);
        verify(contractService).findAllByStatus("ACTIVE");
    }

    @Test
    void testShowCustomerSearchForm() {
        List<Customer> customers = Arrays.asList(new Customer(), new Customer());
        when(customerService.findAllByEnabledTrue()).thenReturn(customers);

        String result = contractController.showCustomerSearchForm(model);

        assertEquals("contract/customer-search", result);
        verify(model).addAttribute(eq("contract"), any(Contract.class));
        verify(model).addAttribute("customers", customers);
    }

    @Test
    void testProcessRequestCustomerSearch() {
        Contract searchContract = new Contract();
        Customer customer = new Customer();
        searchContract.setCustomer(customer);
        List<Contract> contracts = Arrays.asList(new Contract(), new Contract());
        when(contractService.findAllByCustomer(customer)).thenReturn(contracts);

        String result = contractController.processRequestCustomerSearch(searchContract, model);

        assertEquals("contract/show-list", result);
        verify(model).addAttribute("contracts", contracts);
        verify(contractService).findAllByCustomer(customer);
    }

    @Test
    void testShowCustomerUserSearchForm() {
        List<Customer> customers = Arrays.asList(new Customer(), new Customer());
        List<User> users = Arrays.asList(new User(), new User());
        when(customerService.findAllByEnabledTrue()).thenReturn(customers);
        when(userService.listAllUsers()).thenReturn(users);

        String result = contractController.showCustomerUserSearchForm(model);

        assertEquals("contract/customer-user-search", result);
        verify(model).addAttribute(eq("contract"), any(Contract.class));
        verify(model).addAttribute("customers", customers);
        verify(model).addAttribute("users", users);
    }

    @Test
    void testProcessRequestCustomerUserSearch() {
        Contract searchContract = new Contract();
        Customer customer = new Customer();
        User user = new User();
        searchContract.setCustomer(customer);
        searchContract.setUser(user);
        List<Contract> contracts = Arrays.asList(new Contract(), new Contract());
        when(contractService.findAllByCustomerAndUser(customer, user)).thenReturn(contracts);

        String result = contractController.processRequestCustomerUserSearch(searchContract, model);

        assertEquals("contract/show-list", result);
        verify(model).addAttribute("contracts", contracts);
        verify(contractService).findAllByCustomerAndUser(customer, user);
    }

    @Test
    void testShowUserSearchForm() {
        List<User> users = Arrays.asList(new User(), new User());
        when(userService.listAllUsers()).thenReturn(users);

        String result = contractController.showUserSearchForm(model);

        assertEquals("contract/user-search", result);
        verify(model).addAttribute(eq("contract"), any(Contract.class));
        verify(model).addAttribute("users", users);
    }

    @Test
    void testProcessRequestUserSearch() {
        Contract searchContract = new Contract();
        User user = new User();
        searchContract.setUser(user);
        List<Contract> contracts = Arrays.asList(new Contract(), new Contract());
        when(contractService.findAllByUser(user)).thenReturn(contracts);

        String result = contractController.processRequestUserSearch(searchContract, model);

        assertEquals("contract/show-list", result);
        verify(model).addAttribute("contracts", contracts);
        verify(contractService).findAllByUser(user);
    }
}