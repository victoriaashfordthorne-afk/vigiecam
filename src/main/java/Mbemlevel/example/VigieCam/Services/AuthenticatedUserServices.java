package Mbemlevel.example.VigieCam.Services;


import Mbemlevel.example.VigieCam.Model.User;
import Mbemlevel.example.VigieCam.Repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class AuthenticatedUserServices {

    private final UserRepository userRepository;

    public AuthenticatedUserServices(
            UserRepository userRepository
    ) {
        this.userRepository = userRepository;
    }

    public User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        if (authentication == null ||
                !authentication.isAuthenticated()) {

            throw new RuntimeException(
                    "User is not authenticated"
            );
        }

        if (!(authentication.getPrincipal()
                instanceof OAuth2User oauth2User)) {

            throw new RuntimeException(
                    "Authenticated user is not a Google OAuth2 user"
            );
        }

        String email =
                oauth2User.getAttribute("email");

        if (email == null || email.isBlank()) {

            throw new RuntimeException(
                    "Email not found in Google account"
            );
        }

        return userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found in database"
                        )
                );
    }
}
