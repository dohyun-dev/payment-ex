package com.dohyundev.payment.payment.command.cash_receipt;

import com.dohyundev.payment.common.BaseId;
import com.github.f4b6a3.tsid.TsidCreator;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CashReceiptIssueId extends BaseId {
    public CashReceiptIssueId(Long value) {
        super(value);
    }

    public static CashReceiptIssueId create() {
        return new CashReceiptIssueId(TsidCreator.getTsid().toLong());
    }
}
