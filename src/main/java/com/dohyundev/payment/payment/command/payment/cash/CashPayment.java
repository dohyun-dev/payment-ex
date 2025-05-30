package com.dohyundev.payment.payment.command.payment.cash;


import com.dohyundev.payment.payment.command.payment.Payment;
import com.dohyundev.payment.payment.command.payment.PaymentId;
import com.dohyundev.payment.payment.command.payment.PaymentMethod;
import com.dohyundev.payment.pos.TerminalId;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("CARD")
public class CashPayment extends Payment {

    public CashPayment(PaymentId paymentId, TerminalId terminalId, BigDecimal amount) {
        super(PaymentMethod.CARD, paymentId, terminalId, amount);
    }
}
