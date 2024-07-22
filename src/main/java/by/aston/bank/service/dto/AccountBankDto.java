package by.aston.bank.service.dto;

import java.math.BigDecimal;

public record AccountBankDto (
        Long id,
        String number,
        BigDecimal cash,
        UserBankDto user) {
}
