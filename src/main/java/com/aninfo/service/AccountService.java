package com.aninfo.service;

import com.aninfo.exceptions.DepositNegativeSumException;
import com.aninfo.exceptions.InsufficientFundsException;
import com.aninfo.model.Account;
import com.aninfo.model.Deposito;
import com.aninfo.model.Extraccion;
import com.aninfo.model.Operation;
import com.aninfo.repository.AccountRepository;
import com.aninfo.repository.OperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private OperationRepository operationRepository;

    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    public Collection<Account> getAccounts() {
        return accountRepository.findAll();
    }

    public Optional<Account> findById(Long cbu) {
        return accountRepository.findById(cbu);
    }

    public void save(Account account) {
        accountRepository.save(account);
    }

    public void deleteById(Long cbu) {
        accountRepository.deleteById(cbu);
    }

    @Transactional
    public Account withdraw(Long cbu, Double sum) {
        Account account = accountRepository.findAccountByCbu(cbu);

        if (account.getBalance() < sum) {
            throw new InsufficientFundsException("Insufficient funds");
        }
        Operation operation = new Extraccion(cbu, sum);
        Operation operationSaved = operationRepository.save(operation);//para que se autogenere el id
        account.updateOperation(operationSaved.getId());

        account.setBalance(account.getBalance() - sum);
        accountRepository.save(account);

        return account;
    }

    @Transactional
    public Account deposit(Long cbu, Double sum) {

        if (sum <= 0) {
            throw new DepositNegativeSumException("Cannot deposit negative sums");
        }

        Account account = accountRepository.findAccountByCbu(cbu);

        Double value = account.checkPromo(sum);


        Operation operation = new Deposito(cbu, value);
        Operation operationSaved = operationRepository.save(operation);



        account.setBalance(account.getBalance() + value);
        account.updateOperation(operationSaved.getId());
        accountRepository.save(account);

        return account;
    }

    public List<Operation> getAllTransactions(Long cbu) {
        List<Integer> ids = this.accountRepository.findAccountByCbu(cbu).getOperations();
        List<Operation> aux = new ArrayList<>();
        ids.forEach(id -> {
            aux.add(this.operationRepository.findOperationById(id));
        });
        return aux;
    }

    public Operation getOperation(Integer id) {
        return this.operationRepository.findOperationById(id);
    }

    public void deleteOperation(Integer id) {
        Operation operation = this.operationRepository.findOperationById(id);
        this.operationRepository.deleteById(id);
        Account account = this.accountRepository.findAccountByCbu(operation.getCbu());
        Account updateAccount = operation.updateAccount(account, operation);
        this.accountRepository.save(updateAccount);
    }
}
