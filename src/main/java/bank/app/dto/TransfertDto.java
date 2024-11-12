package bank.app.dto;


public record TransfertDto(
        Long sender,
        Long receiver,
        double amount,
        String comment,
        String  transactionType){
}
