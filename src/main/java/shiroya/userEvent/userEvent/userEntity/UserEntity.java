package shiroya.userEvent.userEvent.userEntity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@Table(name = "tb_users")
@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "user_id", length = 20, nullable = false, unique = true)
    private String userId;

    @Column(length = 200, nullable = false)
    private String password;

    @Column(length = 100, nullable = false)
    private String userName;

    @Column(length = 20, nullable = false)
    private String userDob;

    @Column(length = 200, nullable = false)
    private String userAddress;

    @Column(length = 20, nullable = false)
    private String userMobileNumber;

    @Column(length = 50, nullable = false)
    private String userEmail;

    @Column(length = 10, nullable = false)
    private String pinCode;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<RoleEntity> roles;

}
