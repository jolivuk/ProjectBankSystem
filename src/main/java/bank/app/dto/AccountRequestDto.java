package bank.app.dto;

import bank.app.model.enums.Status;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Objects;




public record AccountRequestDto(Status status, Double balance, String iban, String swift) {

}
