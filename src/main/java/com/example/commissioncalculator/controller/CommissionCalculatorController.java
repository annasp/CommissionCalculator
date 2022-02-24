package com.example.commissioncalculator.controller;

import com.example.commissioncalculator.model.CommissionResponse;
import com.example.commissioncalculator.model.Transaction;
import com.example.commissioncalculator.model.TransactionDto;
import com.example.commissioncalculator.service.CommissionCalculatorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;


@RestController
@RequestMapping("/commission")
public class CommissionCalculatorController {

    private final CommissionCalculatorService commissionCalculatorService;

    private final ModelMapper modelMapper;

    @Autowired
    public CommissionCalculatorController(CommissionCalculatorService commissionCalculatorService,
                                          ModelMapper modelMapper) {
        this.commissionCalculatorService = commissionCalculatorService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public CommissionResponse commitTransaction(@RequestBody TransactionDto transactionDto) throws Exception {
        BigDecimal amountInBaseCurrency = commissionCalculatorService.convertToBaseCurrency(transactionDto);
        transactionDto.setAmount(amountInBaseCurrency);

        Transaction transaction = convertToEntity(transactionDto);
        BigDecimal x = commissionCalculatorService.createTransactionAndReturnCommission(transaction);
        return new CommissionResponse(x, "EUR");
    }

    private Transaction convertToEntity(TransactionDto transactionDto) {
        return modelMapper.map(transactionDto, Transaction.class);
    }

    private TransactionDto convertToDto(Transaction transaction) {
        return modelMapper.map(transaction, TransactionDto.class);
    }
}
