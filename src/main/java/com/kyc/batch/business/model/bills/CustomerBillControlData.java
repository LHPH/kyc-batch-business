package com.kyc.batch.business.model.bills;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerBillControlData {

    private Long id;
    private Double taxes;
    private Double subtotal;
    private Double total;
    private Long customerId;
    private boolean settled;
    private Integer idStatus;
    private LocalDateTime issueDate;
    private LocalDate billingStartDate;
    private LocalDate billingFinishDate;
    private LocalDateTime paymentDueDate;
    private LocalDateTime settlementDate;

}
