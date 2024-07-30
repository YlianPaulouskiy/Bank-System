package by.aston.bank.model;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Bank extends BaseEntity {

    private String title;

    private List<User> users;
    private List<Account> accounts;

}
