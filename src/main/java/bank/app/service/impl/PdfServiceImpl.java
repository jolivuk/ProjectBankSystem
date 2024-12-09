package bank.app.service.impl;

import bank.app.dto.AccountReportDto;
import bank.app.dto.TransactionResponseDto;
import bank.app.service.AccountService;
import bank.app.service.PdfService;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.PdfPTable;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@AllArgsConstructor
public class PdfServiceImpl implements PdfService {
    private final AccountService accountService;

    @Override
    public byte[] generateAccountPdf(Long accountId, LocalDate startDate, LocalDate endDate) throws DocumentException {

        AccountReportDto account = accountService.generateAccountPdfBetweenDates(accountId, startDate, endDate);

        Document document = new Document();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        PdfWriter writer = PdfWriter.getInstance(document, outputStream);

        document.open();

        PdfContentByte canvas = writer.getDirectContent();

        Font boldFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);

        //Header
        document.add(new Paragraph("ACCOUNT REPORT ID: " + accountId
                + "    " + LocalDateTime.now().format(formatter), boldFont));

        document.add(new Paragraph("from " + startDate + " to " + endDate));

        float currentY = document.getPageSize().getHeight() - 77;
        drawLine(canvas, currentY);

        //PrivateInfo
        document.add(new Paragraph("First Name: " + account.getFirstName()));
        document.add(new Paragraph("Last Name: " + account.getLastName()));
        document.add(new Paragraph("Address : " + account.getAddress()));

        currentY = document.getPageSize().getHeight() - 131;
        drawLine(canvas, currentY);

        //AccountInfo
        document.add(new Paragraph("IBAN: " + account.getIban()));
        document.add(new Paragraph("Swift: " + account.getSwift()));
        document.add(new Paragraph(Chunk.NEWLINE));

        PdfPTable table1 = getBalanceTable(account, boldFont);
        document.add(table1);

        document.add(new Paragraph(Chunk.NEWLINE));

        //All transactions
        PdfPTable table = getPdfPTable(document, boldFont, account, formatter);

        document.add(table);

        document.close();

        return outputStream.toByteArray();
    }

    private static PdfPTable getPdfPTable(Document document, Font boldFont, AccountReportDto account, DateTimeFormatter formatter) throws DocumentException {
        document.add(new Paragraph("Transactions ", boldFont));
        document.add(new Paragraph(Chunk.NEWLINE));

        PdfPTable table = new PdfPTable(5);
        table.setWidths(new float[]{2, 2, 2, 3, 2});
        table.setWidthPercentage(100);

        table.addCell(new Phrase("ID", boldFont));
        table.addCell(new Phrase("Status", boldFont));
        table.addCell(new Phrase("Amount", boldFont));
        table.addCell(new Phrase("Date", boldFont));
        table.addCell(new Phrase("Type", boldFont));


        for (TransactionResponseDto transaction : account.getTransactions()) {
            table.addCell(String.valueOf(transaction.getTransactionId()));
            table.addCell(String.valueOf(transaction.getTransactionStatus()));
            table.addCell(String.format("%+.2f", transaction.getAmount()));
            table.addCell(transaction.getTransactionDate().format(formatter));
            table.addCell(String.valueOf(transaction.getTransactionType()));
        }
        return table;
    }

    private static PdfPTable getBalanceTable(AccountReportDto accountPdfDto, Font boldFont) throws DocumentException {
        PdfPTable table1 = new PdfPTable(2);

        table1.setWidths(new float[]{1f, 1f});
        table1.setWidthPercentage(40);

        PdfPCell cell = new PdfPCell(new Phrase("Start Balance:"));
        table1.addCell(cell);
        table1.addCell(accountPdfDto.getStartBalance().toString());

        cell = new PdfPCell(new Phrase("Total Income:"));
        table1.addCell(cell);
        if (accountPdfDto.getTotalIncome() > 0) {
            table1.addCell("+" + accountPdfDto.getTotalIncome());
        } else {
            table1.addCell(accountPdfDto.getTotalIncome().toString());
        }

        cell = new PdfPCell(new Phrase("Total Expenses:"));
        table1.addCell(cell);
        if (accountPdfDto.getTotalExpenses() > 0) {
            table1.addCell("-" + accountPdfDto.getTotalExpenses().toString());
        } else {
            table1.addCell(accountPdfDto.getTotalExpenses().toString());
        }

        cell = new PdfPCell(new Phrase("END BALANCE:", boldFont));
        table1.addCell(cell);

        cell = new PdfPCell(new Phrase(accountPdfDto.getBalance().toString(), boldFont));
        table1.addCell(cell);

        return table1;
    }

    private static void drawLine(PdfContentByte canvas, float yPosition) {
        canvas.setLineWidth(1f);
        canvas.moveTo(36, yPosition);
        canvas.lineTo(559, yPosition);
        canvas.stroke();
    }

}
