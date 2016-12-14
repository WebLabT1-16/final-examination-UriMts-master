package cat.tecnocampus.configuration;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * Created by roure on 31/10/2016.
 */
@Component
public class WebSecurity {

    public boolean checkUsername(Authentication authentication, String username) {
        return username.equals(authentication.getName());
    }
}
