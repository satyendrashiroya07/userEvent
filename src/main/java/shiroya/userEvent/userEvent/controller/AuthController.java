package shiroya.userEvent.userEvent.controller;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import shiroya.loggingRequest.LoginRequest;
import shiroya.userEvent.userEvent.security.JwtUtil;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class AuthController {

    private final JwtUtil jwtUtil;

    @PostMapping()
    public String login(@RequestBody LoginRequest request) {
        return jwtUtil.generateToken(request.getUserId());
    }
}

