package com.kyc.batch.business.util;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum KycBatchStatusEnum {

    PAID(1),
    CANCELED(2),
    EXPIRED(3),
    VALID(4);

    private final Integer idStatus;
}
