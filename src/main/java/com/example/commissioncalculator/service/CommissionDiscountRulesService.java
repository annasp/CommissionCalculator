package com.example.commissioncalculator.service;

import com.example.commissioncalculator.model.Transaction;
import java.math.BigDecimal;

/**
 * Service used to handle {@code CommissionCalculationRule}s
 */
public interface CommissionDiscountRulesService {

    /**
     * Method to run the {@code CommissionCalculationRule}s and filter them getting the minimum commission result.
     * @param transaction the provided transaction
     * @return
     */
    BigDecimal getMinCommissionDiscount(Transaction transaction);
}
