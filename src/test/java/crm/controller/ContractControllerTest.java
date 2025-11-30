package crm.controller;

import crm.entity.Contract;
import crm.entity.Customer;
import crm.entity.Status;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ContractControllerTest {

    private ContractController contractController;

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
        when(contractService.listAllContracts()).thenReturn(Arrays.asList());

        String viewName = contractController.showAllContracts(model);

        assertEquals("contract/list", viewName);
        verify(model).addAttribute(eq("contracts"), any());
    }

    @Test
    void testShowFormAddContract() {
        when(customerService.findAllByEnabledTrue()).thenReturn(Arrays.asList());
        when(userService.listAllUsers()).thenReturn(Arrays.asList());

        String viewName = contractController.showFormAddContract(model);

        assertEquals("contract/add", viewName);
        verify(model).addAttribute(eq("contract"), any());
    }

    @Test
    void testProcessRequestAddContractWithValidData() {
        Contract contract = new Contract();
        when(bindingResult.hasErrors()).thenReturn(false);

        String viewName = contractController.processRequestAddContract(contract, bindingResult);

        assertEquals("contract/success", viewName);
        verify(contractService).saveContract(contract);
    }

    @Test
    void testProcessRequestAddContractWithErrors() {
        Contract contract = new Contract();
        when(bindingResult.hasErrors()).thenReturn(true);

        String viewName = contractController.processRequestAddContract(contract, bindingResult);

        assertEquals("redirect:/contract/add", viewName);
    }

    @Test
    void testShowFormEditContract() {
        Contract contract = new Contract();
        when(contractService.showContract(1L)).thenReturn(contract);

        String viewName = contractController.showFormEditContract(model, 1L);

        assertEquals("contract/edit", viewName);
        verify(model).addAttribute("contract", contract);
    }

    @Test
    void testProcessRequestEditContractWithValidData() {
        Contract contract = new Contract();
        when(bindingResult.hasErrors()).thenReturn(false);

        String viewName = contractController.processRequestEditContract(1L, contract, bindingResult);

        assertEquals("redirect:/contract/list", viewName);
        verify(contractService).saveContract(contract);
    }

    @Test
    void testShowNameSearchForm() {
        String viewName = contractController.showNameSearchForm(model);

        assertEquals("contract/name-search", viewName);
    }

    @Test
    void testProcessRequestNameSearch() {
        Contract contract = new Contract();
        contract.setName("Test");
        when(contractService.findByName("Test")).thenReturn(contract);

        String viewName = contractController.processRequestNameSearch(contract, model);

        assertEquals("contract/show-one", viewName);
    }

    @Test
    void testProcessRequestValueLessThanEqualSearch() {
        Contract contract = new Contract();
        contract.setValue(new BigDecimal("1000"));
        when(contractService.findAllByValueLessThanEqual(any())).thenReturn(Arrays.asList());

        String viewName = contractController.processRequestValueLessThanEqualSearch(contract, model);

        assertEquals("contract/show-list", viewName);
    }

    @Test
    void testProcessRequestBeginDateSearch() {
        Contract contract = new Contract();
        contract.setBeginDate(LocalDate.now());
        when(contractService.findAllByBeginDate(any())).thenReturn(Arrays.asList());

        String viewName = contractController.processRequestBeginDateSearch(contract, model);

        assertEquals("contract/show-list", viewName);
    }

    @Test
    void testProcessRequestStatusSearch() {
        Contract contract = new Contract();
        contract.setStatus(Status.PROPOSED);
        when(contractService.findAllByStatus(Status.PROPOSED)).thenReturn(Arrays.asList());

        String viewName = contractController.processRequestStatusSearch(contract, model);

        assertEquals("contract/show-list", viewName);
    }

    @Test
    void testShowCustomerSearchForm() {
        when(customerService.findAllByEnabledTrue()).thenReturn(Arrays.asList());

        String viewName = contractController.showCustomerSearchForm(model);

        assertEquals("contract/customer-search", viewName);
    }

    @Test
    void testProcessRequestCustomerSearch() {
        Contract contract = new Contract();
        Customer customer = new Customer();
        contract.setCustomer(customer);
        when(contractService.findAllByCustomer(customer)).thenReturn(Arrays.asList());

        String viewName = contractController.processRequestCustomerSearch(contract, model);

        assertEquals("contract/show-list", viewName);
    }

    @Test
    void testShowUserSearchForm() {
        when(userService.listAllUsers()).thenReturn(Arrays.asList());

        String viewName = contractController.showUserSearchForm(model);

        assertEquals("contract/user-search", viewName);
    }

    @Test
    void testProcessRequestUserSearch() {
        Contract contract = new Contract();
        User user = new User();
        contract.setUser(user);
        when(contractService.findAllByUser(user)).thenReturn(Arrays.asList());

        String viewName = contractController.processRequestUserSearch(contract, model);

        assertEquals("contract/show-list", viewName);
    }
}
