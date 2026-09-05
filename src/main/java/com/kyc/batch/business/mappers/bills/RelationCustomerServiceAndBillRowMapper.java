package com.kyc.batch.business.mappers.bills;

import com.kyc.batch.business.model.bills.RelationCustomerServiceAndBill;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RelationCustomerServiceAndBillRowMapper implements RowMapper<RelationCustomerServiceAndBill> {

    @Override
    public RelationCustomerServiceAndBill mapRow(ResultSet rs, int rowNum) throws SQLException {

        Long id = rs.getLong("ID");
        Long customerId = rs.getLong("ID_CUSTOMER");
        Long serviceId = rs.getLong("ID_SERVICE");
        Long billId = rs.getLong("ID_BILL");

        return new RelationCustomerServiceAndBill(id,customerId,serviceId,billId);
    }
}
