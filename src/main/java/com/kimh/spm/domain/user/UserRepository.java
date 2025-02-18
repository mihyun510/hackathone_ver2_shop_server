package com.kimh.spm.domain.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.kimh.spm.domain.user.User;

import jakarta.transaction.Transactional;

public interface UserRepository extends CrudRepository<User, String> {
	Optional<User> findByUsername(String username);
	Optional<User> findByUserid(String userid);
	
	@Modifying
    @Transactional
    @Query("UPDATE USER SET us_pw = :newPassword WHERE us_id = :usId")
    int updatePasswordByUserid(@Param("usId") String userid, @Param("newPassword") String newPassword);
}