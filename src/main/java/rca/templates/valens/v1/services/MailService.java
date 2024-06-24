package rca.templates.valens.v1.services;
import rca.templates.valens.v1.models.User;
import rca.templates.valens.v1.utils.Mail;
import org.springframework.stereotype.Component;

@Component
public interface MailService {
    public void sendResetPasswordMail(User user);
    public void sendAccountVerificationEmail(User user);
    public void sendWelcomeEmail(User user);
    public void sendMail(Mail mail);
}
