package shiroya.userEvent.userEvent.userService;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import shiroya.loggingRequest.LoginRequest;
import shiroya.userEvent.userEvent.exception.BadCredentialsUserOrPass;
import shiroya.userEvent.userEvent.security.JwtUtil;
import shiroya.userEvent.userEvent.userEntity.UserEntity;
import shiroya.userEvent.userEvent.userRepo.UserRepo;

@RequiredArgsConstructor
@Service
public class GenerateTokenService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public String userToken(LoginRequest request)
    {
        UserEntity user = userRepo.getByUserId(request.getUserId());

        if (user == null ||
                !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsUserOrPass("Invalid user ID or password");
        }

        return jwtUtil.generateToken1(user);
    }
}
