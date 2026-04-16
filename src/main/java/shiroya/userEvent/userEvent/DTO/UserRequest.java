package shiroya.userEvent.userEvent.DTO;

import lombok.Data;

import java.util.Set;

@Data
public class UserRequest {

    private String userId;
    private String password;
    private String userName;
    private String userDob;
    private String userAddress;
    private String userMobileNumber;
    private String userEmail;
    private String pinCode;

    private Set<String> roles;
}

