package by.aston.bank.service.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record BankWriteDto(
        @NotNull(message = "Title is require field")
        @NotBlank(message = "Title can't be empty")
        String title) {
}
