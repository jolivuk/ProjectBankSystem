package bank.app.model.enums;

public enum TransactionStatusName {
    INITIALIZED,
    PROCESSING,
    PENDING_CONFIRMATION,
    SECURITY_CHECK,
    PENDING_FUNDS,
    PROCESSED,
    COMPLETED,
    DECLINED,
    FAILED,
    CANCELLED
}