package shiroya.userEvent.userEvent.userService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import shiroya.userEvent.UserEvent;
import shiroya.userEvent.userEvent.DTO.UserRequest;
import shiroya.userEvent.userEvent.exception.DuplicateUserException;
import shiroya.userEvent.userEvent.exception.UserNotCreatedException;
import shiroya.userEvent.userEvent.pagination.PageResponse;
import shiroya.userEvent.userEvent.producer.userProducer;
import shiroya.userEvent.userEvent.userEntity.RoleEntity;
import shiroya.userEvent.userEvent.userEntity.UserEntity;
import shiroya.userEvent.userEvent.userEntity.OutBoxEventUser;
import shiroya.userEvent.userEvent.userRepo.OutBoxEventUserRepo;
import shiroya.userEvent.userEvent.userRepo.RoleRepo;
import shiroya.userEvent.userEvent.userRepo.UserRepo;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;
    private final userProducer producer;
    private final RoleRepo roleRepo;
    private final OutBoxEventUserRepo outBoxEventUserRepo;
    private final ObjectMapper objectMapper;

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

            OutBoxEventUser kafkaDbEvent = new OutBoxEventUser();
            kafkaDbEvent.setAggregateType("USER");
            kafkaDbEvent.setAggregateId(user.getId().toString());
            kafkaDbEvent.setEventType("USER_CREATED");
            kafkaDbEvent.setPayload(convertToJson(event));
            kafkaDbEvent.setStatus("NEW");

            outBoxEventUserRepo.save(kafkaDbEvent);

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

    public PageResponse<UserEntity> getUsers(int page, int size, String sortBy, String direction) {

        Sort sort = direction.equalsIgnoreCase("asc") ?
                Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<UserEntity> users = userRepo.findAll(pageable);

        List<UserEntity> content = users.stream().collect(Collectors.toList());

        PageResponse<UserEntity> response = new PageResponse<>();
        response.setContent(content);
        response.setPage(users.getNumber());
        response.setSize(users.getSize());
        response.setTotalElements(users.getTotalElements());
        response.setTotalPages(users.getTotalPages());
        response.setLast(users.isLast());

        return response;
    }


    @Scheduled(fixedDelay = 5000)
    public void publishOutboxEvents() {

        List<OutBoxEventUser> events = outBoxEventUserRepo.findByStatus("NEW");

        for (OutBoxEventUser event : events) {
            try {

                UserEvent userEvent =
                        objectMapper.readValue(event.getPayload(), UserEvent.class);

                producer.sendOrderEvent(userEvent);

                event.setStatus("SENT");
                outBoxEventUserRepo.save(event);

            } catch (Exception e) {
                log.error("Failed to publish event {}", event.getId(), e);
            }
        }
    }


    private String convertToJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting to JSON", e);
        }
    }
}
