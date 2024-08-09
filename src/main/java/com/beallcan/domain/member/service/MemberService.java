package com.beallcan.domain.member.service;

import com.beallcan.domain.member.exception.EmailDuplicateException;
import com.beallcan.domain.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(final MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void checkEmailDuplicate(final String email) {

        if (memberRepository.findByEmail(email).isPresent()){
            throw new EmailDuplicateException();
        }
    }

    @Async
    public void sendVerificationCode(final String email) throws NoSuchAlgorithmException {

        String createdCode = generateRandomNumber();
        Duration duration = Duration.ofMinutes(3);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
    }

    private String generateRandomNumber() throws NoSuchAlgorithmException {
        String result;

        do {
            int num = SecureRandom.getInstanceStrong().nextInt(999999);
            result = String.valueOf(num);
        } while (result.length() != 6);

        return result;
    }
}
