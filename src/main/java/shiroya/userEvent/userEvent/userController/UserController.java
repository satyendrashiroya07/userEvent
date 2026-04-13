package shiroya.userEvent.userEvent.userController;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shiroya.userEvent.userEvent.userEntity.UserEntity;
import shiroya.userEvent.userEvent.userService.UserService;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping()
    public ResponseEntity<String> createUser(@RequestBody UserEntity user){

        userService.createUserService(user);

        return new ResponseEntity<>("Created", HttpStatus.CREATED);
    }

    @GetMapping("/{userName}")
    public UserEntity fetchUser(@PathVariable String userName,
                                @RequestHeader("Authorization") String authHeader){

        System.out.println(authHeader);
        return userService.findUserByUsername(userName);
    }

    @GetMapping("userid/{userId}")
    public UserEntity fetchUserWithUserId(@PathVariable String userId,
                                @RequestHeader("Authorization") String authHeader){

        System.out.println(authHeader);
        return userService.fetchUserUserId(userId);
    }

}
