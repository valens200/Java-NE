package rca.ne.java.v1.models;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
public abstract class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String firstName;
    private String lastName;
    private String mobile;
    private String email;
    private Date dob;


    public Person(String firstName, String lastName, String nationalId, String phoneNumber, String email){
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobile =  phoneNumber;
        this.email = email;
    }
    public Person(){}

}
