package bank.app.dto;

import lombok.Data;

import java.time.LocalDateTime;


public record AccountDto( Double balance,

                          LocalDateTime createdAt,

                          LocalDateTime created) {


}
