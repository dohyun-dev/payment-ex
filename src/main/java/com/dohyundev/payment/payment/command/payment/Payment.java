package com.dohyundev.payment.payment.command.payment;

import com.dohyundev.payment.pos.TerminalId;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Getter
public abstract class Payment {
    private PaymentMethod method;

    @EmbeddedId
    @AttributeOverride(name = "value", column = @Column(name = "tx_id"))
    private PaymentId paymentId;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "terminal_id"))
    private TerminalId terminalId;

    private BigDecimal amount;

    protected Payment(PaymentMethod method, PaymentId paymentId, TerminalId terminalId, BigDecimal amount) {
        this.method = method;
        this.paymentId = paymentId;
        this.terminalId = terminalId;
        this.amount = amount;
    }
}
