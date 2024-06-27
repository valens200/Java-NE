package rca.ne.java.v1.services.serviceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rca.ne.java.v1.dtos.requests.CreateTransactionDTO;
import rca.ne.java.v1.dtos.requests.SaveOrWithDrawDTO;
import rca.ne.java.v1.exceptions.BadRequestException;
import rca.ne.java.v1.exceptions.NotFoundException;
import rca.ne.java.v1.models.Account;
import rca.ne.java.v1.models.Customer;
import rca.ne.java.v1.models.Transaction;
import rca.ne.java.v1.models.enums.EBankingType;
import rca.ne.java.v1.repositories.IAccountRepository;
import rca.ne.java.v1.repositories.ICustomerRepository;
import rca.ne.java.v1.repositories.ITransactionRepository;
import rca.ne.java.v1.services.ITransactionService;
import rca.ne.java.v1.services.IUserService;
import rca.ne.java.v1.services.MailService;
import rca.ne.java.v1.utils.ExceptionsUtils;
import rca.ne.java.v1.utils.helpers.Helper;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl extends ServiceImpl implements ITransactionService {
    private final IAccountRepository accountRepository;
    private final ITransactionRepository transactionRepository;
    private final ICustomerRepository customerRepository;
    private final MailService mailService;
    private final IUserService userService;
    private Optional<Customer> customerOptional;

    /**
     * TransactionServiceImpl::transfer
     * - Method to transfer the money to another account
     * @param dto
     * @param bankingType
     * @return Transaction
     */
    @Override
    @Transactional
    public Transaction transfer(CreateTransactionDTO dto, EBankingType bankingType) {
        try {
//            Check if two accounts in transaction are available and throw exceptions otherwise.
            customer = customerRepository.findByProfile(user).orElseThrow(() -> new NotFoundException("Your profile is not linked with any account"));
            account = customer.getAccount();
            Account destAccount = accountRepository.findById(dto.getDestination_id()).orElseThrow(() -> new NotFoundException("The destination account with the provided id is not found"));
            Helper.logAction(String.format("Transferring money from %s ", account.getId()));
//            Check and validate input amount
            if(dto.getAmount() <= 0) throw  new BadRequestException("You can not perform a transaction on amount less or equal to 0");
            transaction = new Transaction();

            /**
             * Check if the user has enough amount if not throw exception
             */
            if(account.getBalance() < dto.getAmount()) throw new BadRequestException("You don't have enough money on your account");
//            Deduct money from the account which is transferring money.
            account.setBalance(account.getBalance() - dto.getAmount());
            /*
              - Add money to the destination account
             */
            destAccount.setBalance(destAccount.getBalance() + dto.getAmount());

            //Update the destination account database
            destAccount =  accountRepository.save(destAccount);
            //Update the owner account database
            account = accountRepository.save(account);
            transaction.setAccount(account);
            transaction.setDestination(destAccount);
            transaction.setAmount(dto.getAmount());
            transaction.setBankingType(EBankingType.TRANSFER);

            transaction =  transactionRepository.save(transaction);
            customerOptional = customerRepository.findByAccount(account);
//            Send the email to the customer and return transaction.
            customerOptional.ifPresent(value -> this.mailService.sendSaveOrWithDrawEmail(value, EBankingType.TRANSFER, transaction.getAmount()));
            return transaction;
        }catch (Exception exception){
            System.out.println("=====================================================================");
            System.out.println(exception.getMessage());
            System.out.println("=====================================================================");
            ExceptionsUtils.handleServiceExceptions(exception);
            return null;
        }
    }
    /**
     *
     * @param dto
     * @return Transaction
     */
    @Override
    @Transactional
    public Transaction save(SaveOrWithDrawDTO dto) {
        try{
            user = userService.getLoggedInUser();
            customer = customerRepository.findByProfile(user).orElseThrow(() -> new NotFoundException("Your profile is not linked with any account"));
            account = customer.getAccount();
            /**
             * Logging the logs
             */
            Helper.logAction(String.format("Saving " + dto.getAmount() + " to an account " + account.getId()));
            /**
             *
             * Add money and increase the balance of the account
             */
            account.setBalance(account.getBalance() + dto.getAmount());
            transaction = new Transaction();
            transaction.setAccount(account);
            transaction.setAmount(dto.getAmount());
            transaction.setBankingType(EBankingType.SAVING);
            transaction =  transactionRepository.save(transaction);
            customerOptional = customerRepository.findByAccount(account);
            /**
             * Update the transaction and return it to the client.
             */
            customerOptional.ifPresent(value -> this.mailService.sendSaveOrWithDrawEmail(value, EBankingType.SAVING, transaction.getAmount()));
            return transaction;
        }catch (Exception exception){
            ExceptionsUtils.handleServiceExceptions(exception);
            return null;
        }
    }
    /**
     *
     * @param dto
     * @return Transaction
     */
    @Override
    @Transactional
    public Transaction withdraw(SaveOrWithDrawDTO dto) {
        try{
            user = userService.getLoggedInUser();
            customer = customerRepository.findByProfile(user).orElseThrow(() -> new NotFoundException("Your profile is not linked with any account"));
            account = customer.getAccount();
            Helper.logAction(String.format("Withdrawing " + dto.getAmount() + " from an account " + account.getId()));

            if(account.getBalance() < dto.getAmount()) throw new BadRequestException("You don't have enough balance");
            account.setBalance(account.getBalance() - dto.getAmount());
            transaction = new Transaction();
            transaction.setAccount(account);
            transaction.setAmount(dto.getAmount());
            transaction.setBankingType(EBankingType.WITHDRAW);
            /**
             * Update the transaction and return it to the client.
             */
            transaction =  transactionRepository.save(transaction);
            customerOptional = customerRepository.findByAccount(account);
            /**
             * Send email to the client
             */
            customerOptional.ifPresent(value -> this.mailService.sendSaveOrWithDrawEmail(value, EBankingType.WITHDRAW, transaction.getAmount()));
            return transaction;
        }catch (Exception exception){
            ExceptionsUtils.handleServiceExceptions(exception);
            return null;
        }
    }
    /**
     *
     * @return Account
     */
    @Override
    public Account getMyAccount(){
        try {
            user = userService.getLoggedInUser();
            customer = customerRepository.findByProfile(user).orElseThrow(() -> new NotFoundException("Your profile is not linked with any account"));
            Helper.logAction(String.format("Getting account for  an account :  " + customer.getAccount().getId()));
            return customer.getAccount();
        }catch (Exception exception){
            ExceptionsUtils.handleServiceExceptions(exception);
            return null;
        }
    }
}
