package org.example.bankapp.security;

import lombok.NoArgsConstructor;
import org.example.bankapp.model.User;
import org.example.bankapp.repo.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException(username + " not found");
        }
        return new UserPrincipal(user);
    }
}
