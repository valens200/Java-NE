package rca.templates.valens.v1.models;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
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

    private String nationalId;
    private String phoneNumber;
    private String email;

    public Person(String firstName, String lastName, String nationalId, String phoneNumber, String email){
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationalId = nationalId;
        this.phoneNumber =  phoneNumber;
        this.email = email;
    }
    public Person(){}

}
