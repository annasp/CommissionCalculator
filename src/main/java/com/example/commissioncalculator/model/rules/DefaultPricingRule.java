package com.example.commissioncalculator.model.rules;

import com.example.commissioncalculator.model.Transaction;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component("defaultDiscountRule")
public class DefaultPricingRule implements CommissionCalculationRule {

    private final BigDecimal defaultPercentage = BigDecimal.valueOf(0.5);
    private final BigDecimal lowestCommission = BigDecimal.valueOf(0.05);

    @Override
    public BigDecimal calculateDiscount(Transaction transaction) {
        BigDecimal result = (transaction.getAmount().multiply(defaultPercentage)).divide(BigDecimal.valueOf(100), 2, RoundingMode.CEILING);
        return result.compareTo(lowestCommission) < 0 ? lowestCommission : result;
    }
}
