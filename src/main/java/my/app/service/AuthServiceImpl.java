package my.app.service;

import my.app.dto.AppUserDTO;
import my.app.dto.AuthDTO;
import my.app.dto.AuthResponseDTO;
import my.app.model.Role;
import my.app.model.User;
import my.app.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthServiceImpl(UserService userService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public AuthResponseDTO registerUser(AppUserDTO appUserDTO) {
        if(userService.findByUsername(appUserDTO.getUsername()) != null) {
           return new AuthResponseDTO(null, "Error:Username is already taken!");
        }
        User user = new User();
        user.setFullName(appUserDTO.getFullName());
        user.setUsername(appUserDTO.getUsername());
        user.setPassword(passwordEncoder.encode(appUserDTO.getPassword()));
        user.setRole(Role.USER);
        userService.saveUser(user);

        AuthDTO authDTO = new AuthDTO();
        authDTO.setUsername(appUserDTO.getUsername());
        authDTO.setPassword(appUserDTO.getPassword());

        return loginUser(authDTO);
    }

    @Override
    public AuthResponseDTO loginUser(AuthDTO authDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authDTO.getUsername(),
                            authDTO.getPassword()
                    )
            );

            final String token = jwtUtil.generateToken(authDTO.getUsername());
            return new AuthResponseDTO(token, "success");
        } catch (Exception e) {
            return new AuthResponseDTO(null, "Error: Invalid username or password!");
        }
    }
}
