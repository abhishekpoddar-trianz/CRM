package crm.repository;

import crm.entity.Contract;
import crm.entity.Customer;
import crm.entity.Status;
import crm.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DataJpaTest
class ContractRepositoryTest {

    @MockBean
    private ContractRepository contractRepository;

    @Test
    void testFindByName() {
        Contract mockContract = new Contract();
        mockContract.setId(1L);
        mockContract.setName("Test Contract");

        when(contractRepository.findByName("Test Contract")).thenReturn(mockContract);

        Contract result = contractRepository.findByName("Test Contract");

        assertNotNull(result);
        assertEquals("Test Contract", result.getName());
        assertEquals(1L, result.getId());
        verify(contractRepository, times(1)).findByName("Test Contract");
    }

    @Test
    void testFindByNameNotFound() {
        when(contractRepository.findByName("Nonexistent Contract")).thenReturn(null);

        Contract result = contractRepository.findByName("Nonexistent Contract");

        assertNull(result);
        verify(contractRepository, times(1)).findByName("Nonexistent Contract");
    }

    @Test
    void testFindAllByValueLessThanEqual() {
        BigDecimal value = new BigDecimal("1000.00");
        List<Contract> contracts = new ArrayList<>();
        Contract contract = new Contract();
        contract.setId(1L);
        contract.setValue(new BigDecimal("500.00"));
        contracts.add(contract);

        when(contractRepository.findAllByValueLessThanEqual(value)).thenReturn(contracts);

        Iterable<Contract> result = contractRepository.findAllByValueLessThanEqual(value);

        assertNotNull(result);
        List<Contract> resultList = new ArrayList<>();
        result.forEach(resultList::add);
        assertEquals(1, resultList.size());
        assertTrue(resultList.get(0).getValue().compareTo(value) <= 0);
        verify(contractRepository, times(1)).findAllByValueLessThanEqual(value);
    }

    @Test
    void testFindAllByValueGreaterThanEqual() {
        BigDecimal value = new BigDecimal("1000.00");
        List<Contract> contracts = new ArrayList<>();
        Contract contract = new Contract();
        contract.setId(1L);
        contract.setValue(new BigDecimal("1500.00"));
        contracts.add(contract);

        when(contractRepository.findAllByValueGreaterThanEqual(value)).thenReturn(contracts);

        Iterable<Contract> result = contractRepository.findAllByValueGreaterThanEqual(value);

        assertNotNull(result);
        List<Contract> resultList = new ArrayList<>();
        result.forEach(resultList::add);
        assertEquals(1, resultList.size());
        assertTrue(resultList.get(0).getValue().compareTo(value) >= 0);
        verify(contractRepository, times(1)).findAllByValueGreaterThanEqual(value);
    }

    @Test
    void testFindAllByBeginDate() {
        LocalDate beginDate = LocalDate.of(2023, 1, 1);
        List<Contract> contracts = new ArrayList<>();
        Contract contract = new Contract();
        contract.setId(1L);
        contract.setBeginDate(beginDate);
        contracts.add(contract);

        when(contractRepository.findAllByBeginDate(beginDate)).thenReturn(contracts);

        Iterable<Contract> result = contractRepository.findAllByBeginDate(beginDate);

        assertNotNull(result);
        List<Contract> resultList = new ArrayList<>();
        result.forEach(resultList::add);
        assertEquals(1, resultList.size());
        assertEquals(beginDate, resultList.get(0).getBeginDate());
        verify(contractRepository, times(1)).findAllByBeginDate(beginDate);
    }

    @Test
    void testFindAllByBeginDateBefore() {
        LocalDate beforeDate = LocalDate.of(2023, 6, 1);
        List<Contract> contracts = new ArrayList<>();
        Contract contract = new Contract();
        contract.setId(1L);
        contract.setBeginDate(LocalDate.of(2023, 1, 1));
        contracts.add(contract);

        when(contractRepository.findAllByBeginDateBefore(beforeDate)).thenReturn(contracts);

        Iterable<Contract> result = contractRepository.findAllByBeginDateBefore(beforeDate);

        assertNotNull(result);
        List<Contract> resultList = new ArrayList<>();
        result.forEach(resultList::add);
        assertEquals(1, resultList.size());
        assertTrue(resultList.get(0).getBeginDate().isBefore(beforeDate));
        verify(contractRepository, times(1)).findAllByBeginDateBefore(beforeDate);
    }

    @Test
    void testFindAllByBeginDateAfter() {
        LocalDate afterDate = LocalDate.of(2023, 1, 1);
        List<Contract> contracts = new ArrayList<>();
        Contract contract = new Contract();
        contract.setId(1L);
        contract.setBeginDate(LocalDate.of(2023, 6, 1));
        contracts.add(contract);

        when(contractRepository.findAllByBeginDateAfter(afterDate)).thenReturn(contracts);

        Iterable<Contract> result = contractRepository.findAllByBeginDateAfter(afterDate);

        assertNotNull(result);
        List<Contract> resultList = new ArrayList<>();
        result.forEach(resultList::add);
        assertEquals(1, resultList.size());
        assertTrue(resultList.get(0).getBeginDate().isAfter(afterDate));
        verify(contractRepository, times(1)).findAllByBeginDateAfter(afterDate);
    }

    @Test
    void testFindAllByEndDate() {
        LocalDate endDate = LocalDate.of(2023, 12, 31);
        List<Contract> contracts = new ArrayList<>();
        Contract contract = new Contract();
        contract.setId(1L);
        contract.setEndDate(endDate);
        contracts.add(contract);

        when(contractRepository.findAllByEndDate(endDate)).thenReturn(contracts);

        Iterable<Contract> result = contractRepository.findAllByEndDate(endDate);

        assertNotNull(result);
        List<Contract> resultList = new ArrayList<>();
        result.forEach(resultList::add);
        assertEquals(1, resultList.size());
        assertEquals(endDate, resultList.get(0).getEndDate());
        verify(contractRepository, times(1)).findAllByEndDate(endDate);
    }

    @Test
    void testFindAllByStatus() {
        Status status = Status.PROPOSED;
        List<Contract> contracts = new ArrayList<>();
        Contract contract = new Contract();
        contract.setId(1L);
        contract.setStatus(status);
        contracts.add(contract);

        when(contractRepository.findAllByStatus(status)).thenReturn(contracts);

        Iterable<Contract> result = contractRepository.findAllByStatus(status);

        assertNotNull(result);
        List<Contract> resultList = new ArrayList<>();
        result.forEach(resultList::add);
        assertEquals(1, resultList.size());
        assertEquals(status, resultList.get(0).getStatus());
        verify(contractRepository, times(1)).findAllByStatus(status);
    }

    @Test
    void testFindAllByCustomer() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Test Customer");

        List<Contract> contracts = new ArrayList<>();
        Contract contract = new Contract();
        contract.setId(1L);
        contract.setCustomer(customer);
        contracts.add(contract);

        when(contractRepository.findAllByCustomer(customer)).thenReturn(contracts);

        Iterable<Contract> result = contractRepository.findAllByCustomer(customer);

        assertNotNull(result);
        List<Contract> resultList = new ArrayList<>();
        result.forEach(resultList::add);
        assertEquals(1, resultList.size());
        assertEquals(customer, resultList.get(0).getCustomer());
        verify(contractRepository, times(1)).findAllByCustomer(customer);
    }

    @Test
    void testFindAllByUser() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");

        List<Contract> contracts = new ArrayList<>();
        Contract contract = new Contract();
        contract.setId(1L);
        contract.setUser(user);
        contracts.add(contract);

        when(contractRepository.findAllByUser(user)).thenReturn(contracts);

        Iterable<Contract> result = contractRepository.findAllByUser(user);

        assertNotNull(result);
        List<Contract> resultList = new ArrayList<>();
        result.forEach(resultList::add);
        assertEquals(1, resultList.size());
        assertEquals(user, resultList.get(0).getUser());
        verify(contractRepository, times(1)).findAllByUser(user);
    }

    @Test
    void testFindAllByCustomerAndUser() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Test Customer");

        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");

        List<Contract> contracts = new ArrayList<>();
        Contract contract = new Contract();
        contract.setId(1L);
        contract.setCustomer(customer);
        contract.setUser(user);
        contracts.add(contract);

        when(contractRepository.findAllByCustomerAndUser(customer, user)).thenReturn(contracts);

        Iterable<Contract> result = contractRepository.findAllByCustomerAndUser(customer, user);

        assertNotNull(result);
        List<Contract> resultList = new ArrayList<>();
        result.forEach(resultList::add);
        assertEquals(1, resultList.size());
        assertEquals(customer, resultList.get(0).getCustomer());
        assertEquals(user, resultList.get(0).getUser());
        verify(contractRepository, times(1)).findAllByCustomerAndUser(customer, user);
    }

    @Test
    void testRepositoryInterface() {
        assertTrue(ContractRepository.class.isInterface());
    }

    @Test
    void testExtendsJpaRepository() {
        assertTrue(org.springframework.data.jpa.repository.JpaRepository.class.isAssignableFrom(ContractRepository.class));
    }

    @Test
    void testRepositoryAnnotation() {
        assertTrue(ContractRepository.class.isAnnotationPresent(org.springframework.stereotype.Repository.class));
    }
}