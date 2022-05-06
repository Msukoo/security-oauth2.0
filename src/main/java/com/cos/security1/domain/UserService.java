package com.cos.security1.domain;

import com.cos.security1.domain.entity.User;
import com.cos.security1.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User oAuth2UserSave(OAuth2User oAuth2User, String provider){
        String providerId = oAuth2User.getAttribute("sub");
        String username = provider + "_" + providerId;
        String password = "oauth";
        String email = oAuth2User.getAttribute("email");
        String role = "ROLE_USER";

        User user = userRepository.findByUsername(username);
        if(user != null){
            return user;
        }
        user = User.builder()
                .username(username)
                .password(password)
                .email(email)
                .role(role)
                .provider(provider)
                .providerId(providerId)
                .build();
        return userRepository.save(user);
    }
}
