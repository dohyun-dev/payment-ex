package com.dohyundev.payment.payment.command;

import com.dohyundev.payment.payment.command.payment.PaymentId;
import com.github.f4b6a3.tsid.TsidCreator;

public class PaymentIdFactory {
    public static PaymentId create() {
        return new PaymentId(TsidCreator.getTsid().toLong());
    }
}
