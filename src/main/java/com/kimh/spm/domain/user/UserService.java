package com.kimh.spm.domain.user;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {
	
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User saveUser(User user) {
        // 암호화된 비밀번호로 설정
        user.setUsPw(passwordEncoder.encode(user.getUsPw()));
        // 역할 및 이미지 URL 설정
        user.setUsRole("USER");

        // 사용자 저장
        return userRepository.save(user);
    }
    
    @Transactional
    public int resetPassword(String usId) {
        String sNewPassword = "1234";
        String sEncodeNewPassword = passwordEncoder.encode(sNewPassword);
        return userRepository.updatePasswordByUserid(usId,sEncodeNewPassword);  // 비밀번호 업데이트

    }
}
