package rca.ne.java.v1.services.serviceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import rca.ne.java.v1.exceptions.BadRequestException;
import rca.ne.java.v1.models.Customer;
import rca.ne.java.v1.models.User;
import rca.ne.java.v1.models.enums.EBankingType;
import rca.ne.java.v1.services.MailService;
import rca.ne.java.v1.utils.Mail;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class MailServiceImpl extends ServiceImpl implements MailService {

    private final SpringTemplateEngine templateEngine;
    @Value("${spring.mail.username}")
    private String appEmail;


    @Value("${swagger.app_name}")
    private String appName;

    @Value("${client.host}")
    private String clientHost;
    @Value("${front.host}")
    private String frontHost;

    @Value("${admin.email}")
    private String adminEmail;

    @Bean
    private JavaMailSender mailSender(){
        return new JavaMailSenderImpl();
    }

    @Override
    public void sendSaveOrWithDrawEmail(Customer customer, EBankingType bankingType, double amount){
        Mail mail = new Mail(
                appName,
                "Transaction on your Bank account",
                customer.getFirstName() + " " + customer.getLastName(),
                customer.getEmail(),
                bankingType,
                amount,
                "",
                "save-withdraw"
        );
        sendMail(mail);
    }

    public void sendLogsToAdmin(File file) {
        // Assuming Mail class has been adjusted to include a File field and corresponding setter/getter methods.
        Mail mail = new Mail(
                appName,
                "Logs for the " + appName + " application",
                "Admin DJB",
                adminEmail,
                null, // Assuming this constructor parameter is 'data', and null is acceptable when sending a file.
                "send-logs",
                file
        );
        // Set the file to be attached
        mail.setFile(file); // Ensure your Mail class has this setter method.

        // Send the mail
        sendMail(mail);
    }

    @Async
    public void sendMail(Mail mail) {
        try {
            MimeMessage message = mailSender().createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            Context context = new Context();
            context.setVariable("app_name",mail.getAppName());
            context.setVariable("link", mail.getData());
            context.setVariable("name", mail.getFullNames());
            context.setVariable("otherData", mail.getOtherData());
            context.setVariable("subject",mail.getSubject());
            context.setVariable("banking_type", mail.getBankingType());
            context.setVariable("amount", mail.getAmount());
            String html = templateEngine.process(mail.getTemplate(), context);
            helper.setTo("mukamalouis1@gmail.com");
            helper.setText(html, true);
            helper.setSubject(mail.getSubject());
            helper.setFrom(appEmail);

            // Check if there's a file to attach
            if (mail.getFile() != null && mail.getFile().exists()) {
                helper.addAttachment(mail.getFile().getName(), mail.getFile());
            }

            mailSender().send(message);


        } catch (MessagingException exception) {
            throw new BadRequestException("Failed To Send An Email : z"+  exception.getMessage());
        }
    }

}