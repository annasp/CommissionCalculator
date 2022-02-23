package com.example.commissioncalculator.model.rules;

import com.example.commissioncalculator.model.Transaction;
import com.example.commissioncalculator.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component("highTurnoverDiscountRule")
public class HighTurnoverDiscountRule implements CommissionCalculationRule {

    private final TransactionRepository transactionRepository;

    @Autowired
    public HighTurnoverDiscountRule(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public BigDecimal calculateDiscount(Transaction transaction) {
        List<Transaction> transactions = transactionRepository.findByMonthAndClientId(transaction.getDate().getMonthValue(), transaction.getClientId());
        BigDecimal sumOfTransactionsPerMonth = transactions.stream().map(Transaction::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);

        return (sumOfTransactionsPerMonth.compareTo(BigDecimal.valueOf(1000)) >= 0) ? BigDecimal.valueOf(0.03) : null;
    }
}
