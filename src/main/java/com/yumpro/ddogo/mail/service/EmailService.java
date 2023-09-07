package com.yumpro.ddogo.mail.service;

import com.yumpro.ddogo.mail.DTO.MailDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
@AllArgsConstructor
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;
    private static final AtomicInteger counter = new AtomicInteger(0);

    public String sendSimpleMessage(String mailAddress) {
        System.out.println("sendSimpleMessage진입");
        SimpleMailMessage message = new SimpleMailMessage();
        MailDTO mailDto = new MailDTO();
        String str = getTempPassword();

        mailDto.setTo("lry9478@gmail.com");
        mailDto.setTitle("[또 갈지도] 임시비밀번호 메일 발송 안내");
        mailDto.setContent("임시비밀번호 안내 [" + str + "] 홈페이지로 돌아가 로그인 후 정보수정을 통해 패스워드를 변경해주세요");
        mailDto.setAddress(mailAddress);

        message.setFrom(mailDto.getTo());
        message.setTo(mailDto.getAddress());
        message.setSubject(mailDto.getTitle());
        message.setText(mailDto.getContent());
        emailSender.send(message);
        return str;
    }

    // 임시 패스워드
    public String getTempPassword() {
        char[] charSet = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

        String str = "";

        int idx = 0;
        for (int i = 0; i < 10; i++) {
            idx = (int) (charSet.length * Math.random());
            str += charSet[idx];
        }
        return "K"+str;
    }

    // 임시 아이디 생성
        public String generateID() {
            int nextValue = counter.incrementAndGet();
            String id = String.format("K%04d", nextValue);
            return id;
        }

    }

