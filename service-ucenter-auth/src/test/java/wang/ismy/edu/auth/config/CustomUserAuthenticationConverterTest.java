package wang.ismy.edu.auth.config;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.Assert.*;

public class CustomUserAuthenticationConverterTest {

    @Test
    public void testEncrypt(){
        System.out.println(new BCryptPasswordEncoder().encode("123"));
    }
}