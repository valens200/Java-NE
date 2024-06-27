package rca.ne.java.v1.services;


import rca.ne.java.v1.dtos.requests.CreateTransactionDTO;
import rca.ne.java.v1.dtos.requests.SaveOrWithDrawDTO;
import rca.ne.java.v1.models.Account;
import rca.ne.java.v1.models.Transaction;
import rca.ne.java.v1.models.enums.EBankingType;

public interface ITransactionService {
    public Transaction transfer(CreateTransactionDTO dto, EBankingType bankingType);
    public Transaction save(SaveOrWithDrawDTO dto);
    public Transaction withdraw(SaveOrWithDrawDTO dto);

    Account getMyAccount();
}
