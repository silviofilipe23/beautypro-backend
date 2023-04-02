package br.com.beautypro.services;

import br.com.beautypro.models.User;
import br.com.beautypro.repository.UserRepository;
import br.com.beautypro.util.EmailUtil;
import br.com.beautypro.util.TokenUtil;
import com.twilio.converter.Promoter;
import com.twilio.type.PhoneNumber;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.Optional;

@Service
public class PasswordResetService {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    PasswordEncoder encoder;

    @Autowired
    private EmailUtil emailUtil;

    public boolean validatePasswordResetToken(String userEmail, String token) {

        Optional<User> user = userRepository.findByEmail(userEmail);

        if (!user.isPresent()) {
            return false;
        }

        User userReturn = user.get();

        String storedToken = userReturn.getPasswordResetToken();

        if (storedToken == null || storedToken.isEmpty()) {
            return false;
        }

        return TokenUtil.validateToken(token, storedToken);
    }

    public void sendEmailResetPassword(String userEmail) throws MessagingException {

        String token = TokenUtil.generateToken();

        Optional<User> userData = userRepository.findByEmail(userEmail);

        User userUpdate;
        userUpdate = userData.get();

        userData.get().setPasswordResetToken(token);

        userRepository.save(userUpdate);

        String subject = "Recuperação de senha";
        String body = "Olá! Recebemos uma solicitação de recuperação de senha para sua conta. "
                + "Clique neste link para redefinir sua senha: http://localhost:4200/reset-password?token=" + token + "&email=" + userData.get().getEmail();

        emailUtil.sendEmail(userEmail, subject, body);

        Message message = Message.creator(
                        new com.twilio.type.PhoneNumber("+5562998539835"),
                        new com.twilio.type.PhoneNumber("+15074897420"),
                        body)
                .create();

        System.out.println(message.getSid()); // exibe o SID da mensagem
    }

    public boolean saveNewPassword(String email, String newPassword, String token) {

        boolean tokenIsValid = this.validatePasswordResetToken(email, token);

        System.out.println("tokenIsValid " + tokenIsValid);

        if (tokenIsValid) {
            Optional<User> user = userRepository.findByEmail(email);
            if (!user.isPresent()) {
                return false;
            }
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = passwordEncoder.encode(newPassword);
            System.out.println("encodedPassword " + encodedPassword);
            user.get().setPassword(encodedPassword);
            user.get().setPasswordResetToken(null);
            userRepository.save(user.get());

            return true;
        } else  {
            return false;
        }

    }
}
