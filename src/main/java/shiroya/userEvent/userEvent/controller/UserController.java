package shiroya.userEvent.userEvent.controller;


import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import shiroya.userEvent.userEvent.DTO.UserRequest;
import shiroya.userEvent.userEvent.userEntity.UserEntity;
import shiroya.userEvent.userEvent.userService.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping()
    public ResponseEntity<String> createUser(@RequestBody UserRequest user){

        userService.createUserService(user);

        return new ResponseEntity<>("Created", HttpStatus.CREATED);
    }

    @GetMapping("/{userName}")
    public UserEntity fetchUser(@PathVariable String userName,
                                @RequestHeader("Authorization") String authHeader,
                                HttpServletRequest request,
                                @RequestHeader("X-User-Id") String userId,
                                @RequestHeader("X-Roles") String roles){
        System.out.println(roles);
        System.out.println(authHeader);
        return userService.findUserByUsername(userName);
    }

    @GetMapping("userid/{userId}")
    @PreAuthorize("hasRole('ADMIN') or #userId == authentication.name")
    public UserEntity fetchUserWithUserId(@PathVariable String userId,
                                          HttpServletRequest request,
                                          @RequestHeader("X-Roles") String roles){

        String currentUser = (String) request.getAttribute("userId");
        List<String> roles1 = (List<String>) request.getAttribute("roles");
        System.out.println(currentUser);
        System.out.println(roles1);
        System.out.println(roles);
        return userService.fetchUserUserId(userId);
    }

}
