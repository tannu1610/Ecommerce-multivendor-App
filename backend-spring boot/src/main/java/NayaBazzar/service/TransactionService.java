package NayaBazzar.service;

import NayaBazzar.model.Order;
import NayaBazzar.model.Seller;
import NayaBazzar.model.Transaction;

import java.util.List;

public interface TransactionService {

    Transaction createTransaction(Order order);
    List<Transaction> getTransactionBySeller(Seller seller);
    List<Transaction>getAllTransactions();
}
