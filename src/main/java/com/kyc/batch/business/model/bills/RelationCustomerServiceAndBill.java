package com.kyc.batch.business.model.bills;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RelationCustomerServiceAndBill {

    private Long id;
    private Long customerId;
    private Long serviceId;
    private Long billId;
}
