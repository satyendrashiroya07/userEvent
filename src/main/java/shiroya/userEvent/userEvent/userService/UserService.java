package shiroya.userEvent.userEvent.userService;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import shiroya.userEvent.UserEvent;
import shiroya.userEvent.userEvent.DTO.UserRequest;
import shiroya.userEvent.userEvent.exception.DuplicateUserException;
import shiroya.userEvent.userEvent.exception.UserNotCreatedException;
import shiroya.userEvent.userEvent.producer.userProducer;
import shiroya.userEvent.userEvent.userEntity.RoleEntity;
import shiroya.userEvent.userEvent.userEntity.UserEntity;
import shiroya.userEvent.userEvent.userRepo.RoleRepo;
import shiroya.userEvent.userEvent.userRepo.UserRepo;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;
    private final userProducer producer;
    private final RoleRepo roleRepo;

    public UserEntity createUserService(UserRequest request){

        UserEntity usr = userRepo.getByUserId(request.getUserId());

        if(Objects.isNull(usr))
        {
            String pass = request.getPassword();
            request.setPassword(passwordEncoder.encode(pass));

            Set<RoleEntity> roles = request.getRoles().stream()
                    .map(roleName -> roleRepo.findByName(roleName)
                            .orElseThrow(() -> new RuntimeException("Role not found: " + roleName)))
                    .collect(Collectors.toSet());

            UserEntity user = new UserEntity();
            user.setUserId(request.getUserId());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setUserName(request.getUserName());
            user.setUserDob(request.getUserDob());
            user.setUserAddress(request.getUserAddress());
            user.setUserMobileNumber(request.getUserMobileNumber());
            user.setUserEmail(request.getUserEmail());
            user.setPinCode(request.getPinCode());
            user.setRoles(roles);


            UserEntity save = (UserEntity) userRepo.save(user);

            UserEvent event = UserEvent.builder().
                    userId(user.getUserId()).
                    userName(user.getUserName()).
                    userMobileNumber(user.getUserMobileNumber()).
                    userEmail(user.getUserEmail()).
                    build();

            producer.sendOrderEvent(event);

            return save;
        }else {
            throw new DuplicateUserException("User Already Created");
        }

    }

    public List<UserEntity> findUserByUsername(String userName){

               try
               {
                   List<UserEntity> users = userRepo.findByUserNameContainingIgnoreCase(userName);
                   return users;
               }
               catch (RuntimeException e){
                   throw new UserNotCreatedException("Something is Wrong");
        }
    }

    public UserEntity fetchUserUserId(String userId){

        try
        {
            return userRepo.getByUserId(userId);
        }
        catch (RuntimeException e){
            throw new UserNotCreatedException("Something is Wrong");
        }
    }
}
