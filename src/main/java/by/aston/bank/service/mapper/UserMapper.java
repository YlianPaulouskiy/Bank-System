package by.aston.bank.service.mapper;

import by.aston.bank.model.Account;
import by.aston.bank.model.User;
import by.aston.bank.service.dto.*;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper
public abstract class UserMapper {

    public abstract User toEntity(UserWriteDto writeDto);

    public UserReadDto toReadDto(User user) {
        if (user == null) {
            return null;
        }

        Long id = null;
        String name = null;
        String lastName = null;
        List<AccountUserDto> accounts = null;

        id = user.getId();
        name = user.getName();
        lastName = user.getLastName();
        accounts = new ArrayList<>();
        if (user.getAccounts() != null)
            for (Account account : user.getAccounts()) {
                AccountUserDto accountUserDto = new AccountUserDto(
                        account.getId(),
                        account.getNumber(),
                        account.getCash(),
                        new BankUserDto(
                                account.getBank().getId(),
                                account.getBank().getTitle()));
                accounts.add(accountUserDto);
            }

        return new UserReadDto(id, name, lastName, accounts);
    }

    public abstract UserBankDto toBankDto(User user);

}
