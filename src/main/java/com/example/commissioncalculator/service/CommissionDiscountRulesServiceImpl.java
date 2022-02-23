package com.example.commissioncalculator.service;

import com.example.commissioncalculator.model.Transaction;
import com.example.commissioncalculator.model.rules.CommissionCalculationRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class CommissionDiscountRulesServiceImpl implements CommissionDiscountRulesService {

    private final List<CommissionCalculationRule> commissionCalculationRuleList = new ArrayList<>();

    @Autowired
    public CommissionDiscountRulesServiceImpl(
            @Qualifier("clientDiscountRule") CommissionCalculationRule clientDiscountRule,
            @Qualifier("defaultDiscountRule") CommissionCalculationRule defaultDiscountRule,
            @Qualifier("highTurnoverDiscountRule") CommissionCalculationRule highTurnoverDiscountRule) {

        commissionCalculationRuleList.add(clientDiscountRule);
        commissionCalculationRuleList.add(defaultDiscountRule);
        commissionCalculationRuleList.add(highTurnoverDiscountRule);
    }

    @Override
    public BigDecimal getMinCommissionDiscount(Transaction transaction) {
        return commissionCalculationRuleList.stream()
                .map(rule -> rule.calculateDiscount(transaction))
                .collect(Collectors.toList()).stream()
                .filter(Objects::nonNull)
                .min(Comparator.naturalOrder())
                .orElse(null);
    }
}
