package com.kyc.batch.business.constants;

import com.kyc.core.constants.BatchConstants;

public final class BatchBillConstants {

    public static final String CREATE_CUSTOMER_BILL_JOB_NAME = String.format(BatchConstants.KYC_BATCH_JOB_TEMPLATE,
            "BILLS","ADD-BILL");

    public static final String ADD_CUSTOMER_BILL_STEP = String.format(BatchConstants.KYC_BATCH_STEP_TEMPLATE,
            "BILLS","ADD-BILL");

    public static final String ADD_RELATION_CUSTOMER_SERVICE_BILL_STEP = String.format(BatchConstants.KYC_BATCH_STEP_TEMPLATE,
            "BILLS","ADD-RELATION-CUSTOMER-SERVICE-BILL");
}
