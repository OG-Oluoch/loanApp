package com.security.loanApp.security;


import com.security.loanApp.model.Users;
import com.security.loanApp.repository.UsersRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UsersRepository userRepository;

    public MyUserDetailsService(UsersRepository userRepository){
        this.userRepository = userRepository;

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = userRepository.findByUsername(username);

        if(users==null){
            throw new UsernameNotFoundException("This user does not exist");
        }
        return new UserPrincipal(users);
    }
}
