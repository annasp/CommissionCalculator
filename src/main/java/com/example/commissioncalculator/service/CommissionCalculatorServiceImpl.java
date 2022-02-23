package com.example.commissioncalculator.service;

import com.example.commissioncalculator.model.RatesParser;
import com.example.commissioncalculator.model.Transaction;
import com.example.commissioncalculator.model.TransactionDto;
import com.example.commissioncalculator.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;

@Service
@Transactional
public class CommissionCalculatorServiceImpl implements CommissionCalculatorService {

    private final TransactionRepository transactionRepository;

    private final CommissionDiscountRulesService commissionDiscountRulesService;

    private final RatesParser ratesParser;

    @Autowired
    public CommissionCalculatorServiceImpl(TransactionRepository transactionRepository,
                                           CommissionDiscountRulesService commissionDiscountRulesService,
                                           RatesParser ratesParser) {
        this.transactionRepository = transactionRepository;
        this.commissionDiscountRulesService = commissionDiscountRulesService;
        this.ratesParser = ratesParser;
    }

    public BigDecimal convertToBaseCurrency (TransactionDto transactionDto) throws Exception {
        BigDecimal rate = ratesParser.getRate(transactionDto.getCurrency());

        return transactionDto.getAmount().multiply(rate);
    }

    public BigDecimal createTransactionAndReturnCommission(Transaction transaction) {

        BigDecimal minDiscount = commissionDiscountRulesService.getMinCommissionDiscount(transaction);

        transactionRepository.save(transaction);

        return minDiscount;
    }

}
