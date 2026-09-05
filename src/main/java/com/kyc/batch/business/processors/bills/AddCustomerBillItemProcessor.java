package com.kyc.batch.business.processors.bills;

import com.kyc.batch.business.model.bills.CustomerBillControlData;
import com.kyc.batch.business.model.bills.CustomerBillMonthData;
import com.kyc.batch.business.util.KycBatchStatusEnum;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.jspecify.annotations.Nullable;
import org.springframework.batch.infrastructure.item.ItemProcessor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RequiredArgsConstructor
public class AddCustomerBillItemProcessor implements ItemProcessor<CustomerBillMonthData, CustomerBillControlData> {

    private final Integer taxesAmount;
    private final Integer billingStartDay;
    private final Integer billingEndDay;
    private final Integer paymentDueDay;


    @Override
    public @Nullable CustomerBillControlData process(CustomerBillMonthData item)  {

        if(ObjectUtils.getIfNull(item.getTotal(),-1D) > 0){

            CustomerBillControlData result = new CustomerBillControlData();

            LocalDateTime now = LocalDateTime.now();
            LocalDate previousMonth = now.toLocalDate().minusMonths(1);

            result.setTaxes(taxesAmount.doubleValue());
            result.setSubtotal(item.getTotal());

            double total = ((result.getTaxes() / 100) * result.getSubtotal()) + result.getSubtotal();

            result.setTotal(total);
            result.setCustomerId(item.getCustomerId());
            result.setIdStatus(KycBatchStatusEnum.VALID.getIdStatus());
            result.setIssueDate(now);

            result.setBillingStartDate(previousMonth.withDayOfMonth(billingStartDay));
            result.setBillingFinishDate(now.toLocalDate().withDayOfMonth(billingEndDay));
            result.setPaymentDueDate(now.plusMonths(1).withDayOfMonth(paymentDueDay));

            return result;
        }

        return null;
    }
}
