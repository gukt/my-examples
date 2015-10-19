package ktgu.lab.coconut.web.service;

import ktgu.lab.coconut.web.domain.User;
import ktgu.lab.coconut.web.exception.DuplicateEmailException;
import ktgu.lab.coconut.web.exception.DuplicateNameException;
import ktgu.lab.coconut.web.repository.GenericRepository;
import ktgu.lab.coconut.web.repository.GenericRepositoryImpl;
import ktgu.lab.coconut.web.repository.UserRepository;
import ktgu.lab.coconut.web.util.HResult;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserService extends GenericRepositoryImpl<User, Long> implements GenericRepository<User, Long> {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private VelocityEngine velocityEngine;

    private void sendRegistrationConfirmEmail(final User user) {
        final MimeMessagePreparator preparator = new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
                message.setTo(user.getEmail());
                message.setFrom("29283212@qq.com");
                message.setSubject("注册coconut成功");

                Map model = new HashMap();
                model.put("user", user);
                String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "/templates/registration-confirm-mail.html", "gb2312", model);
                message.setText(text, true);
            }
        };
        this.mailSender.send(preparator);
    }

    public User register(User user) throws DuplicateEmailException, DuplicateNameException {
        user.setCreateTime(new Date());

        if (this.checkExistenceByName(user.getName())) {
            throw new DuplicateNameException();
        }

        if (this.checkExistenceByEmail(user.getEmail())) {
            throw new DuplicateEmailException();
        }

        // 发送注册确认邮件
        this.sendRegistrationConfirmEmail(user);

        return userRepository.save(user);
    }

    public boolean checkExistenceByName(String name) {
        return userRepository.checkExistenceByName(name);
    }

    public boolean checkExistenceByEmail(String email) {
        return userRepository.checkExistenceByEmail(email);
    }

    public HResult login(String identifier, String password) {
        User user = userRepository.findById("where name=? or email=? or mobile=?", identifier, identifier, identifier);

        if (user != null) {
            if (user.getPassword().equals(password)) {
                return HResult.SUCCESS;
            } else {
                return HResult.PASSWORD_NOT_MATCHED;
            }
        } else {
            return HResult.IDENTIFIER_NOT_EXITS;
        }
    }

    // TODO 验证邮件

    // TODO 找回密码

    // TODO 发注册邮件给制定的用户
}
