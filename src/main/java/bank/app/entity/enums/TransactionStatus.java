package bank.app.entity.enums;

public enum TransactionStatus {
    INITIALIZED,           // Инициализирована
    PROCESSING,            // В обработке
    PENDING_CONFIRMATION,  // Ожидание подтверждения
    SECURITY_CHECK,        // Проверка безопасности
    PENDING_FUNDS,         // Ожидание средств
    PROCESSED,             // Обработана / Одобрена
    COMPLETED,             // Завершена
    DECLINED,              // Отклонена
    FAILED,                // Не удалась
    CANCELLED              // Отменена
}