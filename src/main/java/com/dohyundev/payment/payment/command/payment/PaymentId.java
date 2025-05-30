package com.dohyundev.payment.payment.command.payment;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@EqualsAndHashCode
public class PaymentId {
    private final Long value;

    public PaymentId(Long value) {
        this.value = value;
    }
}
