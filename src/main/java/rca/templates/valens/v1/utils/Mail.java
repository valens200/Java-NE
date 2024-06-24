package rca.templates.valens.v1.utils;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.File;

@Getter
@Setter
@Data
@NoArgsConstructor
public class Mail {
    private String appName;
    private String subject;
    private String fullNames;
    private String toEmail;
    private String template;
    private String data;
    private Object otherData;
    private File file;

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

    public Mail(String username, String subject,String courseName, String s) {
        this.appName=username;
        this.subject=courseName;
        this.template=s;
        this.toEmail=subject;

    }
}
