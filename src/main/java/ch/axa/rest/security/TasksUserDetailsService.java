package ch.axa.rest.security;



import ch.axa.rest.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class TasksUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public TasksUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       final ch.axa.rest.model.User cinemaUser = userRepository.findByName(username).get();
       if (cinemaUser == null) {
           throw new UsernameNotFoundException(username);
       }
       UserDetails user = User
               .withUsername(cinemaUser.getName())
               .password(cinemaUser.getPassword())
               .roles(cinemaUser.getRole())
//               .authorities(cinemaUser.getAuthorities())
               .build();

        return user;
    }
}
