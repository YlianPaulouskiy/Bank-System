package by.aston.bank.service;

import by.aston.bank.dao.BankDao;
import by.aston.bank.service.dto.*;
import by.aston.bank.service.mapper.BankMapper;
import by.aston.bank.service.mapper.BankMapperImpl;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BankService {

    private final BankDao bankDao = new BankDao();
    private final BankMapper bankMapper = new BankMapperImpl();

    public Optional<BankReadDto> findById(@NotNull
                                          @Positive Long id) {
        return Optional.ofNullable(
                bankMapper.toReadDto(
                        bankDao.findById(id).orElse(null)));
    }

    public List<BankReadDto> findAll() {
        return bankDao.findAll()
                .stream()
                .map(bankMapper::toReadDto)
                .collect(Collectors.toList());
    }

    public Optional<BankUserDto> create(@NotNull BankWriteDto writeDto) {
        return Optional.ofNullable(
                bankMapper.toUserDto(
                        bankDao.save(bankMapper.toEntity(writeDto))
                                .orElse(null)));
    }

    public Optional<BankReadDto> update(@NotNull
                                        @Positive Long id,
                                        @NotNull BankWriteDto writeDto) {
        return Optional.ofNullable(
                bankMapper.toReadDto(
                        bankDao.update(id, bankMapper.toEntity(writeDto))
                                .orElse(null)));
    }

    public boolean delete(@NotNull
                          @Positive Long id) {
        return bankDao.delete(id);
    }

}
