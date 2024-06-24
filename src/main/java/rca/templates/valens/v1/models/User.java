package rca.templates.valens.v1.models;
import rca.templates.valens.v1.dtos.requests.UserTypesDTO;
import rca.templates.valens.v1.models.enums.EAccountStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "users")
public class User  {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String firstName;
    private String lastName;

    private String email;

    private String password;
    private String profilePicture;

    private EAccountStatus status = EAccountStatus.ACTIVE;
    private String activationCode;

    @Transient
    private List<UserTypesDTO> userTypesDTOList;
    public User(){}

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public User(String firstName, String lastName, String email, String password){
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }

}
