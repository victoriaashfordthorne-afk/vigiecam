package Mbemlevel.example.VigieCam.OAuth2UserService;



import Mbemlevel.example.VigieCam.Config.ModeratorProperties;
import Mbemlevel.example.VigieCam.Enums.Role;
import Mbemlevel.example.VigieCam.Model.User;
import Mbemlevel.example.VigieCam.Repository.UserRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService
        extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final ModeratorProperties moderatorProperties;

    public CustomOAuth2UserService(
            UserRepository userRepository,
            ModeratorProperties moderatorProperties
    ) {
        this.userRepository = userRepository;
        this.moderatorProperties = moderatorProperties;
    }

    @Override
    public OAuth2User loadUser(
            OAuth2UserRequest userRequest
    ) throws OAuth2AuthenticationException {

        // Ask Google for the user's information
        OAuth2User googleUser =
                super.loadUser(userRequest);

        String email =
                googleUser.getAttribute("email");

        String name =
                googleUser.getAttribute("name");

        String photoUrl =
                googleUser.getAttribute("picture");

        // Check whether the email belongs to a moderator
        Role role =
                moderatorProperties.getEmails()
                        .stream()
                        .anyMatch(
                                moderatorEmail ->
                                        moderatorEmail.equalsIgnoreCase(email)
                        )
                        ? Role.MODERATOR
                        : Role.USER;

        // Search for existing user
        User user =
                userRepository.findByEmail(email)
                        .orElseGet(User::new);

        // Update user information
        user.setName(name);
        user.setEmail(email);
        user.setPhotoUrl(photoUrl);
        user.setRole(role);

        // Save user
        userRepository.save(user);

        return googleUser;
    }
}
