package ma.nouroun.ebankingbackend.dto;

import lombok.Data;
import ma.nouroun.ebankingbackend.enums.OperationType;

import java.util.Date;

@Data
public class AccountOperationDto {
    private Long id;
    private Date operationDate;
    private double amount;
    private OperationType type;
}
