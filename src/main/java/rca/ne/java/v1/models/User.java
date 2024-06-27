package rca.ne.java.v1.models;
import rca.ne.java.v1.models.enums.EAccountStatus;
import rca.ne.java.v1.dtos.requests.UserTypesDTO;
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

    private String email;

    private String password;

    private EAccountStatus status = EAccountStatus.ACTIVE;

    public User(){}

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public User(String email, String password){
        this.email = email;
        this.password = password;
    }

}
