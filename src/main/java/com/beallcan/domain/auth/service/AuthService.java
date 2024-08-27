package com.beallcan.domain.auth.service;

import com.beallcan.domain.auth.dto.request.SignUpRequest;
import com.beallcan.domain.auth.exception.CodeMismatchException;
import com.beallcan.domain.member.dto.response.MemberResponse;
import com.beallcan.domain.member.entity.Member;
import com.beallcan.domain.member.entity.Role;
import com.beallcan.domain.member.exception.EmailDuplicateException;
import com.beallcan.domain.member.exception.NicknameDuplicateException;
import com.beallcan.domain.member.repository.MemberRepository;
import com.beallcan.global.config.mail.MailService;
import com.beallcan.global.config.redis.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final MemberRepository memberRepository;
    private final RedisService redisService;
    private final MailService mailService;
    private final PasswordEncoder passwordEncoder;

    /**
     * 회원가입
     */
    public MemberResponse signUp(final SignUpRequest signUpRequest) {

        checkEmailDuplicate(signUpRequest.getEmail());
        checkNicknameDuplicate(signUpRequest.getNickname());

        Member createdMember = signUpRequest.toEntity(passwordEncoder, Role.ROLE_USER);

        return MemberResponse.from(memberRepository.save(createdMember));
    }

    /**
     * 이메일 중복 검증
     */
    public void checkEmailDuplicate(final String email) {

        if (memberRepository.findByEmail(email).isPresent()){
            throw new EmailDuplicateException();
        }
    }

    /**
     * 닉네임 중복 검증
     */
    public void checkNicknameDuplicate(final String nickname) {
        if (memberRepository.findByNickname(nickname).isPresent()){
            throw new NicknameDuplicateException();
        }
    }

    /**
     * 이메일 인증번호 발송
     */
    public void sendCode(final String receiverEmail) {

        String mailTitle = "올캔에서 발송한 인증번호를 확인해주세요.";
        Duration duration = Duration.ofMinutes(3);
        String createdCode = generateRandomNumber();
        String mailBody = generateMailBody(createdCode);

        redisService.setValue(receiverEmail, createdCode, duration);
        mailService.sendMail(receiverEmail, mailTitle, mailBody);
    }

    /**
     * 이메일 인증번호 검증
     */
    public String verifyCode(final String email, final String codeInput) {

        if (email.isEmpty() || codeInput.isEmpty()){
            throw new IllegalArgumentException("입력된 값이 없습니다.");
        }

        String resultMessage;
        String issuedCode = redisService.getValue(email);

        if (issuedCode.equals(codeInput)){
            resultMessage = "이메일 인증이 완료되었습니다.";
            redisService.deleteValue(email);
        } else {
            throw new CodeMismatchException();
        }

        return resultMessage;
    }

    /**
     * 랜덤번호 생성
     */
    private String generateRandomNumber() {

        String result;

        try {
            do {
                int num = SecureRandom.getInstanceStrong().nextInt(999999);
                result = String.valueOf(num);
            } while (result.length() != 6);
        } catch (NoSuchAlgorithmException e){
            log.error(e.getMessage());
            throw new IllegalArgumentException("랜덤번호 생성 에러");
        }

        return result;
    }

    /**
     * 메일 내용 템플릿
     */
    private String generateMailBody(String code){

        StringBuilder sb = new StringBuilder();
        sb.append("안녕하세요, 올캔입니다.")
                .append("<br></br>")
                .append("인증코드는 ")
                .append(code)
                .append(" 입니다.")
                .append("<br></br><br></br>")
                .append("감사합니다.");

        return sb.toString();
    }
}
