package com.dohyundev.payment.payment.command.cash_receipt;

import com.dohyundev.payment.payment.command.CashReceiptType;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class CashReceipt {
    private CashReceiptIssueId id;
    private CashReceiptTransactionType transactionType;
    private CashReceiptType type;
    private String identityNumber;
    private CashReceiptIssueState state;
    private BigDecimal amount;
    private LocalDateTime requestAt;

    public CashReceipt(CashReceiptTransactionType transactionType, CashReceiptType type, String identityNumber, BigDecimal amount) {
        this.id = CashReceiptIssueId.create();
        this.transactionType = transactionType;
        this.type = type;
        this.identityNumber = identityNumber;
        this.amount = amount;
        this.state = CashReceiptIssueState.IN_PROGRESS;
        this.requestAt = LocalDateTime.now();
    }

    public static CashReceipt create(CashReceiptType type, String identityNumber, BigDecimal amount) {
        return new CashReceipt(CashReceiptTransactionType.CONFIRM, type, identityNumber, amount);
    }

    public CashReceipt cancel(BigDecimal cancelAmount) {
        // TODO
    }
}
