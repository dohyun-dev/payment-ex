package com.dohyundev.payment.payment.command.cash_receipt;

public interface CashReceiptIssueProcessor {
    CashReceipt issue();
    CashReceipt confirm();
}
