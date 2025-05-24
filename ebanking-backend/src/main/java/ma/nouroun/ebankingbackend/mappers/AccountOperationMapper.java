package ma.nouroun.ebankingbackend.mappers;

import ma.nouroun.ebankingbackend.dto.AccountOperationDto;
import ma.nouroun.ebankingbackend.entities.AccountOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class AccountOperationMapper {

    public AccountOperationDto fromAccountOperation(AccountOperation accountOperation) {
        AccountOperationDto dto = new AccountOperationDto();
        BeanUtils.copyProperties(accountOperation, dto);
        return dto;
    }
}
