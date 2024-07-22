package by.aston.bank.service.dto;

import java.util.List;

public record UserReadDto(
        Long id,
        String name,
        String lastName,
        List<AccountUserDto> accounts) {
}
