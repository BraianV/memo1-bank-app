package com.aninfo.model;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long cbu;

    private Double balance;

    private final Double tope = 500.0;
    private final Double minime = 2000.0;

    public List<Integer> getOperations() {
        return operations;
    }

    @ElementCollection
    private List<Integer> operations;

    public Account(){
    }

    public Account(Double balance) {
        this.balance = balance;
        this.operations = new ArrayList<Integer>();
    }

    public Long getCbu() {
        return cbu;
    }

    public void setCbu(Long cbu) {
        this.cbu = cbu;
    }

    public Double getBalance() {
        return balance;
    }



    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public void updateOperation(Integer value) {
        this.operations.add(value);
    }

    public void setOperations(List<Integer> operations) {
        this.operations = operations;
    }

    public Double checkPromo(Double sum) {
        if (sum >= minime){
            double calculate = sum * 0.10;
            if (calculate > tope)
                    return sum + 500;
            else{
                return sum + calculate;
            }
        }
        return sum;
    }
}
