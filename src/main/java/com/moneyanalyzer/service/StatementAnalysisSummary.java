package com.moneyanalyzer.service;

import com.moneyanalyzer.dto.StatementSummary;
import com.moneyanalyzer.dto.transaction.TransactionDto;
import com.moneyanalyzer.util.DateUtil;
import jakarta.transaction.Transactional;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class StatementAnalysisSummary {

    private final TransactionService transactionService;

    public StatementAnalysisSummary(TransactionService transactionService){
        this.transactionService = transactionService;
    }

    @Transactional
    public StatementSummary processStatement(MultipartFile file) throws Exception {

        List<TransactionDto> transactions = parsePdf(file);
        transactionService.saveTransactions(transactions);

        return calculateExpense(transactions);
    }

    private List<TransactionDto> parsePdf(MultipartFile file) throws Exception {

        List<TransactionDto> transactions = new ArrayList<>();

        try (PDDocument doc = PDDocument.load(file.getInputStream())) {

            String text = new PDFTextStripper().getText(doc);
            String[] lines = text.split("\\r?\\n");

            LocalDate currentDate = null;
            TransactionDto currentTxn = null;
            StringBuilder descriptionBuffer = new StringBuilder();

            Pattern datePattern =
                    Pattern.compile("[A-Za-z]{3}\\s+\\d{1,2},\\s+\\d{4}");

            Pattern amountPattern =
                    Pattern.compile("(DEBIT|CREDIT)\\s*₹?\\s*([0-9,]+)");

            Pattern descPattern =
                    Pattern.compile("(Paid to|Received from)\\s+(.+)");

            for (String raw : lines) {

                String line = raw.trim();
                if (line.isEmpty()) continue;

                // ---- SKIP HEADERS / NOISE ----
                if (line.startsWith("Transaction Statement")
                        || line.contains("Date Transaction Details")
                        || line.startsWith("Transaction ID")
                        || line.startsWith("UTR")
                        || line.startsWith("Paid by")
                        || line.matches("\\d{1,2}:\\d{2}.*")) {
                    continue;
                }

                // ---- DATE (context only) ----
                if (datePattern.matcher(line).matches()) {
                    currentDate = DateUtil.parsePhonePeDate(line);
                    continue;
                }

                // ---- AMOUNT (may appear with description) ----
                Matcher amtMatcher = amountPattern.matcher(line);
                if (amtMatcher.find()) {

                    if (currentTxn == null) {
                        currentTxn = new TransactionDto();
                        currentTxn.setDate(currentDate);
                    }

                    currentTxn.setType(amtMatcher.group(1));
                    currentTxn.setAmount(
                            new BigDecimal(amtMatcher.group(2).replace(",", ""))
                    );
                }

                // ---- DESCRIPTION (can be before OR after amount) ----
                Matcher descMatcher = descPattern.matcher(line);
                if (descMatcher.find()) {

                    if (currentTxn == null) {
                        currentTxn = new TransactionDto();
                        currentTxn.setDate(currentDate);
                    }

                    if (!descriptionBuffer.isEmpty()) {
                        descriptionBuffer.append(" ");
                    }
                    descriptionBuffer.append(descMatcher.group(2).trim());
                }

                // ---- CLOSE TRANSACTION ----
                if (currentTxn != null
                        && currentTxn.getAmount() != null
                        && !descriptionBuffer.isEmpty()) {

                    currentTxn.setDescription(descriptionBuffer.toString().trim());
                    transactions.add(currentTxn);

                    // reset
                    currentTxn = null;
                    descriptionBuffer.setLength(0);
                }
            }
        }

        return transactions;
    }

    private StatementSummary calculateExpense(List<TransactionDto> transactions) {
        System.out.println(transactions);
        BigDecimal debit = BigDecimal.ZERO;
        BigDecimal credit = BigDecimal.ZERO;

        for (TransactionDto t : transactions) {
            if ("DEBIT".equals(t.getType())) {
                debit = debit.add(t.getAmount());
            } else {
                credit = credit.add(t.getAmount());
            }
        }
        return new StatementSummary(debit, credit, transactions.size());
    }
}


/*
@Service
public class StatementAnalysisSummary {

    private final TransactionService transactionService;

    public StatementAnalysisSummary(TransactionService transactionService){
        this.transactionService = transactionService;
    }

    @Transactional
    public StatementSummary processStatement(MultipartFile file) throws Exception {

        List<TransactionDto> transactions = parsePdf(file);
        transactionService.saveTransactions(transactions);

        return calculateExpense(transactions);
    }

    private List<TransactionDto> parsePdf(MultipartFile file) throws Exception {

        List<TransactionDto> transactions = new ArrayList<>();

        try (PDDocument doc = PDDocument.load(file.getInputStream())) {

            String text = new PDFTextStripper().getText(doc);
            String[] lines = text.split("\\r?\\n");

            LocalDate currentDate = null;
            TransactionDto currentTxn = null;
            StringBuilder descriptionBuffer = new StringBuilder();

            Pattern datePattern =
                    Pattern.compile("[A-Za-z]{3}\\s+\\d{1,2},\\s+\\d{4}");

            Pattern amountPattern =
                    Pattern.compile("(DEBIT|CREDIT)\\s*₹?\\s*([0-9,]+)");

            Pattern descPattern =
                    Pattern.compile("(Paid to|Received from)\\s+(.+)");

            for (String raw : lines) {

                String line = raw.trim();
                if (line.isEmpty()) continue;

                // ---- SKIP HEADERS / NOISE ----
                if (line.startsWith("Transaction Statement")
                        || line.contains("Date Transaction Details")
                        || line.startsWith("Transaction ID")
                        || line.startsWith("UTR")
                        || line.startsWith("Paid by")
                        || line.matches("\\d{1,2}:\\d{2}.*")) {
                    continue;
                }

                // ---- DATE (context only) ----
                if (datePattern.matcher(line).matches()) {
                    currentDate = DateUtil.parsePhonePeDate(line);
                    continue;
                }

                // ---- AMOUNT (may appear with description) ----
                Matcher amtMatcher = amountPattern.matcher(line);
                if (amtMatcher.find()) {

                    if (currentTxn == null) {
                        currentTxn = new TransactionDto();
                        currentTxn.setDate(currentDate);
                    }

                    currentTxn.setType(amtMatcher.group(1));
                    currentTxn.setAmount(
                            new BigDecimal(amtMatcher.group(2).replace(",", ""))
                    );
                }

                // ---- DESCRIPTION (can be before OR after amount) ----
                Matcher descMatcher = descPattern.matcher(line);
                if (descMatcher.find()) {

                    if (currentTxn == null) {
                        currentTxn = new TransactionDto();
                        currentTxn.setDate(currentDate);
                    }

                    if (!descriptionBuffer.isEmpty()) {
                        descriptionBuffer.append(" ");
                    }
                    descriptionBuffer.append(descMatcher.group(2).trim());
                }

                // ---- CLOSE TRANSACTION ----
                if (currentTxn != null
                        && currentTxn.getAmount() != null
                        && !descriptionBuffer.isEmpty()) {

                    currentTxn.setDescription(descriptionBuffer.toString().trim());
                    transactions.add(currentTxn);

                    // reset
                    currentTxn = null;
                    descriptionBuffer.setLength(0);
                }
            }
        }

        return transactions;
    }

private StatementSummary calculateExpense(List<TransactionDto> transactions) {
        System.out.println(transactions);
        BigDecimal debit = BigDecimal.ZERO;
        BigDecimal credit = BigDecimal.ZERO;

        for (TransactionDto t : transactions) {
            if ("DEBIT".equals(t.getType())) {
                debit = debit.add(t.getAmount());
            } else {
                credit = credit.add(t.getAmount());
            }
        }
        return new StatementSummary(debit, credit, transactions.size());
    }
}
*/
