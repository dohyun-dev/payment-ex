package com.dohyundev.payment.payment.command;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum CashReceiptType {
    INCOME_DEDUCTION("소득공제"),
    EXPENSE_DOCUMENTATION("지출증빙");

    private final String label;
}