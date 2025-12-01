package my.app.controller;

import my.app.dto.AppUserDTO;
import my.app.dto.AuthDTO;
import my.app.dto.AuthResponseDTO;
import my.app.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService, PasswordEncoder passwordEncoder) {
        this.authService = authService;
        this.passwordEncoder = passwordEncoder;
    }

    private final PasswordEncoder passwordEncoder;



    @PostMapping("/signup")
    public ResponseEntity<AuthResponseDTO> signup(@RequestBody AppUserDTO appUserDTO) {
    AuthResponseDTO responseDTO = authService.registerUser(appUserDTO);

        if("success".equals(responseDTO.getMessage())){
            return ResponseEntity.ok(responseDTO);
        } else {
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }


    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthDTO authDTO) {
        AuthResponseDTO responseDTO = authService.loginUser(authDTO);

        if("success".equals(responseDTO.getMessage())){
            return ResponseEntity.ok(responseDTO);
        } else {
            return ResponseEntity.badRequest().body(responseDTO);
        }

    }
}
