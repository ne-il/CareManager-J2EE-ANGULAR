package application.resources;

import application.staff.domain.Staff;
import com.sun.istack.NotNull;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Entity
public class AuthenticateStaff {

    @NotNull
    @Id
    private String login;

    @NotNull
    private String password;

    public AuthenticateStaff(){

    }

    public AuthenticateStaff(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static String generateHash(String input) {
        StringBuilder hash = new StringBuilder();

        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            byte[] hashedBytes = sha.digest(input.getBytes());
            char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                    'a', 'b', 'c', 'd', 'e', 'f' };
            for (int idx = 0; idx < hashedBytes.length;   idx++) {
                byte b = hashedBytes[idx];
                hash.append(digits[(b & 0xf0) >> 4]);
                hash.append(digits[b & 0x0f]);
            }
        } catch (NoSuchAlgorithmException e) {
            // handle error here.
        }

        return hash.toString();
    }

    public static boolean authenticateStaff(Staff staff, String plainPassword) {
        String saltedPassword = Staff.SALT + plainPassword;
        String hashedPassword = AuthenticateStaff.generateHash(saltedPassword);
        return staff.getPassword().equals(hashedPassword);
    }

}
