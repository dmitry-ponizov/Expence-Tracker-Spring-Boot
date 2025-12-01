package my.app.service;

import my.app.dto.AppUserDTO;
import my.app.dto.AuthDTO;
import my.app.dto.AuthResponseDTO;

public interface AuthService {
    AuthResponseDTO registerUser(AppUserDTO appUserDTO);
    AuthResponseDTO loginUser(AuthDTO authDTO);
}
