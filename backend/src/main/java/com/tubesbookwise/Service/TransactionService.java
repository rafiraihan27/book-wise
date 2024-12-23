package com.tubesbookwise.Service;

import com.tubesbookwise.Models.*;
import com.tubesbookwise.dto.TransactionRequest;
import com.tubesbookwise.Repository.TransactionRepository;
import com.tubesbookwise.Repository.TransactionItemRepository;
import com.tubesbookwise.Repository.BookRepository;
import com.tubesbookwise.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionItemRepository transactionItemRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationsService notificationService;

    @Transactional
    public String createTransaction(TransactionRequest transactionRequest) {
        // 1. Save the transaction first
        User user = userRepository.findById(transactionRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setTotalFee(transactionRequest.getTotalFee());
        transaction.setPaymentMethod(transactionRequest.getPaymentMethod());
        transaction.setPaymentEvidence(transactionRequest.getPaymentEvidence());
        transaction.setDateFrom(transactionRequest.getDateFrom());
        transaction.setDateTo(transactionRequest.getDateTo());
        transaction.setStatus(Transaction.TransactionStatus.PENDING); // Default status
        transaction.setType(Transaction.TransactionType.BORROW); // Default type

        // 2. Generate the invoice code
        String randomHex = generateRandomHex(3);  // Generate 3 random characters (letters + numbers)
        String currentDate = getCurrentDate();   // Get the current date in yyyyMMdd format
        String invoiceCode = "INV-" + currentDate + "-" + randomHex; // Combine in the desired format

        transaction.setInvoiceCode(invoiceCode);

        // Save the transaction and get the saved entity
        Transaction savedTransaction = transactionRepository.save(transaction);

        // 3. Send notification to user about the transaction status
        String title = "Transaction Pending";
        String message = "Your transaction with invoice code " + invoiceCode + " is pending and will be reviewed by an admin until approved.";
        notificationService.addNotification(user, title, message, Notification.NotificationType.INFO);

        // 4. Save the items to the transaction_items table
        List<TransactionItem> transactionItems = transactionRequest.getItems().stream()
                .map(itemRequest -> {
                    Book book = bookRepository.findById(itemRequest.getId())
                            .orElseThrow(() -> new RuntimeException("Book not found"));

                    TransactionItem transactionItem = new TransactionItem();
                    transactionItem.setTransaction(savedTransaction);
                    transactionItem.setBook(book);

                    return transactionItem;
                }).toList();

        // Save all transaction items
        transactionItemRepository.saveAll(transactionItems);

        return savedTransaction.getInvoiceCode();
    }

    private String generateRandomHex(int length) {
        // Generates a random string of letters and numbers (length characters)
        StringBuilder hexString = new StringBuilder();
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"; // Letters and digits
        for (int i = 0; i < length; i++) {
            int randomIndex = (int) (Math.random() * characters.length());
            hexString.append(characters.charAt(randomIndex));
        }
        return hexString.toString();
    }

    private String getCurrentDate() {
        // Gets the current date in yyyyMMdd format
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyyMMdd");
        return sdf.format(new java.util.Date());
    }


    // Method to get transaction by invoiceCode
    @Transactional
    public Map<String, Object> getTransactionByInvoiceCode(String invoiceCode) {
        // Fetch the transaction by invoice code
        Transaction transaction = transactionRepository.findByInvoiceCode(invoiceCode)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        // Ensure lazy-loaded fields are fetched inside the transaction
        transaction.getUser().getName(); // Trigger lazy loading
        transaction.getItems().size(); // Trigger lazy loading

        // Convert to the required response format
        return Map.of(
                "id", transaction.getId(),
                "invoiceCode", transaction.getInvoiceCode(),
                "dateRange", Map.of(
                        "from", transaction.getDateFrom(),
                        "to", transaction.getDateTo()
                ),
                "status", transaction.getStatus(),
                "type", transaction.getType(),
                "user", Map.of(
                        "id", transaction.getUser().getId(),
                        "name", transaction.getUser().getName(),
                        "email", transaction.getUser().getEmail(),
                        "phone", transaction.getUser().getPhone(),
                        "role", transaction.getUser().getRole()
                ),
                "totalFee", transaction.getTotalFee(),
                "paymentMethod", transaction.getPaymentMethod(),
                "paymentEvidence", transaction.getPaymentEvidence(),
                "items", transaction.getItems().stream()
                        .map(this::mapTransactionItemToResponse)
                        .collect(Collectors.toList())
        );
    }

    // Method to map TransactionItem to required response format
    private Map<String, Object> mapTransactionItemToResponse(TransactionItem transactionItem) {
        return Map.of(
                "id", transactionItem.getBook().getId(),
                "title", transactionItem.getBook().getTitle(),
                "author", transactionItem.getBook().getAuthor(),
                "image", transactionItem.getBook().getImage(),
                "lateFee", transactionItem.getBook().getLateFee()
        );
    }

    // Method to get transactions with optional filters
    @Transactional
    public List<Map<String, Object>> getTransactionsWithFilter(String search, String status, String type, String userId) {
        // Fetch transactions with dynamic filters
        List<Transaction> transactions = transactionRepository.findAll().stream()
                .filter(transaction ->
                        // Check status if not "all" or null
                        (status == null || status.equalsIgnoreCase("all") || transaction.getStatus().toString().equalsIgnoreCase(status)) &&
                                // Check type if not "all" or null
                                (type == null || type.equalsIgnoreCase("all") || transaction.getType().toString().equalsIgnoreCase(type)) &&
                                // Check userId if provided
                                (userId == null || transaction.getUser().getId().equals(userId)) &&
                                // Check search against invoiceCode or user name
                                (search == null ||
                                        transaction.getInvoiceCode().contains(search) ||
                                        transaction.getUser().getName().toLowerCase().contains(search.toLowerCase()))
                )
                .toList();

        // Map transactions to the required response format
        return transactions.stream()
                .map(this::mapTransactionToResponse)
                .collect(Collectors.toList());
    }

    // Method to map Transaction to the required response format
    private Map<String, Object> mapTransactionToResponse(Transaction transaction) {
        return Map.of(
                "id", transaction.getId(),
                "invoiceCode", transaction.getInvoiceCode(),
                "dateRange", Map.of(
                        "from", transaction.getDateFrom(),
                        "to", transaction.getDateTo()
                ),
                "status", transaction.getStatus(),
                "type", transaction.getType(),
                "user", Map.of(
                        "id", transaction.getUser().getId(),
                        "name", transaction.getUser().getName(),
                        "email", transaction.getUser().getEmail(),
                        "phone", transaction.getUser().getPhone(),
                        "role", transaction.getUser().getRole()
                ),
                "totalFee", transaction.getTotalFee(),
                "paymentMethod", transaction.getPaymentMethod(),
                "paymentEvidence", transaction.getPaymentEvidence(),
                "items", transaction.getItems().stream()
                        .map(this::mapTransactionItemToResponse)
                        .collect(Collectors.toList())
        );
    }

    @Transactional
    public void updateTransactionStatus(String invoiceCode, String status) {
        // Fetch transaction by invoiceCode
        Transaction transaction = transactionRepository.findByInvoiceCode(invoiceCode)
                .orElseThrow(() -> new IllegalArgumentException("Transaksi dengan kode invoice tidak ditemukan"));

        // Validate status
        if (!List.of("approved", "declined", "pending").contains(status.toLowerCase())) {
            throw new IllegalArgumentException("Status tidak valid. Gunakan 'approved', 'declined', atau 'pending'");
        }

        // Update status
        transaction.setStatus(Transaction.TransactionStatus.valueOf(status.toUpperCase()));
        transactionRepository.save(transaction);

        // Fetch the user associated with the transaction
        User user = transaction.getUser();

        // Prepare notification details based on status
        String title;
        String message;
        Notification.NotificationType type;

        switch (status.toLowerCase()) {
            case "approved":
                title = "Transaction Approved";
                message = "Congratulations! Your transaction with invoice code " + invoiceCode + " has been approved.";
                type = Notification.NotificationType.REMINDER;
                break;
            case "declined":
                title = "Transaction Declined";
                message = "Your transaction with invoice code " + invoiceCode + " has been declined. Please contact support for details.";
                type = Notification.NotificationType.ALERT;
                break;
            default:
                title = "Transaction Pending";
                message = "Your transaction with invoice code " + invoiceCode + " is pending and will be reviewed by an admin.";
                type = Notification.NotificationType.INFO;
                break;
        }

        // Send notification to the user
        notificationService.addNotification(user, title, message, type);
    }
}
