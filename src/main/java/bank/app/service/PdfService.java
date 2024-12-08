package bank.app.service;

import com.itextpdf.text.DocumentException;

import java.io.IOException;
import java.time.LocalDate;

public interface PdfService {
    byte[] generateAccountPdf(Long accountId, LocalDate startDate, LocalDate endDate) throws DocumentException, IOException;
}
