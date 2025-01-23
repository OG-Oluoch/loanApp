package com.security.loanApp.service;

import com.security.loanApp.model.Users;
import com.security.loanApp.repository.UsersRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final UsersRepository userRepository;

    public UsersService(BCryptPasswordEncoder bCryptPasswordEncoder, UsersRepository userRepository){
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;

        this.userRepository=userRepository;
  }
  public List<Users> getUsers(){
      return userRepository.findAll();
  }

  public Users getUser(Long id){

        return userRepository.findById(id).orElse(null);
  }

  public Users addUser(Users users){
        users.setPassword(bCryptPasswordEncoder.encode(users.getPassword()));
        return userRepository.save(users);
  }
  public Users updateUser(Users users){
        return userRepository.save(users);
  }
  public void deleteUser(Long id){

        userRepository.deleteById(id);
  }
    public boolean authenticate(String username, String password) {
        // Find user by username
        Users user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User does not exist in the database");
        }

        // Verify the password
        if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("The password is incorrect");
        }

        return true;
    }

}
