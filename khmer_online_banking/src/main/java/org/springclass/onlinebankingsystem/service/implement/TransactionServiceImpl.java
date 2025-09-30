package org.springclass.onlinebankingsystem.service.implement;

import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springclass.onlinebankingsystem.controller.request.DepositRequest;
import org.springclass.onlinebankingsystem.controller.request.TransferRequest;
import org.springclass.onlinebankingsystem.controller.request.WithdrawRequest;
import org.springclass.onlinebankingsystem.controller.response.TransactionResponse;
import org.springclass.onlinebankingsystem.exception.CustomException;
import org.springclass.onlinebankingsystem.repository.TransactionRepository;
import org.springclass.onlinebankingsystem.repository.entity.Transaction;
import org.springclass.onlinebankingsystem.repository.entity.enumerate.Currency;
import org.springclass.onlinebankingsystem.repository.entity.enumerate.TransactionType;
import org.springclass.onlinebankingsystem.service.AccountService;
import org.springclass.onlinebankingsystem.service.TransactionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository repository;
    private final AccountService accountService;

    @Override
    public void save(Transaction transactions) {
        transactions.setTransactionId(generateTransactionId());
        transactions.setTransactionDate(new Date());
        repository.save(transactions);
    }

    @Override
    public Optional<List<TransactionResponse>> getAllByAccountId(String accountNumber, Long userId) {
        var transactionList = repository.findAllByUserIdAndAccountNumber(accountNumber, userId);
        if (transactionList.isEmpty()) {
            return Optional.empty();
        }
        return transactionList.map(this::toResponse);
    }

    @Override
    public Page<TransactionResponse> getAllByAccountId(String accountNumber, Long userId, int page, int size) {
        return repository.findAll((root, cq, cb) -> {
            ArrayList<Predicate> predicates = new ArrayList<>();
            var account = cb.equal(root.get("accountNumber"), accountNumber);
            var user = cb.equal(root.get("userId"), userId);
            predicates.add(cb.and(account, user));
            predicates.add(cb.isTrue(root.get("status")));
            return cb.and(predicates.toArray(new Predicate[0]));
        }, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"))).map(TransactionResponse::new);
    }

    @Override
    public List<Transaction> findAll() {
        return repository.findAllByStatusTrueOrderByIdAsc().orElse(Collections.emptyList());
    }

    @Transactional
    @Override
    public void transfer(TransferRequest request) {
        var fromAccount = accountService.findByAccountNumber(request.fromAccountNumber());
        var toAccount = accountService.findByAccountNumber(request.toAccountNumber());
        var currency = Currency.find(request.currency());

        var withdrawAmount = (request.amount() / currency.getExchangeRate()) * fromAccount.getCurrency().getExchangeRate();
        var depositAmount = (request.amount() / currency.getExchangeRate()) * toAccount.getCurrency().getExchangeRate();

        var withdrawBalance = checkWithdraw(fromAccount.getBalance(), withdrawAmount);
        var depositBalance = checkDeposit(toAccount.getBalance(), depositAmount);

        accountService.updateBalance(fromAccount.getId(), withdrawBalance);
        accountService.updateBalance(toAccount.getId(), depositBalance);

        var transactionTransfer = new Transaction(fromAccount.getAccountNumber(),
                fromAccount, toAccount, withdrawAmount, TransactionType.TRANSFER,
                fromAccount.getCurrency(), request.description(), fromAccount.getUser().getId(),
                request.amount(), request.currency()
        );
        var transactionsReceive = new Transaction(toAccount.getAccountNumber(),
                fromAccount, toAccount, depositAmount, TransactionType.RECEIVE,
                toAccount.getCurrency(), request.description(), toAccount.getUser().getId(),
                request.amount(), request.currency()
        );

        save(transactionTransfer);
        save(transactionsReceive);

        log.info("Transfer success \n{}\n{}", transactionTransfer, transactionsReceive);
    }

    @Transactional
    @Override
    public void deposit(DepositRequest request) {
        var account = accountService.findByAccountNumber(request.accountNumber());
        var currency = Currency.find(request.currency());

        var depositAmount = (request.amount() / currency.getExchangeRate()) * account.getCurrency().getExchangeRate();

        System.out.println(">>>>> request currency: " + request.currency());
        System.out.println(">>>>> currency: " + currency);
        System.out.println(">>>>> request amount: " + request.amount());
        System.out.println(">>>>> deposit amount: " + depositAmount);


        var depositBalance = checkDeposit(account.getBalance(), depositAmount);
        accountService.updateBalance(account.getId(), depositBalance);

        var transactions = new Transaction(account.getAccountNumber(),
                null, account, depositAmount, TransactionType.DEPOSIT,
                account.getCurrency(), "",  account.getUser().getId(), request.amount(), request.currency()
        );

        save(transactions);
        log.info("Deposit Success \n{}", transactions);
    }

    @Transactional
    @Override
    public void withdraw(WithdrawRequest request) {
        var account = accountService.findByAccountNumber(request.accountNumber());
        var currency = Currency.find(request.currency());
        var withdrawAmount = (request.amount() / currency.getExchangeRate()) * account.getCurrency().getExchangeRate();

        var withdrawBalance = checkWithdraw(account.getBalance(), withdrawAmount);
        accountService.updateBalance(account.getId(), withdrawBalance);

        var transactions = new Transaction(account.getAccountNumber(),
                account, null, withdrawAmount, TransactionType.WITHDRAWAL,
                account.getCurrency(),"Withdrawal", account.getUser().getId(),
                request.amount(), request.currency()
        );

        save(transactions);
        log.info("Withdraw success \n{}", transactions);
    }

    private String generateTransactionId() {
        Random random = new Random();
        int firstDigit = random.nextInt(9) + 1;
        StringBuilder tranId = new StringBuilder();
        tranId.append(firstDigit);
        for (int i = 0; i < 13; i++) {
            tranId.append(random.nextInt(10));
        }

        return tranId.toString();
    }

    private Double checkDeposit(Double balance, Double amount) {
        if (amount.compareTo(0.0) <= 0) {
            throw new CustomException(400, "Amount must be positive");
        }
        balance += amount;
        return balance;
    }

    private Double checkWithdraw(Double balance, Double amount) {
        if (amount.compareTo(0.0) <= 0) {
            throw new CustomException(404, "Amount must be positive");
        }
        if (balance.compareTo(amount) < 0) {
            throw new CustomException(400, "Insufficient funds");
        }
        balance = balance - amount;
        return balance;
    }

    private List<TransactionResponse> toResponse(List<Transaction> transactions) {
        if (transactions == null) {
            return null;
        }
        var transaction = new ArrayList<TransactionResponse>();
        for (Transaction tran: transactions) {
            transaction.add(new TransactionResponse(tran));
        }
        return transaction;
    }
}
