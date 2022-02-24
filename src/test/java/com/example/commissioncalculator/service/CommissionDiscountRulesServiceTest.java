package com.example.commissioncalculator.service;

import com.example.commissioncalculator.model.Transaction;
import com.example.commissioncalculator.model.rules.CommissionCalculationRule;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class CommissionDiscountRulesServiceTest {

    @MockBean(name = "clientDiscountRule")
    private CommissionCalculationRule clientDiscountRule;

    @MockBean(name = "defaultDiscountRule")
    private CommissionCalculationRule defaultDiscountRule;

    @MockBean(name = "highTurnoverDiscountRule")
    private CommissionCalculationRule highTurnoverDiscountRule;

    private CommissionDiscountRulesService commissionDiscountRulesService;

    @Before
    public void setup() {
        commissionDiscountRulesService = new CommissionDiscountRulesServiceImpl(clientDiscountRule,
                defaultDiscountRule, highTurnoverDiscountRule);
    }

    @Test
    public void shouldGetMinCommissionDiscount() {
        // Given
        BigDecimal amount = BigDecimal.valueOf(2000.00);
        Transaction transaction = new Transaction(LocalDate.now(),
                amount, "USD", 42L);

        BigDecimal expectedMinCommissionDiscount = BigDecimal.valueOf(0.05);

        List<CommissionCalculationRule> commissionCalculationRuleList = new ArrayList<>();
        commissionCalculationRuleList.add(clientDiscountRule);
        commissionCalculationRuleList.add(defaultDiscountRule);
        commissionCalculationRuleList.add(highTurnoverDiscountRule);

        when(clientDiscountRule.calculateDiscount(transaction)).thenReturn(expectedMinCommissionDiscount);
        when(defaultDiscountRule.calculateDiscount(transaction)).thenReturn(BigDecimal.valueOf(10));
        when(defaultDiscountRule.calculateDiscount(transaction)).thenReturn(null);

        // When
        BigDecimal minCommissionDiscount = commissionDiscountRulesService.getMinCommissionDiscount(transaction);

        // Then
        assertEquals(minCommissionDiscount, expectedMinCommissionDiscount);
    }
}
