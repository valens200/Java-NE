package rca.templates.valens.v1.utils.helpers;
import org.springframework.stereotype.Component;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class Validator {

    public static boolean isEmailValid(String email){
        if (email !=null){
            String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
            Pattern pattern = Pattern.compile(EMAIL_REGEX);
            Matcher matcher = pattern.matcher(email);
            return  matcher.matches();
        }else {
            return false;
        }

    }
}
