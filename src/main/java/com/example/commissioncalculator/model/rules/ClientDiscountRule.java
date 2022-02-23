package com.example.commissioncalculator.model.rules;

import com.example.commissioncalculator.model.Transaction;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Objects;

@Component("clientDiscountRule")
public class ClientDiscountRule implements CommissionCalculationRule {

    @Override
    public BigDecimal calculateDiscount(Transaction transaction) {
        final Long clientId = 42L;
        return (Objects.equals(transaction.getClientId(), clientId)) ? BigDecimal.valueOf(0.05) : null;
    }
}
