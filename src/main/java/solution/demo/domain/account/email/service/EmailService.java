package hooyn.base.domain.account.email.service;

import hooyn.base.global.exception.CustomException;
import hooyn.base.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender emailSender;
    private final TemplateEngine templateEngine;

    //application.properties에서 사용자 Email 정보 가져오기
    @Value("${spring.mail.username}")
    private String from;

    //이메일 전송이 오래걸려서 비동기로 처리하기 위해 @Async 키워드를 사용하여 처리하였습니다.
    //다음과 같이 사용하기 위해서는 Application.class 에 @EnableAsync 을 추가해주어야 합니다.
    @Async
    public void sendAuthEmail(String email, String authCode) {
        try {
            MimeMessage emailForm = makeEmailForm(email, authCode);
            emailSender.send(emailForm);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.BAD_REQUEST, e.getMessage());
        }
    }

    //이메일 인증을 위해 사용자에게 보낼 이메일 폼을 생성합니다.
    private MimeMessage makeEmailForm(String email, String authCode) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();

        message.addRecipients(MimeMessage.RecipientType.TO, email); //보낼 사람 설정
        message.setSubject("LakeLight 회원가입 이메일 인증"); //이메일 제목
        message.setFrom("LakeLight <"+from+">"); //보내는 사람 설정
        message.setContent(setContext(authCode), "text/html;charset=euc-kr"); //setContext를 통해 html을 만들고 전송

        return message;
    }

    //HTML 제작하고 인증 코드도 HTML에서 보여줄 수 있도록 전달
    private String setContext(String authCode) {
        Context context = new Context();
        context.setVariable("code", authCode);
        return templateEngine.process("email", context);
    }

    //인증 코드 제작하는 코드
    //비동기 처리를 위해 public으로 하고 Controller 부분에서 파라미터로 전달
    public String makeAuthCode() {
        Random random = new Random();
        StringBuffer key = new StringBuffer();

        for (int i = 0; i < 8; i++) {
            int index = random.nextInt(3);

            switch (index) {
                case 0 -> key.append((char) (random.nextInt(26) + 97));
                case 1 -> key.append((char) (random.nextInt(26) + 65));
                case 2 -> key.append(random.nextInt(9));
            }
        }
        return key.toString();
    }
}
