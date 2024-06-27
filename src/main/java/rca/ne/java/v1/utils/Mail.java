package rca.ne.java.v1.utils;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rca.ne.java.v1.models.enums.EBankingType;

import java.io.File;

@Getter
@Setter
@Data
@NoArgsConstructor
public class Mail {
//    String appName, String transactionOnYourBankAccount, String s, String email, EBankingType bankingType, double amount, String s1, String s2
    private String appName;
    private String subject;
    private String fullNames;
    private String toEmail;
    private String template;
    private String data;
    private Object otherData;
    private File file;
    private EBankingType bankingType;
    private double amount;
    private String accountCode;

    public Mail(String appName, String subject, String fullNames, String toEmail,String data,String template) {
        this.appName = appName;
        this.subject = subject;
        this.fullNames = fullNames;
        this.toEmail = toEmail;
        this.template = template;
        this.data = data;
    }

    public Mail(String username, String courseName, String s) {
        this.appName=username;
        this.subject=courseName;
        this.template=s;

    }

    public Mail(String appName, String subject, String fullNames, String toEmail,String data,String template , File file) {
        this.appName = appName;
        this.subject = subject;
        this.fullNames = fullNames;
        this.toEmail = toEmail;
        this.template = template;
        this.data = data;
        this.file = file;
    }

    public Mail(String appName, String subject, String s, String email, EBankingType bankingType, double amount, String accountCode, String template) {
        this.appName=appName;
        this.subject=subject;
        this.template=template;
        this.bankingType = bankingType;
        this.amount = amount;
        this.accountCode = accountCode;
        this.toEmail=email;
    }
}
