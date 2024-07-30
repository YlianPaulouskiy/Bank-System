package by.aston.bank.service;

import by.aston.bank.dao.UserDao;
import by.aston.bank.service.dto.UserBankDto;
import by.aston.bank.service.dto.UserReadDto;
import by.aston.bank.service.dto.UserWriteDto;
import by.aston.bank.service.mapper.UserMapper;
import by.aston.bank.service.mapper.UserMapperImpl;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserService {

    private final UserDao userDao = new UserDao();
    private final UserMapper userMapper = new UserMapperImpl();

    public Optional<UserReadDto> findById(@NotNull
                                          @Positive Long id) {
        return Optional.ofNullable(
                userMapper.toReadDto(
                        userDao.findById(id).orElse(null)));
    }

    public List<UserReadDto> findAll() {
        return userDao.findAll()
                .stream()
                .map(userMapper::toReadDto)
                .collect(Collectors.toList());
    }

    public Optional<UserBankDto> create(@NotNull UserWriteDto writeDto) {
        return Optional.ofNullable(
                userMapper.toBankDto(
                        userDao.save(userMapper.toEntity(writeDto))
                                .orElse(null)));
    }

    public Optional<UserReadDto> update(@NotNull
                                        @Positive Long id,
                                        @NotNull UserWriteDto writeDto) {
        return Optional.ofNullable(
                userMapper.toReadDto(
                        userDao.update(id, userMapper.toEntity(writeDto))
                                .orElse(null)));
    }

    public boolean delete(@NotNull
                          @Positive Long id) {
        return userDao.delete(id);
    }

}
