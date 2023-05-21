package com.aninfo.model;

import javax.persistence.Entity;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Deposito extends Operation {
    public Deposito(Long cbu, Double sum) {
        super(cbu, sum);
    }

    private void applyPromo(Double sum) {
    }

    public Deposito() {

    }

    @Override
    public Account updateAccount(Account account, Operation operation) {
        Double aux = account.getBalance() - operation.getMonto();

        List<Integer> operationUpdate = account.getOperations().stream().filter(x-> !x.equals(operation.getId())).
                collect(Collectors.toList());


        account.setBalance(aux);
        account.setOperations(operationUpdate);



        return account;
    }
}
