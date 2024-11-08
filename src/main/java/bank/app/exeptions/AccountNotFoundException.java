package bank.app.exeptions;

public class AccountNotFoundException extends Exception{

    public AccountNotFoundException(String message) {
        super(message);
    }
}
