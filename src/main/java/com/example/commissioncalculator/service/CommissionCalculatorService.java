package com.example.commissioncalculator.service;

import com.example.commissioncalculator.model.Transaction;
import com.example.commissioncalculator.model.TransactionDto;

import java.math.BigDecimal;

public interface CommissionCalculatorService {

    BigDecimal convertToBaseCurrency (TransactionDto transactionDto) throws Exception;

    BigDecimal createTransactionAndReturnCommission(Transaction transaction) throws Exception;
}
