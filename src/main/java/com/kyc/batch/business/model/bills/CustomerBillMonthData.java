package com.kyc.batch.business.model.bills;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerBillMonthData {

    private Long id;
    private Long customerId;
    private Double total;
}
