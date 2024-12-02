import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderDemo {
    public static void main(String[] args) {

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        String rawPassword = "password4";
        String encodedPassword = passwordEncoder.encode(rawPassword);
        System.out.println("Encoded password: " + encodedPassword);
    }
}
