package shiroya.userEvent.userEvent.controller;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import shiroya.loggingRequest.LoginRequest;
import shiroya.userEvent.userEvent.security.JwtUtil;
import shiroya.userEvent.userEvent.userService.GenerateTokenService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtUtil jwtUtil;
    private final GenerateTokenService generateTokenService;

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        return generateTokenService.userToken(request);
    }
}

