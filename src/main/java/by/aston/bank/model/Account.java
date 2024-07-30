package by.aston.bank.model;

import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Account extends BaseEntity {

    private String number;
    private BigDecimal cash;
    private User user;
    private Bank bank;
}
