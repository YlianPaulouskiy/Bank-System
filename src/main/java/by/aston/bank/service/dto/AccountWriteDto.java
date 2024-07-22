package by.aston.bank.service.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public record AccountWriteDto(
        @Length(max = 15, min = 1)
        String number,
        @NotNull(message = "Cash can't be null")
        BigDecimal cash,
        @NotNull(message = "Bank id can't be null")
        @Positive(message = "Bank id can be positive")
        Long bankId,
        @NotNull(message = "User id can't be null")
        @Positive(message = "User id can be positive")
        Long userId) {
}
