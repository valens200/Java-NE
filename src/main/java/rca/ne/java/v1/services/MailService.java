package rca.ne.java.v1.services;
import rca.ne.java.v1.models.Customer;
import rca.ne.java.v1.models.User;
import rca.ne.java.v1.models.enums.EBankingType;
import rca.ne.java.v1.utils.Mail;
import org.springframework.stereotype.Component;

@Component
public interface MailService {

    void sendSaveOrWithDrawEmail(Customer customer, EBankingType bankingType, double amount);

    public void sendMail(Mail mail);
}
