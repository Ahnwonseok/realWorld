package project7.clonecoding.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import project7.clonecoding.user.UserRepository;
import project7.clonecoding.user.entity.Users;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Users user = userRepository.findByUserName(userName).orElseThrow(
                () -> new IllegalArgumentException("사용자가 없습니다.")
        );

        return new UserDetailsImpl(user, user.getUserName());
    }

}