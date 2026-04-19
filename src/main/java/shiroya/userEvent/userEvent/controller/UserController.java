package shiroya.userEvent.userEvent.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import shiroya.userEvent.userEvent.DTO.UserRequest;
import shiroya.userEvent.userEvent.pagination.PageResponse;
import shiroya.userEvent.userEvent.userEntity.UserEntity;
import shiroya.userEvent.userEvent.userService.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/registration")
    public ResponseEntity<String> createUser(@RequestBody UserRequest user){

        userService.createUserService(user);

        return new ResponseEntity<>("Created", HttpStatus.CREATED);
    }

    @GetMapping("/{userName}")
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserEntity> fetchUser(@PathVariable String userName,
                                      HttpServletRequest request){
        return userService.findUserByUsername(userName);
    }

    @GetMapping("id/{userId}")
    @PreAuthorize("hasRole('ADMIN') or #userId == authentication.name")
    public UserEntity fetchUserWithUserId(@PathVariable String userId,
                                            HttpServletRequest request){

        return userService.fetchUserUserId(userId);
    }

    @GetMapping("/allusers")
    public PageResponse<UserEntity> getUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        return userService.getUsers(page, size, sortBy, direction);
    }
}
