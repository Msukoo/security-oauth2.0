package com.cos.security1.config.auth;

import com.cos.security1.domain.entity.User;
import com.cos.security1.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// loginProcessingUrl("/login);
// login 요청이 오면 자동으로 UserDetailsService 타입으로 loc 되어있는 loadUserByUsername 함수가 실행
@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override // 시큐리티 session(내부 Authentication(내부 UserDetails))
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user != null){
            return new PrincipalDetails(user);
        }
        return null;
    }
}
