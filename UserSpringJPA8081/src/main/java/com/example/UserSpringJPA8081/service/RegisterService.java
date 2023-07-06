package com.example.UserSpringJPA8081.service;

import com.example.UserSpringJPA8081.entity.TokensEntity;
import com.example.UserSpringJPA8081.entity.UsersEntity;
import com.example.UserSpringJPA8081.entity.UsersRolesEntity;
import com.example.UserSpringJPA8081.payload.request.SignInRequest;
import com.example.UserSpringJPA8081.repository.TokensRepository;
import com.example.UserSpringJPA8081.repository.UsersRepository;
import com.example.UserSpringJPA8081.repository.UsersRolesRepository;
import org.apache.commons.codec.CharEncoding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Optional;

@Service
public class RegisterService {
    private final long expiredDate = 30 * 1000;
    private final long expiredDate1 = 2 * 60 * 1000;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private TokensRepository tokensRepository;
    @Autowired
    private UsersRolesRepository usersRolesRepository;
    //    @Autowired
//    private JavaMailSender mailSender;
    @Autowired
    private JavaMailSenderImpl mailSender;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RolesService rolesService;

    //    public UsersEntity registerNewUserAccount(SignInRequest signInRequest, String siteURL,MultipartFile multipartFile) throws UnsupportedEncodingException, MessagingException {
    public UsersEntity registerNewUserAccount(SignInRequest signInRequest, String siteURL) throws UnsupportedEncodingException, MessagingException {
        if (emailExists(signInRequest.getEmail())) {
            System.out.println("There is an account with that email address: "
                    + signInRequest.getEmail());
            return null;
        } else {
            UsersEntity usersEntity = new UsersEntity();
            usersEntity.setEmail(signInRequest.getEmail());
            usersEntity.setPassword(passwordEncoder.encode(signInRequest.getPassword()));
            System.out.println("Đăng kí thành công");
            sendVerificationEmail(signInRequest.getEmail(), siteURL);
            UsersRolesEntity usersRolesEntity = new UsersRolesEntity();
            usersRolesEntity.setUsersId(usersRepository.save(usersEntity).getId());
//            usersRolesEntity.setUserRolesEnum(UserRolesEnum.ROLE_CUSTOMER_DEFAULT);
            usersRolesEntity.setRolesId(1);
            usersRolesRepository.save(usersRolesEntity);
            return usersEntity;
        }
    }

    public UsersEntity confirmByEmail(String email) {
        Optional<UsersEntity> usersEntity = usersRepository.findByEmail(email);
        if (usersEntity.isPresent()) {
            usersEntity.get().setVerify_active(true);
            return usersRepository.save(usersEntity.get());
        } else
            return null;
    }

    public UsersEntity saveTokenByEmail(String email, String token, String tokenRefresh) {
        Optional<UsersEntity> usersEntity = usersRepository.findByEmail(email);
        if (usersEntity.isPresent()) {
            if (usersEntity.get().getTokensEntities() == null) {
                TokensEntity tokensEntity = new TokensEntity();
                tokensEntity.setToken(token);
                tokensEntity.setTokenRefresh(tokenRefresh);
                tokensEntity.setExpired(false);
                tokensEntity.setRevoked(false);
                tokensEntity.setTokenType("default");
                tokensEntity.setUsersEntity(usersEntity.get());
                usersEntity.get().setTokensEntities(tokensEntity);
            } else {
                usersEntity.get().getTokensEntities().setToken(token);
                usersEntity.get().getTokensEntities().setTokenRefresh(tokenRefresh);
                usersEntity.get().getTokensEntities().setExpired(false);
                usersEntity.get().getTokensEntities().setRevoked(false);
                usersEntity.get().getTokensEntities().setTokenType("default");
            }
            return usersRepository.save(usersEntity.get());
        } else
            return null;
    }


    public boolean emailExists(String email) {
        Optional<UsersEntity> usersEntity = usersRepository.findByEmail(email);
        System.out.println("usersEntity.get() = " + usersEntity.isPresent());
        return usersEntity.isPresent();
    }

    public void signInPassword(String email, String password) {
        //verify
        Optional<UsersEntity> usersEntity = usersRepository.findByEmail(email);

        boolean isMatch = passwordEncoder.matches(password, usersEntity.get().getPassword());
        if (isMatch)
            System.out.println("Đăng nhập thành công");
        else
            System.out.println("Sai pass");
    }


    public void sendVerificationEmail(String email, String siteURL) throws UnsupportedEncodingException, MessagingException {
        String toAddress = email;
        String fromAddress = mailSender.getUsername();
        String senderName = "Client-Service-UserSpringJPA8081";
        String subject = "Chỉ cần nhấp chuột để xác nhận";
        String content = "<h1>Xác minh địa chỉ email của bạn</h1>" +
                "Bạn vừa tạo tài khoản với địa chỉ email: [[email]]<br>" +
                "Nhấn nút \"Xác nhận\" để chứng thực địa chỉ email và mở khóa cho toàn bộ tài khoản.<br>" +
                "Chúng tôi cũng sẽ nhập các đặt phòng bạn đã thực hiện với địa chỉ email này.<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">Xác nhận</a></h3>";
        content = content.replace("[[email]]", email);
        System.out.println("verifyURL = " + siteURL);
        content = content.replace("[[URL]]", siteURL);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, CharEncoding.UTF_8);
        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);
        helper.setText(content, true);
        mailSender.send(message);
    }

    public UsersEntity newPassword(String email, String password) {
        Optional<UsersEntity> usersEntities = usersRepository.findByEmail(email);
        if (usersEntities.isPresent() && StringUtils.hasText(password)) {
            usersEntities.get().setPassword(passwordEncoder.encode(password));
            usersRepository.save(usersEntities.get());
            System.out.println("newPassord thành công");
            return usersEntities.get();
        } else {
            System.out.println("newPassword thất bại");
            return null;
        }
    }

}
