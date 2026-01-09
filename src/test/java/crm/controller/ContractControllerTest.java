package crm.controller;

import crm.entity.*;
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
import java.util.List;

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
        List<Contract> contracts = Arrays.asList(
                Contract.builder().id(1L).name("Contract1").build(),
                Contract.builder().id(2L).name("Contract2").build()
        );

        when(contractService.listAllContracts()).thenReturn(contracts);

        String result = contractController.showAllContracts(model);

        assertEquals("contract/list", result);
        verify(model).addAttribute("contracts", contracts);
        verify(contractService).listAllContracts();
    }

    @Test
    void testShowFormAddContract() {
        List<Customer> customers = Arrays.asList(Customer.builder().id(1L).name("Customer1").build());
        List<User> users = Arrays.asList(User.builder().id(1L).username("user1").build());

        when(customerService.findAllByEnabledTrue()).thenReturn(customers);
        when(userService.listAllUsers()).thenReturn(users);

        String result = contractController.showFormAddContract(model);

        assertEquals("contract/add", result);
        verify(model).addAttribute(eq("contract"), any(Contract.class));
        verify(model).addAttribute("customers", customers);
        verify(model).addAttribute("users", users);
    }

    @Test
    void testProcessRequestAddContractWithErrors() {
        Contract contract = Contract.builder().name("Test").build();
        when(bindingResult.hasErrors()).thenReturn(true);

        String result = contractController.processRequestAddContract(contract, bindingResult);

        assertEquals("redirect:/contract/add", result);
        verify(bindingResult).hasErrors();
        verify(contractService, never()).saveContract(any());
    }

    @Test
    void testProcessRequestAddContractSuccess() {
        Contract contract = Contract.builder()
                .name("NewContract")
                .value(BigDecimal.valueOf(10000))
                .build();
        when(bindingResult.hasErrors()).thenReturn(false);

        String result = contractController.processRequestAddContract(contract, bindingResult);

        assertEquals("contract/success", result);
        verify(bindingResult).hasErrors();
        verify(contractService).saveContract(contract);
    }

    @Test
    void testShowFormEditContract() {
        Long contractId = 1L;
        Contract contract = Contract.builder().id(contractId).name("EditContract").build();

        when(contractService.showContract(contractId)).thenReturn(contract);

        String result = contractController.showFormEditContract(model, contractId);

        assertEquals("contract/edit", result);
        verify(model).addAttribute("contract", contract);
        verify(contractService).showContract(contractId);
    }

    @Test
    void testProcessRequestEditContractWithErrors() {
        Long contractId = 1L;
        Contract contract = Contract.builder().id(contractId).name("Test").build();
        when(bindingResult.hasErrors()).thenReturn(true);

        String result = contractController.processRequestEditContract(contractId, contract, bindingResult);

        assertEquals("redirect:/contract/edit/" + contractId, result);
        verify(bindingResult).hasErrors();
        verify(contractService, never()).saveContract(any());
    }

    @Test
    void testProcessRequestEditContractSuccess() {
        Long contractId = 1L;
        Contract contract = Contract.builder()
                .id(contractId)
                .name("EditedContract")
                .value(BigDecimal.valueOf(20000))
                .build();
        when(bindingResult.hasErrors()).thenReturn(false);

        String result = contractController.processRequestEditContract(contractId, contract, bindingResult);

        assertEquals("redirect:/contract/list", result);
        verify(bindingResult).hasErrors();
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
        Contract contract = Contract.builder().name("SearchContract").build();
        Contract foundContract = Contract.builder().id(1L).name("SearchContract").build();

        when(contractService.findByName("SearchContract")).thenReturn(foundContract);

        String result = contractController.processRequestNameSearch(contract, model);

        assertEquals("contract/show-one", result);
        verify(model).addAttribute("contract", foundContract);
        verify(contractService).findByName("SearchContract");
    }

    @Test
    void testShowValueLeesThanEqualSearchForm() {
        String result = contractController.showValueLeesThanEqualSearchForm(model);

        assertEquals("contract/value-le-search", result);
        verify(model).addAttribute(eq("contract"), any(Contract.class));
    }

    @Test
    void testProcessRequestValueLessThanEqualSearch() {
        BigDecimal value = BigDecimal.valueOf(10000);
        Contract contract = Contract.builder().value(value).build();
        List<Contract> contracts = Arrays.asList(
                Contract.builder().id(1L).value(BigDecimal.valueOf(5000)).build()
        );

        when(contractService.findAllByValueLessThanEqual(value)).thenReturn(contracts);

        String result = contractController.processRequestValueLessThanEqualSearch(contract, model);

        assertEquals("contract/show-list", result);
        verify(model).addAttribute("contracts", contracts);
        verify(contractService).findAllByValueLessThanEqual(value);
    }

    @Test
    void testShowBeginDateSearchForm() {
        String result = contractController.showBeginDateSearchForm(model);

        assertEquals("contract/begin-date-search", result);
        verify(model).addAttribute(eq("contract"), any(Contract.class));
    }

    @Test
    void testProcessRequestBeginDateSearch() {
        LocalDate beginDate = LocalDate.of(2024, 1, 1);
        Contract contract = Contract.builder().beginDate(beginDate).build();
        List<Contract> contracts = Arrays.asList(
                Contract.builder().id(1L).beginDate(beginDate).build()
        );

        when(contractService.findAllByBeginDate(beginDate)).thenReturn(contracts);

        String result = contractController.processRequestBeginDateSearch(contract, model);

        assertEquals("contract/show-list", result);
        verify(model).addAttribute("contracts", contracts);
        verify(contractService).findAllByBeginDate(beginDate);
    }

    @Test
    void testShowStatusSearchForm() {
        String result = contractController.showStatusSearchForm(model);

        assertEquals("contract/status-search", result);
        verify(model).addAttribute(eq("contract"), any(Contract.class));
    }

    @Test
    void testProcessRequestStatusSearch() {
        Status status = Status.DONE;
        Contract contract = Contract.builder().status(status).build();
        List<Contract> contracts = Arrays.asList(
                Contract.builder().id(1L).status(status).build()
        );

        when(contractService.findAllByStatus(status)).thenReturn(contracts);

        String result = contractController.processRequestStatusSearch(contract, model);

        assertEquals("contract/show-list", result);
        verify(model).addAttribute("contracts", contracts);
        verify(contractService).findAllByStatus(status);
    }

    @Test
    void testShowCustomerSearchForm() {
        List<Customer> customers = Arrays.asList(Customer.builder().id(1L).name("Customer1").build());
        when(customerService.findAllByEnabledTrue()).thenReturn(customers);

        String result = contractController.showCustomerSearchForm(model);

        assertEquals("contract/customer-search", result);
        verify(model).addAttribute(eq("contract"), any(Contract.class));
        verify(model).addAttribute("customers", customers);
    }

    @Test
    void testShowUserSearchForm() {
        List<User> users = Arrays.asList(User.builder().id(1L).username("user1").build());
        when(userService.listAllUsers()).thenReturn(users);

        String result = contractController.showUserSearchForm(model);

        assertEquals("contract/user-search", result);
        verify(model).addAttribute(eq("contract"), any(Contract.class));
        verify(model).addAttribute("users", users);
    }
}
