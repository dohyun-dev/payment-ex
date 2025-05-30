package com.dohyundev.payment.pos;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Terminal {
    @EmbeddedId
    @AttributeOverride(name = "value", column = @Column(name = "terminal_id"))
    private TerminalId id;
}
