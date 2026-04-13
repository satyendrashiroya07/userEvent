package shiroya.userEvent.userEvent.userService;


import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import productEvent.userEvent.UserEvent;
import shiroya.userEvent.userEvent.exception.DuplicateUserException;
import shiroya.userEvent.userEvent.exception.UserNotCreatedException;
import shiroya.userEvent.userEvent.producer.userProducer;
import shiroya.userEvent.userEvent.userEntity.UserEntity;
import shiroya.userEvent.userEvent.userRepo.UserRepo;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;
    private final userProducer producer;

    public UserEntity createUserService(UserEntity user){

        UserEntity usr = userRepo.getByUserId(user.getUserId());

        if(Objects.isNull(usr))
        {
            String pass = user.getPassword();
            user.setPassword(passwordEncoder.encode(pass));

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

    public UserEntity findUserByUsername(String userName){

               try
               {
                   return userRepo.findByUserName(userName);
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
