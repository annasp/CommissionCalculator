package com.example.commissioncalculator.service;

import com.example.commissioncalculator.model.RatesParser;
import com.example.commissioncalculator.model.Transaction;
import com.example.commissioncalculator.model.TransactionDto;
import com.example.commissioncalculator.repository.TransactionRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class CommissionCalculatorServiceTest {

    @MockBean
    private TransactionRepository transactionRepository;

    @MockBean
    private CommissionDiscountRulesService commissionDiscountRulesService;

    @MockBean
    private RatesParser ratesParser;

    private CommissionCalculatorService commissionCalculatorService;

    @Before
    public void setUp() {
        commissionCalculatorService = new CommissionCalculatorServiceImpl(transactionRepository, commissionDiscountRulesService, ratesParser);
    }

    @Test
    public void shouldConvertToBaseCurrency() throws Exception {

        // Given
        BigDecimal amount = BigDecimal.valueOf(2000.00);
        TransactionDto transactionDto = new TransactionDto(LocalDate.now(),
                amount, "USD", 42L);

        BigDecimal expectedRate = BigDecimal.valueOf(1.217582);

        when(ratesParser.getRate("USD")).thenReturn(expectedRate);

        // When
        BigDecimal amountInBaseCurrency = commissionCalculatorService.convertToBaseCurrency(transactionDto);

        // Then
        assertEquals(amountInBaseCurrency, amount.multiply(expectedRate));
    }

    @Test
    public void shouldCreateTransactionAndReturnCommission() throws Exception {

        // Given
        Transaction transaction = new Transaction();
        transaction.setDate(LocalDate.now());
        transaction.setAmount(BigDecimal.valueOf(2000.00));
        transaction.setCurrency("EUR");
        transaction.setClientId(42L);

        BigDecimal expectedCommission = BigDecimal.valueOf(0.05);

        when(commissionDiscountRulesService.getMinCommissionDiscount(transaction)).thenReturn(expectedCommission);
        when(transactionRepository.save(transaction)).thenReturn(transaction);

        // When
        BigDecimal commission = commissionCalculatorService.createTransactionAndReturnCommission(transaction);

        // Then
        assertEquals(commission, expectedCommission);

    }
}
