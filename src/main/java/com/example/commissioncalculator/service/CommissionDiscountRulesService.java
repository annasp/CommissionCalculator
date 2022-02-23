package com.example.commissioncalculator.service;

import com.example.commissioncalculator.model.Transaction;

import java.math.BigDecimal;

public interface CommissionDiscountRulesService {

    BigDecimal getMinCommissionDiscount(Transaction transaction);
}
