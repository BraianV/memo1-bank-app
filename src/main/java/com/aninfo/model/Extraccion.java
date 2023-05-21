package com.aninfo.model;


import javax.persistence.Entity;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Extraccion extends Operation {
    public Extraccion(Long cbu, Double monto) {
        super(cbu, monto);
    }

    public Extraccion() {

    }

    @Override
    public Account updateAccount(Account account, Operation operation) {
        Double aux = account.getBalance() + operation.getMonto();

        List<Integer> operationUpdate = account.getOperations().stream().filter(x-> x.equals(operation.getId())).collect(Collectors.toList());


        account.setBalance(aux);
        account.setOperations(operationUpdate);



        return account;
    }
}
