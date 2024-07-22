package by.aston.bank.service;

import by.aston.bank.dao.AccountDao;
import by.aston.bank.service.dto.*;
import by.aston.bank.service.mapper.AccountMapper;
import by.aston.bank.service.mapper.AccountMapperImpl;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AccountService {


    private final AccountDao accountDao = new AccountDao();
    private final AccountMapper accountMapper = new AccountMapperImpl();

    public Optional<AccountReadDto> findById(@NotNull
                                          @Positive Long id) {
        return Optional.ofNullable(
                accountMapper.toReadDto(
                        accountDao.findById(id).orElse(null)));
    }

    public List<AccountReadDto> findAll() {
        return accountDao.findAll()
                .stream()
                .map(accountMapper::toReadDto)
                .collect(Collectors.toList());
    }

    public Optional<AccountReadDto> create(@NotNull AccountWriteDto writeDto) {
        return Optional.ofNullable(
                accountMapper.toReadDto(
                        accountDao.save(accountMapper.toEntity(writeDto))
                                .orElse(null)));
    }

    public Optional<AccountReadDto> update(@NotNull
                                        @Positive Long id,
                                        @NotNull AccountWriteDto writeDto) {
        return Optional.ofNullable(
                accountMapper.toReadDto(
                        accountDao.update(id, accountMapper.toEntity(writeDto))
                                .orElse(null)));
    }

    public boolean delete(@NotNull
                          @Positive Long id) {
        return accountDao.delete(id);
    }

}
