package wang.ismy.edu.auth.service;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * @author MY
 * @date 2019/10/28 22:06
 */
@Service
public class UserService {

    public UserDetails loadUserByUsername(String username){

        return new User(username,"$2a$10$6LjS6owlEpWwLHY.0UT9XegE0tr7GbDOjt5oi4OWpJwiuuxbu7hcu", AuthorityUtils.commaSeparatedStringToAuthorityList(""));
    }
}
