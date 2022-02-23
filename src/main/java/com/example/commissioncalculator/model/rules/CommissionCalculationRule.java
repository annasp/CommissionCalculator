package com.example.commissioncalculator.model.rules;

import com.example.commissioncalculator.model.Transaction;

import java.math.BigDecimal;

@FunctionalInterface
public interface CommissionCalculationRule {

    BigDecimal calculateDiscount(Transaction transaction);
}
