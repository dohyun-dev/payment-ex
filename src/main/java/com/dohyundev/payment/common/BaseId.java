package com.dohyundev.payment.common;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@EqualsAndHashCode
public abstract class BaseId {
    private final Long value;

    protected BaseId(Long value) {
        this.value = value;
    }
}
