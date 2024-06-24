package rca.templates.valens.v1.utils.helpers;
import rca.templates.valens.v1.exceptions.BadRequestException;
import rca.templates.valens.v1.models.enums.EGender;
import rca.templates.valens.v1.services.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
public class Helper {

    private static IUserService userService;

    public static String getActor(){
        return "Done at : " + LocalDateTime.now() +  " By Email : " +  userService.getLoggedInUser().getEmail() + ", Name : " + userService.getLoggedInUser().getFirstName() + " " + userService.getLoggedInUser().getLastName() ;
    }
    public static void logAction(String message){
        log.info(message);
        log.info(getActor());
    }
    public static EGender getGender(String gender){
        System.out.println("Gender : " + gender);
        switch (gender.toUpperCase()){
            case "MALE", "M":
                return EGender.MALE;
            case "FEMALE", "F":
                return EGender.FEMALE;
            case "OTHER":
                return  EGender.OTHER;
            default:
                throw new BadRequestException("The provided gender is invalid");
        }
    }
    public static String generateRandomPasswordString(int length) {
        String ALPHA = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String NUM = "0123456789";
        String ALPHANUM = ALPHA + NUM;
        Random rng = new Random();
        StringBuilder sb = new StringBuilder();
        while (length > 0) {
            length--;
            sb.append(ALPHANUM.charAt(rng.nextInt(ALPHANUM.length())));
        }
        return sb.toString();
    }



}
