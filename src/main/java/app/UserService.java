package app;

import app.database.dao.UserDao;
import app.database.entities.Authority;
import app.database.entities.User;
import app.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
//    @Autowired
//    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            User user = UserDao.getUserDetails(email);

            return new org.springframework.security.core.userdetails.User(user.getEmail(),
                    user.getPassword(), mapAuthorities(user.getAuthorities()));
        } catch (ResourceNotFoundException ex) {
            throw new UsernameNotFoundException(ex.getMessage());
        } catch (Throwable t) {
        	t.printStackTrace();
        	throw new RuntimeException("Ovo se ne bi trebalo dogoditi", t);
        }
    }

    private Collection<? extends GrantedAuthority> mapAuthorities(Collection<Authority> authorities){
        return authorities.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getName().toString()))
                .collect(Collectors.toList());
    }
}
