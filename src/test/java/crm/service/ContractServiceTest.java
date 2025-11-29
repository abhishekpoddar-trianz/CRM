package crm.service;

import crm.entity.Contract;
import crm.entity.Customer;
import crm.entity.Status;
import crm.entity.User;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ContractServiceTest {

    @Test
    void testContractServiceInterface() {
        assertTrue(ContractService.class.isInterface());
    }

    @Test
    void testFindByNameMethodExists() {
        assertDoesNotThrow(() -> {
            ContractService.class.getDeclaredMethod("findByName", String.class);
        });
    }

    @Test
    void testListAllContractsMethodExists() {
        assertDoesNotThrow(() -> {
            ContractService.class.getDeclaredMethod("listAllContracts");
        });
    }

    @Test
    void testShowContractMethodExists() {
        assertDoesNotThrow(() -> {
            ContractService.class.getDeclaredMethod("showContract", Long.class);
        });
    }

    @Test
    void testFindAllByValueLessThanEqualMethodExists() {
        assertDoesNotThrow(() -> {
            ContractService.class.getDeclaredMethod("findAllByValueLessThanEqual", BigDecimal.class);
        });
    }

    @Test
    void testFindAllByValueGreaterThanEqualMethodExists() {
        assertDoesNotThrow(() -> {
            ContractService.class.getDeclaredMethod("findAllByValueGreaterThanEqual", BigDecimal.class);
        });
    }

    @Test
    void testFindAllByBeginDateMethodExists() {
        assertDoesNotThrow(() -> {
            ContractService.class.getDeclaredMethod("findAllByBeginDate", LocalDate.class);
        });
    }

    @Test
    void testFindAllByBeginDateBeforeMethodExists() {
        assertDoesNotThrow(() -> {
            ContractService.class.getDeclaredMethod("findAllByBeginDateBefore", LocalDate.class);
        });
    }

    @Test
    void testFindAllByBeginDateAfterMethodExists() {
        assertDoesNotThrow(() -> {
            ContractService.class.getDeclaredMethod("findAllByBeginDateAfter", LocalDate.class);
        });
    }

    @Test
    void testFindAllByStatusMethodExists() {
        assertDoesNotThrow(() -> {
            ContractService.class.getDeclaredMethod("findAllByStatus", Status.class);
        });
    }

    @Test
    void testFindAllByCustomerMethodExists() {
        assertDoesNotThrow(() -> {
            ContractService.class.getDeclaredMethod("findAllByCustomer", Customer.class);
        });
    }

    @Test
    void testFindAllByUserMethodExists() {
        assertDoesNotThrow(() -> {
            ContractService.class.getDeclaredMethod("findAllByUser", User.class);
        });
    }

    @Test
    void testSaveContractMethodExists() {
        assertDoesNotThrow(() -> {
            ContractService.class.getDeclaredMethod("saveContract", Contract.class);
        });
    }

    @Test
    void testInterfaceHasCorrectNumberOfMethods() {
        assertEquals(13, ContractService.class.getDeclaredMethods().length);
    }

    @Test
    void testReturnTypes() throws Exception {
        assertEquals(Contract.class, ContractService.class.getDeclaredMethod("findByName", String.class).getReturnType());
        assertEquals(Iterable.class, ContractService.class.getDeclaredMethod("listAllContracts").getReturnType());
        assertEquals(Contract.class, ContractService.class.getDeclaredMethod("showContract", Long.class).getReturnType());
        assertEquals(void.class, ContractService.class.getDeclaredMethod("saveContract", Contract.class).getReturnType());
    }
}