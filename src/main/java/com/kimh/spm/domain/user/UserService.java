package com.kimh.spm.domain.user;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.kimh.spm.domain.user.User;
import com.kimh.spm.domain.user.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {
	
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User saveUser(User user) {
        // 암호화된 비밀번호로 설정
        user.setUsPw(passwordEncoder.encode(user.getUsPw()));
        // 역할 및 이미지 URL 설정
        user.setUsRole("USER");

        // 사용자 저장
        return userRepository.save(user);
    }
    
    public int resetPassword(String userid) {
        String sNewPassword = "1234";
        String sEncodeNewPassword = passwordEncoder.encode(sNewPassword);
        return userRepository.updatePasswordByUserid(userid,sEncodeNewPassword);  // 비밀번호 업데이트

    }
}
