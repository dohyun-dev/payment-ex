package com.dohyundev.payment.pos;

import com.dohyundev.payment.common.BaseId;
import com.github.f4b6a3.tsid.TsidCreator;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=false)
public class TerminalId extends BaseId {
    public TerminalId(long id) {
        super(id);
    }

    public static TerminalId create() {
        return new TerminalId(TsidCreator.getTsid().toLong());
    }
}
